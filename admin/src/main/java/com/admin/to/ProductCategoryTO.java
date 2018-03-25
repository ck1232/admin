package com.admin.to;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Where;

@Entity
@DynamicUpdate
@Table(name = "product_category")
public class ProductCategoryTO extends BaseTO {

	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "category_id", nullable=false)
	private Long categoryId;
	
	@Column(name = "category_name", nullable=false)
    private String categoryName;
	
	@Column(name = "is_parent", nullable=false)
    private String isParent;
	
	@Column(name = "display_ind", nullable=false)
    private String displayInd;
	
	@OneToMany(mappedBy="productCategoryTO", cascade = CascadeType.ALL)
	@ForeignKey( name = "none" )
	@Where(clause="delete_ind='N'")
	private List<ProductSubCategoryTO> productSubCategoryTOList;
	
	@OneToMany(mappedBy="productCategoryTO", cascade=CascadeType.ALL)
	@ForeignKey( name = "none" )
	@Where(clause="delete_ind='N'")
	private List<CategoryImageLinkRsTO> categoryImageLinkRsTOList;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public String getDisplayInd() {
		return displayInd;
	}

	public void setDisplayInd(String displayInd) {
		this.displayInd = displayInd;
	}

	public List<ProductSubCategoryTO> getProductSubCategoryTOList() {
		return productSubCategoryTOList;
	}

	public void setProductSubCategoryTOList(
			List<ProductSubCategoryTO> productSubCategoryTOList) {
		this.productSubCategoryTOList = productSubCategoryTOList;
	}

	public List<CategoryImageLinkRsTO> getCategoryImageLinkRsTOList() {
		return categoryImageLinkRsTOList;
	}

	public void setCategoryImageLinkRsTOList(List<CategoryImageLinkRsTO> categoryImageLinkRsTOList) {
		this.categoryImageLinkRsTOList = categoryImageLinkRsTOList;
	}
}
