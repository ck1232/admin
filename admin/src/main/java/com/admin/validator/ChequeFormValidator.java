package com.admin.validator;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.admin.cheque.vo.ChequeVO;


@Component
public class ChequeFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ChequeVO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ChequeVO chequeVo = (ChequeVO) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "chequeNum", "error.notempty.paymentform.chequeno");
		
		if(chequeVo.getChequeDateString() == null || chequeVo.getChequeDateString().trim().isEmpty()){
			errors.rejectValue("chequeDateString", "error.notempty.paymentform.chequedate");
		}else{
			try{
				new SimpleDateFormat("dd/MM/yyyy").parse(chequeVo.getChequeDateString());
			}catch(Exception e) {
				errors.rejectValue("chequeDateString", "error.notvalid.paymentform.chequedate");
			}
		}
	}
	
}