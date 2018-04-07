package com.admin.product.vo;

import java.io.Serializable;
import java.util.List;

import com.admin.helper.vo.BaseVO;

public class ProductOptionVO extends BaseVO implements Serializable {
    private Long productOptionId;

    private String name;

    private String displayInd;
    
    private List<ProductSubOptionVO> subOptionList;
    
	private Integer sequence;
	
	private List<String> currentDisplayProductAttributeList;
	
    private static final long serialVersionUID = 1L;

    public Long getProductOptionId() {
        return productOptionId;
    }

    public void setProductOptionId(Long productOptionId) {
        this.productOptionId = productOptionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDisplayInd() {
        return displayInd;
    }

    public void setDisplayInd(String displayInd) {
        this.displayInd = displayInd == null ? null : displayInd.trim();
    }
    
    public List<ProductSubOptionVO> getSubOptionList() {
		return subOptionList;
	}
	public void setSubOptionList(List<ProductSubOptionVO> subOptionList) {
		this.subOptionList = subOptionList;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public String getSubOptionListComma() {
		String subOptionListComma = "";
		if(subOptionList != null && subOptionList.size() > 0){
			for(ProductSubOptionVO vo: subOptionList){
				subOptionListComma += vo.getName() + ", ";
			}
		}
		if(subOptionListComma.length() > 2){
			return subOptionListComma.substring(0, subOptionListComma.length()-2);
		}else{
			return subOptionListComma;
		}
	}

    public List<String> getCurrentDisplayProductAttributeList() {
		return currentDisplayProductAttributeList;
	}

	public void setCurrentDisplayProductAttributeList(
			List<String> currentDisplayProductAttributeList) {
		this.currentDisplayProductAttributeList = currentDisplayProductAttributeList;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", productOptionId=").append(productOptionId);
        sb.append(", name=").append(name);
        sb.append(", displayInd=").append(displayInd);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((productOptionId == null) ? 0 : productOptionId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductOptionVO other = (ProductOptionVO) obj;
		if (productOptionId == null) {
			if (other.productOptionId != null)
				return false;
		} else if (!productOptionId.equals(other.productOptionId))
			return false;
		return true;
	}
}