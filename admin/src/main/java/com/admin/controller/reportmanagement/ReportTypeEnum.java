package com.admin.controller.reportmanagement;


public enum ReportTypeEnum {
	SALARY("Salary"),
	BONUS("Bonus"),
	CHINA_STOCK("China Stock"),
	EXPENSE("Expense"),
	INVOICE("Invoice"),
	GRANT("Grant"),
	SUMMARY("Summary");
	
	private String name;

	private ReportTypeEnum(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	public static String getEnum(String enumString){
		if(ReportTypeEnum.values() != null && ReportTypeEnum.values().length > 0){
			for(ReportTypeEnum reportTypeEnum : ReportTypeEnum.values()){
				if(reportTypeEnum.toString().equals(enumString)){
					return reportTypeEnum.getName();
				}
			}
		}
		return null;
	}
	
	public static ReportTypeEnum getEnumFromName(String name){
		if(ReportTypeEnum.values() != null && ReportTypeEnum.values().length > 0){
			for(ReportTypeEnum reportTypeEnum : ReportTypeEnum.values()){
				if(reportTypeEnum.getName().equals(name)){
					return reportTypeEnum;
				}
			}
		}
		return null;
	}
}
