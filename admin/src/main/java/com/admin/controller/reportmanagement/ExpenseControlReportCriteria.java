package com.admin.controller.reportmanagement;

import java.util.Date;
import java.util.List;

public class ExpenseControlReportCriteria{
	
	private String startdateString;
	
	private String enddateString;
	
	private Date startDate;
	
	private Date endDate;
	
	private List<String> type;

	public String getStartdateString() {
		return startdateString;
	}

	public void setStartdateString(String startdateString) {
		this.startdateString = startdateString;
	}

	public String getEnddateString() {
		return enddateString;
	}

	public void setEnddateString(String enddateString) {
		this.enddateString = enddateString;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<String> getType() {
		return type;
	}

	public void setType(List<String> type) {
		this.type = type;
	}
	
//	private String year;
//
//	public String getYear() {
//		return year;
//	}
//
//	public void setYear(String year) {
//		this.year = year;
//	}
	
}
