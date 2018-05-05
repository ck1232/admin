package com.admin.payment.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.admin.expense.service.ExpenseService;
import com.admin.expense.vo.ExpenseVO;
import com.admin.grant.service.GrantService;
import com.admin.helper.GeneralUtils;
import com.admin.invoice.lookup.controller.ExpenseTypeLookup;
import com.admin.invoice.service.InvoiceService;
import com.admin.invoice.vo.InvoiceVO;
import com.admin.payment.vo.PaymentVO;
import com.admin.validator.PaymentFormValidator;


@Controller  
@EnableWebMvc
@Scope("request")
@RequestMapping(value = "/payment")
public class PaymentController {
	private static final Logger logger = Logger.getLogger(PaymentController.class);
	
	private InvoiceService invoiceService;
	private GrantService grantService;
	private ExpenseService expenseService;
	private ExpenseTypeLookup expenseTypeLookup;
	private PaymentFormValidator paymentFormValidator;
	
	public static final List<String> outMoneyModuleList = Arrays.asList(
			GeneralUtils.MODULE_BONUS, 
			GeneralUtils.MODULE_EXPENSE,
			GeneralUtils.MODULE_SALARY, 
			GeneralUtils.MODULE_SALARY_BONUS);
	public static final List<String> inMoneyModuleList = Arrays.asList(GeneralUtils.MODULE_INVOICE);

	@Autowired
	public PaymentController(InvoiceService invoiceService,
			GrantService grantService,
			ExpenseService expenseService,
			ExpenseTypeLookup expenseTypeLookup,
			PaymentFormValidator paymentFormValidator){
		this.invoiceService = invoiceService;
		this.grantService = grantService;
		this.expenseService = expenseService;
		this.expenseTypeLookup = expenseTypeLookup;
		this.paymentFormValidator = paymentFormValidator;
	}
	
