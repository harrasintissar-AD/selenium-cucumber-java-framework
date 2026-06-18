package reports;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Generates HTML execution reports with embedded base64 screenshots.
 * Creates one report per scenario with all steps captured as images.
 */
public class ReportManager {

	private static final String REPORT_FOLDER = "target/execution-report/";

	private static StringBuilder htmlContent;

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

		htmlContent = new StringBuilder();

		htmlContent.append("""
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

		htmlContent.append("<h1>Scenario: " + scenarioName + "</h1>");
	}

	/**
	 * Adds a step screenshot to the report.
	 * Screenshots are base64 encoded to embed directly in HTML (single-file report).
	 *
	 * @param stepName step description (e.g., "Step 1 - User clicks login")
	 * @param imagePath path to screenshot file
	 */
	public static void addStepScreenshot(String stepName, String imagePath) {

		try {

			File imageFile = new File(imagePath);
			// Base64 encode: makes report self-contained, no external image links
			byte[] imageBytes = java.nio.file.Files.readAllBytes(imageFile.toPath());
			String base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);

			htmlContent.append("<div class='step'>");

			htmlContent.append("<h3 class='step-title'>").append(stepName).append("</h3>");

			htmlContent.append("<div class='image-container'>");

			// Data URI allows embedding image directly without external file reference
			htmlContent.append("<img style='max-width:100%; height:auto;' src='data:image/png;base64,")
					.append(base64Image).append("'/>");

			htmlContent.append("</div>");

			htmlContent.append("</div>");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Finalizes report and saves to HTML file.
	 * Call once per scenario in @After hook.
	 *
	 * @param scenarioName scenario name (used for filename)
	 * @param status "PASS" or "FAIL"
	 */
	public static void finishScenario(String scenarioName, String status) {

		htmlContent.append("<h3>Status: " + status + "</h3>");

		htmlContent.append("</body></html>");

		try {

			FileWriter writer = new FileWriter(REPORT_FOLDER + scenarioName.replace(" ", "_") + ".html");

			writer.write(htmlContent.toString());

			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}