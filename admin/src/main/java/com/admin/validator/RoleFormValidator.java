package com.admin.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.admin.role.vo.RoleVO;

@Component
public class RoleFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return RoleVO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//RoleVO roleVO = (RoleVO) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.notempty.roleform.name");

		
	}
	
}