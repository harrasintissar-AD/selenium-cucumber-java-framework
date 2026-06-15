package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegisterPage {

	private WebDriver driver;

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
	}

	private By registerLink = By.linkText("Register");
	private By firstName = By.id("customer.firstName");
	private By lastName = By.id("customer.lastName");
	private By address = By.id("customer.address.street");
	private By city = By.id("customer.address.city");
	private By state = By.id("customer.address.state");
	private By zipCode = By.id("customer.address.zipCode");
	private By ssn = By.id("customer.ssn");

	private By username = By.id("customer.username");
	private By password = By.id("customer.password");
	private By confirmPassword = By.id("repeatedPassword");

	private By registerBtn = By.xpath("//input[@value='Register']");
	private final By registrationSuccessMessage = By
			.xpath("//p[contains(text(),'Your account was created successfully')]");

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
}