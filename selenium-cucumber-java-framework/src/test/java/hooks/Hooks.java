package hooks;

import java.io.File;
import java.util.Map;

import api.RegistrationApi;
import driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import reports.ReportManager;
import utils.ConfigReader;
import utils.TestUserStorage;

/**
 * Cucumber hooks for test setup and teardown.
 *
 * Thread-safe implementation for parallel test execution:
 * - Each scenario gets its own WebDriver instance via ThreadLocal
 * - Thread-local data (reports, user storage) is properly cleaned up
 * - Supports both sequential and parallel execution without conflicts
 *
 * Execution order:
 * @BeforeAll (once), @Before (each scenario), @After (each scenario)
 */
public class Hooks {


	/**
	 * One-time cleanup - runs once before any scenario.
	 * Removes artifacts from previous test runs.
	 */
	@BeforeAll
	public static void cleanExecutionFolder() {

		ReportManager.cleanOldReports();

		File screenshotsFolder = new File("target/screenshots");

		if (screenshotsFolder.exists()) {

			File[] files = screenshotsFolder.listFiles();

			if (files != null) {

				for (File file : files) {
					file.delete();
				}
			}
		}
	}

	/**
	 * Per-scenario setup - runs before each scenario.
	 * Initializes browser and report for this scenario.
	 * Thread-safe: each thread gets its own WebDriver and report context.
	 *
	 * @param scenario Cucumber scenario object
	 */
	@Before
	public void setUp(Scenario scenario) {

		DriverFactory.initializeDriver();

		DriverFactory.getDriver().get(ConfigReader.getProperty("base.url"));


		ReportManager.startScenario(scenario.getName());
	}

	/**
	 * Per-scenario teardown - runs after each scenario.
	 * Finalizes report with pass/fail status.
	 * Cleans up thread-local resources to prevent memory leaks in parallel execution.
	 *
	 * @param scenario Cucumber scenario object
	 */
	@After
	public void tearDown(Scenario scenario) {

		String status = scenario.isFailed() ? "FAIL" : "PASS";

		ReportManager.finishScenario(scenario.getName(), status);

		// Clear thread-local test user storage to prevent data leaks in parallel scenarios
		TestUserStorage.clear();

		// DriverFactory.quitDriver();
	}

	/*@Before("@debug")
	public void createUserForLogin() {

		Map<String, String> credentials = RegistrationApi.registerUser();

		TestUserStorage.setUsername(credentials.get("username"));
		TestUserStorage.setPassword(credentials.get("password"));
	}*/
}