	@InitBinder("paymentForm")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(paymentFormValidator);
	}
	
	@RequestMapping(value = "/createPayInvoice", method = RequestMethod.POST) //open payment page
	public String createPayInvoice(@RequestParam(value = "checkboxId", required=false) List<String> ids,
			final RedirectAttributes redirectAttributes, Model model) {
		if(ids == null || ids.isEmpty()){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select at least one record!");
			return "redirect:/invoice/listInvoice";
		}
		
		List<Long> idList = new ArrayList<Long>();
		String type = null;
		for(String id : ids){
			String[] splitId = id.split("-");
			if(splitId[0] != null && splitId[1] != null){
				type = type == null ? splitId[1] : type;
				if(!type.equalsIgnoreCase(splitId[1])) {
					redirectAttributes.addFlashAttribute("css", "danger");
					redirectAttributes.addFlashAttribute("msg", "Invoice and grant cannot be in one payment!");
					return "redirect:/invoice/listInvoice";
				}
				idList.add(Long.valueOf(splitId[0]));
			}
		}
		List<InvoiceVO> invoiceList = new ArrayList<InvoiceVO>();
		BigDecimal totalamount = BigDecimal.ZERO;
		String posturl = "";
		if(type.equalsIgnoreCase(GeneralUtils.MODULE_INVOICE)){
			invoiceList = invoiceService.findByIdList(idList);
			posturl = "/admin/payment/createInvoicePayment";
		}else if(type.equalsIgnoreCase("grant")){
			invoiceList = grantService.findByIdList(idList);
			posturl = "/admin/payment/createGrantPayment";
		}
		for(InvoiceVO invoice : invoiceList) {
			totalamount = totalamount.add(invoice.getTotalAmt());
		}
		
		PaymentVO paymentvo = new PaymentVO();
		paymentvo.setType("invoice");
		model.addAttribute("paymentForm", paymentvo);
		model.addAttribute("invoiceList", invoiceList);
		model.addAttribute("totalamount", totalamount);
		model.addAttribute("lastdate", invoiceList.get(invoiceList.size()-1).getInvoicedateString());
		model.addAttribute("idList", idList);
		model.addAttribute("ids", ids);
		model.addAttribute("posturl", posturl);
		return "createPayInvoice";
	}
	
	@RequestMapping(value = "/createInvoicePayment", method = RequestMethod.POST)
    public String saveInvoicePayment(
    		@RequestParam(value = "referenceIds", required=false) List<Integer> invoiceIdList,
    		@RequestParam(value = "ids", required=false) List<String> idList,
    		@RequestParam(value = "totalamount", required=false) BigDecimal totalamount,
    		@RequestParam(value = "lastdate", required=false) String lastdate,
    		@ModelAttribute("paymentForm") @Validated PaymentVO paymentVo, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		logger.debug("saveInvoicePayment() : " + paymentVo.toString());
		GeneralUtils.validate(paymentVo, "paymentForm", result);
		List<Long> idLongList = new ArrayList<Long>();
		if (!result.hasErrors()) {
			boolean hasErrors = false;
			if(!validateInputAmount(totalamount, paymentVo)){
				hasErrors = true;
				result.rejectValue("cashamount", "error.notequal.paymentform.invoicetotalamount");
				result.rejectValue("chequeamount", "error.notequal.paymentform.invoicetotalamount");
				result.rejectValue("giroamount", "error.notequal.paymentform.invoicetotalamount");
			}
			
			if(!hasErrors){
				paymentVo.setReferenceType(GeneralUtils.MODULE_INVOICE);
				try{ 
					paymentVo.setPaymentDate(new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).parse(paymentVo.getPaymentdateString()));
					if(paymentVo.getPaymentmodecheque())
						paymentVo.setChequedate(new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).parse(paymentVo.getChequedateString()));
				}catch(Exception e) {
					logger.info("Error parsing date string");
				}
				
				
				for(Integer s : invoiceIdList) 
					idLongList.add(s.longValue());
				
				invoiceService.saveInvoicePayment(paymentVo, idLongList);
				redirectAttributes.addFlashAttribute("css", "success");
				redirectAttributes.addFlashAttribute("msg", "Payment saved successfully!");
		        return "redirect:/invoice/listInvoice"; 
			}
		}
		
		List<InvoiceVO> invoiceList = invoiceService.findByIdList(idLongList);
		for(InvoiceVO invoice : invoiceList) {
			invoice.setInvoicedateString(new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).format(invoice.getInvoiceDate()));
		}
		model.addAttribute("paymentForm", paymentVo);
		model.addAttribute("invoiceList", invoiceList);
		model.addAttribute("idList", invoiceIdList);
		model.addAttribute("ids", idList);
		model.addAttribute("totalamount", totalamount);
		model.addAttribute("lastdate", invoiceList.get(invoiceList.size()-1).getInvoicedateString());
		model.addAttribute("posturl", "/admin/payment/createInvoicePayment");
		return "createPayInvoice";
		
    }  
	
	@RequestMapping(value = "/createGrantPayment", method = RequestMethod.POST)
    public String saveGrantPayment(
    		@RequestParam(value = "referenceIds", required=false) List<Integer> grantIdList,
    		@RequestParam(value = "ids", required=false) List<String> ids,
    		@RequestParam(value = "totalamount", required=false) BigDecimal totalamount,
    		@RequestParam(value = "lastdate", required=false) String lastdate,
    		@ModelAttribute("paymentForm") @Validated PaymentVO paymentVo, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		logger.debug("saveGrantPayment() : " + paymentVo.toString());
		GeneralUtils.validate(paymentVo, "paymentForm", result);
		List<Long> idLongList = new ArrayList<Long>();
		for(Integer grantId : grantIdList) {
			idLongList.add(grantId.longValue());
		}
		if (!result.hasErrors()) {
			boolean hasErrors = false;
			if(!validateInputAmount(totalamount, paymentVo)){
				hasErrors = true;
				result.rejectValue("cashamount", "error.notequal.paymentform.granttotalamount");
				result.rejectValue("chequeamount", "error.notequal.paymentform.granttotalamount");
				result.rejectValue("giroamount", "error.notequal.paymentform.granttotalamount");
			}
			
			if(!hasErrors){
				paymentVo.setReferenceType(GeneralUtils.MODULE_GRANT);
				try{ 
					paymentVo.setPaymentDate(new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).parse(paymentVo.getPaymentdateString()));
					if(paymentVo.getPaymentmodecheque())
						paymentVo.setChequedate(new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).parse(paymentVo.getChequedateString()));
				}catch(Exception e) {
					logger.info("Error parsing date string");
				}
				
				
				
				grantService.saveGrantPayment(paymentVo, idLongList);
				redirectAttributes.addFlashAttribute("css", "success");
				redirectAttributes.addFlashAttribute("msg", "Payment saved successfully!");
		        return "redirect:/invoice/listInvoice"; 
			}
		}
		List<InvoiceVO> grantList = grantService.findByIdList(idLongList);
		/*for(InvoiceVO grant : grantList) {
			invoice.setInvoicedateString(new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).format(invoice.getInvoiceDate()));
		}*/
		model.addAttribute("paymentForm", paymentVo);
		model.addAttribute("invoiceList", grantList);
		model.addAttribute("idList", grantIdList);
		model.addAttribute("ids", ids);
		model.addAttribute("totalamount", totalamount);
		if(!grantList.isEmpty()){
			model.addAttribute("lastdate", grantList.get(grantList.size()-1).getInvoicedateString());
		}
		model.addAttribute("posturl", "/admin/payment/createGrantPayment");
		return "createPayInvoice";
    }  
	
	@RequestMapping(value = "/createPayExpense", method = RequestMethod.POST)
	public String createPayExpense(@RequestParam(value = "checkboxId", required=false) List<String> ids,
			final RedirectAttributes redirectAttributes, Model model) {
		if(ids == null || ids.isEmpty()){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select at least one record!");
			return "redirect:/expense/listExpense";
		}
		
		List<Long> idList = new ArrayList<Long>();
		for(String id : ids){
			idList.add(Long.valueOf(id));
		}
		List<ExpenseVO> expenseList = expenseService.findByIdList(idList);
		BigDecimal totalamount = BigDecimal.ZERO;
		for(ExpenseVO expense : expenseList) {
			if(expense.getExpensetype().equals(GeneralUtils.EXPENSE_TYPE_STOCK_CHINA)) {
				redirectAttributes.addFlashAttribute("css", "danger");
				redirectAttributes.addFlashAttribute("msg", "Payment is not allowed for Stock(China)!");
				return "redirect:/expense/listExpense";
			}
			totalamount = totalamount.add(expense.getTotalAmt());
		}
		
		PaymentVO paymentvo = new PaymentVO();
		paymentvo.setType(GeneralUtils.MODULE_EXPENSE);
		model.addAttribute("paymentForm", paymentvo);
		model.addAttribute("expenseList", expenseList);
		model.addAttribute("idList", idList);
		model.addAttribute("totalamount", totalamount);
		model.addAttribute("lastdate", expenseList.get(expenseList.size()-1).getExpensedateString());
		model.addAttribute("posturl", "/admin/payment/createExpensePayment");
		return "createPayExpense";
	}
	
	@RequestMapping(value = "/createExpensePayment", method = RequestMethod.POST)
    public String saveExpensePayment(
    		@RequestParam(value = "referenceIds", required=false) List<Integer> expenseIdList,
    		@RequestParam(value = "totalamount", required=false) BigDecimal totalamount,
    		@RequestParam(value = "lastdate", required=false) String lastdate,
    		@ModelAttribute("paymentForm") @Validated PaymentVO paymentVo, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		logger.debug("saveExpensePayment() : " + paymentVo.toString());
		GeneralUtils.validate(paymentVo, "paymentForm", result);
		List<Long> idLongList = new ArrayList<Long>();
		for(Integer expenseId : expenseIdList) {
			idLongList.add(expenseId.longValue());
		}
		if (!result.hasErrors()) {
			boolean hasErrors = false;
			if(!validateInputAmount(totalamount, paymentVo)){
				hasErrors = true;
				result.rejectValue("cashamount", "error.notequal.paymentform.expensetotalamount");
				result.rejectValue("chequeamount", "error.notequal.paymentform.expensetotalamount");
				result.rejectValue("directoramount", "error.notequal.paymentform.expensetotalamount");
			}
			
			if(!hasErrors){
				paymentVo.setReferenceType(GeneralUtils.MODULE_EXPENSE);
				try{ 
					paymentVo.setPaymentDate(new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).parse(paymentVo.getPaymentdateString()));
					if(paymentVo.getPaymentmodecheque())
						paymentVo.setChequedate(new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).parse(paymentVo.getChequedateString()));
				}catch(Exception e) {
					logger.info("Error parsing date string");
				}
				expenseService.saveExpensePayment(paymentVo, idLongList);
				redirectAttributes.addFlashAttribute("css", "success");
				redirectAttributes.addFlashAttribute("msg", "Payment saved successfully!");
		        return "redirect:/expense/listExpense";  
			}
		}
		List<ExpenseVO> expenseList = expenseService.findByIdList(idLongList);
		for(ExpenseVO expense : expenseList) {
			expense.setExpensedateString(new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).format(expense.getExpenseDate()));
			expense.setExpensetype(expenseTypeLookup.getExpenseTypeById(expense.getExpenseTypeId()));
		}
		model.addAttribute("paymentForm", paymentVo);
		model.addAttribute("expenseList", expenseList);
		model.addAttribute("idList", expenseIdList);
		model.addAttribute("totalamount", totalamount);
		model.addAttribute("lastdate", expenseList.get(expenseList.size()-1).getExpensedateString());
		model.addAttribute("posturl", "/admin/payment/createExpensePayment");
		return "createPayExpense";
    }  
	
	private boolean validateInputAmount(BigDecimal totalamount, PaymentVO paymentVo) {
		BigDecimal inputAmount = BigDecimal.ZERO;
		if(paymentVo.getPaymentmodecash()) {
			inputAmount = inputAmount.add(paymentVo.getCashamount());
		}
		if(paymentVo.getPaymentmodecheque()) {
			inputAmount = inputAmount.add(paymentVo.getChequeamount());
		}
		if(outMoneyModuleList.contains(paymentVo.getType()) && paymentVo.getPaymentmodedirector()) {
			inputAmount = inputAmount.add(paymentVo.getDirectoramount());
		}
		if(inMoneyModuleList.contains(paymentVo.getType()) && paymentVo.getPaymentmodegiro()) {
			inputAmount = inputAmount.add(paymentVo.getGiroamount());
		}
		if(inMoneyModuleList.contains(paymentVo.getType()) && paymentVo.getPaymentmodePayToDirector()) {
			inputAmount = inputAmount.add(paymentVo.getPaytodirectoramount());
		}
		if(totalamount.compareTo(inputAmount) == 0){
			return true;
		}
		return false;
	}
	
	
}
