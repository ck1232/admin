package com.admin.to;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;

@Entity
@DynamicUpdate
@Table(name = "product_sub_category")
public class ProductSubCategoryTO extends BaseTO {

	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "sub_category_id", nullable=false)
	private Long subCategoryId;
	
	@Column(name = "name", nullable=false)
    private String name;
	
	@ManyToOne
    @JoinColumn(name = "category_id")
	@ForeignKey( name = "none" )
    private ProductCategoryTO productCategoryTO;
	
	@Column(name = "display_ind", nullable=false)
    private String displayInd;

	public Long getSubCategoryId() {
		return subCategoryId;
	}

	public void setSubCategoryId(Long subCategoryId) {
		this.subCategoryId = subCategoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ProductCategoryTO getProductCategoryTO() {
		return productCategoryTO;
	}

	public void setProductCategoryTO(ProductCategoryTO productCategoryTO) {
		this.productCategoryTO = productCategoryTO;
	}

	public String getDisplayInd() {
		return displayInd;
	}

	public void setDisplayInd(String displayInd) {
		this.displayInd = displayInd;
	}
}
