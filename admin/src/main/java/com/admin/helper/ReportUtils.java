package com.admin.helper;

import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;

import com.admin.helper.vo.ExcelColumn;
import com.admin.helper.vo.ReportMapping;
import com.admin.helper.vo.ExcelColumn.ColumnStyle;
import com.admin.helper.vo.ExcelColumn.ColumnType;

public class ReportUtils {
	public static <T>void writeData(Sheet sheet, List<T> list, ReportMapping reportMapping, String name){
		int rowNum = sheet.getPhysicalNumberOfRows();
		int startRow = rowNum+2;
		Row row = sheet.createRow(rowNum++);
		ExcelUtils excelUtils = new ExcelUtils(sheet.getWorkbook());
		writeHeader(row, reportMapping, excelUtils);
		if(list != null && list.size() > 0 && sheet != null 
				&& reportMapping != null && reportMapping.size() > 0){
			reportMapping = setCellStyleForColumn(excelUtils, reportMapping);
			for(T obj : list){
				boolean isTotal = false;
				Object monthObj = GeneralUtils.getObjectProprty(obj, name);
				if(monthObj != null && monthObj instanceof String){
					String month = monthObj.toString();
					if("total".equalsIgnoreCase(month)){
						sheet.createRow(rowNum++);
						isTotal = true;
					}
				}
				row = sheet.createRow(rowNum++);
				writeRow(row, obj, reportMapping, isTotal, excelUtils, sheet.getWorkbook(), list.size(), startRow);
			}
		}
		setAutoSizeColumn(sheet, reportMapping);
	}
	
	/*public static <T>void writeData(Sheet sheet, LinkedList<T> list, ReportMapping reportMapping){
		int rowNum = sheet.getPhysicalNumberOfRows();
		Row row = sheet.createRow(rowNum++);
		ExcelUtils excelUtils = new ExcelUtils(sheet.getWorkbook());
		writeHeader(row, reportMapping, excelUtils);
		if(list != null && list.size() > 0 && sheet != null 
				&& reportMapping != null && reportMapping.size() > 0){
			reportMapping = setCellStyleForColumn(excelUtils, reportMapping);
			for(T obj : list){
				row = sheet.createRow(rowNum++);
				writeRow(row, obj, reportMapping);
			}
			setAutoSizeColumn(sheet, reportMapping);
		}
	}*/
	
	public static void writeBlankRows(Sheet sheet, int numOfRows){
		int rowNum = sheet.getPhysicalNumberOfRows();
		if(numOfRows > 0){
			for(int i=0;i<numOfRows;i++){
				sheet.createRow(rowNum++);
			}
		}
	}
	
	public static void writeRow(Sheet sheet, String text, int offset, ColumnStyle...columnStyles){
		int rowNum = sheet.getPhysicalNumberOfRows();
		int cellNum = 0;
		if(offset >= 0){
			cellNum += offset;
		}
		ExcelUtils excelUtils = new ExcelUtils(sheet.getWorkbook());
		
		CellStyle cs = excelUtils.generateCellStyle(ColumnType.Text, GeneralUtils.convertArrayToLinkedList(columnStyles));
		Row row = sheet.createRow(rowNum++);
		Cell cell = row.createCell(cellNum);
		cell.setCellValue(text);
		cell.setCellStyle(cs);
	}
	
	private static void setAutoSizeColumn(Sheet sheet, ReportMapping reportMapping){
		if(reportMapping != null && reportMapping.size() > 0){
			for(int i=0;i<reportMapping.size();i++){
				sheet.autoSizeColumn(i);
			}
		}
	}
	
	private static void writeRow(Row row, Object object, ReportMapping reportMapping, boolean isTotal, ExcelUtils excelUtils, Workbook workbook, int numOfRecords, int startRowNum){
		int cellNum = 0;
		for(String header : reportMapping.getMapping().keySet()){
			ExcelColumn column = reportMapping.getMapping().get(header);
			Cell cell = row.createCell(cellNum++);
			CellStyle cs = column.getCellStyle();
			if(isTotal){
				CellStyle csTotal = workbook.createCellStyle();
				csTotal.cloneStyleFrom(cs);
				csTotal = excelUtils.getBorderTopBtmDouble(csTotal);
				cell.setCellStyle(csTotal);
			}else{
				cell.setCellStyle(cs);
			}
			
			Object value = GeneralUtils.getObjectProprty(object, column.getVariableName());
			if(value == null){
				cell.setCellValue("");
			}else{
				switch(column.getColumnType()){
					case Text: cell.setCellValue((String)value);
						break;
					case Date_MonthYear:
					case Date_Year:
					case Date: 
						{
							if(value instanceof Date){
								Date date = (Date)value;
								cell.setCellValue(date);
							}else{
								cell.setCellValue("");
							}
						}
						break;
					case Percentage:
					case Number:
					case Decimal:
					case China_Money:
					case Money:
						{
							if(isTotal) {
								int colInt = cell.getColumnIndex();
								String colName = CellReference.convertNumToColString(colInt);
								int rowFrom = startRowNum;
								int rowTo = rowFrom + numOfRecords - 1;
								cell.setCellType(Cell.CELL_TYPE_FORMULA);
								cell.setCellFormula("SUM(" + colName + rowFrom +":" + colName + rowTo +")");
							}else {
								Number num = (Number)value;
								cell.setCellValue(num.doubleValue());
							}
						}
						break;
					default:cell.setCellValue(value.toString());break;
				}
			}
		}
	}
	
	private static void writeHeader(Row row, ReportMapping reportMapping, ExcelUtils excelUtils){
		int cellNum = 0;
		for(String header : reportMapping.getMapping().keySet()){
			Cell cell = row.createCell(cellNum++);
			cell.setCellValue(header);
			CellStyle cs = excelUtils.generateCellStyle(ColumnType.Text, GeneralUtils.convertArrayToLinkedList(ColumnStyle.Header));
			cell.setCellStyle(cs);
		}
	}
	
	private static ReportMapping setCellStyleForColumn(ExcelUtils excelUtils, ReportMapping reportMapping){
		for(String header : reportMapping.getMapping().keySet()){
			ExcelColumn column = reportMapping.getMapping().get(header);
			CellStyle cs = excelUtils.generateCellStyle(column.getColumnType(), column.getColumnStyleList());
			column.setCellStyle(cs);
		}
		return reportMapping;
	}
}
