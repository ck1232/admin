package com.admin.employee.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.dao.EmployeeDAO;
import com.admin.employee.vo.EmployeeVO;
import com.admin.employee.vo.EmploymentTypeEnum;
import com.admin.helper.GeneralUtils;
import com.admin.to.EmployeeTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class EmployeeServiceImpl implements EmployeeService {
	
	private EmployeeDAO employeeDAO;
	
	
	@Autowired
	public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
	}
	
	public EmployeeVO findById(Long id) {
		List<EmployeeVO> employeeVOList = findByIdList(Arrays.asList(id));
		if(employeeVOList != null && !employeeVOList.isEmpty()) {
			return employeeVOList.get(0);
		}
		return new EmployeeVO();
	}
	
	public List<EmployeeVO> findByIdList(List<Long> idList){
		List<EmployeeTO> employeeTOList = employeeDAO.findByEmployeeIdIn(idList);
		return convertToEmployeeVOList(employeeTOList);
	}
	
	public List<EmployeeVO> getAllEmployee() {
		List<EmployeeTO> employeeTOList = employeeDAO.findByDeleteInd(GeneralUtils.NOT_DELETED);
		return convertToEmployeeVOList(employeeTOList);
	}
	
	public List<EmployeeVO> getAllEmployeeInAscendingName() {
		List<EmployeeTO> employeeTOList = employeeDAO.findByDeleteIndOrderByNameAsc(GeneralUtils.NOT_DELETED);
		return convertToEmployeeVOList(employeeTOList);
	}
	
	public void updateEmployee(EmployeeVO vo) {
		if(vo != null && vo.getEmployeeId() != null) {
			EmployeeTO to = employeeDAO.findByEmployeeId(vo.getEmployeeId());
			to.setName(vo.getName());
			to.setEmployeeType(vo.getEmployeeType());
			to.setDob(GeneralUtils.convertStringToDate(vo.getDobString(), "dd/MM/yyyy"));
			to.setNationality(vo.getNationality());
			to.setBasicSalary(vo.getBasicSalary());
			to.setEmploymentStartDate(GeneralUtils.convertStringToDate(vo.getEmploymentStartDateString(), "dd/MM/yyyy"));
			to.setEmploymentEndDate(GeneralUtils.convertStringToDate(vo.getEmploymentEndDateString(), "dd/MM/yyyy"));
			to.setCdacInd(vo.getCdacIndBoolean() == Boolean.TRUE ? "Y" : "N");
			employeeDAO.save(to);
		}
	}
	
	public void saveEmployee(EmployeeVO vo) {
		List<EmployeeTO> employeeTOList = convertToEmployeeTOList(Arrays.asList(vo));
		employeeDAO.save(employeeTOList);
	}
	
	public void deleteEmployee(List<Long> idList) {
		List<EmployeeTO> employeeTOList = employeeDAO.findByEmployeeIdIn(idList);
		if(employeeTOList != null && !employeeTOList.isEmpty()){
			for(EmployeeTO employeeTO : employeeTOList){
				employeeTO.setDeleteInd(GeneralUtils.DELETED);
			}
			employeeDAO.save(employeeTOList);
		}
	}

	public List<EmployeeVO> convertToEmployeeVOList(List<EmployeeTO> toList) {
		List<EmployeeVO> voList = new ArrayList<EmployeeVO>();
		if(toList != null && !toList.isEmpty()) {
			for(EmployeeTO to : toList) {
				EmployeeVO vo = new EmployeeVO();
				vo.setEmployeeId(to.getEmployeeId());
				vo.setName(to.getName());
				vo.setEmployeeType(to.getEmployeeType());
				vo.setEmployeeTypeString(EmploymentTypeEnum.getEnum(to.getEmployeeType()));
				vo.setDob(to.getDob());
				vo.setDobString(GeneralUtils.convertDateToString(to.getDob(), "dd/MM/yyyy"));
				vo.setNationality(to.getNationality());
				vo.setBasicSalary(to.getBasicSalary());
				vo.setBasicSalaryString("$"+to.getBasicSalary());
				vo.setEmploymentStartDate(to.getEmploymentStartDate());
				vo.setEmploymentStartDateString(GeneralUtils.convertDateToString(to.getEmploymentStartDate(), "dd/MM/yyyy"));
				vo.setEmploymentEndDate(to.getEmploymentEndDate());
				vo.setEmploymentEndDateString(GeneralUtils.convertDateToString(to.getEmploymentEndDate(), "dd/MM/yyyy"));
				vo.setCdacInd(to.getCdacInd());
				vo.setCdacIndBoolean(to.getCdacInd().equals("Y") ? Boolean.TRUE: Boolean.FALSE);
				voList.add(vo);
			}
		}
		return voList;
	}
	
	public List<EmployeeTO> convertToEmployeeTOList(List<EmployeeVO> voList/*, Employee employee*/) {
		List<EmployeeTO> toList = new ArrayList<EmployeeTO>();
		if(voList != null && !voList.isEmpty()) {
			List<Long> idList = GeneralUtils.convertListToLongList(voList, "employeeId", false);
			Map<Long, EmployeeTO> employeeTOMap = new HashMap<Long, EmployeeTO>();
			if(!idList.isEmpty()){
				List<EmployeeTO> employeeTOList = employeeDAO.findByEmployeeIdIn(idList);
				employeeTOMap = GeneralUtils.convertListToLongMap(employeeTOList, "employeeId");
			}
			for(EmployeeVO vo : voList) {
				EmployeeTO to = new EmployeeTO();
				EmployeeTO dbTO = employeeTOMap.get(vo.getEmployeeId());
				if(dbTO != null){ //update
					to = dbTO;
				}
				to.setName(vo.getName());
				to.setEmployeeType(vo.getEmployeeType());
				to.setDob(GeneralUtils.convertStringToDate(vo.getDobString(), "dd/MM/yyyy"));
				to.setNationality(vo.getNationality());
				to.setBasicSalary(vo.getBasicSalary());
				to.setEmploymentStartDate(GeneralUtils.convertStringToDate(vo.getEmploymentStartDateString(), "dd/MM/yyyy"));
				to.setEmploymentEndDate(GeneralUtils.convertStringToDate(vo.getEmploymentEndDateString(), "dd/MM/yyyy"));
				to.setCdacInd(vo.getCdacIndBoolean() == Boolean.TRUE ? "Y" : "N");
				toList.add(to);
			}
		}
		return toList;
	}
}
