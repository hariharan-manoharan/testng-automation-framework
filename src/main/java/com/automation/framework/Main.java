package com.automation.framework;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;


/**
 * @author Hari
 * @since 03/18/2017
 * 
 */
public class Main 
{
	
	public WebDriver webDriver;
	public Properties properties; 
	public ExtentReports testReport;
	public ExtentTest test;
	public Report report;

	/**
	 * 
	 */
	@BeforeSuite
	public void beforeClass() {
		
		initializeTestReport();
		getUserPreferences();
		prepareDiverObject();
		
	}
	
	/**
	 * 
	 */
	@AfterSuite
	public void afterClass() {
		
		if(webDriver!=null) {
			webDriver.quit();
		}
		
		try {
			Desktop.getDesktop().open(new File(report.getReportPath()));
		} catch (IOException e) {			
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 
	 */
	private void initializeTestReport() {
		
		report = new Report("Report");
		testReport = report.initialize();
		testReport.loadConfig((new File("src/test/resources/extent-report-config.xml")));
		
	}

   
	/**
	 * 
	 */
	public void getUserPreferences() {

		InputStream in = null;

		try {
			properties = new Properties();
			
			in = new FileInputStream("src/test/resources/UserPreferences.properties");
			properties.load(in);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
	
	
	/**
	 * 
	 */
	public void prepareDiverObject() {
		
		String browser = properties.getProperty("Browser");
		
		switch(browser)
		{
		
		case "Chrome":
			
			System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
			webDriver = new ChromeDriver();
			webDriver.manage().window().maximize();
			
		 break;
		
		case "Firefox":
			
			System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
			webDriver = new FirefoxDriver();
			webDriver.manage().window().maximize();
			
			break;
		
		default:
			
			System.setProperty("webdriver.gecko.driver", "src/test/resources/geckodriver.exe");
			webDriver = new FirefoxDriver();
			webDriver.manage().window().maximize();
			
			break;		
			
		}
		
	}	
	
}
