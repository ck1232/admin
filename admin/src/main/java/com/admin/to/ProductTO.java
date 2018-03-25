package com.admin.to;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Where;

@Entity
@DynamicUpdate
@Table(name = "product")
public class ProductTO extends BaseTO {
	@Transient
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "product_id", nullable=false)
	private Long productId;
	
	@Column(name = "product_name", nullable=false)
    private String productName;
	
	@Column(name = "unit_amt", nullable=false)
    private BigDecimal unitAmt;
	
	@Column(name = "weight", nullable=false)
    private Integer weight;

	@Column(name = "sub_category_id", nullable=true)
    private Long subCategoryId;

	@Column(name = "description", nullable=true)
    private String description;
	
	@Column(name = "product_code", nullable=false)
    private String productCode;

	@Column(name = "paypay_id", nullable=true)
    private String paypalId;
	
	@OneToOne(mappedBy="productTO", cascade = CascadeType.ALL)
	@Where(clause="delete_ind='N'")
	@ForeignKey( name = "none" )
	private ProductSpecificationTO productSpecificationTO;
	
	@OneToMany(mappedBy="productTO", cascade = CascadeType.ALL)
	@Where(clause="delete_ind='N'")
	@ForeignKey( name = "none" )
	private List<ProductSubOptionTO> productionSubOptionTOList;
	
	@OneToMany(mappedBy="productTO", cascade = CascadeType.ALL)//FetchType.EAGER is default
	@Where(clause="delete_ind='N'")
	@ForeignKey( name = "none")
	private List<ProductAttributeTO> productAttributeTOList;
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
		      name="product_tags_rs", 
		      joinColumns=@JoinColumn(name="product_id"),
		      inverseJoinColumns=@JoinColumn(name="product_tags_id", referencedColumnName="tags_id"))
	@ForeignKey( name = "none")
	private List<ProductTagsTO> productTagsTOList;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="productTO", cascade=CascadeType.ALL)
	@Where(clause="delete_ind='N'")
	@ForeignKey( name = "none")
	private List<ProductImageLinkRsTO> imageLinkRsTOList;
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
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

	public String getPaypalId() {
		return paypalId;
	}

	public void setPaypalId(String paypalId) {
		this.paypalId = paypalId;
	}

	public ProductSpecificationTO getProductSpecificationTO() {
		return productSpecificationTO;
	}

	public void setProductSpecificationTO(ProductSpecificationTO productSpecificationTO) {
		this.productSpecificationTO = productSpecificationTO;
	}

	public List<ProductSubOptionTO> getProductionSubOptionTOList() {
		return productionSubOptionTOList;
	}

	public void setProductionSubOptionTOList(
			List<ProductSubOptionTO> productionSubOptionTOList) {
		this.productionSubOptionTOList = productionSubOptionTOList;
	}

	public List<ProductAttributeTO> getProductAttributeTOList() {
		return productAttributeTOList;
	}

	public void setProductAttributeTOList(
			List<ProductAttributeTO> productAttributeTOList) {
		this.productAttributeTOList = productAttributeTOList;
	}
	
	public List<ProductTagsTO> getProductTagsTOList() {
		return productTagsTOList;
	}

	public void setProductTagsTOList(List<ProductTagsTO> productTagsTOList) {
		this.productTagsTOList = productTagsTOList;
	}

	public List<ProductImageLinkRsTO> getImageLinkRsTOList() {
		return imageLinkRsTOList;
	}

	public void setImageLinkRsTOList(List<ProductImageLinkRsTO> imageLinkRsTOList) {
		this.imageLinkRsTOList = imageLinkRsTOList;
	}

}
