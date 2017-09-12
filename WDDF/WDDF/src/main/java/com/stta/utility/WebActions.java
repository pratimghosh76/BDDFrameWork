package com.stta.utility;

import java.awt.Robot;
import java.io.File;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
//import org.apache.xmlbeans.impl.tool.Extension.Param;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.SessionNotFoundException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.stta.TestSuiteBase.SuiteBase;
import com.stta.TestSuiteBase.Globals;
//import com.sun.jna.platform.FileUtils;

public class WebActions {
	//public static WebDriver driver = null;
	public static Exception exception;
	private static Select objSelect;
	public static Robot robot;
	
	public static String captureScreenshot() {
		System.out.println("Screen capture method for " + Globals.GBL_TestCaseName);
		String fileName = null;
		try {
			Globals.GBL_strScreenshotsFolderPath = "./TestReport/"
					+ Globals.GBL_TestCaseName.replaceAll(" ", "_")
					+ "\\Screenshots";
			
			File screenShotDir = new File(Globals.GBL_strScreenshotsFolderPath);
			if (!new File(Globals.GBL_strScreenshotsFolderPath).exists())
				new File(Globals.GBL_strScreenshotsFolderPath).mkdirs();

			int randomInt = screenShotDir.listFiles().length;
			File scrFile = ((TakesScreenshot) SuiteBase.driver)
					.getScreenshotAs(OutputType.FILE);
			System.out.println("Take screenshot");
			fileName = Globals.GBL_TestCaseName + "_Itr"
					+ Globals.GBL_CurrentIterationNumber + "_" + randomInt
					+ ".png";
			System.out.println("File Name");
			FileUtils.copyFile(scrFile, new File(
					Globals.GBL_strScreenshotsFolderPath + "\\" + fileName));
			System.out.println("File copied");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "./" + Globals.GBL_TestCaseName.replaceAll(" ", "_")
				+ "\\Screenshots" + "\\" + fileName;
	}
	public static boolean hasQuit(WebDriver driver) {
        try {
            System.out.println(driver.getTitle());
            return false;
        } catch (SessionNotFoundException e) {
            return true;
        }
}
	
	
}
