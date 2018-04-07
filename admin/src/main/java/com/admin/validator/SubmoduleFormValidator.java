package com.admin.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.admin.submodule.vo.SubModuleVO;

@Component
public class SubmoduleFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return SubModuleVO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
//		SubModuleVO subModuleVO = (SubModuleVO) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "error.notempty.submoduleform.name");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "icon", "error.notempty.submoduleform.icon");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url", "error.notempty.submoduleform.url");

		
	}
	
}