package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

/**
 * Thread-safe singleton for managing WebDriver instances.
 * Uses ThreadLocal to support parallel test execution.
 */
public class DriverFactory {

	private static DriverFactory instance = null;
	// ThreadLocal ensures each thread has its own WebDriver instance
	private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	private DriverFactory() {
	}

	/**
	 * Returns singleton instance using double-checked locking.
	 */
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

	/**
	 * Initializes WebDriver from configuration. No-op if already initialized.
	 */
	public static void initializeDriver() {

		if (driver.get() == null) {

			String browser = ConfigReader.getProperty("browser");

			WebDriver webDriver;

			switch (browser.toLowerCase()) {

			case "chrome":

				WebDriverManager.chromedriver().setup();

				ChromeOptions options = new ChromeOptions();

				boolean headless = Boolean.parseBoolean(ConfigReader.getProperty("headless"));
				System.out.println(headless);

				if (headless) {
					options.addArguments("--headless=new");
				}

				// Prevent bot detection while maintaining performance
				options.addArguments("--disable-gpu");
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-blink-features=AutomationControlled");
				options.addArguments("--disable-dev-shm-usage");
				
				// Wait for document ready state before proceeding
				options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

				webDriver = new ChromeDriver(options);
				break;

			default:
				throw new RuntimeException("Browser not supported: " + browser);
			}

			webDriver.manage().window().maximize();
			webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

			driver.set(webDriver);
		}
	}

	/**
	 * Returns WebDriver for current thread.
	 */
	public static WebDriver getDriver() {
		return driver.get();
	}

	/**
	 * Quits WebDriver and removes from ThreadLocal to prevent memory leaks.
	 */
	public static void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}

}