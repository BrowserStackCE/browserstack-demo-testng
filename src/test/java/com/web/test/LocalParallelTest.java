package com.web.test;

import com.browserstack.local.Local;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LocalParallelTest {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private Local local;

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String URL = "http://hub-cloud.browserstack.com/wd/hub";

    @BeforeSuite(alwaysRun = true)
    public void before() throws Exception {
        System.out.println("Connecting local");
        local = new Local();
        Map<String, String> bsLocalArgs = new HashMap<>();
        bsLocalArgs.put("key", ACCESS_KEY);
        local.start(bsLocalArgs);
        System.out.println("Connected. Now testing...");
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"config", "environment"})
    public void setup(String configFile, String environment, Method m) throws MalformedURLException {
        JsonPath jsonPath = JsonPath.from(new File("src/test/resources/web/config/" + configFile + ".json"));
        Map<String, String> basicCapabilities = jsonPath.getMap("capabilities");
        Map<String, String> browserCapabilities = jsonPath.getMap("environments." + environment);
        Map<String, String> capabilitiesMap = new HashMap<>();
        capabilitiesMap.putAll(basicCapabilities);
        capabilitiesMap.putAll(browserCapabilities);
        capabilitiesMap.put("name", m.getName() + " - " + browserCapabilities.get("browser") + " " + browserCapabilities.get("browser_version"));
        capabilitiesMap.put("browserstack.local", "true");
        capabilitiesMap.put("browserstack.user", USERNAME);
        capabilitiesMap.put("browserstack.key", ACCESS_KEY);
        driverThread.set(new RemoteWebDriver(new URL(URL), new DesiredCapabilities(capabilitiesMap)));

    }

    @Test
    public void openLocalWebPage() {
        WebDriver driver = driverThread.get();
        driver.get("http://localhost:8000");
        Assert.assertEquals(driver.getTitle(), "Local Server", "Incorrect title");
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(Method m) {
        JavascriptExecutor js = (JavascriptExecutor) driverThread.get();
        js.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\"}}");
        driverThread.get().quit();
        driverThread.remove();
    }

    @AfterSuite(alwaysRun = true)
    public void after() throws Exception {
        local.stop();
        System.out.println("Binary stopped");
    }

}
