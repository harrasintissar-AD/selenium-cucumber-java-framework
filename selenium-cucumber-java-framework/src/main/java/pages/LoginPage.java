package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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

		//clickLogin();
	}

	public boolean isLoginSuccessful() {

		return driver.findElement(accountsOverview).isDisplayed();
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
}