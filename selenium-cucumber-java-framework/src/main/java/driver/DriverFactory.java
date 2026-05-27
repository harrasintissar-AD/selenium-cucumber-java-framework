package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {

	private static DriverFactory instance = null;
	private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	private DriverFactory() {
		// Private constructor for Singleton
	}

	public static DriverFactory getInstance() {
		if (instance == null) {
			synchronized (DriverFactory.class) {
				if (instance == null) {
					instance = new DriverFactory();
				}
			}
		}
		return instance;
	}

	public static void initializeDriver() {

		if (driver.get() == null) {

			WebDriverManager.chromedriver().setup();

			ChromeOptions options = new ChromeOptions();

			options.addArguments("--headless=new");

			options.addArguments("--disable-gpu");

			options.addArguments("--no-sandbox");

			options.addArguments("--window-size=1920,1080");

			WebDriver chromeDriver = new ChromeDriver(options);

			chromeDriver.manage().window().maximize();

			chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

			driver.set(chromeDriver);
		}
	}

	public static WebDriver getDriver() {
		return driver.get();
	}

	public static void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}

}