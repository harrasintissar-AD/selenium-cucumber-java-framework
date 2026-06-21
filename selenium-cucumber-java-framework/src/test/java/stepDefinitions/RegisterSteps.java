package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import driver.DriverFactory;
import pages.LoginPage;
import pages.RegisterPage;
import utils.HighlightUtil;
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

	@And("user clicks on register button")
	public void click_register_button() {

		registerPage.registerBtn();
	}

	@And("user should see the accounts overview page")
	public void verify_login() {

		Assert.assertTrue(loginPage.isLoginSuccessful());
	}

	@Then("user should see registration successful message")
	public void user_should_see_registration_successful_message() {

		String expectedMessage = "Your account was created successfully. You are now logged in.";

		HighlightUtil.highlightElement(DriverFactory.getDriver(), registerPage.getRegistrationSuccessMessageElement());

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

	@Then("the registration validation message {string} should be displayed")
	public void verifyValidationMessage(String expectedMessage) {

		HighlightUtil.highlightElement(DriverFactory.getDriver(), registerPage.getValidationMessageElement(expectedMessage));
		Assert.assertEquals(registerPage.getValidationMessage(expectedMessage), expectedMessage);
	}

	@When("the customer registers with invalid data:")
	public void customerRegistersWithInvalidData(DataTable dataTable) {

		Map<String, String> data =
				dataTable.asMap(String.class, String.class);

		registerPage.openRegister();
		registerPage.registerWithData(
				cleanValue(data.get("firstName")),
				cleanValue(data.get("lastName")),
				cleanValue(data.get("address")),
				cleanValue(data.get("city")),
				cleanValue(data.get("state")),
				cleanValue(data.get("zipCode")),
				cleanValue(data.get("ssn")),
				cleanValue(data.get("username")),
				cleanValue(data.get("password")),
				cleanValue(data.get("confirmPassword")));
	}

	private String cleanValue(String value) {
		if (value == null || value.equalsIgnoreCase("[empty]")) {
			return "";
		}
		return value;
	}
}