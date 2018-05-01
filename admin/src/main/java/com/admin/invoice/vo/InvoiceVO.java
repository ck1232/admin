package com.admin.invoice.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.admin.helper.vo.BaseVO;
import com.admin.payment.vo.PaymentDetailVO;
import com.admin.payment.vo.PaymentRsVO;
import com.admin.validator.annotation.ValidDate;

public class InvoiceVO extends BaseVO implements Serializable {
	
	private Long grantId;
	
    private Long invoiceId;
    
    @NotEmpty(message="error.notempty.grantform.organisation")
    private String messenger;
    
    private Date invoiceDate;
    
    @NotNull(message="error.notempty.grantform.totalamount")
    @Digits(integer=6, fraction=2, message="error.decimal.twodecimcalpoint")
    @Min(value=0L, message="error.negative.grantform.totalamount")
    private BigDecimal totalAmt;
    
    private String totalAmtString;

    private String status;
    
    //non db fields
    @NotNull(message="error.notempty.grantform.date")
    @ValidDate(dateFormat="dd/MM/yyyy", message="error.notempty.grantform.date")
  	private String invoicedateString;
  	
  	private String type;
  	
  	private List<PaymentRsVO> paymentRsVOList;
  	
  	private List<PaymentDetailVO> paymentDetailVOList;
  	
    private static final long serialVersionUID = 1L;

    public Long getGrantId() {
		return grantId;
	}

	public void setGrantId(Long grantId) {
		this.grantId = grantId;
	}

	public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger == null ? null : messenger.trim();
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getTotalAmtString() {
		return totalAmtString;
	}

	public void setTotalAmtString(String totalAmtString) {
		this.totalAmtString = totalAmtString;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
    
    public String getInvoicedateString() {
  		return invoicedateString;
  	}

  	public void setInvoicedateString(String invoicedateString) {
  		this.invoicedateString = invoicedateString;
  	}

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public List<PaymentRsVO> getPaymentRsVOList() {
		return paymentRsVOList;
	}

	public void setPaymentRsVOList(List<PaymentRsVO> paymentRsVOList) {
		this.paymentRsVOList = paymentRsVOList;
	}

	public List<PaymentDetailVO> getPaymentDetailVOList() {
		paymentDetailVOList = new ArrayList<PaymentDetailVO>();
		if(paymentRsVOList != null && !paymentRsVOList.isEmpty()) {
			for(PaymentRsVO paymentRsVO : paymentRsVOList) {
				PaymentDetailVO paymentDetailVO = paymentRsVO.getPaymentDetailVO();
				if(paymentDetailVO != null)
					paymentDetailVOList.add(paymentDetailVO);
			}
		}
		return paymentDetailVOList;
	}

	public void setPaymentDetailVOList(List<PaymentDetailVO> paymentDetailVOList) {
		this.paymentDetailVOList = paymentDetailVOList;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", invoiceId=").append(invoiceId);
        sb.append(", messenger=").append(messenger);
        sb.append(", invoiceDate=").append(invoiceDate);
        sb.append(", totalAmt=").append(totalAmt);
        sb.append(", totalAmtString=").append(totalAmtString);
        sb.append(", status=").append(status);
        sb.append(", invoicedateString=").append(invoicedateString);
        sb.append(", type=").append(type);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}