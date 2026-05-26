package hooks;

import java.io.File;

import driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import reports.ReportManager;
import utils.ScreenshotUtil;

public class Hooks {

    private int stepCounter = 1;
    private Scenario currentScenario;

    @BeforeAll
    public static void cleanExecutionFolder() {

        ReportManager.cleanOldReports();

        File screenshotsFolder =
                new File(
                        "target/screenshots");

        if (screenshotsFolder.exists()) {

            File[] files =
                    screenshotsFolder.listFiles();

            if (files != null) {

                for (File file : files) {
                    file.delete();
                }
            }
        }
    }

    @Before
    public void setUp(
            Scenario scenario) {

        DriverFactory.initializeDriver();

        this.currentScenario =
                scenario;

        stepCounter = 1;

        ReportManager.startScenario(
                scenario.getName());
    }

    @AfterStep
    public void takeScreenshotAfterStep() {

        String screenshotPath =
                ScreenshotUtil.captureScreenshot(

                        currentScenario.getName()
                                + "_STEP_"
                                + stepCounter);

        ReportManager.addStepScreenshot(
                "Step " + stepCounter,
                screenshotPath);

        stepCounter++;
    }

    @After
    public void tearDown(
            Scenario scenario) {

        String status =
                scenario.isFailed()
                        ? "FAIL"
                        : "PASS";

        ReportManager.finishScenario(
                scenario.getName(),
                status);

        DriverFactory.quitDriver();
    }
}