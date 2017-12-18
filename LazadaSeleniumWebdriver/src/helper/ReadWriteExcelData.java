package helper;

import java.io.*;
import java.text.*;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.*;

public class ReadWriteExcelData {
	public FileInputStream fis = null;
	public FileOutputStream fos = null;
	private XSSFWorkbook workbook = null;
	private XSSFSheet sheet = null;
	private XSSFRow row = null;
	private XSSFCell cell = null;
	public String filePath;
	
	public ReadWriteExcelData(String filePath) throws Exception {
		this.filePath = filePath;
		fis = new FileInputStream(filePath);
		workbook = new XSSFWorkbook(fis);
		fis.close();
	}
	
	public String getCellData(String sheetName, String colName, int rowNum) {
		try {
			int col_Num = -1;
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);
			for(int i = 0; i < row.getLastCellNum(); i++) {
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim())) {
					col_Num = i;
				}
			}
			
			row = sheet.getRow(rowNum - 1);
			cell = row.getCell(col_Num);
			
			if(cell.getCellTypeEnum()==CellType.STRING) {
				return cell.getStringCellValue();
			}
			else if(cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {
				String cellValue = String.valueOf(cell.getNumericCellValue());
				if(HSSFDateUtil.isCellDateFormatted(cell)) {
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					Date date = cell.getDateCellValue();
					cellValue = df.format(date);
				}
				return cellValue;
			}
			else if(cell.getCellTypeEnum() == CellType.BLANK) {
				return "";
			}
			else {
				return String.valueOf(cell.getBooleanCellValue());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return "Row " + rowNum + " or column" + colName + " does not exist in Excel";
		}
	}
	
	public String getCellData(String sheetName, int colNum, int rowNum) {
		try {
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(rowNum);
			cell = row.getCell(colNum);

			if(cell.getCellTypeEnum()==CellType.STRING) {
				return cell.getStringCellValue();
			}
			else if(cell.getCellTypeEnum() == CellType.NUMERIC || cell.getCellTypeEnum() == CellType.FORMULA) {
				String cellValue = String.valueOf(cell.getNumericCellValue());
				if(HSSFDateUtil.isCellDateFormatted(cell)) {
					DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
					Date date = cell.getDateCellValue();
					cellValue = df.format(date);
				}
				return cellValue;
			}
			else if(cell.getCellTypeEnum() == CellType.BLANK) {
				return "";
			}
			else {
				return String.valueOf(cell.getBooleanCellValue());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return "Row " + rowNum + " or column" + colNum + " does not exist in Excel";
		}
	}
	
	public boolean setCellData(String sheetName, int colNum, int rowNum, String value) {
		try {
			fis = new FileInputStream(filePath);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(rowNum);
			if(row == null) {
				row = sheet.createRow(rowNum);
			}
			
			cell = row.getCell(colNum);
			if(cell == null) {
				cell = row.createCell(colNum);
			}
			
			cell.setCellValue(value);
			
			fos = new FileOutputStream(filePath);
			workbook.write(fos);
			fos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean setCellData(String sheetName, String colName, int rowNum, String value) {
		try {
			fis = new FileInputStream(filePath);
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);
			
			int col_Num = -1;
			
			for(int i = 0; i < row.getLastCellNum(); i++) {
				if(row.getCell(i).getStringCellValue().trim().equals(colName)) {
					col_Num = i;
					//System.out.println("==Index of column " + colName + " is " + i);
				}
			}
			if(col_Num==-1)
				return false;
			
			sheet.autoSizeColumn(col_Num);
			row = sheet.getRow(rowNum - 1);
			if(row == null) {
				row = sheet.createRow(rowNum - 1);
			}
			
			cell = row.getCell(col_Num);
			if(cell == null) {
				cell = row.createCell(col_Num);
			}
			
			cell.setCellValue(value);
			
			fos = new FileOutputStream(filePath);
			workbook.write(fos);
			fos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	//return the row count in a sheet
	public int getRowCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if(index == -1) {
			return 0;
		}
		else {
			sheet = workbook.getSheet(sheetName);
			int rowCount = sheet.getLastRowNum() + 1;
			
			return rowCount;
		}
	}
	
	//return the column in a sheet
	public int getColumnCount(String sheetName) {
		int index = workbook.getSheetIndex(sheetName);
		if(index == -1) {
			return 0;
		}
		else {
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(0);
			int colCount = row.getLastCellNum();
			
			return colCount;
		}
	}
	
	
	public int getCellRowNum(String sheetName, String colName, String cellValue) {
		for(int i = 1; i <= getRowCount(sheetName); i++) {
			if(getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
				return i;
			}
		}
		return -1;
	}
	
}
