package com.stta.TestSuiteBase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.stta.utility.Read_XLS;
import com.stta.utility.WebActions;

public class SuiteBase {	
	public static Read_XLS TestSuiteListExcel=null;
	public static Read_XLS TestCaseListExcelOne=null;
	public static Read_XLS TestCaseListExcelTwo=null;
	public static Read_XLS TestCaseListExcelGen=null;
	public static Logger Add_Log = null;
	public boolean BrowseralreadyLoaded=false;
	public static Properties Param = null;
	public static Properties Object = null;
	public static WebDriver driver=null;
	public static WebDriver ExistingchromeBrowser;
	public static WebDriver ExistingmozillaBrowser;
	public static WebDriver ExistingIEBrowser;
	
	public void init() throws IOException{
		//System.out.println(" SuiteBase.init");
		//To Initialize logger service.
		Add_Log = Logger.getLogger("rootLogger");				
		//Globals.GBdriver = 	driver;	
		//Please change file's path strings bellow If you have stored them at location other than bellow.
		//Initializing Test Suite List(TestSuiteList.xls) File Path Using Constructor Of Read_XLS Utility Class.
		TestSuiteListExcel = new Read_XLS(System.getProperty("user.dir")+"\\src\\main\\java\\com\\stta\\ExcelFiles\\TestSuiteList.xls");
		//Initializing Test Suite One(SuiteOne.xls) File Path Using Constructor Of Read_XLS Utility Class.
		TestCaseListExcelOne = new Read_XLS(System.getProperty("user.dir")+"\\src\\main\\java\\com\\stta\\ExcelFiles\\SuiteOne.xls");
		//Initializing Test Suite Two(SuiteTwo.xls) File Path Using Constructor Of Read_XLS Utility Class.
		TestCaseListExcelTwo = new Read_XLS(System.getProperty("user.dir")+"\\src\\main\\java\\com\\stta\\ExcelFiles\\SuiteTwo.xls");
		//Bellow given syntax will Insert log In applog.log file.
		if(Globals.GBL_SuiteFileName.length()>1) {
			//Initializing Get Test Suite (SuiteThree.xls) File Path Using Constructor Of Read_XLS Utility Class.
			TestCaseListExcelGen = new Read_XLS(System.getProperty("user.dir")+"\\src\\main\\java\\com\\stta\\ExcelFiles\\"+Globals.GBL_SuiteFileName);
		}
		Add_Log.info("All Excel Files Initialised successfully.");		
		
		//Create object of Java Properties class
		Param = new Properties();
		FileInputStream fip = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//com//stta//property//Param.properties");
		Param.load(fip);
		Add_Log.info("Param.properties file loaded successfully.");	

		//Initialize Objects.properties file.
		Object = new Properties();
		fip = new FileInputStream(System.getProperty("user.dir")+"//src//main//java//com//stta//property//Objects.properties");
		Object.load(fip);
		Add_Log.info("Objects.properties file loaded successfully.");		
	}
	
