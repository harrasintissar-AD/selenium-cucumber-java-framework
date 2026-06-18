package hooks;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import reports.ReportManager;
import utils.ScreenshotUtil;

/**
 * Captures screenshots after each Gherkin step for visual test documentation.
 * Ignores hook steps to capture only actual test steps.
 */
public class StepListener implements ConcurrentEventListener {

	private int stepCounter = 1;

	@Override
	public void setEventPublisher(EventPublisher publisher) {
		// Hook steps (Before/After) should not be captured
		publisher.registerHandlerFor(TestStepFinished.class, this::handleStep);
	}

	private void handleStep(TestStepFinished event) {
		// Distinguish real test steps from framework hook steps
		if (event.getTestStep() instanceof PickleStepTestStep) {
			PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();
			String stepText = step.getStep().getText();

			// Sanitize step text to create valid filenames
			String screenshotPath = ScreenshotUtil
					.captureScreenshot(stepText.replaceAll("[^a-zA-Z0-9]", "_") + "_STEP_" + stepCounter);

			ReportManager.addStepScreenshot("Step " + stepCounter + " - " + stepText, screenshotPath);

			stepCounter++;
		}
	}
}