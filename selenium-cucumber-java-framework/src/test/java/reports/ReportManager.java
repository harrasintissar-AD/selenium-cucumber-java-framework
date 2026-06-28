package reports;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Generates HTML execution reports with embedded base64 screenshots.
 * Creates one report per scenario with all steps captured as images.
 *
 * Thread-safe implementation using ThreadLocal to support parallel test execution.
 * Each thread maintains its own report context, preventing data leaks in parallel scenarios.
 */
public class ReportManager {

	private static final String REPORT_FOLDER = "target/execution-report/";

	// ThreadLocal ensures each thread has its own report being built
	private static final ThreadLocal<StringBuilder> htmlContent = new ThreadLocal<>();

	/**
	 * Deletes all old reports and creates fresh report directory.
	 * Call once at start of test suite.
	 */
	public static void cleanOldReports() {

		File folder = new File(REPORT_FOLDER);

		if (folder.exists()) {

			File[] files = folder.listFiles();

			if (files != null) {

				for (File file : files) {
					file.delete();
				}
			}
		}

		folder.mkdirs();
	}

	/**
	 * Initializes report structure for a scenario.
	 * Call once per scenario in @Before hook.
	 *
	 * @param scenarioName the scenario name from feature file
	 */
	public static void startScenario(String scenarioName) {

		StringBuilder content = new StringBuilder();

		content.append("""
								<html>
								<head>
								    <title>Scenario Report</title>
								    <style>
				    body {
				        font-family: Arial;
				        margin: 30px;
				    }

				    h1 {
				        color: #333;
				    }

				    .step {
				        margin-top: 40px;
				        page-break-after: always;
				        text-align: center;
				    }

				    .step-title {
				        color: #2c3e50;
				        font-size: 22px;
				        margin-bottom: 15px;
				    }

				    .image-container {
				        width: 100%;
				        border: 1px solid #ddd;
				        padding: 10px;
				    }

				    img {
				        width: 100%;
				        height: auto;
				    }
				</style>
								</head>
								<body>
								""");

		content.append("<h1>Scenario: " + scenarioName + "</h1>");

		// Store this thread's report in ThreadLocal
		htmlContent.set(content);
	}

	/**
	 * Adds a step screenshot to the report.
	 * Screenshots are base64 encoded to embed directly in HTML (single-file report).
	 *
	 * @param stepName step description (e.g., "Step 1 - User clicks login")
	 * @param imagePath path to screenshot file
	 */
	public static void addStepScreenshot(String stepName, String imagePath) {

		StringBuilder content = htmlContent.get();

		if (content == null) {
			System.err.println("Warning: Report not initialized. Call startScenario() first.");
			return;
		}

		try {

			File imageFile = new File(imagePath);
			// Base64 encode: makes report self-contained, no external image links
			byte[] imageBytes = java.nio.file.Files.readAllBytes(imageFile.toPath());
			String base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);

			content.append("<div class='step'>");

			content.append("<h3 class='step-title'>").append(stepName).append("</h3>");

			content.append("<div class='image-container'>");

			// Data URI allows embedding image directly without external file reference
			content.append("<img style='max-width:100%; height:auto;' src='data:image/png;base64,")
					.append(base64Image).append("'/>");

			content.append("</div>");

			content.append("</div>");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Finalizes report and saves to HTML file.
	 * Call once per scenario in @After hook.
	 * Automatically cleans up ThreadLocal to prevent memory leaks.
	 *
	 * @param scenarioName scenario name (used for filename)
	 * @param status "PASS" or "FAIL"
	 */
	public static void finishScenario(String scenarioName, String status) {

		StringBuilder content = htmlContent.get();

		if (content == null) {
			System.err.println("Warning: Report not initialized for scenario: " + scenarioName);
			return;
		}

		content.append("<h3>Status: " + status + "</h3>");

		content.append("</body></html>");

		try {

			FileWriter writer = new FileWriter(REPORT_FOLDER + scenarioName.replace(" ", "_") + ".html");

			writer.write(content.toString());

			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Critical: cleanup ThreadLocal to prevent memory leaks in thread pools
			htmlContent.remove();
		}
	}
}