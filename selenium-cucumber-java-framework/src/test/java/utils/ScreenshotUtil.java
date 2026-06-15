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

public class ScreenshotUtil {

	public static String captureScreenshot(String fileName) {

		try {

			JavascriptExecutor js = (JavascriptExecutor) DriverFactory.getDriver();

			Long pageHeight = (Long) js.executeScript(
					"return Math.max(" + "document.body.scrollHeight," + "document.documentElement.scrollHeight);");

			DriverFactory.getDriver().manage().window()
					.setSize(new org.openqa.selenium.Dimension(1600, pageHeight.intValue()));

			js.executeScript("document.body.style.overflow='hidden'");

			File source = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);

			String path = "target/screenshots/" + fileName.replace(" ", "_") + ".png";

			File destination = new File(path);

			destination.getParentFile().mkdirs();

			Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

			js.executeScript("document.body.style.overflow='auto'");

			return path;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void captureHighlightedScreenshot(WebDriver driver, WebElement element, String name) {

		HighlightUtil.highlightElement(driver, element);

		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ScreenshotUtil.captureScreenshot(name);

		HighlightUtil.removeHighlight(driver, element);
	}
}