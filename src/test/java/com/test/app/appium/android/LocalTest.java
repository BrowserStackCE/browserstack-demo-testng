package com.test.app.appium.android;

import com.utils.AppUtils;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import static java.util.stream.Collectors.joining;
import static org.testng.Assert.assertTrue;

public class LocalTest {

    private MobileDriver<MobileElement> driver;

    @BeforeSuite(alwaysRun = true)
    public void setupAppAndLocal() {
        AppUtils.uploadApp("AndroidLocalApp", "android/LocalSample.apk");
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(Method m) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
    }

    @Test
    public void testLocalConnection() {
        driver.findElementById("com.example.android.basicnetworking:id/test_action").click();
        String allText = driver.findElementsByClassName("android.widget.TextView")
                .stream().map(MobileElement::getText).collect(joining());
        assertTrue(allText.contains("The active connection is wifi"), "Text is not present");
        assertTrue(allText.contains("Up and running"), "Text is not present");
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        driver.quit();
    }

}