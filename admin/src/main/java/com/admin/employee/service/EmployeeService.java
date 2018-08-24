package com.admin.employee.service;

import java.util.List;

import com.admin.employee.vo.EmployeeVO;
import com.admin.to.EmployeeTO;

public interface EmployeeService {
	EmployeeVO findById(Long id);
	List<EmployeeVO> findByIdList(List<Long> idList);
	List<EmployeeVO> getAllEmployee();
	List<EmployeeVO> getAllEmployeeInAscendingName();
	void updateEmployee(EmployeeVO vo);
	void saveEmployee(EmployeeVO vo);
	void deleteEmployee(List<Long> idList);
	List<EmployeeVO> convertToEmployeeVOList(List<EmployeeTO> toList);
	List<EmployeeTO> convertToEmployeeTOList(List<EmployeeVO> voList);
}
