package com.test.app.xcui;

import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

public class XCUITest {

    @BeforeSuite
    public void setup() {
        PreemptiveBasicAuthScheme authenticationScheme = new PreemptiveBasicAuthScheme();
        authenticationScheme.setUserName(System.getenv("BROWSERSTACK_USERNAME"));
        authenticationScheme.setPassword(System.getenv("BROWSERSTACK_ACCESS_KEY"));
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://api-cloud.browserstack.com")
                .setBasePath("app-automate/xcuitest/v2")
                .setAuth(authenticationScheme)
                .build();
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    @BeforeTest
    public void uploadAppAndTestSuite() {
        List<String> customIds = get("apps").jsonPath().getList("apps.custom_id");
        if (customIds.isEmpty() || !customIds.contains("SampleApp")) {
            System.out.println("Uploading app ...");
            given()
                    .header("Content-Type", "multipart/form-data")
                    .multiPart("url", "https://www.browserstack.com/app-automate/sample-apps/ios/BrowserStack-SampleApp.ipa", "text")
                    .param("custom_id", "SampleApp")
                    .post("app");
        } else {
            System.out.println("Using previously uploaded app...");
        }
        customIds = get("test-suites").jsonPath().getList("test_suites.custom_id");
        if (customIds.isEmpty() || !customIds.contains("SampleTest")) {
            System.out.println("Uploading test suite ...");
            given()
                    .header("Content-Type", "multipart/form-data")
                    .multiPart("url", "https://www.browserstack.com/app-automate/sample-apps/ios/BrowserStack-SampleXCUITest.zip", "text")
                    .param("custom_id", "SampleTest")
                    .post("test-suite");
        } else {
            System.out.println("Using previously uploaded test suite...");
        }
    }

    @Test
    public void xcuiTest() {
        System.out.println("Executing test suite...");
        String message = given()
                .header("Content-Type", "application/json")
                .body(new File("src/test/resources/app/config/xcuitest.json"))
                .post("build")
                .jsonPath()
                .get("message");
        assertEquals(message, "Success", "Build did not start");
    }

}
