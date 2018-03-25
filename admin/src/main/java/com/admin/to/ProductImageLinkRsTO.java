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
@Table(name = "image_link_rs")
public class ProductImageLinkRsTO extends ImageLinkRsTO {
	
	@Transient
	private static final long serialVersionUID = 1L;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "ref_id", nullable=false)
	@Where(clause="ref_type='product'")
	@ForeignKey( name = "none" )
    private ProductTO productTO;

	public ProductTO getProductTO() {
		return productTO;
	}

	public void setProductTO(ProductTO productTO) {
		this.productTO = productTO;
	}

}
