package com.admin.controller.reportmanagement.vo;

import java.math.BigDecimal;

public class PaymentSummaryReportVO {
	private String month;
	private BigDecimal cashAmt;
	private BigDecimal chequeAmt;
	private BigDecimal giroAmt;
	private BigDecimal transferAmt;
	private BigDecimal paidByDirectorAmt;
	private BigDecimal notPaidAmt;
	private BigDecimal badDebtAmt;
	private BigDecimal totalAmt;
	
	public PaymentSummaryReportVO(String month) {
		setMonth(month);
		setCashAmt(BigDecimal.ZERO);
		setChequeAmt(BigDecimal.ZERO);
		setGiroAmt(BigDecimal.ZERO);
		setTransferAmt(BigDecimal.ZERO);
		setPaidByDirectorAmt(BigDecimal.ZERO);
		setNotPaidAmt(BigDecimal.ZERO);
		setBadDebtAmt(BigDecimal.ZERO);
		setTotalAmt(BigDecimal.ZERO);
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public BigDecimal getCashAmt() {
		return cashAmt;
	}
	public void setCashAmt(BigDecimal cashAmt) {
		this.cashAmt = cashAmt;
	}
	public BigDecimal getChequeAmt() {
		return chequeAmt;
	}
	public void setChequeAmt(BigDecimal chequeAmt) {
		this.chequeAmt = chequeAmt;
	}
	public BigDecimal getGiroAmt() {
		return giroAmt;
	}
	public void setGiroAmt(BigDecimal giroAmt) {
		this.giroAmt = giroAmt;
	}
	public BigDecimal getTransferAmt() {
		return transferAmt;
	}
	public void setTransferAmt(BigDecimal transferAmt) {
		this.transferAmt = transferAmt;
	}
	public BigDecimal getPaidByDirectorAmt() {
		return paidByDirectorAmt;
	}
	public void setPaidByDirectorAmt(BigDecimal paidByDirectorAmt) {
		this.paidByDirectorAmt = paidByDirectorAmt;
	}
	public BigDecimal getNotPaidAmt() {
		return notPaidAmt;
	}
	public void setNotPaidAmt(BigDecimal notPaidAmt) {
		this.notPaidAmt = notPaidAmt;
	}
	public BigDecimal getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}
	public BigDecimal getBadDebtAmt() {
		return badDebtAmt;
	}
	public void setBadDebtAmt(BigDecimal badDebtAmt) {
		this.badDebtAmt = badDebtAmt;
	}
	
	
	
	
	
}
