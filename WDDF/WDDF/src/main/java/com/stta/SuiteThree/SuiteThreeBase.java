package com.stta.SuiteThree;

import java.io.IOException;

import org.testng.SkipException;
import org.testng.annotations.BeforeSuite;

import com.stta.TestSuiteBase.SuiteBase;
import com.stta.utility.Log;
import com.stta.utility.Read_XLS;
import com.stta.utility.Reporter;
import com.stta.utility.SuiteUtility;
import com.stta.utility.Log.Level;

public class SuiteThreeBase extends SuiteBase {
	Read_XLS FilePath = null;
	String SheetName = null;
	String SuiteName = null;
	String ToRunColumnName = null;
	String ToRunColumnNameTestCase = null;
	String ToRunColumnNameTestData = null;
	
	//This function will be executed before SuiteOne's test cases to check SuiteToRun flag.
	@BeforeSuite
	public void checkSuiteToRun() throws IOException{		
		//Called init() function from SuiteBase class to Initialize .xls Files
		System.out.println(" SuiteThreeBase.checkSuiteToRun @BeforeSuite");
		init();			
		//To set TestSuiteList.xls file's path In FilePath Variable.
		FilePath = TestSuiteListExcel;
		SheetName = "SuitesList";
		SuiteName = "SuiteThree";
		ToRunColumnName = "SuiteToRun";
		
		//Bellow given syntax will Insert log In applog.log file.
		Add_Log.info("Execution started for SuiteOneBase.");
		Log.Report(Level.INFO, "Wrapper class log4j info");
		Log.Report(Level.DEBUG, "Wrapper class log4j debug");
		Log.Report(Level.WARN, "Wrapper class log4j warn");
		Log.Report(Level.TRACE, "Wrapper class log4j trace");
		Log.Report(Level.ERROR, "Wrapper class log4j error");
		//If SuiteToRun !== "y" then SuiteOne will be skipped from execution.
		if(!SuiteUtility.checkToRunUtility(FilePath, SheetName,ToRunColumnName,SuiteName)){			
			Add_Log.info("SuiteToRun = N for "+SuiteName+" So Skipping Execution.");
			//To report SuiteOne as 'Skipped' In SuitesList sheet of TestSuiteList.xls If SuiteToRun = N.
			
			SuiteUtility.WriteResultUtility(FilePath, SheetName, "Skipped/Executed", SuiteName, "Skipped");
			//It will throw SkipException to skip test suite's execution and suite will be marked as skipped In testng report.
			throw new SkipException(SuiteName+"'s SuiteToRun Flag Is 'N' Or Blank. So Skipping Execution Of "+SuiteName);
		}
		System.out.println("Initiating the suit start");
		Reporter.initializeModule(SuiteName);
		System.out.println("Initiated the suit end");
		//To report SuiteOne as 'Executed' In SuitesList sheet of TestSuiteList.xls If SuiteToRun = Y.
		SuiteUtility.WriteResultUtility(FilePath, SheetName, "Skipped/Executed", SuiteName, "Executed");		
	}
	
	public void CheckTestToRun(String TestCaseName) {
		System.out.println(" SuiteThreeBase.CheckTestToRun ");
		//To set SuiteOne.xls file's path In FilePath Variable.
				FilePath = TestCaseListExcelGen;		
				//TestCaseName = TestCaseName;	
				//SheetName to check CaseToRun flag against test case.
				SheetName = "TestCasesList";
				//Name of column In TestCasesList Excel sheet.
				ToRunColumnNameTestCase = "CaseToRun";
				//Name of column In Test Case Data sheets.
				ToRunColumnNameTestData = "DataToRun";
				//Bellow given syntax will Insert log In applog.log file.
				Add_Log.info(TestCaseName+" : Execution started.");
				//To check test case's CaseToRun = Y or N In related excel sheet.
				//If CaseToRun = N or blank, Test case will skip execution. Else It will be executed.
				if(!SuiteUtility.checkToRunUtility(FilePath, SheetName,ToRunColumnNameTestCase,TestCaseName)){
					Add_Log.info(TestCaseName+" : CaseToRun = N for So Skipping Execution.");
					//To report result as skip for test cases In TestCasesList sheet.
					SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseName, "SKIP");
					//To throw skip exception for this test case.
					throw new SkipException(TestCaseName+"'s CaseToRun Flag Is 'N' Or Blank. So Skipping Execution Of "+TestCaseName);
				}
	}

}
