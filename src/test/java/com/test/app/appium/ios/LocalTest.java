package com.test.app.appium.ios;

import com.utils.AppUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.testng.Assert.assertEquals;

public class LocalTest {

    private IOSDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void setupAppAndLocal() {
        AppUtils.uploadApp("iOSLocalApp", "ios/LocalSample.ipa");
    }

    @BeforeMethod(alwaysRun = true)
    public void setupDriver(Method m) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
    }

    @Test
    public void testLocalConnection() {
        Wait<IOSDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(NotFoundException.class);
        driver.findElement(AppiumBy.accessibilityId("TestBrowserStackLocal")).click();
        By result = AppiumBy.accessibilityId("ResultBrowserStackLocal");
        wait.until(d -> d.findElement(result).getText().contains("Up and running"));
        String resultText = driver.findElement(result).getText();
        assertEquals(resultText, "Up and running", "Local connection is not up");
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        driver.quit();
    }

}