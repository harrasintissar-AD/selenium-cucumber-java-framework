package utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import driver.DriverFactory;

/**
 * Utility for capturing full-page screenshots of browser state.
 */
public class ScreenshotUtil {

	/**
	 * Captures full-page screenshot and saves to target/screenshots directory.
	 *
	 * Dynamically adjusts window height to capture entire page content.
	 *
	 * @param fileName screenshot filename (spaces replaced with underscores)
	 * @return file path to saved screenshot, or null on error
	 */
	public static String captureScreenshot(String fileName) {

		try {

			JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getDriver();

			// Calculate total page height including scrollable content
			Long pageHeight = (Long) js.executeScript(
					"return Math.max(" + "document.body.scrollHeight," + "document.documentElement.scrollHeight);");

			// Resize window to fit entire page content
			DriverFactory.getDriver().manage().window()
					.setSize(new org.openqa.selenium.Dimension(1600, pageHeight.intValue()));

			// Hide scrollbar to get clean full-page screenshot
			js.executeScript("document.body.style.overflow='hidden'");

			File source = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);

			String path = "target/screenshots/" + fileName.replace(" ", "_") + ".png";

			File destination = new File(path);
			destination.getParentFile().mkdirs();

			Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

			// Restore scrollbar visibility
			js.executeScript("document.body.style.overflow='auto'");

			return path;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Captures screenshot with highlighted web element.
	 * Highlights are removed after capture.
	 *
	 * @param driver WebDriver instance
	 * @param element element to highlight
	 * @param name screenshot filename
	 */
	public static void captureHighlightedScreenshot(WebDriver driver, WebElement element, String name) {

		HighlightUtil.highlightElement(driver, element);

		try {
			// Brief pause to ensure highlight is rendered
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ScreenshotUtil.captureScreenshot(name);

		HighlightUtil.removeHighlight(driver, element);
	}
}