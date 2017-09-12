package com.stta.SuiteThree;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.stta.PageObject.CalculatePage;
import com.stta.SuiteOne.SuiteOneBase;
import com.stta.TestSuiteBase.Globals;
import com.stta.TestSuiteBase.SuiteBase;
import com.stta.utility.Read_XLS;
import com.stta.utility.Reporter;
import com.stta.utility.SuiteUtility;
import com.stta.utility.Reporter.Status;

public class SuiteThreeCaseTwo extends SuiteThreeBase {
	Read_XLS FilePath = null;
	String SheetName = null;
	String DataSheetName = null;
	String TestCaseName = null;
	String TestCaseMethodName = null;	
	String ToRunColumnNameTestCase = null;
	String ToRunColumnNameTestData = null;
	String TestDataToRun[]=null;
	static boolean TestCasePass=true;
	static int DataSet=-1;	
	static boolean Testskip=false;
	static boolean Testfail=false;
	SoftAssert s_assert =null;
	int TestIteration = 0;
	String LastRunTestName = null;
	LinkedHashMap<Integer, Map<String,String>> testData = null;
	private CalculatePage calculatePage;
	
	@BeforeTest
	public void checkCaseToRun() throws IOException{
		System.out.println(" SuiteThreeCaseTwo.checkCaseToRun @BeforeTest");
		//Called init() function from SuiteBase class to Initialize .xls Files
		init();
		//To set SuiteOne.xls file's path In FilePath Variable.
		FilePath = TestCaseListExcelGen;
		TestCaseName=Globals.GC_MANUAL_TC_NAME;
		//SheetName to check CaseToRun flag against test case.
		SheetName = "TestCasesList";
		DataSheetName = "LoanTestData";
		//Name of column In TestCasesList Excel sheet.
		ToRunColumnNameTestCase = "TestNGTestName";
		//Name of column In Test Case Data sheets.
		ToRunColumnNameTestData = "DataToRun";
		loadWebBrowser();
	}
	@BeforeMethod
	public void initializeTestProperties(){
		System.out.println(" SuiteThreeCaseTwo.initializeTestProperties @BeforeMethod");
			
		//TestCaseName = TestCaseName;	
		//SheetName to check CaseToRun flag against test case.
		//SheetName = "TestCasesList";	
		System.out.println("initializeTestProperties : ");
		
	}
	@DataProvider
	public Object[][] setData(Method tc) throws Exception {
		System.out.println(" SuiteThreeCaseTwo.setData @DataProvider");
		prepTestData(tc);
		System.out.println(" SuiteThreeCaseTwo.setData @DataProvider2");
		return SuiteUtility.SetGenTestDataUtility(FilePath, this.testData);
	}
	
	private void prepTestData(Method testCase) throws Exception {
		System.out.println(" SuiteThreeCaseTwo.prepTestData");
		this.testData = SuiteUtility.GetGenTestDataUtility(FilePath, DataSheetName, ToRunColumnNameTestCase, TestCaseName);
	}
	
	@Test(dataProvider = "setData")
	public void TC_03_TestCase(int itr,Map<String, String> MapTestdata) throws Exception{
		System.out.println("Inside test TC_03_TestCase");
		SuiteBase.driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		System.out.println("initializeTestProperties - " + this.getClass().getPackage().getName());
		TestCaseMethodName = Thread.currentThread().getStackTrace()[1].getMethodName().toString();
		CheckTestToRun(TestCaseMethodName);
		
		if(Thread.currentThread().getStackTrace()[1].getMethodName().toString().equalsIgnoreCase(LastRunTestName)){
			System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName().toString() );
			TestIteration =TestIteration +1;			
		}else if(LastRunTestName == null) {
			LastRunTestName = Thread.currentThread().getStackTrace()[1].getMethodName().toString();
			TestIteration = 1;	
		} else if (!Thread.currentThread().getStackTrace()[1].getMethodName().toString().equalsIgnoreCase(LastRunTestName)) {
			LastRunTestName = Thread.currentThread().getStackTrace().toString();
			TestIteration = 1;
		}
		
		System.out.println(itr + " Run TC_03_TestCase " + MapTestdata.get(ToRunColumnNameTestCase));
		Reporter.initializeReportForTC(TestIteration, LastRunTestName);
		calculatePage = new CalculatePage().get();
		calculatePage.clearResultTxt();
		calculatePage.clickFirstNumber();
		calculatePage.clickPlus();
		calculatePage.clickSecondNumber();
		calculatePage.clickEqualsTo();
		String txtResult = calculatePage.getResultTxt();
		System.out.println("Test result is " + txtResult);
		Reporter.logEvent(Status.INFO, TestCaseMethodName, "Iteration = "+itr+" Test Result = "+ txtResult+ " and Data = "+MapTestdata, false);
		
	}
	
	
	
	//To report result as pass or fail for test cases In TestCasesList sheet.
		@AfterMethod
		public void closeTest(){
			System.out.println(" SuiteThreeCaseTwo.closeBrowser @AfterTest");
			//To Close the web browser at the end of test.
			System.out.println("Call close browser");
			closeWebBrowser();
			if(TestCasePass){
				Add_Log.info(TestCaseMethodName+" : Reporting test case as PASS In excel.");
				Reporter.logEvent(Status.PASS, TestCaseMethodName, "Execution successfull", false);
				SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseMethodName, "PASS");
					
			}
			else{
				Add_Log.info(TestCaseMethodName+" : Reporting test case as FAIL In excel.");
				Reporter.logEvent(Status.FAIL, TestCaseMethodName, "Execution Failed", false);	
				SuiteUtility.WriteResultUtility(FilePath, SheetName, "Pass/Fail/Skip", TestCaseMethodName, "FAIL");			
			}		
		}
}
