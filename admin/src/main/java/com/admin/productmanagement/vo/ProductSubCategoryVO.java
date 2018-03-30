package com.admin.productmanagement.vo;

import java.io.Serializable;

import com.admin.helper.vo.BaseVO;

public class ProductSubCategoryVO extends BaseVO implements Serializable {
    private Long subCategoryId;

    private String name;

    private Long categoryId;

    private String displayInd;
    
    private Boolean displayIndBoolean;

    private static final long serialVersionUID = 1L;

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
        this.name = name == null ? null : name.trim();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
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

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", subCategoryId=").append(subCategoryId);
        sb.append(", name=").append(name);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", displayInd=").append(displayInd);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}