package com.admin.controller.reportmanagement.vo;

import java.math.BigDecimal;

public class TotalIncomeSummaryVO {
	private String title;
	private String description;
	private BigDecimal income;
	private BigDecimal moneyReceived;
	private BigDecimal badDebt;
	private BigDecimal outstanding;
	
	public TotalIncomeSummaryVO(String title, String description) {
		setTitle(title);
		setDescription(description);
		setIncome(BigDecimal.ZERO);
		setMoneyReceived(BigDecimal.ZERO);
		setOutstanding(BigDecimal.ZERO);
		setBadDebt(BigDecimal.ZERO);
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getIncome() {
		return income;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
	}
	public BigDecimal getMoneyReceived() {
		return moneyReceived;
	}
	public void setMoneyReceived(BigDecimal moneyReceived) {
		this.moneyReceived = moneyReceived;
	}
	public BigDecimal getOutstanding() {
		return outstanding;
	}
	public void setOutstanding(BigDecimal outstanding) {
		this.outstanding = outstanding;
	}

	public BigDecimal getBadDebt() {
		return badDebt;
	}

	public void setBadDebt(BigDecimal badDebt) {
		this.badDebt = badDebt;
	}
	
	
}
