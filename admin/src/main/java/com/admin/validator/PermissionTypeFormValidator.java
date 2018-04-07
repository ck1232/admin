package com.admin.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.admin.permissionmgmt.vo.SubModulePermissionTypeVO;

@Component
public class PermissionTypeFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return SubModulePermissionTypeVO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
//		SubModulePermissionTypeVO subModulePermissionTypeVO = (SubModulePermissionTypeVO) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "permissionType", "error.notempty.permissiontypeform.permissiontype");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url", "error.notempty.permissiontypeform.url");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "seqNum", "error.notempty.permissiontypeform.seqno");

		
	}
	
}