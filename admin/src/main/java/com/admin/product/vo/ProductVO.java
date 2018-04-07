package com.admin.product.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.admin.file.vo.FileMetaVO;
import com.admin.file.vo.ImageLinkVO;
import com.admin.helper.vo.BaseVO;

public class ProductVO extends BaseVO implements Serializable {
    private Long productId;

    private String productName;

    private BigDecimal unitAmt;

    private Integer weight;

    private Long subCategoryId;

    private String description;

    private String productCode;

    private String paypalId;

    private List<ProductOptionVO> optionList;
    
    private LinkedList<FileMetaVO> images;
    
    private LinkedList<ImageLinkVO> imagesLink;
    
    private List<FileMetaVO> removeImagesLink;
    
    private List<ProductTagsVO> tags;
    
    private ProductSubCategoryVO subCategory;
    
    private String productInfo;
    
    private List<ProductAttributeVO> productAttributeVOList; 
    
    private static final long serialVersionUID = 1L;

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
        this.productName = productName == null ? null : productName.trim();
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
        this.description = description == null ? null : description.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode == null ? null : productCode.trim();
    }

    public String getPaypalId() {
        return paypalId;
    }

    public void setPaypalId(String paypalId) {
        this.paypalId = paypalId == null ? null : paypalId.trim();
    }

    public List<ProductOptionVO> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<ProductOptionVO> optionList) {
		this.optionList = optionList;
	}

	public LinkedList<FileMetaVO> getImages() {
		return images;
	}

	public void setImages(LinkedList<FileMetaVO> images) {
		this.images = images;
	}

	public List<ProductTagsVO> getTags() {
		return tags;
	}

	public void setTags(List<ProductTagsVO> tags) {
		this.tags = tags;
	}

	public ProductSubCategoryVO getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(ProductSubCategoryVO subCategory) {
		this.subCategory = subCategory;
	}

	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

	public LinkedList<ImageLinkVO> getImagesLink() {
		return imagesLink;
	}

	public void setImagesLink(LinkedList<ImageLinkVO> imagesLink) {
		this.imagesLink = imagesLink;
	}

	public List<FileMetaVO> getRemoveImagesLink() {
		return removeImagesLink;
	}

	public void setRemoveImagesLink(List<FileMetaVO> removeImagesLink) {
		this.removeImagesLink = removeImagesLink;
	}
	
	public void addRemoveImagesLink(FileMetaVO fileMetaVO){
		if(removeImagesLink == null){
			removeImagesLink = new ArrayList<FileMetaVO>();
		}
		removeImagesLink.add(fileMetaVO);
	}

	public List<ProductAttributeVO> getProductAttributeVOList() {
		return productAttributeVOList;
	}

	public void setProductAttributeVOList(List<ProductAttributeVO> productAttributeVOList) {
		this.productAttributeVOList = productAttributeVOList;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", productId=").append(productId);
        sb.append(", productName=").append(productName);
        sb.append(", unitAmt=").append(unitAmt);
        sb.append(", weight=").append(weight);
        sb.append(", subCategoryId=").append(subCategoryId);
        sb.append(", description=").append(description);
        sb.append(", productCode=").append(productCode);
        sb.append(", paypalId=").append(paypalId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}