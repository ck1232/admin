package com.admin.productmanagement.vo;

import java.io.Serializable;

import com.admin.helper.vo.BaseVO;

public class ProductTagsVO extends BaseVO implements Serializable {
    private Long tagsId;

	private String name;

	private Long productId;

	private static final long serialVersionUID = 1L;

	public Long getTagsId() {
		return tagsId;
	}

	public void setTagsId(Long tagsId) {
		this.tagsId = tagsId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName());
		sb.append(" [");
		sb.append("Hash = ").append(hashCode());
		sb.append(", tagsId=").append(tagsId);
		sb.append(", name=").append(name);
		sb.append(", productId=").append(productId);
		sb.append(", serialVersionUID=").append(serialVersionUID);
		sb.append("]");
		sb.append(", from super class ");
		sb.append(super.toString());
		return sb.toString();
	}
}