package hooks;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import reports.ReportManager;
import utils.ScreenshotUtil;

/**
 * Captures screenshots after each Gherkin step for visual test documentation.
 * Ignores hook steps to capture only actual test steps.
 *
 * Thread-safe implementation using ThreadLocal for step counter to support
 * parallel test execution where multiple scenarios run concurrently.
 */
public class StepListener implements ConcurrentEventListener {

	// ThreadLocal ensures each thread maintains its own step counter
	private static final ThreadLocal<Integer> stepCounter = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return 1;
		}
	};

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

			// Get the current thread's step counter
			Integer currentStep = stepCounter.get();

			// Sanitize step text to create valid filenames
			String screenshotPath = ScreenshotUtil
					.captureScreenshot(stepText.replaceAll("[^a-zA-Z0-9]", "_") + "_STEP_" + currentStep);

			ReportManager.addStepScreenshot("Step " + currentStep + " - " + stepText, screenshotPath);

			// Increment step counter for this thread
			stepCounter.set(currentStep + 1);
		}
	}
}