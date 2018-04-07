package com.admin.product.vo;

import java.io.Serializable;

public class ProductAttributeVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long productAttributeId;
	private Long productId;
	private String sku;
	private Long subOptionId;
	private ProductSubOptionVO subOptionVO;
	private Long optionId;
	private String displayInd;
	public ProductAttributeVO() {}
	public ProductAttributeVO(String sku) {
		this.sku = sku;
	}
	public ProductAttributeVO(String sku, String displayInd) {
		this.sku = sku;
		this.displayInd = displayInd;
	}
	public ProductAttributeVO(String sku, Long subOptionId,
			ProductSubOptionVO subOptionVO, String displayInd) {
		this.sku = sku;
		this.subOptionId = subOptionId;
		this.subOptionVO = subOptionVO;
		this.displayInd = displayInd;
	}
	public Long getProductAttributeId() {
		return productAttributeId;
	}
	public void setProductAttributeId(Long productAttributeId) {
		this.productAttributeId = productAttributeId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Long getSubOptionId() {
		return subOptionId;
	}
	public void setSubOptionId(Long subOptionId) {
		this.subOptionId = subOptionId;
	}
	public Long getOptionId() {
		return optionId;
	}
	public void setOptionId(Long optionId) {
		this.optionId = optionId;
	}
	public String getDisplayInd() {
		return displayInd;
	}
	public void setDisplayInd(String displayInd) {
		this.displayInd = displayInd;
	}
	public ProductSubOptionVO getSubOptionVO() {
		return subOptionVO;
	}
	public void setSubOptionVO(ProductSubOptionVO subOptionVO) {
		this.subOptionVO = subOptionVO;
	}
}
