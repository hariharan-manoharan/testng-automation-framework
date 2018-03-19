package com.automation.framework.locators;

import org.openqa.selenium.By;
/**
 * @author Hari
 * @since 03/18/2017
 * 
 * This interface acts as an object repository
 * 
 */
public interface PageObjectRepository {
	
	//HOME PAGE
	
	By XPATH_SEARCH_DROPDOWN = By.xpath("//select[@id='searchDropdownBox']");
	By XPATH_SELECTED_SEARCH_CATEGORY = By.xpath("//span[@class='nav-search-label']");
	By XPATH_SEARCH_BUTTON = By.xpath("//div[@class='nav-search-submit nav-sprite']//input[@type='submit']");
	By ID_SEARCH_TEXTBOX = By.id("twotabsearchtextbox");
	String XPATH_SEARCH_DROPDOWN_OPTION_FORMAT = "//option[text() = '%s']";
	
	
	//SEARCH RESULT
	
	By XPATH_RESULT_PRODUCTS_LIST = By.xpath("//li[contains(@id, 'result_')]");
	
	String XPATH_RESULT_LINES = "(//li[contains(@id, 'result_')])[%d]";

	String XPATH_DETAILS_SECTION_1_1 = "//div[@class='a-fixed-left-grid-col a-col-right']/div[@class='a-row a-spacing-small']/child::*[1]/child::*[not(contains(@class,'a-letter-space'))]";	
	String XPATH_DETAILS_SECTION_1_2 = "//div[@class='a-fixed-left-grid-col a-col-right']/div[@class='a-row a-spacing-small']/child::*[2]/child::*";	
	String XPATH_DETAILS_SECTION_2 = XPATH_RESULT_LINES +"//div[@class='a-fixed-left-grid-col a-col-right']/div[@class='a-row']/child::*[1]/child::*";
	
	String XPATH_PRODUCT_TITLE = XPATH_RESULT_LINES + XPATH_DETAILS_SECTION_1_1+"[1]";
	String XPATH_PRODUCT_EDITION = XPATH_RESULT_LINES + XPATH_DETAILS_SECTION_1_1+"[2]";
	String XPATH_PRODUCT_BY = XPATH_RESULT_LINES + XPATH_DETAILS_SECTION_1_2+"[%d]";
	
	
	String XPATH_PRODUCT_DETAILS =  XPATH_DETAILS_SECTION_2 + "[%d]" + "/child::*";
	

}
