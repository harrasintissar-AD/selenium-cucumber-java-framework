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


//    @AfterMethod
//    public void tearDown() {
//        DriverFactory.quitDriver();
//    }
}