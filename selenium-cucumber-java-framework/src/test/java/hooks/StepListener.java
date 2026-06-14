package hooks;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;
import reports.ReportManager;
import utils.ScreenshotUtil;

public class StepListener implements ConcurrentEventListener {

	private int stepCounter = 1;

	@Override
	public void setEventPublisher(EventPublisher publisher) {

		publisher.registerHandlerFor(TestStepFinished.class, this::handleStep);
	}

	private void handleStep(TestStepFinished event) {

		// Only real Gherkin steps (ignore hooks)
		if (event.getTestStep() instanceof PickleStepTestStep) {

			PickleStepTestStep step = (PickleStepTestStep) event.getTestStep();

			String stepText = step.getStep().getText();

			String screenshotPath = ScreenshotUtil
					.captureScreenshot(stepText.replaceAll("[^a-zA-Z0-9]", "_") + "_STEP_" + stepCounter);

			ReportManager.addStepScreenshot("Step " + stepCounter + " - " + stepText, screenshotPath);

			stepCounter++;
		}
	}
}