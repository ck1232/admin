package com.admin.employee.vo;

public enum EmploymentTypeEnum {
	
	PART_LOCAL("Part Time Local"),
	FULL_LOCAL("Full Time Local"),
	PART_FW("Part Time Foreigner"),
	FULL_FW("Full Time Foreigner"),
	DIRECTOR("Director");
	
	private String type;
	
	EmploymentTypeEnum(String type){
		this.type = type;
	}
	public String getType(){
		return type;
	}
	
	public static String getEnum(String enumString){
		if(EmploymentTypeEnum.values() != null && EmploymentTypeEnum.values().length > 0){
			for(EmploymentTypeEnum employmentEnum : EmploymentTypeEnum.values()){
				if(employmentEnum.toString().equals(enumString)){
					return employmentEnum.getType();
				}
			}
		}
		return null;
	}
}
