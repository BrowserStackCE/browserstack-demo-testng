package com.app.test;

import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.*;

public class EspressoTest {

    String appUrl, testSuiteUrl;

    @BeforeSuite
    public void setup() {
        PreemptiveBasicAuthScheme authenticationScheme = new PreemptiveBasicAuthScheme();
        authenticationScheme.setUserName(System.getenv("BROWSERSTACK_USERNAME"));
        authenticationScheme.setPassword(System.getenv("BROWSERSTACK_ACCESS_KEY"));
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://api-cloud.browserstack.com")
                .setBasePath("app-automate/espresso/v2")
                .setAuth(authenticationScheme)
                .build();
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    @BeforeTest
    public void uploadAppAndTestSuite() {
        System.out.println("Uploading app ...");
        appUrl = given()
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("src/test/resources/app/appdata/Espresso-App.apk"), "text/apk")
                .param("custom_id", "EspressoApp")
                .post("app")
                .jsonPath()
                .get("app_url");
        System.out.println("Uploading test suite ...");
        testSuiteUrl = given()
                .header("Content-Type", "multipart/form-data")
                .multiPart("file", new File("src/test/resources/app/appdata/Espresso-AppTest.apk"), "text/apk")
                .param("custom_id", "EspressoAppTest")
                .post("test-suite")
                .jsonPath()
                .get("test_suite_url");
    }

    @Test
    public void espressoTest() {
        System.out.println("Executing test suite ...");
        String body = JsonPath.given(new File("src/test/resources/app/config/espresso.json"))
                .prettyPrint()
                .replaceFirst("appUrl", appUrl)
                .replaceFirst("testSuiteUrl", testSuiteUrl);
        given()
                .header("Content-Type", "application/json")
                .body(body)
                .post("build");
    }

}
