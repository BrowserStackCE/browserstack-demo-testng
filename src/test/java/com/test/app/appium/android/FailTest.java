package com.test.app.appium.android;

import com.utils.AppUtils;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.ITestResult;
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
import static org.testng.Assert.assertFalse;

public class FailTest {

    private AndroidDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void setupApp() {
        AppUtils.uploadApp("AndroidDemoApp", "android/WikipediaSample.apk");
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(Method m) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
    }

    @Test
    public void searchWikipedia() {
        Wait<AndroidDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .ignoring(NotFoundException.class);
        driver.findElement(AppiumBy.accessibilityId("Search Wikipedia")).click();
        WebElement insertTextElement = wait.until(d -> d.findElement(AppiumBy.id("org.wikipedia.alpha:id/search_src_text")));
        insertTextElement.sendKeys("BrowserStack");
        wait.until(d -> d.findElement(AppiumBy.className("android.widget.ListView")));
        List<String> companyNames = driver.findElements(AppiumBy.className("android.widget.TextView"))
                .stream().map(WebElement::getText).collect(toList());
        assertFalse(companyNames.contains("BrowserStack"), "Company is present in the list");

//        Wait<MobileDriver<MobileElement>> wait = new FluentWait<>(driver)
//                .withTimeout(Duration.ofSeconds(10))
//                .ignoring(NotFoundException.class);
//        driver.findElementByAccessibilityId("Search Wikipedia").click();
//        MobileElement insertTextElement = wait.until(d -> d.findElementById("org.wikipedia.alpha:id/search_src_text"));
//        insertTextElement.sendKeys("BrowserStack");
//        wait.until(d -> d.findElementByClassName("android.widget.ListView").isDisplayed());
//        List<String> companyNames = driver.findElementsByClassName("android.widget.TextView")
//                .stream().map(MobileElement::getText).collect(toList());
//        assertFalse(companyNames.contains("BrowserStack"), "Company is present in the list");
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult tr) {
        driver.quit();
    }

}