	public void loadWebBrowser(){
		//System.out.println(" SuiteBase.loadWebBrowser");
		//Check If any previous webdriver browser Instance Is exist then run new test In that existing webdriver browser Instance.
			if(Param.getProperty("testBrowser").equalsIgnoreCase("Mozilla") && ExistingmozillaBrowser!=null){
				driver = ExistingmozillaBrowser;
				//System.out.println("existing mozila");
				return;
			}else if(Param.getProperty("testBrowser").equalsIgnoreCase("chrome") && ExistingchromeBrowser!=null){
				driver = ExistingchromeBrowser;
				//System.out.println("existing chrome");
				return;
			}else if(Param.getProperty("testBrowser").equalsIgnoreCase("IE") && ExistingIEBrowser!=null){
				driver = ExistingIEBrowser;
				//System.out.println("existing IE");
				return;
			}		
		
		
			if(Param.getProperty("testBrowser").equalsIgnoreCase("Mozilla")){
				//To Load Firefox driver Instance. 
				driver = new FirefoxDriver();
				//System.out.println("FF driver created");
				ExistingmozillaBrowser=driver;
				Add_Log.info("Firefox Driver Instance loaded successfully.");
				
			}else if(Param.getProperty("testBrowser").equalsIgnoreCase("Chrome")){
				//To Load Chrome driver Instance.
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//BrowserDrivers//chromedriver.exe");
				driver = new ChromeDriver();
				//System.out.println("Chrome driver created");
				ExistingchromeBrowser=driver;
				Add_Log.info("Chrome Driver Instance loaded successfully.");
				
			}else if(Param.getProperty("testBrowser").equalsIgnoreCase("IE")){
				//To Load IE driver Instance.
				System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"//BrowserDrivers//IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				//System.out.println("IE driver created");
				ExistingIEBrowser=driver;
				Add_Log.info("IE Driver Instance loaded successfully.");
				
			}			
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
			driver.manage().window().maximize();			
	}
	
	public void closeWebBrowser(){
		//System.out.println(" SuiteBase.closeWebBrowser");
		driver.close();
		//null browser Instance when close.
		ExistingchromeBrowser=null;
		ExistingmozillaBrowser=null;
		ExistingIEBrowser=null;
	}
	
		//getElementByXPath function for static xpath
	public WebElement getElementByXPath(String Key){
		//System.out.println(" SuiteBase.getElementByXPath");
		try{
			//This block will find element using Key value from web page and return It.
			return driver.findElement(By.xpath(Object.getProperty(Key)));
		}catch(Throwable t){
			//If element not found on page then It will return null.
			Add_Log.debug("Object not found for key --"+Key);
			return null;
		}
	}
	
	//getElementByXPath function for dynamic xpath
	public WebElement getElementByXPath(String Key1, int val, String key2){
		//System.out.println(" SuiteBase.getElementByXPath");
		try{
			//This block will find element using values of Key1, val and key2 from web page and return It.
			return driver.findElement(By.xpath(Object.getProperty(Key1)+val+Object.getProperty(key2)));
		}catch(Throwable t){
			//If element not found on page then It will return null.
			Add_Log.debug("Object not found for custom xpath");
			return null;
		}
	}
	
	//Call this function to locate element by ID locator.
	public WebElement getElementByID(String Key){
		//System.out.println(" SuiteBase.getElementByID");
		try{
			//System.out.println("Inside id try");
			return driver.findElement(By.id(Object.getProperty(Key)));
		}catch(Throwable t){
			//System.out.println("inside id catch" + Key + " - " + Add_Log.isDebugEnabled()+ " - " + Add_Log.isInfoEnabled());
			Add_Log.debug("Object not found for key --"+Key);
			return null;
		}
	}
	
	//Call this function to locate element by Name Locator.
	public WebElement getElementByName(String Key){
		//System.out.println(" SuiteBase.getElementByName");
		try{
			return driver.findElement(By.name(Object.getProperty(Key)));
		}catch(Throwable t){
			Add_Log.debug("Object not found for key --"+Key);
			return null;
		}
	}
	
	//Call this function to locate element by cssSelector Locator.
	public WebElement getElementByCSS(String Key){
		//System.out.println(" SuiteBase.getElementByCSS");
		try{
			return driver.findElement(By.cssSelector(Object.getProperty(Key)));
		}catch(Throwable t){
			Add_Log.debug("Object not found for key --"+Key);
			return null;
		}
	}
	
	//Call this function to locate element by ClassName Locator.
	public WebElement getElementByClass(String Key){
		//System.out.println(" SuiteBase.getElementByClass");
		try{
			return driver.findElement(By.className(Object.getProperty(Key)));
		}catch(Throwable t){
			Add_Log.debug("Object not found for key --"+Key);
			return null;
		}
	}
	
