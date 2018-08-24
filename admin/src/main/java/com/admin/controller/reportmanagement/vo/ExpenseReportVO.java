package com.admin.controller.reportmanagement.vo;

import java.io.Serializable;

import com.admin.expense.vo.ExpenseVO;
import com.admin.payment.vo.PaymentDetailVO;

public class ExpenseReportVO implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ExpenseVO expense;
    
    private PaymentDetailVO paymentDetail;
    
	public ExpenseVO getExpense() {
		return expense;
	}

	public void setExpense(ExpenseVO expense) {
		this.expense = expense;
	}

	public PaymentDetailVO getPaymentDetail() {
		return paymentDetail;
	}

	public void setPaymentDetail(PaymentDetailVO paymentDetail) {
		this.paymentDetail = paymentDetail;
	}
	
}