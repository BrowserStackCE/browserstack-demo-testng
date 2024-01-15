package com.test.app.appium.ios;

import com.utils.AppUtils;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
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

    @BeforeSuite(alwaysRun = true)
    public void setupApp() {
        AppUtils.uploadApp("iOSDemoApp", "ios/BStackSampleApp.ipa");
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(Method m) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        driver = new IOSDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
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
        driver.quit();
    }

}