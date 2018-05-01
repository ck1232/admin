package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.ChequeTO;
@Transactional(readOnly = true)
public interface ChequeDAO extends BaseDAO<ChequeTO> {
	ChequeTO findByChequeId(Long chequeId);
	List<ChequeTO> findByChequeIdIn(List<Long> chequeIdList);
}
