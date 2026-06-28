package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import utils.ConfigReader;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.time.Duration;

/**
 * Thread-safe singleton for managing WebDriver instances.
 * Uses ThreadLocal to support parallel test execution.
 *
 * Each thread gets its own isolated WebDriver instance:
 * - Thread-1 calls getDriver() → gets Driver-1
 * - Thread-2 calls getDriver() → gets Driver-2
 *
 * This design prevents driver conflicts in parallel scenarios while
 * maintaining the singleton pattern per thread.
 */
public class DriverFactory {

	private static DriverFactory instance = null;
	// ThreadLocal ensures each thread has its own WebDriver instance
	// This is critical for parallel test execution safety
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

			// Guard against missing configuration
			if (browser == null) {
				throw new RuntimeException("Property 'browser' is not set in configuration");
			}

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

		case "edge":

			String edgeDriverPath = ConfigReader.getProperty("edge.driver.path");
			if (edgeDriverPath != null && !edgeDriverPath.isEmpty()) {
				System.setProperty("webdriver.edge.driver", edgeDriverPath);
			} else {
				// Fallback to WebDriverManager to resolve the correct binary automatically
				WebDriverManager.edgedriver().setup();
			}

			EdgeOptions edgeOptions = new EdgeOptions();

			boolean headlessEdge = Boolean.parseBoolean(ConfigReader.getProperty("headless"));

			if (headlessEdge) {
				// use the newer headless flag for Chromium-based browsers
				edgeOptions.addArguments("--headless=new");
			}

			// Prevent bot detection while maintaining performance
			edgeOptions.addArguments("--disable-gpu");
			edgeOptions.addArguments("--no-sandbox");
			edgeOptions.addArguments("--disable-blink-features=AutomationControlled");
			edgeOptions.addArguments("--disable-dev-shm-usage");

			// Wait for document ready state before proceeding
			edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);

			webDriver = new EdgeDriver(edgeOptions);
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
	 * Should be called in @After hook or @AfterMethod for each test.
	 */
	public static void quitDriver() {
		if (driver.get() != null) {
			driver.get().quit();
			driver.remove();
		}
	}

}