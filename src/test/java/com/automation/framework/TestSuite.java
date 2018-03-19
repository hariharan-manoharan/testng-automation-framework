package com.automation.framework;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.automation.framework.locators.PageObjectRepository;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Hari
 * @since 03/18/2017
 * 
 * This Test Suite consists of following test cases
 * 
 * <ul>
 * <li>selectSearchCategory</li>
 * <li>searchProduct</li>
 * <li>readProductDetails</li>
 * </ul>
 *
 */
public class TestSuite extends ReusableLibrary implements PageObjectRepository {

	/**
	 * Launches application with URL given by user and asserts if the Home Page
	 * Title is displayed as expected
	 * 
	 * 
	 */
	@BeforeClass
	public void launchApp() {

		webDriver.get(properties.getProperty("AppUrl"));

		if (webDriver.getTitle().equals(properties.getProperty("HomePageTitle"))) {
			addTestStep(LogStatus.PASS,
					"Application with URL  " + properties.getProperty("AppUrl") + " is launched successfully");
		} else {
			addTestStep(LogStatus.FAIL,
					"Application with URL  " + properties.getProperty("AppUrl") + " is not launched successfully");
		}

		Assert.assertEquals(webDriver.getTitle(), properties.getProperty("HomePageTitle"));

	}

	
	/**
	 * This method initializes custom test report for the particular test suite with
	 * description provided
	 * 
	 */
	@BeforeTest
	public void startTest() {
		test = testReport.startTest(properties.getProperty("TestDescription"));
	}
	

	/**
	 * This method ends the test reporting and flushes the output to generate HTML
	 * report which can be found under Results folder
	 * 
	 */
	@AfterTest
	public void endTest() {

		testReport.endTest(test);
		testReport.flush();
	}
	

	/**
	 * Select Search Category
	 * 
	 * Given Search category will be selected from the drop down and the asserts if the selected category is correct
	 * 
	 */
	@Test(priority = 1)
	public void selectSearchCategory() {

		addInfo(LogStatus.INFO, "Test 1: selectSearchCategory");

		click(XPATH_SEARCH_DROPDOWN);
		click(By.xpath(String.format(XPATH_SEARCH_DROPDOWN_OPTION_FORMAT, properties.getProperty("SearchCategory"))));

		if (getText(XPATH_SELECTED_SEARCH_CATEGORY).equals(properties.getProperty("SearchCategory"))) {
			addTestStep(LogStatus.PASS,
					"Search category " + properties.getProperty("SearchCategory") + " is selected successfully");
		} else {
			addTestStep(LogStatus.FAIL,
					"Search category " + properties.getProperty("SearchCategory") + " is not selected successfully");
		}

		Assert.assertEquals(getText(XPATH_SELECTED_SEARCH_CATEGORY), properties.getProperty("SearchCategory"));
	}
	

	/**
	 * Search Product
	 * 
	 * Enters given product in search text box and clicks search button and asserts window title if search result page id displayed
	 * 
	 */
	@Test(priority = 2)
	public void searchProduct() {

		addInfo(LogStatus.INFO, "Test 2: searchProduct");

		enterText(ID_SEARCH_TEXTBOX, properties.getProperty("SearchProduct"));
		click(XPATH_SEARCH_BUTTON);

		if (webDriver.getTitle().equals(properties.getProperty("SearchResultPageTitle"))) {
			addTestStep(LogStatus.PASS,
					"Product " + properties.getProperty("SearchResultPageTitle") + " is searched successfully");
		} else {
			addTestStep(LogStatus.FAIL,
					"Product " + properties.getProperty("SearchResultPageTitle") + " is not searched successfully");
		}

		Assert.assertEquals(webDriver.getTitle(), properties.getProperty("SearchResultPageTitle"));

	}
	

	/**
	 * Read Product Details
	 * 
	 * Reads the given product number to get Title, Edition, Published By, Available formats and its prices
	 * 
	 */
	@Test(priority = 3)
	public void readProductDetails() {

		addInfo(LogStatus.INFO, "Test 3: productDetails");

		int productNumber = Integer.parseInt(properties.getProperty("ProductNumber"));
		String productTitle = null;
		String productEdition = null;
		StringBuilder productBy = new StringBuilder();

		if (getElementList(XPATH_RESULT_PRODUCTS_LIST).size() > 0) {

			productTitle = getText(formatXpath(XPATH_PRODUCT_TITLE, productNumber));
			productEdition = getText(formatXpath(XPATH_PRODUCT_EDITION, productNumber));

			List<WebElement> elementBy = getElementList(
					formatXpath(XPATH_RESULT_LINES + XPATH_DETAILS_SECTION_1_2, productNumber));

			if (elementBy.size() > 1) {
				for (int i = 1; i <= elementBy.size(); i++) {
					productBy.append(getText(formatXpath(XPATH_PRODUCT_BY, productNumber, i)));
					productBy.append(" ");
				}
			}

			test.log(LogStatus.PASS,
					"<b>Product Details</b></br>" + "</br> <b>Title : </b>" + productTitle + "</br> <b>Edition : </b>"
							+ productEdition + "</br> <b>By : </b>" + productBy.toString()
							+ "</br> <b>Available Formats and its Price : </b> </br>"
							+ getFormatAndPriceDetails(productNumber));
		}

	}
	

	/**
	 * 
	 * Get Format and Price details of the product
	 * 
	 * This function aids in retrieving the all available formats for the particular product number displayed in the search result and its price
	 * 
	 * @return formatAndPrice All available format and its price details
	 * 
	 */
	public String getFormatAndPriceDetails(int productNumber) {

		StringBuilder formatAndPrice = new StringBuilder();
		int formatCounter = 0;

		formatAndPrice.append("<b>Format " + (++formatCounter) + ": </b>"
				+ getText(formatXpath(XPATH_PRODUCT_DETAILS, productNumber, 1)) + " <b>Price: </b>"
				+ getText(formatXpath(XPATH_PRODUCT_DETAILS, productNumber, 2)));

		List<WebElement> details = getElementList(formatXpath(XPATH_DETAILS_SECTION_2, productNumber));

		for (int i = 0; i < details.size(); i++) {

			if (details.get(i).getTagName().equals("hr")) {
				if (!details.get(i + 1).getText().contains("Other Formats")) {
					formatAndPrice.append("</br><b>Format " + (++formatCounter) + ": </b>"
							+ getText(formatXpath(XPATH_PRODUCT_DETAILS, productNumber, i + 2)) + " <b>Price: </b>"
							+ getText(formatXpath(XPATH_PRODUCT_DETAILS, productNumber, i + 3)));
				}
			}
		}

		return formatAndPrice.toString();

	}

}
