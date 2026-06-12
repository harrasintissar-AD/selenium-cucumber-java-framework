package runners;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import driver.DriverFactory;
import pages.LoginPage;
import utils.ConfigReader;

public class LoginTest {

	LoginPage loginPage;

	@BeforeMethod
	public void setup() {

		DriverFactory.initializeDriver();

		DriverFactory.getDriver().get(ConfigReader.getProperty("base.url"));

		loginPage = new LoginPage(DriverFactory.getDriver());
	}

	@Test(enabled = true)
	public void successfulLoginTest() {

		loginPage.login(ConfigReader.getProperty("username"),

				ConfigReader.getProperty("password"));

		Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed!");
	}

	@Test
	public void invalidLoginTest() {

		loginPage.login(ConfigReader.getProperty("invalid.username"),

				ConfigReader.getProperty("invalid.password"));

		String actualError = loginPage.getErrorMessage();

		Assert.assertTrue(actualError.contains("The username and password could not be verified."),
				"Error message validation failed!");
	}

//    @AfterMethod
//    public void tearDown() {
//        DriverFactory.quitDriver();
//    }
}