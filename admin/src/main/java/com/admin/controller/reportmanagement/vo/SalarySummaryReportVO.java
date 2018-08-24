package com.admin.controller.reportmanagement.vo;

import java.math.BigDecimal;

public class SalarySummaryReportVO {
	private String month; //just in case next time need to use
	private BigDecimal grossSalaryAmt;
	private BigDecimal overtimeAmt;
	private BigDecimal bonusAmt;
	private BigDecimal allowanceAmt;
	private BigDecimal medicalAmt;
	private BigDecimal employeeCPFAmt;
	private BigDecimal salaryPaidAmt;
	private BigDecimal employerCPFAmt;
	private BigDecimal cdacAmt;
	private BigDecimal sdlAmt;
	private BigDecimal fwlAmt;
	private BigDecimal totalRemuneration;
	
	public SalarySummaryReportVO(String month) {
		setMonth(month);
		setGrossSalaryAmt(BigDecimal.ZERO);
		setOvertimeAmt(BigDecimal.ZERO);
		setBonusAmt(BigDecimal.ZERO);
		setAllowanceAmt(BigDecimal.ZERO);
		setMedicalAmt(BigDecimal.ZERO);
		setEmployeeCPFAmt(BigDecimal.ZERO);
		setSalaryPaidAmt(BigDecimal.ZERO);
		setEmployerCPFAmt(BigDecimal.ZERO);
		setCdacAmt(BigDecimal.ZERO);
		setSdlAmt(BigDecimal.ZERO);
		setFwlAmt(BigDecimal.ZERO);
		setTotalRemuneration(BigDecimal.ZERO);
	}
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public BigDecimal getGrossSalaryAmt() {
		return grossSalaryAmt;
	}
	public void setGrossSalaryAmt(BigDecimal grossSalaryAmt) {
		this.grossSalaryAmt = grossSalaryAmt;
	}
	public BigDecimal getOvertimeAmt() {
		return overtimeAmt;
	}
	public void setOvertimeAmt(BigDecimal overtimeAmt) {
		this.overtimeAmt = overtimeAmt;
	}
	public BigDecimal getBonusAmt() {
		return bonusAmt;
	}
	public void setBonusAmt(BigDecimal bonusAmt) {
		this.bonusAmt = bonusAmt;
	}
	public BigDecimal getAllowanceAmt() {
		return allowanceAmt;
	}
	public void setAllowanceAmt(BigDecimal allowanceAmt) {
		this.allowanceAmt = allowanceAmt;
	}
	public BigDecimal getMedicalAmt() {
		return medicalAmt;
	}
	public void setMedicalAmt(BigDecimal medicalAmt) {
		this.medicalAmt = medicalAmt;
	}
	public BigDecimal getEmployeeCPFAmt() {
		return employeeCPFAmt;
	}
	public void setEmployeeCPFAmt(BigDecimal employeeCPFAmt) {
		this.employeeCPFAmt = employeeCPFAmt;
	}
	public BigDecimal getSalaryPaidAmt() {
		return salaryPaidAmt;
	}
	public void setSalaryPaidAmt(BigDecimal salaryPaidAmt) {
		this.salaryPaidAmt = salaryPaidAmt;
	}
	public BigDecimal getEmployerCPFAmt() {
		return employerCPFAmt;
	}
	public void setEmployerCPFAmt(BigDecimal employerCPFAmt) {
		this.employerCPFAmt = employerCPFAmt;
	}
	public BigDecimal getCdacAmt() {
		return cdacAmt;
	}
	public void setCdacAmt(BigDecimal cdacAmt) {
		this.cdacAmt = cdacAmt;
	}
	public BigDecimal getSdlAmt() {
		return sdlAmt;
	}
	public void setSdlAmt(BigDecimal sdlAmt) {
		this.sdlAmt = sdlAmt;
	}
	public BigDecimal getFwlAmt() {
		return fwlAmt;
	}
	public void setFwlAmt(BigDecimal fwlAmt) {
		this.fwlAmt = fwlAmt;
	}
	public BigDecimal getTotalRemuneration() {
		return totalRemuneration;
	}
	public void setTotalRemuneration(BigDecimal totalRemuneration) {
		this.totalRemuneration = totalRemuneration;
	}
	
	
	
}
