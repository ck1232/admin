package com.admin.validator;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.admin.payment.vo.PaymentVO;


@Component
public class PaymentFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PaymentVO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PaymentVO paymentVo = (PaymentVO) target;
		
		/*if(paymentVo.getPaymentdateString() == null || paymentVo.getPaymentdateString().trim().isEmpty()){
			errors.rejectValue("paymentdateString", "error.notempty.paymentform.paymentdate");
		}else{
			try{
				new SimpleDateFormat("dd/MM/yyyy").parse(paymentVo.getPaymentdateString());
			}catch(Exception e) {
				errors.rejectValue("paymentdateString", "error.notvalid.paymentform.paymentdate");
			}
		}*/

		boolean hasPayment = true;
		switch(paymentVo.getType()) {
			case "salary":
			case "bonus":
			case "salarybonus":
			case "expense":
				if(!paymentVo.getPaymentmodecash() && !paymentVo.getPaymentmodecheque() && !paymentVo.getPaymentmodedirector()) {
					errors.rejectValue("paymentmodedirector", "error.notempty.paymentform.paymentmode");
					hasPayment = false;
				}
				break;
			case "invoice":
				if(!paymentVo.getPaymentmodecash() && !paymentVo.getPaymentmodecheque() && !paymentVo.getPaymentmodegiro() && !paymentVo.getPaymentmodePayToDirector()) {
					errors.rejectValue("paytodirectoramount", "error.notempty.paymentform.paymentmode");
					hasPayment = false;
				}
				break;
			default:
				if(!paymentVo.getPaymentmodecash() && !paymentVo.getPaymentmodecheque()) {
					errors.rejectValue("paymentmodecheque", "error.notempty.paymentform.paymentmode");
					hasPayment = false;
				}
		
		}

		if(hasPayment){ 
			if(paymentVo.getPaymentmodecash()){
				if(paymentVo.getCashamount() == null){
					errors.rejectValue("cashamount", "error.notempty.paymentform.cashamount");
				}else if(paymentVo.getCashamount().compareTo(BigDecimal.ZERO) <= 0){
					errors.rejectValue("cashamount", "error.notvalid.paymentform.cashamount");
				}
			}
			
			if(paymentVo.getPaymentmodecheque()){
				if(paymentVo.getChequeamount() == null){
					errors.rejectValue("chequeamount", "error.notempty.paymentform.chequeamount");
				}else if(paymentVo.getChequeamount().compareTo(BigDecimal.ZERO) <= 0){
					errors.rejectValue("chequeamount", "error.notvalid.paymentform.chequeamount");
				}
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "chequeno", "error.notempty.paymentform.chequeno");
				
				if(paymentVo.getChequedateString() == null || paymentVo.getChequedateString().trim().isEmpty()){
					errors.rejectValue("chequedateString", "error.notempty.paymentform.chequedate");
				}else{
					try{
						new SimpleDateFormat("dd/MM/yyyy").parse(paymentVo.getChequedateString());
					}catch(Exception e) {
						errors.rejectValue("chequedateString", "error.notvalid.paymentform.chequedate");
					}
				}
			}
			
			if("expense".equals(paymentVo.getType()) && paymentVo.getPaymentmodedirector()){
				if(paymentVo.getDirectoramount() == null){
					errors.rejectValue("directoramount", "error.notempty.paymentform.directoramount");
				}else if(paymentVo.getDirectoramount().compareTo(BigDecimal.ZERO) <= 0){
					errors.rejectValue("directoramount", "error.notvalid.paymentform.directoramount");
				}
			}
			
			if("invoice".equals(paymentVo.getType()) && paymentVo.getPaymentmodegiro()){
				if(paymentVo.getGiroamount() == null){
					errors.rejectValue("giroamount", "error.notempty.paymentform.giroamount");
				}else if(paymentVo.getGiroamount().compareTo(BigDecimal.ZERO) <= 0){
					errors.rejectValue("giroamount", "error.notvalid.paymentform.giroamount");
				}
			}
		}	
	}
	
}