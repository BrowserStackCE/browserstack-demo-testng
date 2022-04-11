package com.test.app.appium.android;

import com.utils.AppUtils;
import com.utils.LocalUtils;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static org.testng.Assert.assertTrue;

public class LocalParallelTest {

    private static final ThreadLocal<MobileDriver<MobileElement>> driverThread = new ThreadLocal<>();

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String HUB_URL = "https://hub-cloud.browserstack.com/wd/hub";

    @BeforeSuite(alwaysRun = true)
    public void setupApp() {
        AppUtils.uploadApp("AndroidLocalApp", "android/LocalSample.apk");
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
        capabilitiesMap.put("app", "AndroidLocalApp");
        capabilitiesMap.put("browserstack.user", USERNAME);
        capabilitiesMap.put("browserstack.key", ACCESS_KEY);
        capabilitiesMap.put("browserstack.local", "true");
        driverThread.set(new AndroidDriver<>(new URL(HUB_URL), new DesiredCapabilities(capabilitiesMap)));
    }

    @Test
    public void testLocalConnection() {
        MobileDriver<MobileElement> driver = driverThread.get();
        driver.findElementById("com.example.android.basicnetworking:id/test_action").click();
        String allText = driver.findElementsByClassName("android.widget.TextView")
                .stream().map(MobileElement::getText).collect(joining());
        assertTrue(allText.contains("The active connection is wifi."), "Text is not present");
        assertTrue(allText.contains("Response is: Up and running"), "Text is not present");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        JavascriptExecutor js = (JavascriptExecutor) driverThread.get();
        js.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\"}}");
        driverThread.get().quit();
        driverThread.remove();
    }

    @AfterSuite(alwaysRun = true)
    public void closeLocal() {
        LocalUtils.stopLocal();
    }

}
