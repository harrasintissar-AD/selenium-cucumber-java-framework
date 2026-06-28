package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegisterPage {

	private WebDriver driver;

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
	}

	private final By registerLink = By.linkText("Register");
	private final By firstName = By.id("customer.firstName");
	private final By lastName = By.id("customer.lastName");
	private final By address = By.id("customer.address.street");
	private final By city = By.id("customer.address.city");
	private final By state = By.id("customer.address.state");
	private final By zipCode = By.id("customer.address.zipCode");
	private final By ssn = By.id("customer.ssn");

	private final By username = By.id("customer.username");
	private final By password = By.id("customer.password");
	private final By confirmPassword = By.id("repeatedPassword");
	private final By logoutButton = By.linkText("Log Out");

	private final By registerBtn = By.xpath("//input[@value='Register']");
	private final By registrationSuccessMessage = By
			.xpath("//p[contains(text(),'Your account was created successfully')]");

	private final By firstNameError = By.id("customer.firstName.errors");

	private final By lastNameError = By.id("customer.lastName.errors");

	private final By addressError = By.id("customer.address.street.errors");

	private final By usernameError = By.id("customer.username.errors");

	public void openRegister() {
		driver.findElement(registerLink).click();
	}

	public void register(String user, String pass) {

		driver.findElement(firstName).sendKeys("Test");
		driver.findElement(lastName).sendKeys("User");
		driver.findElement(address).sendKeys("Street 1");
		driver.findElement(city).sendKeys("Ottawa");
		driver.findElement(state).sendKeys("ON");
		driver.findElement(zipCode).sendKeys("K1A0B1");
		driver.findElement(ssn).sendKeys("1234");

		driver.findElement(username).sendKeys(user);
		driver.findElement(password).sendKeys(pass);
		driver.findElement(confirmPassword).sendKeys(pass);
	}

	public void registerWithData(
			String firstNameValue,
			String lastNameValue,
			String addressValue,
			String cityValue,
			String stateValue,
			String zipCodeValue,
			String ssnValue,
			String usernameValue,
			String passwordValue,
			String confirmPasswordValue) {

		driver.findElement(firstName).sendKeys(firstNameValue);
		driver.findElement(lastName).sendKeys(lastNameValue);
		driver.findElement(address).sendKeys(addressValue);
		driver.findElement(city).sendKeys(cityValue);
		driver.findElement(state).sendKeys(stateValue);
		driver.findElement(zipCode).sendKeys(zipCodeValue);
		driver.findElement(ssn).sendKeys(ssnValue);

		driver.findElement(username).sendKeys(usernameValue);
		driver.findElement(password).sendKeys(passwordValue);
		driver.findElement(confirmPassword).sendKeys(confirmPasswordValue);
	}
	
	public void registerBtn() {
		driver.findElement(registerBtn).click();
	}

	public String getRegistrationSuccessMessage() {
		WebElement messageElement = driver.findElement(registrationSuccessMessage);

		return messageElement.getText();
	}
	
	public WebElement getRegistrationSuccessMessageElement() {
		WebElement messageElement = driver.findElement(registrationSuccessMessage);
	    return messageElement;
	}

	public String getValidationMessage(String message) {

		By locator = By.xpath("//span[@class='error' and normalize-space()='" + message + "']");

		return driver.findElement(locator).getText();
	}

	public WebElement getValidationMessageElement(String message) {
		By locator = By.xpath("//span[@class='error' and normalize-space()='" + message + "']");
		return driver.findElement(locator);
	}

	public void clickLogout() {

		driver.findElement(logoutButton).click();
	}
}