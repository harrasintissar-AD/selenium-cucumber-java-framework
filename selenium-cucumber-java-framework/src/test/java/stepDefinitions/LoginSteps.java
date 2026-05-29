package stepDefinitions;

import org.testng.Assert;

import driver.DriverFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;

public class LoginSteps {

    LoginPage loginPage;

    @Given("user is on SauceDemo login page")
    public void user_is_on_sauce_demo_login_page() {


        DriverFactory.getDriver()
                .get("https://www.saucedemo.com/");

        loginPage = new LoginPage(
                DriverFactory.getDriver());
    }

    @When("user enters valid username and password")
    public void user_enters_valid_username_and_password() {

        loginPage.login(
                "standard_user",
                "secret_sauce");
    }

    @Then("user should be redirected to inventory page")
    public void user_should_be_redirected_to_inventory_page() {

        String currentUrl =
                DriverFactory.getDriver()
                        .getCurrentUrl();

        WebDriverWait wait = new WebDriverWait(DriverFactory.getDriver(), Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("inventory"));

        Assert.assertTrue(
                currentUrl.contains("inventory"),
                "Login failed!");
    }

    @When("user enters invalid username and password")
    public void user_enters_invalid_username_and_password() {

        loginPage.login(
                "wrong_user",
                "wrong_password");
    }

    @Then("error message should be displayed")
    public void error_message_should_be_displayed() {

        String actualError =
                loginPage.getErrorMessage();

        Assert.assertTrue(
                actualError.contains(
                        "Username and password do not match"),
                "Error message validation failed!");

    }
}
