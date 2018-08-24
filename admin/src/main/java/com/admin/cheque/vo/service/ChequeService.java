package com.admin.cheque.vo.service;

import java.util.Date;
import java.util.List;

import com.admin.cheque.vo.ChequeVO;
import com.admin.payment.vo.PaymentVO;
import com.admin.to.PaymentDetailTO;
import com.admin.to.PaymentRsTO;

public interface ChequeService {
	ChequeVO findById(Long id);
	List<ChequeVO> findByIdList(List<Long> idList);
	List<ChequeVO> getChequeList();
	List<PaymentDetailTO> getPaymentDetailListByChequeId(Long chequeId);
	Date getEarliestChequeDate(ChequeVO chequeVo);
	void updateCheque(ChequeVO vo);
	void updateToBounceCheque(List<Long> idList, Date bounceDate);
	void saveBounceCheque(PaymentVO paymentVo, List<Long> chequeIdList, List<PaymentRsTO> paymentRsList);
}
