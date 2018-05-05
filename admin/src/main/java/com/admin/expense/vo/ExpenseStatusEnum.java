package com.admin.expense.vo;

public enum ExpenseStatusEnum {
	UNPAID("Unpaid"),
	PAID("Paid"),
	PAID_PARTIAL("PaidPartial"),
	ALL("All");
	private String status;
	
	ExpenseStatusEnum(String status){
		this.status = status;
	}
	public String getStatus(){
		return status;
	}
	
}
