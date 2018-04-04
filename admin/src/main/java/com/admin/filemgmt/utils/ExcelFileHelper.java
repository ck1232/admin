package com.admin.filemgmt.utils;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;

public class ExcelFileHelper {
	private static final Logger logger = Logger.getLogger(ExcelFileHelper.class);
	
	public ExcelFileHelper() {}
		
	//if merged, return last cell column index
	public static HSSFWorkbook writeToFile(ResultSet resultSet){
		try {
			int rowNum = 0;
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("EXPORT");
			HSSFCellStyle style = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFillBackgroundColor(IndexedColors.CORAL.getIndex());
			style.setWrapText(true);
			style.setFont(font);
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			HSSFRow header = sheet.createRow(rowNum++);
			for (int i = 0; i < columnCount; i++){
				HSSFCell cell = header.createCell(i);
				cell.setCellStyle(style);
				cell.setCellValue(metaData.getColumnName(i+1));
			}
			style = workbook.createCellStyle();
			style.setWrapText(true);
			while (resultSet.next()){
				HSSFRow row = sheet.createRow(rowNum++);
				for (int i = 0; i < columnCount; i++){
					HSSFCell cell = row.createCell(i);
					cell.setCellStyle(style);
					cell.setCellValue(resultSet.getString(i+1));
				}
			}
			for (int i = 0; i < columnCount; i++){
				sheet.autoSizeColumn(i);
			}
            
			return workbook;
		} catch (Exception e) {
			logger.error("ExcelFileHelper", e);
			e.printStackTrace();
		}
		return null;
	}
	
	
}
