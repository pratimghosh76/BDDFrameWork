package com.stta.utility;

import java.util.LinkedHashMap;
import java.util.Map;

public class SuiteUtility {	
	
	public static boolean checkToRunUtility(Read_XLS xls, String sheetName, String ToRun, String testSuite){
		System.out.println("SuiteUtility.checkToRunUtility");
		boolean Flag = false;		
		if(xls.retrieveToRunFlag(sheetName,ToRun,testSuite).equalsIgnoreCase("y")){
			Flag = true;
		}
		else{
			Flag = false;
		}
		return Flag;		
	}
	
	public static String[] checkToRunUtilityOfData(Read_XLS xls, String sheetName, String ColName){	
		System.out.println("SuiteUtility.checkToRunUtilityOfData");
		return xls.retrieveToRunFlagTestData(sheetName,ColName);		 	
	}
 
	public static Object[][] GetTestDataUtility(Read_XLS xls, String sheetName){
		System.out.println("SuiteUtility.GetTestDataUtility");
	 	return xls.retrieveTestData(sheetName);	
	}
	
	public static boolean WriteResultUtility(Read_XLS xls, String sheetName, String ColName, int rowNum, String Result){
		System.out.println("SuiteUtility.WriteResultUtility");
		return xls.writeResult(sheetName, ColName, rowNum, Result);		 	
	}
 
	public static boolean WriteResultUtility(Read_XLS xls, String sheetName, String ColName, String rowName, String Result){
		System.out.println("SuiteUtility.WriteResultUtility");
		return xls.writeResult(sheetName, ColName, rowName, Result);		 	
	}
	public static LinkedHashMap<Integer, Map<String,String>> GetGenTestDataUtility(Read_XLS xls, String SheetName, String TCColName, String TCName){
		System.out.println("SuiteUtility.GetGenTestDataUtility");
	 	return xls.getTestData(SheetName, TCColName, TCName);	
	}
	public static Object[][] SetGenTestDataUtility(Read_XLS xls, LinkedHashMap<Integer, Map<String,String>> objTestData){
		System.out.println("SuiteUtility.SetGenTestDataUtility");
	 	return xls.setDataProvider(objTestData);	
	}
}

