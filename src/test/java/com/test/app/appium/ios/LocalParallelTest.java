package com.test.app.appium.ios;

import com.utils.AppUtils;
import com.utils.LocalUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class LocalParallelTest {

    private static final ThreadLocal<MobileDriver<MobileElement>> driverThread = new ThreadLocal<>();

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String HUB_URL = "https://hub-cloud.browserstack.com/wd/hub";

    @BeforeSuite(alwaysRun = true)
    public void setupApp() {
        AppUtils.uploadApp("iOSLocalApp", "ios/LocalSample.ipa");
        LocalUtils.startLocal();
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"config", "capability"})
    public void setupDriver(String configFile, String capability, Method m) throws MalformedURLException {
        JsonPath jsonPath = JsonPath.from(new File("src/test/resources/app/config/" + configFile + ".json"));
        Map<String, String> capabilitiesMap = new HashMap<>();
        capabilitiesMap.putAll(jsonPath.getMap("commonCapabilities"));
        capabilitiesMap.putAll(jsonPath.getMap("capabilities[" + capability + "]"));
        capabilitiesMap.put("name", m.getName() + " - " + capabilitiesMap.get("device"));
        capabilitiesMap.put("app", "iOSLocalApp");
        capabilitiesMap.put("browserstack.user", USERNAME);
        capabilitiesMap.put("browserstack.key", ACCESS_KEY);
        capabilitiesMap.put("browserstack.local", "true");
        driverThread.set(new IOSDriver<>(new URL(HUB_URL), new DesiredCapabilities(capabilitiesMap)));
    }

    @Test
    public void testLocalConnection() {
        MobileDriver<MobileElement> driver = driverThread.get();
        Wait<MobileDriver<MobileElement>> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NotFoundException.class);
        driver.findElementByAccessibilityId("TestBrowserStackLocal").click();
        By result = MobileBy.AccessibilityId("ResultBrowserStackLocal");
        wait.until(d -> d.findElement(result).getText().contains("Response is:"));
        String resultText = driver.findElement(result).getText();
        assertEquals(resultText, "Response is: Up and running", "Local connection is not up");
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        JavascriptExecutor js = (JavascriptExecutor) driverThread.get();
        js.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\"}}");
        driverThread.get().quit();
        driverThread.remove();
    }

    @AfterSuite(alwaysRun = true)
    public void closeLocal() throws Exception {
        LocalUtils.stopLocal();
    }

}
