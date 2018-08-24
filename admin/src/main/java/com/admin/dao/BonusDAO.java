package com.admin.dao;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.BonusTO;
@Transactional(readOnly = true)
public interface BonusDAO extends BaseDAO<BonusTO> {
	BonusTO findByBonusId(Long bonusId);
	List<BonusTO> findByBonusIdIn(List<Long> bonusIdList);
	List<BonusTO> findByBonusIdInOrderByBonusDateDesc(List<Long> bonusIdList);
	List<BonusTO> findByEmployeeTO_employeeIdOrderByBonusDateDesc(Long employeeId);
	List<BonusTO> findByBonusDateBetweenAndDeleteIndOrderByBonusDate(Date dateFrom, Date dateTo, String deleteInd);
}
