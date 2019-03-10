package com.admin.payment.service;

import java.util.Date;
import java.util.List;

import com.admin.expense.vo.ExpenseVO;
import com.admin.payment.vo.PaymentDetailVO;
import com.admin.payment.vo.PaymentRsVO;
import com.admin.payment.vo.PaymentVO;
import com.admin.salarybonus.vo.SalaryBonusVO;
import com.admin.to.PaymentDetailTO;
import com.admin.to.PaymentRsTO;

public interface PaymentService {
	List<PaymentRsVO> convertToPaymentRsVOList(List<? extends PaymentRsTO> toList);
	List<PaymentRsVO> findByPaymentRsIdList(List<Long> rsIdList, String moduleExpense);
	List<PaymentDetailTO> genPaymentDetail(PaymentVO paymentVo);
//	List<PaymentDetailVO> getAllPaymentByRefTypeAndRefId(String type, Long id, Date dateAsOf, Date endDate);
//	List<PaymentDetailVO> getAllPaymentByRefTypeAndRefId(String type, Long id);
	List<PaymentDetailVO> getAllPaymentByRefTypeAndRefId(ExpenseVO vo);
	List<PaymentDetailVO> getAllPaymentByRefTypeAndRefId(SalaryBonusVO vo);
	List<PaymentDetailVO> getAllPaymentByRefTypeAndRefId(ExpenseVO vo, Date dateAsOf, Date endDate);
	List<PaymentDetailVO> getAllPaymentByRefTypeAndRefId(SalaryBonusVO vo, Date dateAsOf, Date endDate);
}
