package com.stta.utility;

import java.io.File;
import java.util.Random;

import com.google.common.base.Throwables;
import com.relevantcodes.extentreports.DisplayOrder;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.GridType;
import com.relevantcodes.extentreports.LogStatus;
import com.stta.TestSuiteBase.Globals;

public class Reporter {
	public static String strLogFolderPath;
	public static ExtentReports objReport;
	private static int iRandTraceCntr = 0;
	
	public enum Status {
		PASS, FAIL, WARNING, INFO
	}	
	private static boolean checkTestStatus;
	public static boolean isCheckTestStatus() {
		return checkTestStatus;
	}
	public static void setCheckTestStatus(boolean checkTestStatus) {
		Reporter.checkTestStatus = checkTestStatus;
	}
	public static void initializeModule (String SuitName) {
		if(!Globals.GBL_SuiteName.toString().equalsIgnoreCase(SuitName)) {
			Globals.GBL_SuiteName = SuitName;
			//Initialize reporter object
			String reportFilePath = Globals.GC_TEST_REPORT_DIR + Globals.GBL_SuiteName + ".html";
			Reporter.objReport = ExtentReports.get(Reporter.class);
			if(!new File(Globals.GC_TEST_REPORT_DIR).exists()){
				new File(Globals.GC_TEST_REPORT_DIR).mkdir();
			}
			Reporter.objReport.init(reportFilePath, 
					Boolean.parseBoolean(Globals.GBL_REPLACE_EXISTING_HTML_REPORT), 
					DisplayOrder.BY_OLDEST_TO_LATEST, 
					GridType.MASONRY);
			Globals.GBL_REPLACE_EXISTING_HTML_REPORT = "false";
			Reporter.objReport.config().documentTitle(Globals.GBL_SuiteName + ": Summary Report");
			Reporter.objReport.config().reportHeadline("Report options");
			Reporter.objReport.config().reportTitle("Execution summary report for [" + Globals.GBL_SuiteName + "]");
			Reporter.objReport.config().displayCallerClass(false);
			Reporter.objReport.config().useExtentFooter(false);
			Reporter.objReport.config().setImageSize("10%");	
			checkTestStatus = true;
		}
	}
	public static void initializeReportForTC(int currentIterationNumber, String testCaseName) throws Exception {
		System.out.println("Report.initializeReportForTC");
		Globals.GBL_TestCaseName = testCaseName;
		Globals.GBL_CurrentIterationNumber = currentIterationNumber;
		Reporter.objReport.startTest(testCaseName+"<br>Iteration " + Globals.GBL_CurrentIterationNumber);
	}
	public static void logEvent(Reporter.Status logStatus, String Step, String Details, boolean attachScreenshot) {
		System.out.println(logStatus.toString());
		if (Step.trim().length() == 0 && Details.trim().length() == 0 && attachScreenshot) {
			Reporter.objReport.attachScreenshot(WebActions.captureScreenshot());
		} else if (Step.trim().length() == 0 && attachScreenshot) {
			Reporter.objReport.attachScreenshot(WebActions.captureScreenshot(), Details);
		} else {
			LogStatus tmpLogStatus;
			String stackTrace = "";
			
			if (Globals.exception != null) {
				stackTrace = Throwables.getStackTraceAsString(Globals.exception);
				Globals.exception = null;
			} else if (Globals.assertionerror != null) {
				stackTrace = Throwables.getStackTraceAsString(Globals.assertionerror);
				Globals.assertionerror = null;
			} else
				stackTrace = Throwables.getStackTraceAsString(new Throwable());
			
			iRandTraceCntr = new Random().nextInt(10000);
			String stackTraceLnk = "<br><a href=\"#div" + iRandTraceCntr + "\" onclick=\"var el=getElementById('div" + iRandTraceCntr + "');" +
					"(el.style.display=='none')? (el.style.display='block') : (el.style.display='none'); " +
					"(this.text == '+ Show stack trace')? " +
					"(this.text='- Hide stack trace') : (this.text='+ Show stack trace'); this.style.fontSize='small'\"><font size='1'>+ Show stack trace</font></a>" +
					"<div id=\"div" + iRandTraceCntr + "\" style=\"display:none\"><pre>" + stackTrace + "</pre></div>";
			
			switch (logStatus) {
			case PASS:
				tmpLogStatus = LogStatus.PASS;				
				Details = "<font size=\"3\" color=\"green\"><pre>" + Details + "</pre></font>";
				break;
			case FAIL:
				tmpLogStatus = LogStatus.FAIL;				
				Details = "<font size=\"3\" color=\"red\"><pre>" + Details + "</pre></font>";
				checkTestStatus = false;
				Details += stackTraceLnk;
				break;
			case WARNING:
				tmpLogStatus = LogStatus.WARNING;
				Details = "<font size=\"3\" color=\"amber\"><pre>" + Details + "</pre></font>";
				Details += stackTraceLnk;
				break;
			case INFO:
				tmpLogStatus = LogStatus.INFO;
				Details = "<pre>" + Details + "</pre>";
				break;
				default:
					tmpLogStatus = LogStatus.WARNING;
					Details = "<pre>" + Details + "</pre>";
					Details += stackTraceLnk; 
			}
			
			if (attachScreenshot) {
				Reporter.objReport.log(tmpLogStatus, Step, Details, WebActions.captureScreenshot());
			} else {
				Reporter.objReport.log(tmpLogStatus, Step, Details);
			}
		}
	}
	public static void finalizeTCReport () throws Exception {
		//Checking final report status
		if(!checkTestStatus){
			//TestListener.setFinalTestStatus(false);
			System.out.println("Test Case failed");
		}
		Reporter.objReport.endTest();		
	}
}
