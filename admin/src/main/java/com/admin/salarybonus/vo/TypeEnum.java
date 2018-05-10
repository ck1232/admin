package com.admin.salarybonus.vo;

public enum TypeEnum {
	
	SALARY("Salary"),
	BONUS("Bonus");
	
	private String type;
	
	TypeEnum(String type){
		this.type = type;
	}
	public String getType(){
		return type;
	}
	
	public static String getEnum(String enumString){
		if(TypeEnum.values() != null && TypeEnum.values().length > 0){
			for(TypeEnum employmentEnum : TypeEnum.values()){
				if(employmentEnum.toString().equals(enumString)){
					return employmentEnum.getType();
				}
			}
		}
		return null;
	}
}
