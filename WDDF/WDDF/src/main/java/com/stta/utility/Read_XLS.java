package com.stta.utility;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.stta.TestSuiteBase.Globals;



public class Read_XLS {	
	public  String filelocation;
	public  FileInputStream ipstr = null;
	public  FileOutputStream opstr =null;
	private  HSSFWorkbook wb = null;
	private XSSFWorkbook xWB = null;
	private  HSSFSheet ws = null;
	private int dataProviderIterations;
	
	public Read_XLS(String filelocation) {
		//System.out.println(" Read_XLS.Read_XLS");
		System.out.println(filelocation);
		this.filelocation=filelocation;
		try {
			ipstr = new FileInputStream(filelocation);
			wb = new HSSFWorkbook(ipstr);
			ws = wb.getSheetAt(0);
			ipstr.close();
			
						
		} catch (Exception e) {			
			e.printStackTrace();
		} 
		
	}
	
	//To retrieve No Of Rows from .xls file's sheets.
	public int retrieveNoOfRows(String wsName){	
		System.out.println(" Read_XLS.retrieveNoOfRows");
		int sheetIndex=wb.getSheetIndex(wsName);
		if(sheetIndex==-1)
			return 0;
		else{
			ws = wb.getSheetAt(sheetIndex);
			int rowCount=ws.getLastRowNum()+1;		
			return rowCount;		
		}
	}
	
	//To retrieve No Of Columns from .cls file's sheets.
	public int retrieveNoOfCols(String wsName){
		System.out.println("Read_XLS.retrieveNoOfCols");
		int sheetIndex=wb.getSheetIndex(wsName);
		if(sheetIndex==-1)
			return 0;
		else{
			ws = wb.getSheetAt(sheetIndex);
			int colCount=ws.getRow(0).getLastCellNum();			
			return colCount;
		}
	}
	
	//To retrieve SuiteToRun and CaseToRun flag of test suite and test case.
	public String retrieveToRunFlag(String wsName, String colName, String rowName){
		System.out.println("Read_XLS.retrieveToRunFlag " + wsName);
		int sheetIndex=wb.getSheetIndex(wsName);
		if(sheetIndex==-1){
			System.out.println("null");
			return null;
		}
		else{
			int rowNum = retrieveNoOfRows(wsName);
			int colNum = retrieveNoOfCols(wsName);
			int colNumber=-1;
			int rowNumber=-1;			
			
			HSSFRow Suiterow = ws.getRow(0);				
			
			for(int i=0; i<colNum; i++){
				if(Suiterow.getCell(i).getStringCellValue().equals(colName.trim())){
					colNumber=i;					
				}					
			}
			
			if(colNumber==-1){
				return "";				
			}
			
			
			for(int j=0; j<rowNum; j++){
				HSSFRow Suitecol = ws.getRow(j);				
				if(Suitecol.getCell(0).getStringCellValue().equals(rowName.trim())){
					rowNumber=j;	
				}					
			}
			
			if(rowNumber==-1){
				return "";				
			}
			
			HSSFRow row = ws.getRow(rowNumber);
			HSSFCell cell = row.getCell(colNumber);
			if(cell==null){
				return "";
			}
			String value = cellToString(cell);
			
			Path p = Paths.get(this.filelocation);
			System.out.println(p.getFileName().toString());
			//Globals.GBL_CurrentXLSFileName = p.getFileName().toString();
			System.out.println(p.getFileName().toString()  + "  -   " + Globals.GBL_SuitListFileName);
			if(p.getFileName().toString().equalsIgnoreCase(Globals.GBL_SuitListFileName)) {
				System.out.println(" equal ");
				HSSFCell cellSuitXLName = row.getCell(3);
				System.out.println(cellSuitXLName);
				if(cellSuitXLName!=null){
					Globals.GBL_SuiteFileName = cellToString(cellSuitXLName);
					System.out.println(Globals.GBL_SuiteFileName);
				}
			}						
			return value;			
		}			
	}
	
