package com.artoftesting.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.artoftesting.base.TestBase;

public class ExcelUtil {
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;

	public static String[][] getExcelDataIn2DArray(String Path, String SheetName) throws Exception {
		
		String[][] excelDataArray = null;
		try {

			FileInputStream ExcelFile = new FileInputStream(Path);

			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);

			int numOfColumns = ExcelWSheet.getRow(0).getPhysicalNumberOfCells();
			int numOfRows = ExcelWSheet.getPhysicalNumberOfRows();

			excelDataArray = new String[numOfRows][numOfColumns];

			for (int i = 0; i < numOfRows; i++) {

				for (int j = 0; j < numOfColumns; j++) {
					try {
						excelDataArray[i][j] = ExcelWSheet.getRow(i).getCell(j).getStringCellValue();
					} catch (NullPointerException e) {
						excelDataArray[i][j] = null;
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return excelDataArray;
	}

	public static HashMap<String, String> getExcelDataHashMap(String Path, String SheetName) throws Exception {
		HashMap<String, String> excel_data = new HashMap<>();

		FileInputStream ExcelFile = new FileInputStream(Path);

		ExcelWBook = new XSSFWorkbook(ExcelFile);
		ExcelWSheet = ExcelWBook.getSheet(SheetName);

		Iterator<Row> rowIterator = ExcelWSheet.iterator();

		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			Cell keyCell = row.getCell(0); // Assuming key is in the first column
			Cell valueCell = row.getCell(1); // Assuming value is in the second column

			String key = keyCell.getStringCellValue();
			String value = valueCell.getStringCellValue();

			excel_data.put(key, value);
		}

		return excel_data;

	}

	public static HashMap<String, String> testcasedata(String Path, String SheetName, String testcaseid)
			throws Exception {
		String[][] excelDataArray = null;
		int testcaseid_column = 0;
		HashMap<String, String> excel_data = new HashMap<>();
		try {

			FileInputStream ExcelFile = new FileInputStream(Path);

			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);

			int numOfColumns = ExcelWSheet.getRow(0).getPhysicalNumberOfCells();
			int numOfRows = ExcelWSheet.getPhysicalNumberOfRows();

			excelDataArray = new String[numOfRows][numOfColumns];

			for (int i = 0; i < numOfRows; i++) {

				for (int j = 0; j < numOfColumns; j++) {
					try {
						excelDataArray[i][j] = ExcelWSheet.getRow(i).getCell(j).getStringCellValue();
					} catch (NullPointerException e) {
						excelDataArray[i][j] = null;
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < excelDataArray.length - 1; i++) {
			if (testcaseid_column == 1)
				break;
			for (int j = 0; j < excelDataArray[0].length; j++) {
				if (testcaseid.equalsIgnoreCase(excelDataArray[i][j])) {
					testcaseid_column = j;
					break;
				}

			}

		}
		for (int z = 0; z < excelDataArray.length - 1; z++) {
			String key = excelDataArray[z][0];
			String value = excelDataArray[z][testcaseid_column];

			excel_data.put(key, value);
		}
		return excel_data;

	}
public static Object[][] getExcelDataprovider(String tcid) throws Exception {
	String Path ="C:\\Users\\acer\\git\\booking\\Resource\\TabbedForm.xlsx";
	
	String testcaseid =tcid;
	 int testcaseid_column =0;
		Object[][] excelDataArray = null;
		Object[][] excelDataProvider = null;
		int numOfRows =0;
		try {

			FileInputStream ExcelFile = new FileInputStream(Path);

			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet("Sheet1");

			int numOfColumns = ExcelWSheet.getRow(0).getPhysicalNumberOfCells();
			numOfRows = ExcelWSheet.getPhysicalNumberOfRows();

			excelDataArray = new String[numOfRows][numOfColumns];

			for (int i = 0; i < numOfRows; i++) {

				for (int j = 0; j < numOfColumns; j++) {
					try {
						excelDataArray[i][j] = ExcelWSheet.getRow(i).getCell(j).getStringCellValue();
					} catch (NullPointerException e) {
						excelDataArray[i][j] = null;
					}
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int i =0;i<excelDataArray.length-1;i++)
    	{
			
			if(testcaseid_column==1)
				break;
//    		for(int j =0;j<excelDataArray[0].length;j++)
//    		{
//    			 if(testcaseid.equalsIgnoreCase(excelDataArray[i][j]))
//    			 {
//    					 testcaseid_column =j;	 
//    					 break;
//    			 }
//    			 
//    						    	
//    		}
    		
    	}
		excelDataProvider = new String[numOfRows-1][2];

		for (int i = 0; i < numOfRows-1; i++) {
			excelDataProvider[i][0]=excelDataArray[i+1][0];
			excelDataProvider[i][1]=excelDataArray[i+1][1];
			System.out.println(excelDataProvider[i][0]);
			System.out.println(excelDataProvider[i][1]);
		}
		return excelDataProvider;
	}
	

}
