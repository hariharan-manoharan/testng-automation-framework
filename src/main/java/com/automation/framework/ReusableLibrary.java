package com.automation.framework;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import com.google.common.base.Function;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Hari
 * @since 03/18/2017
 * 
 * This class consists of reusable functions like "click", "enterText",
 * "waitCommand" etc. Also it consists of custom reporting functions
 * 
 * 
 */
public class ReusableLibrary extends Main {

	/**
	 * 
	 * This method is used to click a specific element
	 * 
	 * @param by
	 *            The locator
	 * 
	 */
	public void click(By by) {

		try {
			waitCommand(by);
			webDriver.findElement(by).click();
			test.log(LogStatus.PASS, "Element with locator - <b>" + by + "</b> is clicked successfully",
					"<b>Screenshot: <b>" + test.addScreenCapture("./screenshots/" + screenShot() + ".png"));
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Element with locator - <b>" + by + "</b> is not clicked successfully",
					"<b>Screenshot: <b>" + test.addScreenCapture("./screenshots/" + screenShot() + ".png"));
			test.log(LogStatus.FAIL, e.getMessage());
		}

	}

	/**
	 * 
	 * This method is used to enter text
	 * 
	 * @param by
	 *            The locator
	 * @param textToEnter
	 *            The text that needs to be entered
	 * 
	 */
	public void enterText(By by, String textToEnter) {
		try {
			waitCommand(by);
			webDriver.findElement(by).sendKeys(textToEnter);
			test.log(LogStatus.PASS,
					"Text '<b>" + textToEnter + "</b>' is entered successfully in element with locator - <b>" + by
							+ "</b>",
					"<b>Screenshot: <b>" + test.addScreenCapture("./screenshots/" + screenShot() + ".png"));
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					"Text '<b>" + textToEnter + "</b>' is not entered successfully in element with locator - <b>" + by
							+ "</b>",
					"<b>Screenshot: <b>" + test.addScreenCapture("./screenshots/" + screenShot() + ".png"));
			test.log(LogStatus.FAIL, e.getMessage());
		}
	}

	/**
	 * 
	 * This method is used to get the inner text of an given element
	 * 
	 * @param by
	 *            The locator
	 * @return text The inner text of the element
	 * 
	 */
	public String getText(By by) {

		String text = null;

		try {
			waitCommand(by);
			text = webDriver.findElement(by).getText().trim();
			test.log(LogStatus.PASS,
					"getText function returned - '<b>" + text + "</b>' from element with locator - <b>" + by + "</b>",
					"<b>Screenshot: <b>" + test.addScreenCapture("./screenshots/" + screenShot() + ".png"));
		} catch (Exception e) {
			test.log(LogStatus.FAIL,
					"getText function returned - '<b>" + text + "</b>' from element with locator - <b>" + by + "</b>",
					"<b>Screenshot: <b>" + test.addScreenCapture("./screenshots/" + screenShot() + ".png"));
			test.log(LogStatus.FAIL, e.getMessage());
		}

		return text;
	}

	/**
	 * This method is the wait command which is used before performing each actions
	 * like click, enterText etc to confirm if the element is available in the DOM.
	 * 
	 * It polls every 5 seconds to check if the element is available with timeout duration of total 20 secs
	 * 
	 * 
	 * @param by The locator
	 * 
	 * 
	 */
	public void waitCommand(final By by) throws TimeoutException {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(webDriver);
		wait.pollingEvery(5, TimeUnit.SECONDS);
		wait.withTimeout(20, TimeUnit.SECONDS);
		wait.ignoring(NoSuchElementException.class);

		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {

			@Override
			public Boolean apply(WebDriver webDriver) {
				boolean displayed = webDriver.findElement(by).isEnabled();
				if (displayed) {
					return true;
				} else {
					return false;
				}
			}

		};
		wait.until(function);
	}

	/**
	 * 
	 * This method is to format the string. Used to format xpath at runtime
	 * 
	 * 
	 * @param expression The expression to be formatted
	 * @param args Arguments referenced by the format specifiers in the format expression
	 * 
	 */
	public By formatXpath(String expression, Object... args) {

		By formattedXpath = By.xpath(String.format(expression, args));

		return formattedXpath;

	}

	/**
	 * This method is used to get the list of web elements
	 * 
	 * @param by The Locator
	 * @return elements A list of web elements
	 * 
	 * 
	 */
	public List<WebElement> getElementList(By by) {

		List<WebElement> elements = webDriver.findElements(by);

		return elements;

	}

	/**
	 * 
	 * This method is used to format the current time stamp which is used for naming the screenshot, report folder etc
	 * 
	 * @param format The format describing the date and time format
	 * @return Formatted time stamp
	 * 
	 */
	public String getCurrentFormattedTime(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 
	 * This method is used to take screenshot
	 * 
	 * @return screenshotName The name of the screenshot file
	 * 
	 */
	public String screenShot() {

		String screenshotName = null;

		screenshotName = getCurrentFormattedTime("dd_MMM_yyyy_hh_mm_ss_SSS");
		File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);

		try {

			FileUtils.copyFile(scrFile,
					new File("./Results/" + report.getReportFolderName() + "/screenshots/" + screenshotName + ".png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return screenshotName;

	}

	/**
	 * 
	 * This method is used to add info steps to the custom report where screenshot is not required
	 * 
	 * @param status The status to be given to the step
	 * 
	 * @param testInfo The test step info to be displayed in the report
	 */
	public void addInfo(LogStatus status, String testInfo) {

		test.log(status, "<b>" + testInfo + "</b>", "");

	}

	/**
	 * 
	 * This method is used to add test step to the custom report where screenshot is necessary
	 * 
	 * @param status The status to be given to the step
	 * @param testInfo The test step info to be displayed in the report
	 * 
	 */
	public void addTestStep(LogStatus status, String testInfo) {

		test.log(status, testInfo,
				"<b>Screenshot: <b>" + test.addScreenCapture("./screenshots/" + screenShot() + ".png"));

	}

}
