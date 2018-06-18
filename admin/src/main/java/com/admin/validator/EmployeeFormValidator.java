package com.admin.validator;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.admin.employee.vo.EmployeeVO;

@Component
public class EmployeeFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return EmployeeVO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		EmployeeVO employeeVo = (EmployeeVO) target;
		
		/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.notempty.employeeform.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeType", "error.notempty.employeeform.employmenttype");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nationality", "error.notempty.employeeform.nationality");
		
		if(employeeVo.getBasicSalary() == null) {
			errors.rejectValue("basicSalary", "error.notempty.employeeform.basicsalary");
		}else{
			BigDecimal value = employeeVo.getBasicSalary();
			if(value.compareTo(BigDecimal.ZERO) < 0) {
				errors.rejectValue("basicSalary", "error.negative.employeeform.basicsalary");
			}
		}*/
		
		if(!employeeVo.getEmploymentStartDateString().isEmpty() && !employeeVo.getEmploymentEndDateString().isEmpty()) {
			Date startdate = null, enddate = null;
			try{
				startdate = new SimpleDateFormat("dd/MM/yyyy").parse(employeeVo.getEmploymentStartDateString());
			}catch(Exception e) {
				errors.rejectValue("employmentStartDateString", "error.notvalid.employeeform.startdate");
			}
			try{
				enddate = new SimpleDateFormat("dd/MM/yyyy").parse(employeeVo.getEmploymentEndDateString());
			}catch(Exception e) {
				errors.rejectValue("employmentEndDateString", "error.notvalid.employeeform.enddate");
			}
			
			if(startdate != null && enddate != null && startdate.after(enddate)) {
				errors.rejectValue("employmentStartDateString", "error.employeeform.startdate.after.enddate");
				errors.rejectValue("employmentEndDateString", "error.employeeform.enddate.before.startdate");
			}
		}
		
		/*if(employeeVo.getDobString() != null && !employeeVo.getDobString().isEmpty()) {
			try{
				new SimpleDateFormat("dd/MM/yyyy").parse(employeeVo.getDobString());
			}catch(Exception e) {
				errors.rejectValue("dobString", "error.notvalid.employeeform.dob");
			}
		}*/
	}
	
}