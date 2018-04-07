package com.admin.to;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Where;
@Entity
@DynamicUpdate
public class CategoryImageLinkRsTO extends ImageLinkRsTO {
	
	@Transient
	private static final long serialVersionUID = 1L;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "ref_id", nullable=false)
	@Where(clause="ref_type='product_category'")
	@ForeignKey( name = "none" )
    private ProductCategoryTO productCategoryTO;

	public ProductCategoryTO getProductCategoryTO() {
		return productCategoryTO;
	}

	public void setProductCategoryTO(ProductCategoryTO productCategoryTO) {
		this.productCategoryTO = productCategoryTO;
	}
}
