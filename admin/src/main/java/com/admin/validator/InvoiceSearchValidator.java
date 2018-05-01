package com.admin.validator;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.admin.invoice.vo.InvoiceSearchCriteria;

@Component
public class InvoiceSearchValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return InvoiceSearchCriteria.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		InvoiceSearchCriteria searchCriteria = (InvoiceSearchCriteria) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "messenger", "error.notempty.invoiceexportform.messenger");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "status", "error.notempty.invoiceexportform.status");
		
		boolean validDate = true;
		Date datefrom = new Date(), dateto = new Date();
		
		if(!"".equals(searchCriteria.getInvoicedatefrom())) {
			try{
				datefrom = new SimpleDateFormat("MM/dd/yyyy").parse(searchCriteria.getInvoicedatefrom());
			}catch(Exception e){
				errors.rejectValue("createddatefrom", "error.notvalid.invoiceexportform.invoicedatefrom");
				validDate = false;
			}
		}
		
		if(!"".equals(searchCriteria.getInvoicedateto())) {
			try{
				dateto = new SimpleDateFormat("MM/dd/yyyy").parse(searchCriteria.getInvoicedateto());
			}catch(Exception e){
				errors.rejectValue("createddateto", "error.notvalid.invoiceexportform.invoicedateto");
				validDate = false;
			}
		}
		
		if(validDate && !"".equals(searchCriteria.getInvoicedatefrom()) && !"".equals(searchCriteria.getInvoicedateto())){
			if(datefrom.compareTo(dateto) > 0){
				errors.rejectValue("createddatefrom", "error.invoiceexportform.invoicedatefrom.later.than.invoicedateto");
				errors.rejectValue("createddateto", "error.invoiceexportform.invoicedateto.earlier.than.invoicedatefrom");
			}
		}

		
	}
	
}