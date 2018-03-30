package com.admin.productcategory.vo;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import com.admin.filemgmt.vo.FileMetaVO;
import com.admin.filemgmt.vo.ImageLinkVO;
import com.admin.helper.vo.BaseVO;
import com.admin.productmanagement.vo.ProductSubCategoryVO;

public class ProductCategoryVO extends BaseVO implements Serializable {
    private Long categoryId;

    private String categoryName;

    private String isParent;
    
    private String isParentString;
    
    private Boolean isParentBoolean;

    private String displayInd;
    
    private Boolean displayIndBoolean;

    private List<ProductSubCategoryVO> subcategoryList;
    
    private List<ImageLinkVO> imageList;
    
    private ImageLinkVO firstImageLink;
    
    private LinkedList<FileMetaVO> imageMetaList;
    
    private List<ImageLinkVO> deletedImageList;

    private static final long serialVersionUID = 1L;

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
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public String getIsParent() {
        return isParent;
    }

    public void setIsParent(String isParent) {
        this.isParent = isParent == null ? null : isParent.trim();
    }

    public String getIsParentString() {
		return isParentString;
	}

	public void setIsParentString(String isParentString) {
		this.isParentString = isParentString;
	}

	public Boolean getIsParentBoolean() {
		return isParentBoolean;
	}

	public void setIsParentBoolean(Boolean isParentBoolean) {
		this.isParentBoolean = isParentBoolean;
	}

	public String getDisplayInd() {
        return displayInd;
    }

    public void setDisplayInd(String displayInd) {
        this.displayInd = displayInd == null ? null : displayInd.trim();
    }

    public Boolean getDisplayIndBoolean() {
		return displayIndBoolean;
	}

	public void setDisplayIndBoolean(Boolean displayIndBoolean) {
		this.displayIndBoolean = displayIndBoolean;
	}

	public List<ProductSubCategoryVO> getSubcategoryList() {
		return subcategoryList;
	}

	public void setSubcategoryList(List<ProductSubCategoryVO> subcategoryList) {
		this.subcategoryList = subcategoryList;
	}

	public List<ImageLinkVO> getImageList() {
		return imageList;
	}

	public void setImageList(List<ImageLinkVO> imageList) {
		this.imageList = imageList;
	}

	public ImageLinkVO getFirstImageLink() {
		return firstImageLink;
	}

	public void setFirstImageLink(ImageLinkVO firstImageLink) {
		this.firstImageLink = firstImageLink;
	}

	public LinkedList<FileMetaVO> getImageMetaList() {
		return imageMetaList;
	}

	public void setImageMetaList(LinkedList<FileMetaVO> imageMetaList) {
		this.imageMetaList = imageMetaList;
	}

	public List<ImageLinkVO> getDeletedImageList() {
		return deletedImageList;
	}

	public void setDeletedImageList(List<ImageLinkVO> deletedImageList) {
		this.deletedImageList = deletedImageList;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", categoryId=").append(categoryId);
        sb.append(", categoryName=").append(categoryName);
        sb.append(", isParent=").append(isParent);
        sb.append(", displayInd=").append(displayInd);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}