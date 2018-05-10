package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.BonusPaymentRsTO;
@Transactional(readOnly = true)
public interface BonusPaymentRsDAO extends BaseDAO<BonusPaymentRsTO> {
	List<BonusPaymentRsTO> findByPaymentRsIdIn(List<Long> paymentRsIdList);
}
