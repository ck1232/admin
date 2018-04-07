package com.admin.product.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class ResponseProductVO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer productId;

    private String productName;

    private BigDecimal unitAmt;

    private Integer weight;

    private Long subCategoryId;

    private String description;

    private String productCode;
    
    private List<String> tags;
    
    private String productInfo;
    
    private List<String> productAttributeList;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getUnitAmt() {
		return unitAmt;
	}

	public void setUnitAmt(BigDecimal unitAmt) {
		this.unitAmt = unitAmt;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

	public List<String> getProductAttributeList() {
		return productAttributeList;
	}

	public void setProductAttributeList(List<String> productAttributeList) {
		this.productAttributeList = productAttributeList;
	}
	
}
