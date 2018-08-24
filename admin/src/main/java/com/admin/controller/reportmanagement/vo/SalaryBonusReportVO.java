package com.admin.controller.reportmanagement.vo;

import java.io.Serializable;

import com.admin.payment.vo.PaymentDetailVO;
import com.admin.salarybonus.vo.SalaryBonusVO;

public class SalaryBonusReportVO implements Serializable {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SalaryBonusVO salarybonus;
    
    private PaymentDetailVO paymentDetail;
    

	public SalaryBonusVO getSalarybonus() {
		return salarybonus;
	}

	public void setSalarybonus(SalaryBonusVO salarybonus) {
		this.salarybonus = salarybonus;
	}

	public PaymentDetailVO getPaymentDetail() {
		return paymentDetail;
	}

	public void setPaymentDetail(PaymentDetailVO paymentDetail) {
		this.paymentDetail = paymentDetail;
	}
	
}