package com.admin.payment.vo;

import com.admin.invoice.vo.InvoiceVO;

public class InvoicePaymentRsVO extends PaymentRsVO {
	
	private static final long serialVersionUID = 1L;
	
	private InvoiceVO invoiceVO;

	public InvoiceVO getInvoiceVO() {
		return invoiceVO;
	}

	public void setInvoiceVO(InvoiceVO invoiceVO) {
		this.invoiceVO = invoiceVO;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", invoiceId=").append(invoiceVO.getInvoiceId());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}