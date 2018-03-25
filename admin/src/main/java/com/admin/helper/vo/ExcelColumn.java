package com.admin.helper.vo;

import java.io.Serializable;
import java.util.LinkedList;

import org.apache.poi.ss.usermodel.CellStyle;

public class ExcelColumn implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String variableName;
	private ColumnType columnType;
	private LinkedList<ColumnStyle> columnStyleList;
	//cellStyle is computed automatically, please dont set cellStyle
	private CellStyle cellStyle;
	
	public ExcelColumn(String variableName, ColumnType columnType) {
		this.variableName = variableName;
		this.columnType = columnType;
		this.columnStyleList = new LinkedList<ColumnStyle>();
	}
	
	public ExcelColumn(String variableName, ColumnType columnType, ColumnStyle... styleList) {
		this.variableName = variableName;
		this.columnType = columnType;
		this.columnStyleList = new LinkedList<ColumnStyle>();
		if(styleList != null){
			for(ColumnStyle style : styleList){
				columnStyleList.add(style);
			}
		}
	}
	
	public ExcelColumn(String variableName, ColumnType columnType, LinkedList<ColumnStyle> styleList) {
		this.variableName = variableName;
		this.columnType = columnType;
		this.columnStyleList = styleList;
	}
	
	public CellStyle getCellStyle() {
		return cellStyle;
	}

	public void setCellStyle(CellStyle cellStyle) {
		this.cellStyle = cellStyle;
	}
	
	public enum ColumnType{
		Text, Money, China_Money, Date, Percentage, Decimal, Number, Date_MonthYear, Date_Year;
	}
	
	public enum ColumnStyle{
		Header, Bold, Underline, Italic, Bg_blue, Bg_skyBlue, Border_Top_Btm_Double;
	}


	public String getVariableName() {
		return variableName;
	}


	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}


	public ColumnType getColumnType() {
		return columnType;
	}


	public void setColumnType(ColumnType columnType) {
		this.columnType = columnType;
	}


	public LinkedList<ColumnStyle> getColumnStyleList() {
		return columnStyleList;
	}


	public void setColumnStyleList(LinkedList<ColumnStyle> columnStyleList) {
		this.columnStyleList = columnStyleList;
	}
}


