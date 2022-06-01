package com.test.app.appium.android;

import com.utils.AppUtils;
import com.utils.LocalUtils;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import static java.util.stream.Collectors.joining;
import static org.testng.Assert.assertTrue;

public class LocalTest {

    private MobileDriver<MobileElement> driver;

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String HUB_URL = "https://hub-cloud.browserstack.com/wd/hub";

    @BeforeSuite(alwaysRun = true)
    public void setupAppAndLocal() {
        AppUtils.uploadApp("AndroidLocalApp", "android/LocalSample.apk");
        LocalUtils.startLocal();
    }

    @BeforeMethod(alwaysRun = true)
    public void setup(Method m) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("project", "BrowserStack Demo TestNG");
        caps.setCapability("build", "Demo");
        caps.setCapability("name", m.getName() + " - Google Pixel 3");

        caps.setCapability("device", "Google Pixel 3");
        caps.setCapability("os_version", "9.0");
        caps.setCapability("app", "AndroidLocalApp");

        caps.setCapability("browserstack.user", USERNAME);
        caps.setCapability("browserstack.key", ACCESS_KEY);
        caps.setCapability("browserstack.debug", true);
        caps.setCapability("browserstack.networkLogs", true);
        caps.setCapability("browserstack.local", true);

        driver = new AndroidDriver<>(new URL(HUB_URL), caps);
    }

    @Test
    public void testLocalConnection() {
        driver.findElementById("com.example.android.basicnetworking:id/test_action").click();
        String allText = driver.findElementsByClassName("android.widget.TextView")
                .stream().map(MobileElement::getText).collect(joining());
        assertTrue(allText.contains("The active connection is wifi."), "Text is not present");
        assertTrue(allText.contains("Response is: Up and running"), "Text is not present");
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