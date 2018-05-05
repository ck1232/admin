package com.admin.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.admin.expense.vo.ExpenseVO;

@Component
public class ExpenseFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ExpenseVO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ExpenseVO expense = (ExpenseVO) target;
		
		if(expense.getExpenseTypeId() != null && expense.getExpenseTypeId()!= 15)
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "supplier", "error.notempty.expenseform.supplier");
		
		if(expense.getExpenseTypeId() != null && expense.getExpenseTypeId()== 1)
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "invoiceNo", "error.notempty.expenseform.invoiceno");
	}
	
}