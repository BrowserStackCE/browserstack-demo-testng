package com.test.app.appium.ios;

import com.utils.AppUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.assertEquals;

public class SingleTest {

    private IOSDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void setupApp() {
        AppUtils.uploadApp("iOSDemoApp", "ios/BStackSampleApp.ipa");
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(Method m) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
    }

    @Test
    public void printText() {
        driver.findElement(AppiumBy.accessibilityId("Text Button")).click();
        driver.findElement(AppiumBy.accessibilityId("Text Input")).click();
        driver.findElement(AppiumBy.accessibilityId("Text Input")).sendKeys("Welcome to BrowserStack" + Keys.ENTER);
        assertEquals(driver.findElement(AppiumBy.accessibilityId("Text Output")).getText(),
                "Welcome to BrowserStack", "Incorrect text");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}