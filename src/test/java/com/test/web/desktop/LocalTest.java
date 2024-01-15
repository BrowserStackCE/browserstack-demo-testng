package com.test.web.desktop;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import static org.testng.Assert.assertEquals;

public class LocalTest {

    private WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setupDriver(Method m) throws MalformedURLException {
        DesiredCapabilities caps = new DesiredCapabilities();
        driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), caps);
    }

    @Test
    public void openLocalWebPage() {
        driver.get("http://localhost:8000");
        assertEquals(driver.getTitle(), "Local Server", "Incorrect title");
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        driver.quit();
    }

}
