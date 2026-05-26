package runners;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import driver.DriverFactory;
import pages.LoginPage;

public class LoginTest {

    LoginPage loginPage;

    @BeforeMethod
    public void setup() {

        DriverFactory.initializeDriver();

        DriverFactory.getDriver()
                .get("https://www.saucedemo.com/");

        loginPage = new LoginPage(
                DriverFactory.getDriver());
    }

    @Test(enabled = true)
    public void successfulLoginTest() {

        loginPage.login(
                "standard_user",
                "secret_sauce");

        String currentUrl =
                DriverFactory.getDriver()
                        .getCurrentUrl();

        Assert.assertTrue(
                currentUrl.contains("inventory"),
                "Login failed!");
    }
    
    @Test
    public void invalidLoginTest() {

        loginPage.login(
                "wrong_user",
                "wrong_password");

        String actualError =
                loginPage.getErrorMessage();

        Assert.assertTrue(
                actualError.contains("Username and password do not match any user in this service"),
                "Error message validation failed!");
    }
    

//    @AfterMethod
//    public void tearDown() {
//        DriverFactory.quitDriver();
//    }
}