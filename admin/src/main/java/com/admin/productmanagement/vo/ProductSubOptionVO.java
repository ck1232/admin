package com.admin.productmanagement.vo;

import java.io.Serializable;

import com.admin.helper.vo.BaseVO;

public class ProductSubOptionVO extends BaseVO implements Serializable {
    private Long productSuboptionId;

    private Long productId;

    private Long productOptionId;

    private String name;

    private String displayInd;

    private String code;
    
    private String optionName;
    
    private int seq;
    
    private static final long serialVersionUID = 1L;

    public Long getProductSuboptionId() {
        return productSuboptionId;
    }

    public void setProductSuboptionId(Long productSuboptionId) {
        this.productSuboptionId = productSuboptionId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getOptionName() {
		return optionName;
	}

	public int getSeq() {
		return seq;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}
	
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", productSuboptionId=").append(productSuboptionId);
        sb.append(", productId=").append(productId);
        sb.append(", productOptionId=").append(productOptionId);
        sb.append(", name=").append(name);
        sb.append(", displayInd=").append(displayInd);
        sb.append(", code=").append(code);
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
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result
				+ ((displayInd == null) ? 0 : displayInd.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((optionName == null) ? 0 : optionName.hashCode());
		result = prime * result
				+ ((productId == null) ? 0 : productId.hashCode());
		result = prime * result
				+ ((productOptionId == null) ? 0 : productOptionId.hashCode());
		result = prime
				* result
				+ ((productSuboptionId == null) ? 0 : productSuboptionId
						.hashCode());
		result = prime * result + seq;
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
		ProductSubOptionVO other = (ProductSubOptionVO) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (displayInd == null) {
			if (other.displayInd != null)
				return false;
		} else if (!displayInd.equals(other.displayInd))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (optionName == null) {
			if (other.optionName != null)
				return false;
		} else if (!optionName.equals(other.optionName))
			return false;
		if (productId == null) {
			if (other.productId != null)
				return false;
		} else if (!productId.equals(other.productId))
			return false;
		if (productOptionId == null) {
			if (other.productOptionId != null)
				return false;
		} else if (!productOptionId.equals(other.productOptionId))
			return false;
		if (productSuboptionId == null) {
			if (other.productSuboptionId != null)
				return false;
		} else if (!productSuboptionId.equals(other.productSuboptionId))
			return false;
		if (seq != other.seq)
			return false;
		return true;
	}
}