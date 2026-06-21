package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {

        this.driver = driver;
    }

    private final By usernameField = By.name("username");

    private final By passwordField = By.name("password");

    private final By loginButton = By.xpath("//input[@value='Log In']");

    private final By accountsOverview = By.xpath("//h1[contains(text(),'Accounts Overview')]");

    private final By errorMessage = By.xpath("//p[@class='error']");

    private final By logoutButton = By.linkText("Log Out");

    private final By loginErrorMessage = By.xpath("//p[@class='error']");

    public void enterUsername(String username) {

        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {

        driver.findElement(passwordField).sendKeys(password);
    }

    public void clickLogin() {

        driver.findElement(loginButton).click();
    }

    public void login(String username, String password) {

        enterUsername(username);

        enterPassword(password);

    }

    public boolean isLoginSuccessful() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(accountsOverview));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    public WebElement getOverviewElement() {
        return driver.findElement(accountsOverview);
    }

    public String getErrorMessage() {

        return driver.findElement(errorMessage).getText();
    }

    public void clickLogout() {

        driver.findElement(logoutButton).click();
    }

    public boolean isAtLoginPage() {

        return driver.findElement(loginButton).isDisplayed();
    }

    public String getLoginErrorMessage() {
        return driver.findElement(loginErrorMessage).getText();
    }

    public boolean isLoginErrorDisplayed() {
        return driver.findElement(loginErrorMessage).isDisplayed();
    }

    public WebElement getErrorLoggingElement() {
        return driver.findElement(loginErrorMessage);
    }
}