	//To retrieve DataToRun flag of test data.
	public String[] retrieveToRunFlagTestData(String wsName, String colName){
		System.out.println("Read_XLS.retrieveToRunFlagTestData");
		int sheetIndex=wb.getSheetIndex(wsName);
		if(sheetIndex==-1)
			return null;
		else{
			int rowNum = retrieveNoOfRows(wsName);
			int colNum = retrieveNoOfCols(wsName);
			int colNumber=-1;
					
			
			HSSFRow Suiterow = ws.getRow(0);				
			String data[] = new String[rowNum-1];
			for(int i=0; i<colNum; i++){
				if(Suiterow.getCell(i).getStringCellValue().equals(colName.trim())){
					colNumber=i;					
				}					
			}
			
			if(colNumber==-1){
				return null;				
			}
			
			for(int j=0; j<rowNum-1; j++){
				HSSFRow Row = ws.getRow(j+1);
				if(Row==null){
					data[j] = "";
				}
				else{
					HSSFCell cell = Row.getCell(colNumber);
					if(cell==null){
						data[j] = "";
					}
					else{
						String value = cellToString(cell);
						data[j] = value;	
					}	
				}
			}
			
			return data;			
		}			
	}
	
	//To retrieve test data from test case data sheets.
	public Object[][] retrieveTestData(String wsName){
		System.out.println("Read_XLS.retrieveTestData");
		int sheetIndex=wb.getSheetIndex(wsName);
		if(sheetIndex==-1)
			return null;
		else{
				int rowNum = retrieveNoOfRows(wsName);
				int colNum = retrieveNoOfCols(wsName);
		
				Object data[][] = new Object[rowNum-1][colNum-2];
		
				for (int i=0; i<rowNum-1; i++){
					HSSFRow row = ws.getRow(i+1);
					for(int j=0; j< colNum-2; j++){					
						if(row==null){
							data[i][j] = "";
						}
						else{
							HSSFCell cell = row.getCell(j);	
					
							if(cell==null){
								data[i][j] = "";							
							}
							else{
								cell.setCellType(Cell.CELL_TYPE_STRING);
								String value = cellToString(cell);
								data[i][j] = value;						
							}
						}
					}				
				}			
				return data;		
		}
	
	}		
	
	
	public  String cellToString(HSSFCell cell){
		//System.out.println("Read_XLS.cellToString");
		int type;
		Object result;
		type = cell.getCellType();			
		switch (type){
			case 0 :
				result = cell.getNumericCellValue();
				break;
				
			case 1 : 
				result = cell.getStringCellValue();
				break;
				
			default :
				throw new RuntimeException("Unsupportd cell.");			
		}
		return result.toString();
	}
	
