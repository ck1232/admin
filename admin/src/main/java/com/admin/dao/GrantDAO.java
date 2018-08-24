package com.admin.dao;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.GrantTO;
@Transactional(readOnly = true)
public interface GrantDAO extends BaseDAO<GrantTO> {
	GrantTO findByGrantId(Long grantId);
	List<GrantTO> findByGrantIdIn(List<Long> grantIdList);
	List<GrantTO> findByGrantDateBetweenAndDeleteInd(Date dateFrom, Date dateTill, String deleteInd);
}
