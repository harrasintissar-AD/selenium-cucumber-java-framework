package stepDefinitions;

import driver.DriverFactory;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.LoginPage;
import utils.ConfigReader;
import utils.TestUserStorage;

public class LoginSteps {

	private LoginPage loginPage = new LoginPage(DriverFactory.getDriver());

	@Given("user is on Parabank login page")
	public void userIsOnLoginPage() {

		DriverFactory.getDriver().get(ConfigReader.getProperty("base.url"));
	}

	@When("user enters valid credentials and clicks login button")
	public void userEntersValidCredentialsAndClickLogingButton() {

		System.out.println("Login Username: " + TestUserStorage.getUsername());
		loginPage.login(TestUserStorage.getUsername(), TestUserStorage.getPassword());
	}

	@And("user clicks login button")
	public void userClicksLoginButton() {

		loginPage.clickLogin();
	}

	@Then("user should be redirected to account overview page")
	public void userShouldBeRedirectedToAccountOverviewPage() {

		Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed");
	}

}