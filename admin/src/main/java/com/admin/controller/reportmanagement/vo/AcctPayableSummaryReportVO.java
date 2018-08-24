package com.admin.controller.reportmanagement.vo;

import java.math.BigDecimal;

public class AcctPayableSummaryReportVO {
	private String month;
	private BigDecimal expensePurchaseAmt;
	private BigDecimal chinaStockPurchaseAmt;
	
	public AcctPayableSummaryReportVO(String month) {
		setMonth(month);
		setExpensePurchaseAmt(BigDecimal.ZERO);
		setChinaStockPurchaseAmt(BigDecimal.ZERO);
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public BigDecimal getExpensePurchaseAmt() {
		return expensePurchaseAmt;
	}
	public void setExpensePurchaseAmt(BigDecimal expensePurchaseAmt) {
		this.expensePurchaseAmt = expensePurchaseAmt;
	}
	public BigDecimal getChinaStockPurchaseAmt() {
		return chinaStockPurchaseAmt;
	}
	public void setChinaStockPurchaseAmt(BigDecimal chinaStockPurchaseAmt) {
		this.chinaStockPurchaseAmt = chinaStockPurchaseAmt;
	}
	
	
}
