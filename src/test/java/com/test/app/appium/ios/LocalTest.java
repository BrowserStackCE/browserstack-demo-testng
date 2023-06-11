package com.test.app.appium.ios;

import com.utils.AppUtils;
import com.utils.LocalUtils;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class LocalTest {

    private MobileDriver<MobileElement> driver;

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String HUB_URL = "https://hub.browserstack.com/wd/hub";

    @BeforeSuite(alwaysRun = true)
    public void setupAppAndLocal() {
        AppUtils.uploadApp("iOSLocalApp", "ios/LocalSample.ipa");
        LocalUtils.startLocal();
    }

    @BeforeMethod(alwaysRun = true)
    public void setupDriver(Method m) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("project", "BrowserStack Demo TestNG");
        caps.setCapability("build", "Demo");
        caps.setCapability("name", m.getName());

        caps.setCapability("device", "iPhone 14");
        caps.setCapability("os_version", "16");
        caps.setCapability("app", "iOSLocalApp");

        caps.setCapability("browserstack.user", USERNAME);
        caps.setCapability("browserstack.key", ACCESS_KEY);
        caps.setCapability("browserstack.debug", true);
        caps.setCapability("browserstack.networkLogs", true);
        caps.setCapability("browserstack.local", true);

        driver = new IOSDriver<>(new URL(HUB_URL), caps);
    }

    @Test
    public void testLocalConnection() {
        Wait<MobileDriver<MobileElement>> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NotFoundException.class);
        driver.findElementByAccessibilityId("TestBrowserStackLocal").click();
        By result = MobileBy.AccessibilityId("ResultBrowserStackLocal");
        wait.until(d -> d.findElement(result).getText().contains("Up and running"));
        String resultText = driver.findElement(result).getText();
        assertEquals(resultText, "Up and running", "Local connection is not up");
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\"}}");
        driver.quit();
    }

    @AfterSuite(alwaysRun = true)
    public void closeLocal() {
        LocalUtils.stopLocal();
    }

}