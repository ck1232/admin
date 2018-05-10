package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.EmployeeTO;
@Transactional(readOnly = true)
public interface EmployeeDAO extends BaseDAO<EmployeeTO> {
	EmployeeTO findByEmployeeId(Long employeeId);
	List<EmployeeTO> findByEmployeeIdIn(List<Long> employeeIdList);
	List<EmployeeTO> findByDeleteIndOrderByNameAsc(String deleteInd);
}
