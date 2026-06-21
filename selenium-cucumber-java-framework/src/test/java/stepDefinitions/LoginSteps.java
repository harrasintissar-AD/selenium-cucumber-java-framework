package stepDefinitions;

import driver.DriverFactory;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.LoginPage;
import utils.ConfigReader;
import utils.HighlightUtil;
import utils.TestUserStorage;

public class LoginSteps {

    private LoginPage loginPage = new LoginPage(DriverFactory.getDriver());

    @Given("user is on Parabank login page")
    public void userIsOnLoginPage() {

        DriverFactory.getDriver().get(ConfigReader.getProperty("base.url"));
    }

    @When("user enters valid credentials")
    public void userEntersValidCredentials() {

        System.out.println("Login Username: " + TestUserStorage.getUsername());
        loginPage.login(TestUserStorage.getUsername(), TestUserStorage.getPassword());
    }

    @And("user clicks on login button")
    public void userClicksOnLoginButton() {

        loginPage.clickLogin();
    }

    @Then("user should be redirected to account overview page")
    public void userShouldBeRedirectedToAccountOverviewPage() {

        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login failed");
        HighlightUtil.highlightElement(DriverFactory.getDriver(), loginPage.getOverviewElement());
    }

    @When("the customer enters username {string} and password {string}")
    public void enterCredentials(String username, String password) {

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
    }

    @Then("a login error message should be displayed")
    public void verifyLoginErrorMessage() {

        HighlightUtil.highlightElement(DriverFactory.getDriver(), loginPage.getErrorLoggingElement());

        Assert.assertTrue(
                loginPage.isLoginErrorDisplayed(),
                "The username and password could not be verified.");
    }

}