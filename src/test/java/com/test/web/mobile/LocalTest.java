package com.test.web.mobile;

import com.browserstack.local.Local;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class LocalTest {

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String HUB_URL = "https://hub-cloud.browserstack.com/wd/hub";
    private WebDriver driver;
    private Local local;

    @BeforeSuite(alwaysRun = true)
    public void setupLocal() throws Exception {
        local = new Local();
        Map<String, String> bsLocalArgs = new HashMap<>();
        bsLocalArgs.put("key", ACCESS_KEY);
        local.start(bsLocalArgs);
        System.out.println("Local testing connection established...");
    }

    @BeforeMethod(alwaysRun = true)
    public void setupDriver(Method m) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("project", "BrowserStack Demo TestNG");
        caps.setCapability("build", System.getenv("BROWSERSTACK_BUILD_NAME"));
        caps.setCapability("name", m.getName() + " - Google Pixel 5");

        caps.setCapability("device", "Google Pixel 5");
        caps.setCapability("os_version", "11.0");

        caps.setCapability("browserstack.user", USERNAME);
        caps.setCapability("browserstack.key", ACCESS_KEY);
        caps.setCapability("browserstack.debug", true);
        caps.setCapability("browserstack.networkLogs", true);
        caps.setCapability("browserstack.local", true);

        driver = new RemoteWebDriver(new URL(HUB_URL), caps);
    }

    @Test
    public void openLocalWebPage() {
        driver.get("http://bs-local.com:45691/check");
        assertEquals(driver.findElement(By.tagName("body")).getText(), "Up and running", "Local connection not found");
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\"}}");
        driver.quit();
    }

    @AfterSuite(alwaysRun = true)
    public void closeLocal() throws Exception {
        local.stop();
        System.out.println("Local testing connection terminated...");
    }

}
