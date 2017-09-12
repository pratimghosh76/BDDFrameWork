package com.stta.TestSuiteBase;

import org.openqa.selenium.WebDriver;

public class Globals {
	public static String GBL_SuiteName = "";
	public static String GBL_TestCaseName = "";
	public static String GBL_TestCaseNameColumn = "TestNGTestName";
	public static String GBL_TestDataFlagColumn = "DataToRun";
	public static String GBL_strScreenshotsFolderPath = "";
	public static String GBL_REPLACE_EXISTING_HTML_REPORT = "true";
	public static String GBL_CurrentXLSFileName = "";
	public static String GBL_SuiteFileName = "";
	public static String GBL_SuitListFileName = "TestSuiteList.xls";
	public static String GC_MANUAL_TC_NAME;
	public static int GBL_CurrentIterationNumber = 0;
	public static Exception exception = null;
	public static AssertionError assertionerror = null;
	public static final String GC_PROJECT_DIR = System.getProperty("user.dir")+"\\";
    public static final String GC_TESTCONFIGLOC = GC_PROJECT_DIR;
    public static final String GC_TEST_REPORT_DIR =  GC_PROJECT_DIR+"TestReport\\";
    //public static WebDriver GBdriver=null;
    
}
