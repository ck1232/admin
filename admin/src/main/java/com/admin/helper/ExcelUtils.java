package com.admin.helper;

import java.util.LinkedList;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

import com.admin.helper.vo.ExcelColumn.ColumnStyle;
import com.admin.helper.vo.ExcelColumn.ColumnType;

public class ExcelUtils {
	private DataFormat decimalDataFormat;
	private Workbook wb;
	public ExcelUtils(Workbook wb){
		this.wb = wb;
		decimalDataFormat = wb.createDataFormat();
	}
	
	public CellStyle generateCellStyle(ColumnType type, LinkedList<ColumnStyle> styleList){
		CellStyle cs = wb.createCellStyle();
		switch(type){
			case Date: cs = getDateDataFormat(cs);break;
			case Date_MonthYear: cs = getDateMonthYearDataFormat(cs);break;
			case Date_Year: cs = getDateYearDataFormat(cs);break;
			case Decimal:cs = getDecimalDataFormat(cs);break;
			case Money: cs = getCurrencyDataFormat(cs);break;
			case China_Money:cs = getChinaCurrencyDataFormat(cs);break;
			case Number: cs = getNumberDataFormat(cs);break;
			case Percentage:cs = getPercentageDataFormat(cs);break;
			case Text:
			default: break;
		}
		
		if(styleList != null){
			for(ColumnStyle style : styleList){
				switch(style){
					case Header :
					case Bg_skyBlue: cs = getSkyBlue(cs);break;
					case Bg_blue: cs = getBgBlue(cs);break;
					case Border_Top_Btm_Double : getBorderTopBtmDouble(cs);break;
					default:break;
				}
			}
		}
		
		if(styleList != null){
			Font font = wb.createFont();
			for(ColumnStyle style : styleList){
				switch(style){
					case Bold : font = getBoldStyle(font);break;
					case Underline : font = getUnderlineStyle(font);break;
					case Italic : font = getItalicStyle(font);break;
					case Header : font = getBoldStyle(font);break;
					default:break;
				}
			}
			cs.setFont(font);
		}
		return cs;
	}

	public CellStyle getBorderTopBtmDouble(CellStyle cs) {
		cs.setBorderTop(CellStyle.BORDER_THIN);
		cs.setBorderBottom(CellStyle.BORDER_DOUBLE);
		return cs;
	}

	private CellStyle getDateMonthYearDataFormat(CellStyle cs) {
		cs.setDataFormat(decimalDataFormat.getFormat(GeneralUtils.EXCEL_DATE_MONTH_YEAR_FORMAT));
		return cs;
	}

	private CellStyle getDateYearDataFormat(CellStyle cs) {
		cs.setDataFormat(decimalDataFormat.getFormat(GeneralUtils.EXCEL_DATE_YEAR_FORMAT));
		return cs;
	}

	private CellStyle getSkyBlue(CellStyle cs) {
		cs.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
		cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		return cs;
	}

	private CellStyle getBgBlue(CellStyle cs) {
		cs.setFillForegroundColor(HSSFColor.BLUE.index);
		cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		return cs;
	}

	private Font getItalicStyle(Font font) {
		font.setItalic(true);
		return font;
	}

	private Font getUnderlineStyle(Font font) {
		font.setUnderline(Font.U_SINGLE);
		return font;
	}

	private Font getBoldStyle(Font font) {
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		return font;
	}

	private CellStyle getPercentageDataFormat(CellStyle cs) {
		cs.setDataFormat(decimalDataFormat.getFormat("0.00%"));
		return cs;
	}

	private CellStyle getChinaCurrencyDataFormat(CellStyle cs) {
		cs.setDataFormat(decimalDataFormat.getFormat("¥#,##0.00"));
		return cs;
	}

	private CellStyle getDateDataFormat(CellStyle cs) {
		cs.setDataFormat(decimalDataFormat.getFormat(GeneralUtils.STANDARD_DATE_FORMAT));
		return cs;
	}

	private CellStyle getNumberDataFormat(CellStyle cs) {
		cs.setDataFormat(decimalDataFormat.getFormat("0"));
		return cs;
	}

	private CellStyle getDecimalDataFormat(CellStyle cs){
		cs.setDataFormat(decimalDataFormat.getFormat("0.00"));
		return cs;
	}
	
	private CellStyle getCurrencyDataFormat(CellStyle cs){
		cs.setDataFormat(decimalDataFormat.getFormat("$#,##0.00"));
		return cs;
	}
	
	
}
