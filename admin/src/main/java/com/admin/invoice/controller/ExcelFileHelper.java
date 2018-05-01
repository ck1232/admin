package com.admin.invoice.controller;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

import com.admin.invoice.vo.InvoiceVO;

public class ExcelFileHelper {
	private static final Logger logger = Logger.getLogger(ExcelFileHelper.class);
	
	public ExcelFileHelper() {}
	
	public InvoiceVO readFromFile(byte[] file) {
		InvoiceVO invoice = new InvoiceVO();
		InputStream is = null;
		
		try {
            is = new ByteArrayInputStream(file);
//            logger.debug("input stream done");
            Workbook workbook = WorkbookFactory.create(is);
//            logger.debug("open workbook");
            Sheet sheet = workbook.getSheet("Sheet1");
//            logger.debug("open sheet");
            
            /* Getting Messenger */
            int[] messengerIndex = findIndex(sheet, "Messrs");
//            logger.debug("find");
            int messengerContent = getLastCell(sheet, messengerIndex);
            Cell messenger = sheet.getRow(messengerIndex[0]).getCell(messengerContent+1);
            String cellvalue = messenger.getRichStringCellValue().getString();
            messenger = sheet.getRow(messengerIndex[0]+2).getCell(messengerContent+1);
            cellvalue += messenger.getRichStringCellValue().getString();
            
            logger.info(messengerIndex[0]+ ", " +  messengerIndex[1] + ", " + messengerContent);
            logger.info(cellvalue);
            invoice.setMessenger(cellvalue);
            
            /* Getting Invoice Id */
            int[] invoiceIndex = findIndexWithPattern(sheet, "INVOICE");
            Cell invoiceId = sheet.getRow(invoiceIndex[0]).getCell(invoiceIndex[1]);
            cellvalue = invoiceId.getRichStringCellValue().getString();
            int i = 0;
            while (i < cellvalue.length() && !Character.isDigit(cellvalue.charAt(i))) i++;
//            logger.info("index: " + i);
            if(i < cellvalue.length()) {
            	logger.info(cellvalue.substring(i, cellvalue.length()));
            	invoice.setInvoiceId(Long.valueOf(cellvalue.substring(i, cellvalue.length())));
            }
            
            /* Getting Invoice Date */
            int[] dateIndex = findIndex(sheet, "Date:");
            Cell invoiceDate = sheet.getRow(dateIndex[0]).getCell(dateIndex[1]+1);
            try{
            	Date celldatevalue = invoiceDate.getDateCellValue();
            	logger.info(celldatevalue);
                invoice.setInvoiceDate(celldatevalue);
            }catch(Exception e) {
            	logger.info("Error getting date");
//            	e.printStackTrace();
            	try{
	            	cellvalue = invoiceDate.getRichStringCellValue().getString();
	            	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
	            	invoice.setInvoiceDate(formatter.parse(cellvalue));
            	}catch(Exception ex){
            		logger.info("Error getting date again");
            	}
            }
            
            
            /* Getting Invoice Total Price */
            int[] totalPriceIndex = findIndex(sheet, "TOTAL");
            Cell invoiceTotalPrice = sheet.getRow(totalPriceIndex[0]).getCell(totalPriceIndex[1]+1);
            Double cellnumvalue = invoiceTotalPrice.getNumericCellValue();
            
            logger.info(messengerIndex[0]+ ", " +  messengerIndex[1] + ", " + messengerContent);
            logger.info(cellnumvalue);
            invoice.setTotalAmt(BigDecimal.valueOf(cellnumvalue));
            return invoice;
        } catch (Exception e) {
        	logger.debug("error :", e);
            e.printStackTrace();
        } finally {
            try{
                if(is != null) is.close();
            } catch (Exception ex){
                 
            }
        }
		return null;
	}
	
