package com.test.app.appium.android;

import com.browserstack.local.Local;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.*;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static java.util.stream.Collectors.joining;
import static org.testng.Assert.assertTrue;

public class LocalParallelTest {

    private static final ThreadLocal<MobileDriver<MobileElement>> driverThread = new ThreadLocal<>();
    private Local local;

    private static final String USERNAME = System.getenv("BROWSERSTACK_USERNAME");
    private static final String ACCESS_KEY = System.getenv("BROWSERSTACK_ACCESS_KEY");
    private static final String HUB_URL = "https://hub-cloud.browserstack.com/wd/hub";

    @BeforeSuite(alwaysRun = true)
    public void setupApp() throws Exception {
        PreemptiveBasicAuthScheme authenticationScheme = new PreemptiveBasicAuthScheme();
        authenticationScheme.setUserName(USERNAME);
        authenticationScheme.setPassword(ACCESS_KEY);
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://api-cloud.browserstack.com")
                .setBasePath("app-automate")
                .setAuth(authenticationScheme)
                .build();
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
        List<String> customIds = get("recent_apps").jsonPath().getList("custom_id");
        if (customIds == null || !customIds.contains("AndroidLocalApp")) {
            System.out.println("Uploading app...");
            given()
                    .header("Content-Type", "multipart/form-data")
                    .multiPart("url", "https://www.browserstack.com/app-automate/sample-apps/android/LocalSample.apk", "text")
                    .param("custom_id", "AndroidLocalApp")
                    .post("upload");
        } else {
            System.out.println("Using previously uploaded app...");
        }
        local = new Local();
        Map<String, String> options = new HashMap<>();
        options.put("key", ACCESS_KEY);
        local.start(options);
        System.out.println("Local testing connection established...");
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"config", "capability"})
    public void setupDriver(String configFile, String capability, Method m) throws MalformedURLException {
        JsonPath jsonPath = JsonPath.from(new File("src/test/resources/app/config/" + configFile + ".json"));
        Map<String, String> capabilitiesMap = new HashMap<>();
        capabilitiesMap.putAll(jsonPath.getMap("commonCapabilities"));
        capabilitiesMap.putAll(jsonPath.getMap("capabilities[" + capability + "]"));
        capabilitiesMap.put("build", System.getenv("BROWSERSTACK_BUILD_NAME"));
        capabilitiesMap.put("name", m.getName() + " - " + capabilitiesMap.get("device"));
        capabilitiesMap.put("app", "AndroidLocalApp");
        capabilitiesMap.put("browserstack.user", USERNAME);
        capabilitiesMap.put("browserstack.key", ACCESS_KEY);
        capabilitiesMap.put("browserstack.local", "true");
        driverThread.set(new AndroidDriver<>(new URL(HUB_URL), new DesiredCapabilities(capabilitiesMap)));
    }

    @Test
    public void testLocalConnection() {
        MobileDriver<MobileElement> driver = driverThread.get();
        driver.findElementById("com.example.android.basicnetworking:id/test_action").click();
        String allText = driver.findElementsByClassName("android.widget.TextView")
                .stream().map(MobileElement::getText).collect(joining());
        assertTrue(allText.contains("The active connection is wifi."), "Text is not present");
        assertTrue(allText.contains("Response is: Up and running"), "Text is not present");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
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
