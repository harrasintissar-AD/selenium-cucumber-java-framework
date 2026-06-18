package hooks;

import java.io.File;

import driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import reports.ReportManager;
import utils.ConfigReader;
import utils.ScreenshotUtil;

/**
 * Cucumber hooks for test setup and teardown.
 *
 * Execution order:
 * @BeforeAll (once), @Before (each scenario), @After (each scenario)
 */
public class Hooks {

	private int stepCounter = 1;
	private Scenario currentScenario;

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
	 *
	 * @param scenario Cucumber scenario object
	 */
	@Before
	public void setUp(Scenario scenario) {

		DriverFactory.initializeDriver();

		DriverFactory.getDriver().get(ConfigReader.getProperty("base.url"));

		this.currentScenario = scenario;

		stepCounter = 1;

		ReportManager.startScenario(scenario.getName());
	}

	/**
	 * Per-scenario teardown - runs after each scenario.
	 * Finalizes report with pass/fail status.
	 *
	 * @param scenario Cucumber scenario object
	 */
	@After
	public void tearDown(Scenario scenario) {

		String status = scenario.isFailed() ? "FAIL" : "PASS";

		ReportManager.finishScenario(scenario.getName(), status);

		// Uncomment to close browser after each scenario (useful for sequential tests)
		// DriverFactory.quitDriver();
	}
}