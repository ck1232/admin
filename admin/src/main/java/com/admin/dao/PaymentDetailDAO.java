package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.PaymentDetailTO;
@Transactional(readOnly = true)
public interface PaymentDetailDAO extends BaseDAO<PaymentDetailTO> {
	List<PaymentDetailTO> findByPaymentDetailIdIn(List<Long> paymentDetailIdList);
	List<PaymentDetailTO> findByChequeTOIsNotNull();
	List<PaymentDetailTO> findByChequeTO_chequeId(Long chequeId);
}
