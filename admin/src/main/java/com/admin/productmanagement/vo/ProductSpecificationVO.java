package com.admin.productmanagement.vo;

import java.io.Serializable;

import com.admin.helper.vo.BaseVO;

public class ProductSpecificationVO extends BaseVO implements Serializable {
    private Integer productSpecificationId;

    private Integer productId;

    private String content;

    private static final long serialVersionUID = 1L;

    public Integer getProductSpecificationId() {
        return productSpecificationId;
    }

    public void setProductSpecificationId(Integer productSpecificationId) {
        this.productSpecificationId = productSpecificationId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", productSpecificationId=").append(productSpecificationId);
        sb.append(", productId=").append(productId);
        sb.append(", content=").append(content);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}