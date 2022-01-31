package com.test.web.desktop;

import com.browserstack.local.Local;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class LocalParallelTest {

    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();
    private final Map<String, String> capabilitiesMap = new HashMap<>();
    private Local local;

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String URL = "http://hub-cloud.browserstack.com/wd/hub";

    @BeforeSuite(alwaysRun = true)
    public void before() throws Exception {
        local = new Local();
        Map<String, String> bsLocalArgs = new HashMap<>();
        bsLocalArgs.put("key", ACCESS_KEY);
        local.start(bsLocalArgs);
        System.out.println("Local testing connection established...");
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"config", "capability"})
    public void setup(String configFile, String capability, Method m) throws MalformedURLException {
        JsonPath jsonPath = JsonPath.from(new File("src/test/resources/web/config/" + configFile + ".json"));
        capabilitiesMap.putAll(jsonPath.getMap("commonCapabilities"));
        capabilitiesMap.putAll(jsonPath.getMap("capabilities[" + capability + "]"));
        if (capabilitiesMap.get("device") == null) {
            capabilitiesMap.put("name", m.getName() + " - " + capabilitiesMap.get("browser") + " " + capabilitiesMap.get("browser_version"));
        } else {
            capabilitiesMap.put("name", m.getName() + " - " + capabilitiesMap.get("device"));
        }
        capabilitiesMap.put("browserstack.user", USERNAME);
        capabilitiesMap.put("browserstack.key", ACCESS_KEY);
        capabilitiesMap.put("browserstack.local", "true");
        driverThread.set(new RemoteWebDriver(new URL(URL), new DesiredCapabilities(capabilitiesMap)));
    }

    @Test
    public void openLocalWebPage() {
        WebDriver driver = driverThread.get();
        if (capabilitiesMap.getOrDefault("device", "none").contains("iPhone")) {
            driver.get("http://bs-local.com:8000");
        } else {
            driver.get("http://localhost:8000");
        }
        assertEquals(driver.getTitle(), "Local Server", "Incorrect title");
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        JavascriptExecutor js = (JavascriptExecutor) driverThread.get();
        js.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\"}}");
        driverThread.get().quit();
        driverThread.remove();
    }

    @AfterSuite(alwaysRun = true)
    public void closeLocal() throws Exception {
        local.stop();
        System.out.println("Local testing connection terminated...");
    }

}
