package reports;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ReportManager {

	private static final String REPORT_FOLDER = "target/execution-report/";

	private static StringBuilder htmlContent;

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

	public static void addStepScreenshot(String stepName, String imagePath) {

		try {

			File imageFile = new File(imagePath);
			byte[] imageBytes = java.nio.file.Files.readAllBytes(imageFile.toPath());
			String base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);

			htmlContent.append("<div class='step'>");

			htmlContent.append("<h3 class='step-title'>").append(stepName).append("</h3>");

			htmlContent.append("<div class='image-container'>");

			htmlContent.append("<img style='max-width:100%; height:auto;' src='data:image/png;base64,")
					.append(base64Image).append("'/>");

			htmlContent.append("</div>");

			htmlContent.append("</div>");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

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