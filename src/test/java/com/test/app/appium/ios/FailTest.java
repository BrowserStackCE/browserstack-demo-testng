package com.test.app.appium.ios;

import com.utils.AppUtils;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.assertNotEquals;

public class FailTest {

    private MobileDriver<MobileElement> driver;

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String HUB_URL = "https://hub-cloud.browserstack.com/wd/hub";

    @BeforeSuite(alwaysRun = true)
    public void setupApp() {
        AppUtils.uploadApp("iOSDemoApp", "ios/BStackSampleApp.ipa");
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(Method m) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("project", "BrowserStack Demo TestNG");
        caps.setCapability("build", "Demo");
        caps.setCapability("name", m.getName() + " - iPhone 11 Pro");

        caps.setCapability("device", "iPhone 11 Pro");
        caps.setCapability("os_version", "13");
        caps.setCapability("app", "iOSDemoApp");

        caps.setCapability("browserstack.user", USERNAME);
        caps.setCapability("browserstack.key", ACCESS_KEY);
        caps.setCapability("browserstack.debug", true);
        caps.setCapability("browserstack.networkLogs", true);

        driver = new IOSDriver<>(new URL(HUB_URL), caps);
    }

    @Test
    public void printText() {
        driver.findElementByAccessibilityId("Text Button").click();
        driver.findElementByAccessibilityId("Text Input").click();
        driver.findElementByAccessibilityId("Text Input").sendKeys("Welcome to BrowserStack" + Keys.ENTER);
        assertNotEquals(driver.findElementByAccessibilityId("Text Output").getText(),
                "Welcome to BrowserStack", "Incorrect text");
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult tr) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String reason = tr.getThrowable().getMessage().split("\\n")[0].replaceAll("[\\\\{}\"]", "");
        js.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"" + reason + "\"}}");
        driver.quit();
    }

}