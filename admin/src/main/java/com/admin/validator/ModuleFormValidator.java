package com.admin.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.admin.common.vo.ModuleVO;

@Component
public class ModuleFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ModuleVO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		//ModuleVO moduleVO = (ModuleVO) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "moduleName", "error.notempty.moduleform.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "icon", "error.notempty.moduleform.icon");

		
	}
	
}