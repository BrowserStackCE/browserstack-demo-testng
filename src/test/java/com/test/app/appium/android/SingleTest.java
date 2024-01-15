package com.test.app.appium.android;

import com.utils.AppUtils;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
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
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.testng.Assert.assertTrue;

public class SingleTest {

    private MobileDriver<MobileElement> driver;

    @BeforeSuite(alwaysRun = true)
    public void setupApp() {
        AppUtils.uploadApp("AndroidDemoApp", "android/WikipediaSample.apk");
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(Method m) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
    }

    @Test
    public void searchWikipedia() {
        Wait<MobileDriver<MobileElement>> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .ignoring(NotFoundException.class);
        driver.findElementByAccessibilityId("Search Wikipedia").click();
        MobileElement insertTextElement = wait.until(d -> d.findElementById("org.wikipedia.alpha:id/search_src_text"));
        insertTextElement.sendKeys("BrowserStack");
        wait.until(d -> d.findElementByClassName("android.widget.ListView").isDisplayed());
        List<String> companyNames = driver.findElementsByClassName("android.widget.TextView")
                .stream().map(MobileElement::getText).collect(toList());
        assertTrue(companyNames.contains("BrowserStack"), "Company is not present in the list");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}