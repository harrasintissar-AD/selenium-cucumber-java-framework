package stepDefinitions;

import driver.DriverFactory;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.LoginPage;
import pages.RegisterPage;
import utils.ConfigReader;
import utils.HighlightUtil;
import utils.TestUserStorage;

public class LoginSteps {

    private LoginPage loginPage;
    private RegisterPage registerPage;

    public String username;
    public String password;

    @Given("user is on Parabank login page")
    public void userIsOnLoginPage() {

        DriverFactory.getDriver().get(ConfigReader.getProperty("base.url"));
        loginPage = new LoginPage(DriverFactory.getDriver());
        registerPage = new RegisterPage(DriverFactory.getDriver());
    }

    @When("user enters valid credentials")
    public void userEntersValidCredentials() {

        registerPage.openRegister();
        username = "user" + new java.util.Random().nextInt(900);
        password = "Pass123!";
        registerPage.register(username, password);
        System.out.println("Login Username: " + username);
        System.out.println("Login Password: " + password);
        registerPage.registerBtn();
        registerPage.clickLogout();
        loginPage.login(username, password);
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