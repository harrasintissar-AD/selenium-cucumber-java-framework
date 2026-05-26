package runners;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import driver.DriverFactory;

public class SmokeTest {

    @BeforeMethod
    public void setup() {
        DriverFactory.initializeDriver();
    }

    @Test
    public void launchWebsite() {

        DriverFactory.getDriver()
                .get("https://www.saucedemo.com/");
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}