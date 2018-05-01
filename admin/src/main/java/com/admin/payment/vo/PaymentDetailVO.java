package com.admin.payment.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.admin.cheque.vo.ChequeVO;
import com.admin.helper.vo.BaseVO;

public class PaymentDetailVO extends BaseVO implements Serializable {
	
    private Long paymentDetailId;

    private Date paymentDate;
    
    private String paymentDateString;

    private Long paymentMode;
    
    private String paymentModeString;

    private BigDecimal paymentAmt;
    
    private String paymentAmtString;

    private ChequeVO chequeVO;
    
    private List<Long> paymentRsIdList;
    
    private static final long serialVersionUID = 1L;

    public Long getPaymentDetailId() {
        return paymentDetailId;
    }

    public void setPaymentDetailId(Long paymentDetailId) {
        this.paymentDetailId = paymentDetailId;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public String getPaymentDateString() {
  		return paymentDateString;
  	}

  	public void setPaymentDateString(String paymentDateString) {
  		this.paymentDateString = paymentDateString == null ? null : paymentDateString.trim();
  	}

    public Long getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(Long paymentMode) {
        this.paymentMode = paymentMode;
    }
    
    public String getPaymentModeString() {
  		return paymentModeString;
  	}

  	public void setPaymentModeString(String paymentModeString) {
  		this.paymentModeString = paymentModeString == null ? null : paymentModeString.trim();
  	}

    public BigDecimal getPaymentAmt() {
        return paymentAmt;
    }

    public void setPaymentAmt(BigDecimal paymentAmt) {
        this.paymentAmt = paymentAmt;
    }

    public String getPaymentAmtString() {
		return paymentAmtString;
	}

	public void setPaymentAmtString(String paymentAmtString) {
		this.paymentAmtString = paymentAmtString;
	}

	public ChequeVO getChequeVO() {
		return chequeVO;
	}

	public void setChequeVO(ChequeVO chequeVO) {
		this.chequeVO = chequeVO;
	}
	
	public List<Long> getPaymentRsIdList() {
		return paymentRsIdList;
	}

	public void setPaymentRsIdList(List<Long> paymentRsIdList) {
		this.paymentRsIdList = paymentRsIdList;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", paymentDetailId=").append(paymentDetailId);
        sb.append(", paymentDate=").append(paymentDate);
        sb.append(", paymentMode=").append(paymentMode);
        sb.append(", paymentAmt=").append(paymentAmt);
        sb.append(", paymentAmtString=").append(paymentAmtString);
        sb.append(", chequeId=").append(chequeVO.getChequeId());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}