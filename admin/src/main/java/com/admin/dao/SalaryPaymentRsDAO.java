package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.SalaryPaymentRsTO;
@Transactional(readOnly = true)
public interface SalaryPaymentRsDAO extends BaseDAO<SalaryPaymentRsTO> {
	List<SalaryPaymentRsTO> findByPaymentRsIdIn(List<Long> paymentRsIdList);
}
