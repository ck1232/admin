package com.admin.controller.reportmanagement.vo;

import java.math.BigDecimal;

public class ExpenseSummaryReportVO {
	private String month;
	private BigDecimal stockAmt;
	private BigDecimal badDebtAmt;
	private BigDecimal chinaStockAmt;
	private BigDecimal subConAmt;
	private BigDecimal vehicleFuelAmt;
	private BigDecimal vehicleRoadTaxAmt;
	private BigDecimal vehicleRepairAmt;
	private BigDecimal vehicleParkingERPAmt;
	private BigDecimal vehicleInsuranceAmt;
	private BigDecimal officeExpenseAmt;
	private BigDecimal assetEquipmentAmt;
	private BigDecimal assetVehicleAmt;
	private BigDecimal rentExpenseAmt;
	private BigDecimal mealExpenseAmt;
	private BigDecimal entertainmentAmt;
	private BigDecimal feeTaxesAmt;
	private BigDecimal commissionAmt;
	private BigDecimal workerInsuranceAmt;
	private BigDecimal dividendsAmt;
	private BigDecimal payToDirectorAmt;
	private BigDecimal telephoneAmt;
	private BigDecimal transportAmt;
	private BigDecimal totalAmt;
	
	public ExpenseSummaryReportVO(String expMonth) {
		setMonth(expMonth);
		setStockAmt(BigDecimal.ZERO);
		setBadDebtAmt(BigDecimal.ZERO);
		setChinaStockAmt(BigDecimal.ZERO);
		setSubConAmt(BigDecimal.ZERO);
		setVehicleFuelAmt(BigDecimal.ZERO);
		setVehicleRoadTaxAmt(BigDecimal.ZERO);
		setVehicleRepairAmt(BigDecimal.ZERO);
		setVehicleParkingERPAmt(BigDecimal.ZERO);
		setVehicleInsuranceAmt(BigDecimal.ZERO);
		setOfficeExpenseAmt(BigDecimal.ZERO);
		setAssetEquipmentAmt(BigDecimal.ZERO);
		setAssetVehicleAmt(BigDecimal.ZERO);
		setRentExpenseAmt(BigDecimal.ZERO);
		setMealExpenseAmt(BigDecimal.ZERO);
		setEntertainmentAmt(BigDecimal.ZERO);
		setFeeTaxesAmt(BigDecimal.ZERO);
		setWorkerInsuranceAmt(BigDecimal.ZERO);
		setCommissionAmt(BigDecimal.ZERO);
		setTotalAmt(BigDecimal.ZERO);
		setDividendsAmt(BigDecimal.ZERO);
		setTelephoneAmt(BigDecimal.ZERO);
		setTransportAmt(BigDecimal.ZERO);
		setPayToDirectorAmt(BigDecimal.ZERO);
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public BigDecimal getStockAmt() {
		return stockAmt;
	}
	public BigDecimal getBadDebtAmt() {
		return badDebtAmt;
	}
	public void setBadDebtAmt(BigDecimal badDebtAmt) {
		this.badDebtAmt = badDebtAmt;
	}
	public void setStockAmt(BigDecimal stockAmt) {
		this.stockAmt = stockAmt;
	}
	public BigDecimal getChinaStockAmt() {
		return chinaStockAmt;
	}
	public void setChinaStockAmt(BigDecimal chinaStockAmt) {
		this.chinaStockAmt = chinaStockAmt;
	}
	public BigDecimal getSubConAmt() {
		return subConAmt;
	}
	public void setSubConAmt(BigDecimal subConAmt) {
		this.subConAmt = subConAmt;
	}
	public BigDecimal getVehicleFuelAmt() {
		return vehicleFuelAmt;
	}
	public void setVehicleFuelAmt(BigDecimal vehicleFuelAmt) {
		this.vehicleFuelAmt = vehicleFuelAmt;
	}
	public BigDecimal getVehicleRoadTaxAmt() {
		return vehicleRoadTaxAmt;
	}
	public void setVehicleRoadTaxAmt(BigDecimal vehicleRoadTaxAmt) {
		this.vehicleRoadTaxAmt = vehicleRoadTaxAmt;
	}
	public BigDecimal getVehicleRepairAmt() {
		return vehicleRepairAmt;
	}
	public void setVehicleRepairAmt(BigDecimal vehicleRepairAmt) {
		this.vehicleRepairAmt = vehicleRepairAmt;
	}
	public BigDecimal getVehicleParkingERPAmt() {
		return vehicleParkingERPAmt;
	}
	public void setVehicleParkingERPAmt(BigDecimal vehicleParkingERPAmt) {
		this.vehicleParkingERPAmt = vehicleParkingERPAmt;
	}
	public BigDecimal getVehicleInsuranceAmt() {
		return vehicleInsuranceAmt;
	}
	public void setVehicleInsuranceAmt(BigDecimal vehicleInsuranceAmt) {
		this.vehicleInsuranceAmt = vehicleInsuranceAmt;
	}
	public BigDecimal getOfficeExpenseAmt() {
		return officeExpenseAmt;
	}
	public void setOfficeExpenseAmt(BigDecimal officeExpenseAmt) {
		this.officeExpenseAmt = officeExpenseAmt;
	}
	public BigDecimal getAssetEquipmentAmt() {
		return assetEquipmentAmt;
	}
	public void setAssetEquipmentAmt(BigDecimal assetEquipmentAmt) {
		this.assetEquipmentAmt = assetEquipmentAmt;
	}
	public BigDecimal getAssetVehicleAmt() {
		return assetVehicleAmt;
	}
	public void setAssetVehicleAmt(BigDecimal assetVehicleAmt) {
		this.assetVehicleAmt = assetVehicleAmt;
	}
	public BigDecimal getRentExpenseAmt() {
		return rentExpenseAmt;
	}
	public void setRentExpenseAmt(BigDecimal rentExpenseAmt) {
		this.rentExpenseAmt = rentExpenseAmt;
	}
	public BigDecimal getMealExpenseAmt() {
		return mealExpenseAmt;
	}
	public void setMealExpenseAmt(BigDecimal mealExpenseAmt) {
		this.mealExpenseAmt = mealExpenseAmt;
	}
	public BigDecimal getEntertainmentAmt() {
		return entertainmentAmt;
	}
	public void setEntertainmentAmt(BigDecimal entertainmentAmt) {
		this.entertainmentAmt = entertainmentAmt;
	}
	public BigDecimal getFeeTaxesAmt() {
		return feeTaxesAmt;
	}
	public void setFeeTaxesAmt(BigDecimal feeTaxesAmt) {
		this.feeTaxesAmt = feeTaxesAmt;
	}
	public BigDecimal getCommissionAmt() {
		return commissionAmt;
	}
	public void setCommissionAmt(BigDecimal commissionAmt) {
		this.commissionAmt = commissionAmt;
	}
	public BigDecimal getWorkerInsuranceAmt() {
		return workerInsuranceAmt;
	}
	public void setWorkerInsuranceAmt(BigDecimal workerInsuranceAmt) {
		this.workerInsuranceAmt = workerInsuranceAmt;
	}
	public BigDecimal getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}
	public BigDecimal getDividendsAmt() {
		return dividendsAmt;
	}
	public void setDividendsAmt(BigDecimal dividendsAmt) {
		this.dividendsAmt = dividendsAmt;
	}
	public BigDecimal getPayToDirectorAmt() {
		return payToDirectorAmt;
	}
	public void setPayToDirectorAmt(BigDecimal payToDirectorAmt) {
		this.payToDirectorAmt = payToDirectorAmt;
	}
	public BigDecimal getTelephoneAmt() {
		return telephoneAmt;
	}
	public void setTelephoneAmt(BigDecimal telephoneAmt) {
		this.telephoneAmt = telephoneAmt;
	}
	public BigDecimal getTransportAmt() {
		return transportAmt;
	}
	public void setTransportAmt(BigDecimal transportAmt) {
		this.transportAmt = transportAmt;
	}
}
