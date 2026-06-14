package stepDefinitions;

import io.cucumber.java.en.*;

import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import driver.DriverFactory;
import pages.LoginPage;
import pages.RegisterPage;
import utils.ConfigReader;
import utils.TestUserStorage;

public class RegisterSteps {

	WebDriver driver;
	RegisterPage registerPage;
	LoginPage loginPage;

	public String username;
	public String password;

	@Given("user opens ParaBank application")
	public void open_app() {

		driver = DriverFactory.getDriver();

		registerPage = new RegisterPage(driver);

		loginPage = new LoginPage(driver);

		Random random = new Random();

		username = "user" + random.nextInt(900);
		password = "Pass123!";

		TestUserStorage.setUsername(username);
		TestUserStorage.setPassword(password);

		System.out.println("Register Username: " + TestUserStorage.getUsername());
	}

	@When("user registers with valid details")
	public void register_user() {

		registerPage.openRegister();
		registerPage.register(username, password);
	}

	@And("user should see the accounts overview page")
	public void verify_login() {

		Assert.assertTrue(loginPage.isLoginSuccessful());
	}

	@Then("user should see registration successful message")
	public void user_should_see_registration_successful_message() {

		String expectedMessage = "Your account was created successfully. You are now logged in.";

		String actualMessage = registerPage.getRegistrationSuccessMessage();

		Assert.assertEquals(actualMessage, expectedMessage, "Registration success message is incorrect");
	}

	@Then("user should be logged out successfully")
	public void user_should_be_logged_out_successfully() {
		DriverFactory.initializeDriver();
		driver = DriverFactory.getDriver();

		loginPage = new LoginPage(driver);
		loginPage.clickLogout();
		System.out.println("User logged out successfully");

		Assert.assertTrue(loginPage.isAtLoginPage());
	}
}