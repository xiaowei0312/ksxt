package com.sxsram.ssm.test;

import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

public class PoiExcelTest {
	@Test
	private void excelImportTest() throws Exception {
		int lastRowNum = 0, lastColumnNum = 0;

		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("d:/test.xls"));
		Workbook book = null;
		// 得到Excel工作簿对象
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		// 得到Excel工作表对象
		HSSFSheet sheet = wb.getSheetAt(1);
		if(sheet == null){
			System.out.println("Empty Excel.");
			return;
		}
		// 得到总行数
		lastRowNum = sheet.getLastRowNum();
		// 获取单元格数据
		HSSFCell cell = null;
		for (int i = 0; i <= lastRowNum; i++) {
			// 得到Excel工作表的行
			HSSFRow row = sheet.getRow(i);
			if (row == null) {
				break;
			}
			lastColumnNum = row.getLastCellNum();
			//System.out.println(lastColumnNum);
			for (int j = 0; j < lastColumnNum; j++) {
				cell = row.getCell((short) j);
				if(cell == null)
					break;
				System.out.print(getCellStringValue(cell) + ",");
			}
			System.out.println();
		}
	}

	public String getCellStringValue(HSSFCell cell) {
		String cellValue = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING:// 字符串类型
			cellValue = cell.getStringCellValue().trim();
			if (cellValue.equals("") || cellValue.length() <= 0)
				cellValue = "";
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:// 数值类型
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA:// 公式
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
			cellValue = String.valueOf(cell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_BLANK:
			cellValue = "";
			break;
		case HSSFCell.CELL_TYPE_BOOLEAN:
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			break;
		default:
			break;
		}
		return cellValue;
	}

	public static void main(String[] args) {
		PoiExcelTest test = new PoiExcelTest();
		try {
			test.excelImportTest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