	//Call this function to locate element by tagName Locator.
	public WebElement getElementByTagName(String Key){
		//System.out.println(" SuiteBase.getElementByTagName");
		try{
			return driver.findElement(By.tagName(Object.getProperty(Key)));
		}catch(Throwable t){
			Add_Log.debug("Object not found for key --"+Key);
			return null;
		}
	}
	
	//Call this function to locate element by link text Locator.
	public WebElement getElementBylinkText(String Key){
		//System.out.println(" SuiteBase.getElementBylinkText");
		try{
			return driver.findElement(By.linkText(Object.getProperty(Key)));
		}catch(Throwable t){
			Add_Log.debug("Object not found for key --"+Key);
			return null;
		}
	}
	
	//Call this function to locate element by partial link text Locator.
	public WebElement getElementBypLinkText(String Key){
		//System.out.println(" SuiteBase.getElementBypLinkText");
		try{
			return driver.findElement(By.partialLinkText(Object.getProperty(Key)));
		}catch(Throwable t){
			Add_Log.debug("Object not found for key --"+Key);
			return null;
		}
	}
	/// method to find if element exists
		///-----------------------------------------------
		
		public static void waitForElement(WebElement element) {
			//System.out.println("SuiteBase.waitForElement");
			try {
				(new WebDriverWait(SuiteBase.driver, Long.parseLong(SuiteBase.Param
						.getProperty("WaitinMilliSeconds")))).ignoring(
						StaleElementReferenceException.class).until(
						ExpectedConditions.visibilityOf(element));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		///-------------------------------
		
		public static boolean isWebElementDisplayed(WebElement element,			
				boolean... waitForElement) {
			//System.out.println("SuiteBase.isWebElementDisplayed");
			SuiteBase.driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			boolean blnElementDisplayed = false;
			try {
				try {
						//System.out.println(waitForElement.length);
					if (waitForElement.length > 0) {
						if (waitForElement[0] == true) {
							SuiteBase.waitForElement(element);
							//Thread.sleep(2000);
							//System.out.println("Try if-1");
						}
						//System.out.println("Try if-2");
					}
					//System.out.println("Try out of if");
				} catch (Exception e) {
					// Do nothing
					//System.out.println("catch 1");
				}
				////System.out.println(" checking blnElementDisplayed" + element.isDisplayed());
				//System.out.println(WebActions.hasQuit(SuiteBase.driver));
				blnElementDisplayed = element.isDisplayed();
				System.out.println(blnElementDisplayed);
			} catch (NoSuchElementException | ElementNotVisibleException |StaleElementReferenceException
					|TimeoutException e) {
				blnElementDisplayed = false;
				System.out.println("catch 2");
			}
			System.out.println(blnElementDisplayed);
			return blnElementDisplayed;
		}
		public static void clearTxtBox(WebElement textBoxField) {
			//System.out.println("SuiteBase.clearTxtBox");
			if(SuiteBase.isWebElementDisplayed(textBoxField, true)) {
				textBoxField.clear();
			}
		}
		public static void setTxtBox(WebElement textBoxField, CharSequence... valueToSet) {
			//System.out.println("SuiteBase.setTxtBox");
			if(SuiteBase.isWebElementDisplayed(textBoxField, true)) {
				textBoxField.sendKeys(valueToSet);
			}
		}
		public static String getTxtBox(WebElement textBoxField, CharSequence... valueToSet) {
			//System.out.println("SuiteBase.getTxtBox");
			if(SuiteBase.isWebElementDisplayed(textBoxField, true)) {
				//System.out.println(textBoxField.getAttribute("value"));
				return textBoxField.getAttribute("value");
			}
			return "";
		}
		public static boolean clickOnElement(WebElement clickElement) {
			//System.out.println("SuiteBase.clickOnElement");
			boolean success = false;
			if(SuiteBase.isWebElementDisplayed(clickElement, true)) {
				clickElement.click();
				success = true;
			}
			return success;
			
		}
}
