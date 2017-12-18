package helper;

import helper.ReadWriteExcelData;

public class TestExcelUtil {
	static ReadWriteExcelData read = null;
	
	public static Object[][] testData(String filePath, String sheetName) throws Exception{
		Object[][] excelData = null;
		read = new ReadWriteExcelData(filePath);
		
		int rows = read.getRowCount(sheetName);
		int cols = read.getColumnCount(sheetName);
		
		excelData = new Object[rows - 1][cols];
		
		for(int i = 1; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				excelData[i-1][j] = read.getCellData(sheetName, j, i);
			}
		}
		
		return excelData;
	}
	

	public static boolean writeResult(String filePath, String sheetName, String colName, String id_case, boolean result) throws Exception {
		ReadWriteExcelData writer = new ReadWriteExcelData(filePath);
		
		int rowNum = writer.getCellRowNum(sheetName, "ID_Testcase", id_case);
		//System.out.println("Row number of test case " + id_case + " is " + rowNum);
		
		if(result) {
			writer.setCellData(sheetName, colName, rowNum, "Pass");
		}
		else {
			writer.setCellData(sheetName, colName, rowNum, "Fail");
		}
		
		return true;
	}
}
