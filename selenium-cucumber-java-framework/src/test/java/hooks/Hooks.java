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

public class Hooks {

	private int stepCounter = 1;
	private Scenario currentScenario;

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

	@Before
	public void setUp(Scenario scenario) {

		DriverFactory.initializeDriver();

		DriverFactory.getDriver().get(ConfigReader.getProperty("base.url"));

		this.currentScenario = scenario;

		stepCounter = 1;

		ReportManager.startScenario(scenario.getName());
	}

	@After
	public void tearDown(Scenario scenario) {

		String status = scenario.isFailed() ? "FAIL" : "PASS";

		ReportManager.finishScenario(scenario.getName(), status);

//        DriverFactory.quitDriver();
	}
}