	private static int[] findIndex(Sheet sheet, String cellContent) {
//		logger.debug("into find index");
		int[] index = new int[2];
	    for (Row row : sheet) {
	        for (Cell cell : row) {
//	        	logger.debug("loop "+ cell);
	            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
	            	String cellvalue = cell.getRichStringCellValue().getString();
	                if (cellvalue.trim().equals(cellContent)) {
	                	index[0] = row.getRowNum();
	                	index[1] = cell.getColumnIndex();
	                    return index;  
	                }
	            }
	        }
	    }               
	    return index;
	}
	
	private static int[] findIndexWithPattern(Sheet sheet, String pattern) {
		int[] index = new int[2];
	    for (Row row : sheet) {
	        for (Cell cell : row) {
	            if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
	            	String cellvalue = cell.getRichStringCellValue().getString();
	                if (cellvalue.trim().contains(pattern)) {
	                	index[0] = row.getRowNum();
	                	index[1] = cell.getColumnIndex();
	                    return index;  
	                }
	            }
	        }
	    }               
	    return index;
	}
	
	//if merged, return last cell column index
	private static int getLastCell(Sheet sheet, int[] cell) {
        for(int i = 0; i < sheet.getNumMergedRegions(); ++i)
        {
            CellRangeAddress range = sheet.getMergedRegion(i);
            if (range.getFirstRow() <= cell[0] && range.getLastRow() >= cell[0] 
            		&& range.getFirstColumn() == cell[1] && range.getLastColumn() != cell[1])
                return range.getLastColumn();
            if(range.getFirstRow() != cell[0]) {
            	break;
            }
        }
        return cell[1];
	}
	
	private HSSFCellStyle createTextStyle(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("Arial");
		style.setFont(font);
		return style;
	}
	
	private HSSFCellStyle createTextStyleMoney(HSSFWorkbook workbook){
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("Arial");
		style.setFont(font);
		HSSFDataFormat format = workbook.createDataFormat();
		style.setDataFormat(format.getFormat("0.00"));
		return style;
	}
	
	public HSSFWorkbook writeToFile(File inputfile, List<InvoiceVO> invoiceList, String statementPeriod){
		try {
			FileInputStream file = new FileInputStream(inputfile);
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFCellStyle cellStyle = createTextStyle(workbook);
			HSSFCellStyle cellStyleMoney = createTextStyleMoney(workbook);
			workbook.setMissingCellPolicy(Row.CREATE_NULL_AS_BLANK);
			HSSFSheet sheet = workbook.getSheet("Sheet1");
			Cell cell = null;
			cell = sheet.getRow(5).getCell(2);
			cell.setCellValue("Company name");
			/* Writing Messenger */
			int[] messengerIndex = findIndex(sheet, "Messrs");
            int messengerContent = getLastCell(sheet, messengerIndex);
            cell = sheet.getRow(messengerIndex[0]).getCell(messengerContent+1);
            cell.setCellValue(invoiceList.get(0).getMessenger());
            /* Writing Statement of Account As Of */
            int[] statementDateIndex = findIndexWithPattern(sheet, "Statement of Account as of");
            cell = sheet.getRow(statementDateIndex[0]).getCell(statementDateIndex[1]);
            cell.setCellValue("Statement of Account as " + statementPeriod);
            /* Writing content */
            int[] dateHeaderIndex = findIndex(sheet, "Date");
            int[] invoiceNoHeaderIndex = findIndex(sheet, "Invoice");
            int[] statusHeaderIndex = findIndex(sheet, "Status");
//            int[] chequeNoHeaderIndex = findIndex(sheet, "Cheque No.");
            int[] amountHeaderIndex = findIndex(sheet, "($)    Amount");
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM");
            int row = dateHeaderIndex[0] + 1;
            Double totalAmount = 0.0;
            for(InvoiceVO invoice : invoiceList) {
//            	logger.debug("row:"+row);
            	if(sheet.getRow(row) == null){
            		sheet.createRow(row);
            	}
            	cell = sheet.getRow(row).getCell(dateHeaderIndex[1]);
            	cell.setCellStyle(cellStyle);
            	cell.setCellValue(formatter.format(invoice.getInvoiceDate()));
            	
            	cell = sheet.getRow(row).getCell(invoiceNoHeaderIndex[1]);
            	cell.setCellValue(invoice.getInvoiceId());
            	cell.setCellStyle(cellStyle);
            	
            	cell = sheet.getRow(row).getCell(statusHeaderIndex[1]);
            	cell.setCellValue(invoice.getStatus());
            	cell.setCellStyle(cellStyle);
            	/*if(invoice.getChequeid() != null) {
            		cell = sheet.getRow(row).getCell(chequeNoHeaderIndex[1]);
            		cell.setCellValue(invoice.getChequeid());
            	}*/
            	
            	cell = sheet.getRow(row).getCell(amountHeaderIndex[1]);
            	cell.setCellValue(invoice.getTotalAmt().doubleValue());
            	cell.setCellStyle(cellStyleMoney);
            	totalAmount += invoice.getTotalAmt().doubleValue();
            	row++;
            }
            /* Writing Invoice Total Price */
//            int[] totalPriceIndex = findIndex(sheet, "TOTAL");
            if(sheet.getRow(row) == null){
            	sheet.createRow(row);
            }
            cell = sheet.getRow(row).getCell(9);
            cell.setCellValue("TOTAL");
            cell.setCellStyle(cellStyle);
            
            cell = sheet.getRow(row++).getCell(10);
            cell.setCellValue(totalAmount);
            cell.setCellStyle(cellStyleMoney);
			return workbook;
		} catch (Exception e) {
			logger.error("error",e);
			e.printStackTrace();
		}
		return null;
	}
	
	
}
