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

				        img {
				            width: 100%;
				            border: 1px solid #ccc;
				            margin-bottom: 20px;
				        }

				        h1 {
				            color: #333;
				        }

				        .step {
				            margin-top: 25px;
				        }
				    </style>
				</head>
				<body>
				""");

		htmlContent.append("<h1>Scenario: " + scenarioName + "</h1>");
	}

	public static void addStepScreenshot(String stepName, String imagePath) {

		File imageFile = new File(imagePath);

		String imageName = imageFile.getName();

		String relativePath = "../screenshots/" + imageName;

		htmlContent.append("<div class='step'>");

		htmlContent.append("<h3>" + stepName + "</h3>");

		htmlContent.append("<img src='" + relativePath + "'>");

		htmlContent.append("</div>");
	}

	public static void finishScenario(String scenarioName, String status) {

		htmlContent.append("<h2>Status: " + status + "</h2>");

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