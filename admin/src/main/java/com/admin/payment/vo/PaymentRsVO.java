package com.admin.payment.vo;

import java.io.Serializable;

import com.admin.helper.vo.BaseVO;

public class PaymentRsVO extends BaseVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long paymentRsId;

    private String referenceType;

    private Long referenceId;
    
    private PaymentDetailVO paymentDetailVO;
    
    
    public Long getPaymentRsId() {
        return paymentRsId;
    }

    public void setPaymentRsId(Long paymentRsId) {
        this.paymentRsId = paymentRsId;
    }

    public String getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType == null ? null : referenceType.trim();
    }

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public PaymentDetailVO getPaymentDetailVO() {
		return paymentDetailVO;
	}

	public void setPaymentDetailVO(PaymentDetailVO paymentDetailVO) {
		this.paymentDetailVO = paymentDetailVO;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", paymentRsId=").append(paymentRsId);
        sb.append(", referenceType=").append(referenceType);
        sb.append(", referenceId=").append(referenceId);
        sb.append(", paymentDetailId=").append(paymentDetailVO.getPaymentDetailId());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}