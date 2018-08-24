package com.admin.grant.service;

import java.util.Date;
import java.util.List;

import com.admin.invoice.vo.InvoiceVO;
import com.admin.payment.vo.PaymentVO;
import com.admin.to.GrantTO;

public interface GrantService {
	InvoiceVO findById(Long id);
	List<InvoiceVO> findByIdList(List<Long> idList);
	List<InvoiceVO> getAllGrant();
	GrantTO saveGrant(InvoiceVO grantVO);
	void updateGrant(InvoiceVO vo);
	void deleteGrant(List<Long> idList);
	void saveGrantPaymentByGrantId(PaymentVO paymentVo, List<Long> grantidList);
	void saveGrantPaymentByGrant(PaymentVO paymentVo, List<GrantTO> grantList);
	List<GrantTO> convertToGrantTOList(List<InvoiceVO> voList);
	List<InvoiceVO> getAllGrant(Date dateAsOf, Date endDate);
}
