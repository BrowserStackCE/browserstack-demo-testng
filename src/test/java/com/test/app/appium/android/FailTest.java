package com.test.app.appium.android;

import com.utils.AppUtils;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NotFoundException;
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

    private MobileDriver<MobileElement> driver;

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String HUB_URL = "https://hub.browserstack.com/wd/hub";

    @BeforeSuite(alwaysRun = true)
    public void setupApp() {
        AppUtils.uploadApp("AndroidDemoApp", "android/WikipediaSample.apk");
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(Method m) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("project", "BrowserStack Demo TestNG");
        caps.setCapability("build", "Demo");
        caps.setCapability("name", m.getName());

        caps.setCapability("device", "Google Pixel 7");
        caps.setCapability("os_version", "13.0");
        caps.setCapability("app", "AndroidDemoApp");

        caps.setCapability("browserstack.user", USERNAME);
        caps.setCapability("browserstack.key", ACCESS_KEY);
        caps.setCapability("browserstack.debug", true);
        caps.setCapability("browserstack.networkLogs", true);

        driver = new AndroidDriver<>(new URL(HUB_URL), caps);
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
        assertFalse(companyNames.contains("BrowserStack"), "Company is present in the list");
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestResult tr) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String reason = tr.getThrowable().getMessage().split("\\n")[0].replaceAll("[\\\\{}\"]", "");
        js.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \"" + reason + "\"}}");
        driver.quit();
    }

}