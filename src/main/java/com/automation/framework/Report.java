package com.automation.framework;

import java.io.File;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;

/**
 * @author Hari
 * @since 03/18/2017
 * 
 * This class is used to create Custom Report using Extent Reports
 * 
 */
public class Report extends ReusableLibrary{
	
	private String relativePath;
	private String reportPath;	
	private String reportFolderName;
	private String reportName;
	
	
	public Report(String reportName) {
		
		setRelativePath();
		setReportFolderName();
		setReportName(reportName);
		setReportPath();
		
	}
	
	/**
	 * 
	 * This method initializes Extent Reports in offline mode
	 * 
	 * 
	 * @return ExtentReport object
	 * 
	 */
	public ExtentReports initialize() {	
		
		return new ExtentReports(this.reportPath, true, NetworkMode.OFFLINE);
		
	}
	
	/**
	 * 
	 * This method is used to set relative path in order store the report files in specific path
	 * 
	 */
	public void setRelativePath() {
		
		this.relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();
	}
	
	/**
	 * 
	 * This method is used to set Custom report folder name which takes current time stamp as its name
	 * 
	 */
	public void setReportFolderName() {
		
		this.reportFolderName = getCurrentFormattedTime("dd_MMM_yyyy_hh_mm_ss");
	}
	
	/**
	 * 
	 * This method is used set the name of the HTML file generated after flushing the Extent Report
	 * 
	 * @param reportName Name of the final HTML report 
	 * 
	 */
	public void setReportName(String reportName) {
		
		this.reportName = reportName;
	}
	
	/**
	 * 
	 * Getter method to get Report Folder name
	 * 
	 * @return reportFolderName Name of the report folder where the results are stored for the particular run
	 * 
	 */
	public String getReportFolderName() {
		
		return this.reportFolderName;
		
	}
	
	/**
	 * 
	 * This method is used to set report path 
	 * 
	 * 
	 * 
	 */
	public void setReportPath() {
		
		this.reportPath = this.relativePath+ "/Results/" + reportFolderName +"/"+ reportName +".html";
	}
	
	/**
	 * 
	 * Getter method is used to get report path which is used while opening the report after execution is completed
	 * 
	 * @return reportPath
	 * 
	 */
	
	public String getReportPath() {
		
		return this.reportPath;
	}

}
