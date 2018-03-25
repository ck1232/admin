package com.admin.to;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;

@Entity
@DynamicUpdate
@Table(name = "product_attribute")
public class ProductAttributeTO extends BaseTO {
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="product_attribute_id", nullable=false)
	private Long productAttributeId;

	@Column(name="sku", nullable=false)
	private String sku;
	
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ProductSubOptionTO.class)
	@JoinColumn(name="product_suboption_id", nullable=true)
	@ForeignKey( name = "none" )
	private ProductSubOptionTO productSubOptionTO;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="product_id", nullable=false)
	@ForeignKey( name = "none" )
	private ProductTO productTO;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="product_option_id", nullable=false)
	@ForeignKey( name = "none" )
	private ProductOptionTO productOptionTO;
	
	

	@Column(name="display_ind", nullable=false)
	private String displayInd;
	
	public Long getProductAttributeId() {
		return productAttributeId;
	}

	public void setProductAttributeId(Long productAttributeId) {
		this.productAttributeId = productAttributeId;
	}

	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public ProductSubOptionTO getProductSubOptionTO() {
		return this.productSubOptionTO;
	}

	public void setProductSubOptionTO(ProductSubOptionTO productSubOptionTO) {
		this.productSubOptionTO = productSubOptionTO;
	}

	public ProductTO getProductTO() {
		return productTO;
	}

	public void setProductTO(ProductTO productTO) {
		this.productTO = productTO;
	}

	public ProductOptionTO getProductOptionTO() {
		return productOptionTO;
	}

	public void setProductOptionTO(ProductOptionTO productOptionTO) {
		this.productOptionTO = productOptionTO;
	}

	public String getDisplayInd() {
		return displayInd;
	}

	public void setDisplayInd(String displayInd) {
		this.displayInd = displayInd;
	}
}
