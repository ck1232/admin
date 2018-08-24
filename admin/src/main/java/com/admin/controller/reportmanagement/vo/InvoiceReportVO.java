package com.admin.controller.reportmanagement.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.admin.invoice.vo.InvoiceVO;
import com.admin.payment.vo.PaymentDetailVO;

public class InvoiceReportVO implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private InvoiceVO invoice;
    
    private List<PaymentDetailVO> paymentDetailList;
    
    private String paymentMode;
    
    private Date chequeDate;
    
    private String chequeNo;
    
    private BigDecimal paymentAmt;
    
    private Date debitDate;

	public InvoiceVO getInvoice() {
		return invoice;
	}

	public void setInvoice(InvoiceVO invoice) {
		this.invoice = invoice;
	}

	public List<PaymentDetailVO> getPaymentDetailList() {
		return paymentDetailList;
	}

	public void setPaymentDetailList(List<PaymentDetailVO> paymentDetailList) {
		this.paymentDetailList = paymentDetailList;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public String getChequeNo() {
		return chequeNo;
	}

	public void setChequeNo(String chequeNo) {
		this.chequeNo = chequeNo;
	}

	public BigDecimal getPaymentAmt() {
		return paymentAmt;
	}

	public void setPaymentAmt(BigDecimal paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	public Date getDebitDate() {
		return debitDate;
	}

	public void setDebitDate(Date debitDate) {
		this.debitDate = debitDate;
	}


	
}