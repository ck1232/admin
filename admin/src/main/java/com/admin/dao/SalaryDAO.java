package com.admin.dao;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.SalaryTO;
@Transactional(readOnly = true)
public interface SalaryDAO extends BaseDAO<SalaryTO> {
	SalaryTO findBySalaryId(Long salaryId);
	List<SalaryTO> findBySalaryIdIn(List<Long> salaryIdList);
	List<SalaryTO> findBySalaryIdInOrderBySalaryDateDesc(List<Long> salaryIdList);
	List<SalaryTO> findByEmployeeTO_employeeIdOrderBySalaryDateDesc(Long employeeId);
	List<SalaryTO> findBySalaryDateBetweenAndDeleteIndOrderBySalaryDate(Date dateFrom, Date dateTill, String deleteInd);
}