	//To write result In test data and test case list sheet.
	public boolean writeResult(String wsName, String colName, int rowNumber, String Result){
		System.out.println("Read_XLS.writeResult");
		try{
			int sheetIndex=wb.getSheetIndex(wsName);
			if(sheetIndex==-1)
				return false;			
			int colNum = retrieveNoOfCols(wsName);
			int colNumber=-1;
					
			
			HSSFRow Suiterow = ws.getRow(0);			
			for(int i=0; i<colNum; i++){				
				if(Suiterow.getCell(i).getStringCellValue().equals(colName.trim())){
					colNumber=i;					
				}					
			}
			
			if(colNumber==-1){
				return false;				
			}
			
			HSSFRow Row = ws.getRow(rowNumber);
			HSSFCell cell = Row.getCell(colNumber);
			if (cell == null)
		        cell = Row.createCell(colNumber);			
			
			cell.setCellValue(Result);
			
			opstr = new FileOutputStream(filelocation);
			wb.write(opstr);
			opstr.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	//To write result In test suite list sheet.
	public boolean writeResult(String wsName, String colName, String rowName, String Result){
		System.out.println("Read_XLS.writeResult");
		try{
			int rowNum = retrieveNoOfRows(wsName);
			int rowNumber=-1;
			int sheetIndex=wb.getSheetIndex(wsName);
			if(sheetIndex==-1)
				return false;			
			int colNum = retrieveNoOfCols(wsName);
			int colNumber=-1;
					
			
			HSSFRow Suiterow = ws.getRow(0);			
			for(int i=0; i<colNum; i++){				
				if(Suiterow.getCell(i).getStringCellValue().equals(colName.trim())){
					colNumber=i;					
				}					
			}
			
			if(colNumber==-1){
				return false;				
			}
			
			for (int i=0; i<rowNum-1; i++){
				HSSFRow row = ws.getRow(i+1);				
				HSSFCell cell = row.getCell(0);	
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String value = cellToString(cell);	
				if(value.equals(rowName)){
					rowNumber=i+1;
					break;
				}
			}		
			
			HSSFRow Row = ws.getRow(rowNumber);
			HSSFCell cell = Row.getCell(colNumber);
			if (cell == null)
		        cell = Row.createCell(colNumber);			
			
			cell.setCellValue(Result);
			
			opstr = new FileOutputStream(filelocation);
			wb.write(opstr);
			opstr.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public LinkedHashMap<Integer, Map<String, String>> getTestData(String wsName, String TCColumnName, String tcName) {
		/*
		try {
			ipstr = new FileInputStream(filelocation);
			wb = new HSSFWorkbook(ipstr);
			ws = wb.getSheetAt(0);
			ipstr.close();
		} catch (Exception e) {			
			e.printStackTrace();
		} 
		*/
		//---------Not Required-------------
		System.out.println(" Read_XLS.getTestData");
		LinkedHashMap<Integer, Map<String, String>> td = null;
		Map<String, String> mapData = null;	
		boolean ifTCFound = false;
		String TCColName = Globals.GBL_TestCaseNameColumn;
		String TCFlagColName = Globals.GBL_TestDataFlagColumn;
		int sheetIndex=wb.getSheetIndex(wsName);
		int manualTCColNo=0;
		int flagTCColNo=0;
		int itcPointer = 0;
		int itrColNo =0;
		int itrRowNo =0;
		int itr = 0;
		System.out.println(wsName+ " sheet ind = "+sheetIndex+" Path = "+ filelocation);
		if(sheetIndex==-1)
			return null;
		else{
			int rowNum = retrieveNoOfRows(wsName);
			int colNum = retrieveNoOfCols(wsName);
			int colNumber=-1;
			
			HSSFRow row = ws.getRow(0);
			System.out.println(wsName+ " row ="+rowNum+ " collum ="+colNum);
			
			for(itrColNo = 0; itrColNo <colNum; itrColNo++) {
				if(row.getCell(itrColNo).toString().equalsIgnoreCase(TCColName)){
					manualTCColNo= itrColNo;
					System.out.println("TC name col found");
					break;
				}				
			}
			for(itrColNo = 0; itrColNo <colNum; itrColNo++) {
				if(row.getCell(itrColNo).toString().equalsIgnoreCase(TCFlagColName)){
					flagTCColNo= itrColNo;
					System.out.println("TC Flag col found");
					break;
				}
			}
			
			System.out.println(itrColNo+"manualTCColNo="+manualTCColNo+"flagTCColNo="+flagTCColNo);
			td = new LinkedHashMap<Integer, Map<String, String>>();
			
			for(; itrRowNo<rowNum; itrRowNo++){
				HSSFRow row1 = ws.getRow(itrRowNo);
				if(row1==null){
					break;
				}else{
					HSSFCell TCCell = row1.getCell(manualTCColNo);
					HSSFCell TCRunFlag = row1.getCell(flagTCColNo);
					if(TCCell == null || TCRunFlag ==null){
						
					}
					else if(row1.getCell(manualTCColNo).toString().equalsIgnoreCase(tcName) && row1.getCell(flagTCColNo).toString().equalsIgnoreCase("Y")){					
						ifTCFound = true;					
						System.out.println("TC name found" + colNum);
						mapData = new HashMap<String, String>();
						for(int i = 0;i<colNum; i++) {
							HSSFCell cell = row1.getCell(i);
							if(cell==null){
								mapData.put(row.getCell(i).toString(), "");	
								
							}
							else{
								String value = cellToString(cell);
								mapData.put(row.getCell(i).toString(), value);	
								//System.out.println(colNum + " store data for = "  + " - " + row.getCell(i).toString() + mapData.get(row.getCell(i).toString()));
							}
							//System.out.println(colNum + " store data for = "  + " - " + row.getCell(i).toString() + mapData.get(row.getCell(i).toString()));							
								
						}
					td.put(itr, mapData);
					//System.out.println(td);
					itr++;
					}
				}
			}
		}
		System.out.println(td);
		return td;		
	}
	
	public  Object[][] setDataProvider(LinkedHashMap<Integer, Map<String, String>> dataObj) {
		// Converting Map to Object[][] to handle @DataProvider
		System.out.println("Read_XLS.setDataProvider");
		Object[][] tdObject = null;
		try{
			if (dataObj != null) {
				tdObject = new Object[dataObj.size()][2];
				int count = 0;
				for (Map.Entry<Integer, Map<String, String>> entry : dataObj.entrySet()) {
					tdObject[count][0] = entry.getKey();
					tdObject[count][1] = entry.getValue();
					count++;
				}
				dataProviderIterations = tdObject.length;
				return tdObject;
			}
		}catch(Exception e){
			//ThrowException.Report(TYPE.EXCEPTION, "Exception occurred while setting DataProvider : " + e.getMessage());
		}	
		return new Object[][] { {} }; // As null cannot be returned in DataProvider
	}
	

}
