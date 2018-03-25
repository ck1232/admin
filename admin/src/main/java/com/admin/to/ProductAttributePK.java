package com.admin.to;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;
@Embeddable
public class ProductAttributePK implements Serializable{
	@Transient
	private static final long serialVersionUID = 1L;

	

	@Column(name="sku", nullable=false)
	private String sku;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ProductSubOptionTO.class)
	@JoinColumn(name="product_suboption_id", nullable=false)
	@ForeignKey( name = "none" )
	private ProductSubOptionTO productSubOptionTO;
	
	public ProductAttributePK() {
	}

	public ProductAttributePK(String sku,
			ProductSubOptionTO productSubOptionTO) {
		this.sku = sku;
		this.productSubOptionTO = productSubOptionTO;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public ProductSubOptionTO getProductSubOptionTO() {
		return productSubOptionTO;
	}

	public void setProductSubOptionTO(ProductSubOptionTO productSubOptionTO) {
		this.productSubOptionTO = productSubOptionTO;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((productSubOptionTO == null) ? 0 : productSubOptionTO
						.hashCode());
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductAttributePK other = (ProductAttributePK) obj;
		if (productSubOptionTO == null) {
			if (other.productSubOptionTO != null)
				return false;
		} else if (!productSubOptionTO.equals(other.productSubOptionTO))
			return false;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.equals(other.sku))
			return false;
		return true;
	}
}