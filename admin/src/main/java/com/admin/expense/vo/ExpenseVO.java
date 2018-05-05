package com.admin.expense.vo;

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

public class ExpenseVO extends BaseVO implements Serializable {
    private Long expenseId;
    
    @NotNull(message="error.notempty.expenseform.expensetype")
    @Min(value=1L, message="error.notempty.expenseform.expensetype")
    private Long expenseTypeId;

    private String invoiceNo;

    private String description;

    private Date expenseDate;
    
    private String supplier;
    
    @NotNull(message="error.notempty.expenseform.totalamount")
    @Digits(integer=6, fraction=2, message="error.decimal.twodecimcalpoint")
    private BigDecimal totalAmt;
    
    private String remarks;

    private String status;
    
    //non db fields
    private String expensetype;
    
    @NotEmpty(message="error.notempty.expenseform.expensedate")
    @ValidDate(dateFormat="dd/MM/yyyy", message="error.notvalid.expenseform.expensedate")
    private String expensedateString;
    
    private String totalAmtString;
    
    private List<PaymentRsVO> paymentRsVOList;
  	
  	private List<PaymentDetailVO> paymentDetailVOList;
    
	private static final long serialVersionUID = 1L;

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public Long getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(Long expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }
    
    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
    
    public String getExpensetype() {
		return expensetype;
	}

	public void setExpensetype(String expensetype) {
		this.expensetype = expensetype;
	}

	public String getExpensedateString() {
		return expensedateString;
	}

	public void setExpensedateString(String expensedateString) {
		this.expensedateString = expensedateString;
	}


    public String getTotalAmtString() {
		return totalAmtString;
	}

	public void setTotalAmtString(String totalAmtString) {
		this.totalAmtString = totalAmtString;
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
        sb.append(", expenseId=").append(expenseId);
        sb.append(", expensetype=").append(expensetype);
        sb.append(", expenseTypeId=").append(expenseTypeId);
        sb.append(", invoiceNo=").append(invoiceNo);
        sb.append(", description=").append(description);
        sb.append(", expenseDate=").append(expenseDate);
        sb.append(", expensedateString=").append(expensedateString);
        sb.append(", supplier=").append(supplier);
        sb.append(", totalAmt=").append(totalAmt);
        sb.append(", remarks=").append(remarks);
        sb.append(", status=").append(status);
        sb.append(", totalAmtString=").append(totalAmtString);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}