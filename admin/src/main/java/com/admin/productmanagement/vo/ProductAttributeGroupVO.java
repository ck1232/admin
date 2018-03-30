package com.admin.productmanagement.vo;

import java.io.Serializable;
import java.util.LinkedList;

public class ProductAttributeGroupVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String sku;
	private LinkedList<ProductSubOptionVO> subOptionList;
	private String productCode;
	private boolean displayInd;
	private boolean hideByDefault;
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public LinkedList<ProductSubOptionVO> getSubOptionList() {
		return subOptionList;
	}
	public void setSubOptionList(LinkedList<ProductSubOptionVO> subOptionList) {
		this.subOptionList = subOptionList;
	}
	public boolean isDisplayInd() {
		return displayInd;
	}
	public void setDisplayInd(boolean displayInd) {
		this.displayInd = displayInd;
	}
	public boolean isHideByDefault() {
		return hideByDefault;
	}
	public void setHideByDefault(boolean hideByDefault) {
		this.hideByDefault = hideByDefault;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
}
