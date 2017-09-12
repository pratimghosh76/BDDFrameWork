package com.stta.PageObject;

import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import com.stta.TestSuiteBase.SuiteBase;
import com.stta.utility.WebActions;
//import com.stta.TestSuiteBase.SuiteBase;

public class CalculatePage  extends LoadableComponent<CalculatePage>{
	//Webelement declarations
		@FindBy(xpath = ".//*[@id='header-inner']/div[1]/h1/a")
		private WebElement onlyTestingHome;
		
		@FindBy(id = "Resultbox") 
		private WebElement txtResult;
		
		@FindBy(xpath = ".//*[@id='9']")
		private WebElement btnFirstNumbr;
		
		@FindBy(id = "plus")
		private WebElement btnNumAction;
		
		@FindBy(id = "6")
		private WebElement btnSecondNumbr;
		
		@FindBy(id = "equals")
		private WebElement btnEquals;
		
		//Object declarations
		//LoginPage loginPage;	
		
		
		public CalculatePage()
		{
			//this.driver = driver;
			//System.out.println("CalculatePage.CalculatePage");
			PageFactory.initElements(SuiteBase.driver, this);
			SuiteBase.driver.get(SuiteBase.Param.getProperty("siteURL")+"/2014/04/calc.html");
			//SuiteBase.driver.get("https://www.google.com/");
		}
		
		@Override
		protected void isLoaded() throws Error {
			//System.out.println("CalculatePage.isLoaded");			
			////System.out.println(SuiteBase.driver.findElement(By.xpath(".//*[@id='header-inner']/div[1]/h1/a")).isDisplayed());
			////System.out.println(btnNumAction.isEnabled());			
			Assert.assertTrue(SuiteBase.isWebElementDisplayed(txtResult));
			//System.out.println("CalculatePage.isLoaded2");
		}

		@Override
		protected void load() {
			// TODO Auto-generated method stub
			System.out.println("CalculatePage.load = " + SuiteBase.Param.getProperty("siteURL")+"/2014/04/calc.html");
			SuiteBase.driver.get(SuiteBase.Param.getProperty("siteURL")+"/2014/04/calc.html");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(onlyTestingHome.isDisplayed());
		}
		
		public void clearResultTxt() {
			txtResult.sendKeys("testing");
			//System.out.println("testing PF clearResultTxt");
			SuiteBase.clearTxtBox(txtResult);
		}
		public void clickFirstNumber() {
			SuiteBase.clickOnElement(btnFirstNumbr);
		}
		public void clickPlus() {
			SuiteBase.clickOnElement(btnNumAction);
		}
		public void clickSecondNumber() {
			SuiteBase.clickOnElement(btnSecondNumbr);
		}
		public void clickEqualsTo() {
			SuiteBase.clickOnElement(btnEquals);
		}
		public String getResultTxt() {
			return SuiteBase.getTxtBox(txtResult);			 			
		}
		
}
