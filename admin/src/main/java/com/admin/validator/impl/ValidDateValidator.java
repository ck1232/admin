package com.admin.validator.impl;

import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.admin.validator.annotation.ValidDate;

public class ValidDateValidator implements ConstraintValidator<ValidDate, String> {
	private String dateFormat = "";
	private boolean nullable;
	@Override
	public void initialize(ValidDate constraintAnnotation) {
		this.dateFormat = constraintAnnotation.dateFormat();
		this.nullable = constraintAnnotation.nullable();
	}

	@Override
	public boolean isValid(String dateString, ConstraintValidatorContext context) {
		try{
			if(nullable && (dateString == null || dateString.trim().isEmpty())){
				return true;
			}
			if(dateString.length() == dateFormat.length()) {
				new SimpleDateFormat(dateFormat).parse(dateString);
				return true;
			}
		}catch(Exception e) {
		}
		return false;
	}

}
