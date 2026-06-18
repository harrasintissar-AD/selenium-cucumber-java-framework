package utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Utility for visually highlighting web elements on the page.
 */
public class HighlightUtil {

	/**
	 * Highlights element with yellow border and background, scrolls into view.
	 *
	 * @param driver WebDriver instance
	 * @param element element to highlight
	 */
	public static void highlightElement(WebDriver driver, WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView(true);", element);
		// Yellow border (3px) with semi-transparent yellow background (30% opacity)
		js.executeScript("arguments[0].style.border='3px solid yellow';"
				+ "arguments[0].style.backgroundColor='rgba(255, 255, 0, 0.3)';", element);
	}

	/**
	 * Removes highlighting from element.
	 *
	 * @param driver WebDriver instance
	 * @param element element to remove highlighting from
	 */
	public static void removeHighlight(WebDriver driver, WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].style.border='';" + "arguments[0].style.backgroundColor='';", element);
	}
}