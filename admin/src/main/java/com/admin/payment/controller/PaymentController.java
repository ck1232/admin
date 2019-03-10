package com.admin.payment.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.admin.cheque.vo.ChequeVO;
import com.admin.cheque.vo.service.ChequeService;
import com.admin.expense.service.ExpenseService;
import com.admin.expense.vo.ExpenseVO;
import com.admin.grant.service.GrantService;
import com.admin.helper.GeneralUtils;
import com.admin.invoice.lookup.controller.ExpenseTypeLookup;
import com.admin.invoice.service.InvoiceService;
import com.admin.invoice.vo.InvoiceVO;
import com.admin.payment.vo.PaymentVO;
import com.admin.salarybonus.service.SalaryBonusService;
import com.admin.salarybonus.vo.SalaryBonusVO;
import com.admin.salarybonus.vo.TypeEnum;
import com.admin.to.BonusPaymentRsTO;
import com.admin.to.ExpensePaymentRsTO;
import com.admin.to.GrantPaymentRsTO;
import com.admin.to.InvoicePaymentRsTO;
import com.admin.to.PaymentDetailTO;
import com.admin.to.PaymentRsTO;
import com.admin.to.SalaryPaymentRsTO;
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
	private SalaryBonusService salaryBonusService;
	private ChequeService chequeService;
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
			SalaryBonusService salaryBonusService,
			ChequeService chequeService,
			ExpenseTypeLookup expenseTypeLookup,
			PaymentFormValidator paymentFormValidator){
		this.invoiceService = invoiceService;
		this.grantService = grantService;
		this.expenseService = expenseService;
		this.salaryBonusService = salaryBonusService;
		this.chequeService = chequeService;
		this.expenseTypeLookup = expenseTypeLookup;
		this.paymentFormValidator = paymentFormValidator;
	}
	
	@InitBinder("paymentForm")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(paymentFormValidator);
	}
	
	/* Invoice Payment Start */
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
				
				invoiceService.saveInvoicePaymentByInvoiceId(paymentVo, idLongList);
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
				
				
				
				grantService.saveGrantPaymentByGrantId(paymentVo, idLongList);
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
	/* Invoice Payment End */
	
	/* Expense Payment Start */
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
			if(expense.getExpenseType().equals(GeneralUtils.EXPENSE_TYPE_STOCK_CHINA)) {
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
				expenseService.saveExpensePaymentByExpenseId(paymentVo, idLongList);
				redirectAttributes.addFlashAttribute("css", "success");
				redirectAttributes.addFlashAttribute("msg", "Payment saved successfully!");
		        return "redirect:/expense/listExpense";  
			}
		}
		List<ExpenseVO> expenseList = expenseService.findByIdList(idLongList);
		for(ExpenseVO expense : expenseList) {
			expense.setExpensedateString(new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).format(expense.getExpenseDate()));
			expense.setExpenseType(expenseTypeLookup.getExpenseTypeById(expense.getExpenseTypeId()));
		}
		model.addAttribute("paymentForm", paymentVo);
		model.addAttribute("expenseList", expenseList);
		model.addAttribute("idList", expenseIdList);
		model.addAttribute("totalamount", totalamount);
		model.addAttribute("lastdate", expenseList.get(expenseList.size()-1).getExpensedateString());
		model.addAttribute("posturl", "/admin/payment/createExpensePayment");
		return "createPayExpense";
    }  
	/* Expense Payment End */
	
	/* Salary Payment Start */
	@RequestMapping(value = "/createPaySalary", method = RequestMethod.POST)
	public String createPaySalary(@RequestParam(value = "checkboxId", required=false) List<String> ids,
			final RedirectAttributes redirectAttributes, Model model) {
		if(ids == null || ids.size() < 1){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select at least one record!");
			return "redirect:/salarybonus/listSalaryBonus";
		}
		
		List<Long> idList = new ArrayList<Long>();
		for(String id : ids){
			idList.add(Long.valueOf(id));
		}
		List<SalaryBonusVO> salaryBonusVoList = salaryBonusService.findSalaryByIdList(idList);
		BigDecimal totalamount = BigDecimal.ZERO;
		for(SalaryBonusVO salaryBonusVo : salaryBonusVoList) {
			salaryBonusVo.setDateString(GeneralUtils.convertDateToString(salaryBonusVo.getDate(), GeneralUtils.SALARY_DATE_FORMAT));
			totalamount = totalamount.add(salaryBonusVo.getTakehomeAmt());
		}
		
		PaymentVO paymentvo = new PaymentVO();
		paymentvo.setType(GeneralUtils.MODULE_SALARY);
		model.addAttribute("paymentForm", paymentvo);
		model.addAttribute("salaryList", salaryBonusVoList);
		model.addAttribute("idList", idList);
		model.addAttribute("totalamount", totalamount);
		model.addAttribute("lastdate", salaryBonusVoList.get(salaryBonusVoList.size()-1).getDateString());
		model.addAttribute("posturl", "/admin/payment/createSalaryPayment");
		return "createPaySalary";
	}
	
	@RequestMapping(value = "/createSalaryPayment", method = RequestMethod.POST)
    public String saveSalaryPayment(
    		@RequestParam(value = "referenceIds", required=false) List<Integer> salaryIdList,
    		@RequestParam(value = "totalamount", required=false) BigDecimal totalamount,
    		@RequestParam(value = "lastdate", required=false) String lastdate,
    		@ModelAttribute("paymentForm") @Validated PaymentVO paymentVo, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		logger.debug("saveSalaryPayment() : " + paymentVo.toString());
		GeneralUtils.validate(paymentVo, "paymentForm", result);
		List<Long> idLongList = new ArrayList<Long>();
		for(Integer salaryId : salaryIdList) {
			idLongList.add(salaryId.longValue());
		}
		List<SalaryBonusVO> salaryBonusVoList = salaryBonusService.findSalaryByIdList(idLongList);
		for(SalaryBonusVO salaryBonusVo : salaryBonusVoList) {
			salaryBonusVo.setDateString(GeneralUtils.convertDateToString(salaryBonusVo.getDate(), GeneralUtils.SALARY_DATE_FORMAT));
		}
		if (!result.hasErrors()) {
			boolean hasErrors = false;
			if(!validateInputAmount(totalamount, paymentVo)){
				hasErrors = true;
				result.rejectValue("cashamount", "error.notequal.paymentform.salarytotalamount");
				result.rejectValue("chequeamount", "error.notequal.paymentform.salarytotalamount");
			}
			if(!validateInputDate(lastdate, GeneralUtils.SALARY_DATE_FORMAT, paymentVo.getPaymentdateString())){
				hasErrors = true;
				result.rejectValue("paymentdateString", "error.paymentform.paymentdate.before.salarylastdate");
			}
			
			if(paymentVo.getPaymentmodecheque() && !validateInputDate(lastdate, GeneralUtils.SALARY_DATE_FORMAT, paymentVo.getChequedateString())){
				hasErrors = true;
				result.rejectValue("chequedateString", "error.paymentform.chequedate.before.salarylastdate");
			}
			
			if(!hasErrors){
				paymentVo.setReferenceType(GeneralUtils.MODULE_SALARY);
				try{ 
					paymentVo.setPaymentDate(new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).parse(paymentVo.getPaymentdateString()));
					if(paymentVo.getPaymentmodecheque())
						paymentVo.setChequedate(new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).parse(paymentVo.getChequedateString()));
				}catch(Exception e) {
					logger.info("Error parsing date string");
				}
				salaryBonusService.saveSalaryPaymentBySalaryId(paymentVo, idLongList);
				redirectAttributes.addFlashAttribute("css", "success");
				redirectAttributes.addFlashAttribute("msg", "Payment saved successfully!");
		        return "redirect:/salarybonus/listSalaryBonus";  
			}
		}
		model.addAttribute("paymentForm", paymentVo);
		model.addAttribute("salaryList", salaryBonusVoList);
		model.addAttribute("idList", salaryIdList);
		model.addAttribute("totalamount", totalamount);
		model.addAttribute("lastdate", salaryBonusVoList.get(salaryBonusVoList.size()-1).getDateString());
		model.addAttribute("posturl", "/admin/payment/createSalaryPayment");
		return "createPaySalary";
    }
	/* Salary Payment End */
	
	/* Bonus Payment Start */
	@RequestMapping(value = "/createPayBonus", method = RequestMethod.POST)
	public String createPayBonus(@RequestParam(value = "checkboxId", required=false) List<String> ids,
			final RedirectAttributes redirectAttributes, Model model) {
		if(ids == null || ids.size() < 1){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select at least one record!");
			return "redirect:/salarybonus/listSalaryBonus";
		}
		
		List<Long> idList = new ArrayList<Long>();
		for(String id : ids){
			idList.add(Long.valueOf(id));
		}
		List<SalaryBonusVO> salaryBonusVoList = salaryBonusService.findBonusByIdList(idList);
		BigDecimal totalamount = BigDecimal.ZERO;
		for(SalaryBonusVO salaryBonusVo : salaryBonusVoList) {
			salaryBonusVo.setDateString(GeneralUtils.convertDateToString(salaryBonusVo.getDate(), GeneralUtils.BONUS_DATE_FORMAT));
			totalamount = totalamount.add(salaryBonusVo.getTakehomeAmt());
		}
		
		PaymentVO paymentvo = new PaymentVO();
		paymentvo.setType(GeneralUtils.MODULE_BONUS);
		model.addAttribute("paymentForm", paymentvo);
		model.addAttribute("bonusList", salaryBonusVoList);
		model.addAttribute("idList", idList);
		model.addAttribute("totalamount", totalamount);
		model.addAttribute("lastdate", salaryBonusVoList.get(salaryBonusVoList.size()-1).getDateString());
		model.addAttribute("posturl", "/admin/payment/createBonusPayment");
		return "createPayBonus";
	}
	
	@RequestMapping(value = "/createBonusPayment", method = RequestMethod.POST)
    public String saveBonusPayment(
    		@RequestParam(value = "referenceIds", required=false) List<Integer> bonusIdList,
    		@RequestParam(value = "totalamount", required=false) BigDecimal totalamount,
    		@RequestParam(value = "lastdate", required=false) String lastdate,
    		@ModelAttribute("paymentForm") @Validated PaymentVO paymentVo, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		logger.debug("saveBonusPayment() : " + paymentVo.toString());
		GeneralUtils.validate(paymentVo, "paymentForm", result);
		List<Long> idLongList = new ArrayList<Long>();
		for(Integer bonusId : bonusIdList) {
			idLongList.add(bonusId.longValue());
		}
		List<SalaryBonusVO> salaryBonusVoList = salaryBonusService.findBonusByIdList(idLongList);
		for(SalaryBonusVO salaryBonusVo : salaryBonusVoList) {
			salaryBonusVo.setDateString(GeneralUtils.convertDateToString(salaryBonusVo.getDate(), GeneralUtils.BONUS_DATE_FORMAT));
		}
		if (!result.hasErrors()) {
			boolean hasErrors = false;
			if(!validateInputAmount(totalamount, paymentVo)){
				hasErrors = true;
				result.rejectValue("cashamount", "error.notequal.paymentform.bonustotalamount");
				result.rejectValue("chequeamount", "error.notequal.paymentform.bonustotalamount");
			}
			if(!validateInputDate(lastdate, GeneralUtils.BONUS_DATE_FORMAT, paymentVo.getPaymentdateString())){
				hasErrors = true;
				result.rejectValue("paymentdateString", "error.paymentform.paymentdate.before.bonuslastdate");
			}
			
			if(paymentVo.getPaymentmodecheque() && !validateInputDate(lastdate, GeneralUtils.BONUS_DATE_FORMAT, paymentVo.getChequedateString())){
				hasErrors = true;
				result.rejectValue("chequedateString", "error.paymentform.chequedate.before.bonuslastdate");
			}
			
			if(!hasErrors){
				paymentVo.setReferenceType(GeneralUtils.MODULE_BONUS);
				try{ 
					paymentVo.setPaymentDate(new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).parse(paymentVo.getPaymentdateString()));
					if(paymentVo.getPaymentmodecheque())
						paymentVo.setChequedate(new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).parse(paymentVo.getChequedateString()));
				}catch(Exception e) {
					logger.info("Error parsing date string");
				}
				salaryBonusService.saveBonusPaymentByBonusId(paymentVo, idLongList);
				redirectAttributes.addFlashAttribute("css", "success");
				redirectAttributes.addFlashAttribute("msg", "Payment saved successfully!");
		        return "redirect:/salarybonus/listSalaryBonus";  
			}
		}
		model.addAttribute("paymentForm", paymentVo);
		model.addAttribute("bonusList", salaryBonusVoList);
		model.addAttribute("idList", bonusIdList);
		model.addAttribute("totalamount", totalamount);
		model.addAttribute("lastdate", salaryBonusVoList.get(salaryBonusVoList.size()-1).getDateString());
		model.addAttribute("posturl", "/admin/payment/createBonusPayment");
		return "createPayBonus";
    }  
	/* Bonus Payment End */
	
	/* Salary Bonus Payment Start */
	@RequestMapping(value = "/createPaySalaryBonus", method = RequestMethod.POST)
	public String createMultiplePaySalary(@RequestParam(value = "checkboxId", required=false) List<String> ids,
			final RedirectAttributes redirectAttributes, Model model) {
		List<Long> salaryIdList = new ArrayList<Long>();
		List<Long> bonusIdList = new ArrayList<Long>();
		if(ids == null || ids.size() < 1){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select at least one record!");
			return "redirect:/salarybonus/listSalaryBonus";
		}else{
			for(String id : ids){
				String[] idArray = id.split("-");
				if(idArray.length != 2 || !GeneralUtils.isInteger(idArray[0])){
					redirectAttributes.addFlashAttribute("css", "danger");
					redirectAttributes.addFlashAttribute("msg", "Invalid Selection, Please try again.");
					return "redirect:/salarybonus/listSalaryBonus";
				}else{
					Long salaryBonusId = Long.parseLong(idArray[0]);
					if(idArray[1].equals(TypeEnum.SALARY.getType())) {
						salaryIdList.add(salaryBonusId);
					}else if(idArray[1].equals(TypeEnum.BONUS.getType())) {
						bonusIdList.add(salaryBonusId);
					}
				}
			}
		}
		List<SalaryBonusVO> salaryBonusVoList = new ArrayList<SalaryBonusVO>();
		if(!salaryIdList.isEmpty()){
			salaryBonusVoList.addAll(salaryBonusService.findSalaryByIdList(salaryIdList));
		}
		
		if(!bonusIdList.isEmpty()){
			salaryBonusVoList.addAll(salaryBonusService.findBonusByIdList(bonusIdList));
		}
		
		if(salaryBonusVoList.isEmpty()){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Invalid Selection, Please try again.");
			return "redirect:/salarybonus/listSalaryBonus";
		}
		
		//check if all the record is for the same employee
		Set<Long> employeeIdSet = new HashSet<Long>();
		for(SalaryBonusVO vo : salaryBonusVoList){
			employeeIdSet.add(vo.getEmployeeVO().getEmployeeId());
		}
		if(employeeIdSet.size() > 1){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select records for one employee only!");
			return "redirect:/salarybonus/listSalaryBonus";
		}
		
		BigDecimal totalamount = BigDecimal.ZERO;
		for(SalaryBonusVO salaryBonusVo : salaryBonusVoList) {
			if(salaryBonusVo.getBonusAmt() != null && salaryBonusVo.getBonusAmt().compareTo(BigDecimal.ZERO) > 0){
				totalamount = totalamount.add(salaryBonusVo.getBonusAmt());
			}else{
				totalamount = totalamount.add(salaryBonusVo.getTakehomeAmt());
			}
		}
		
		Collections.sort(salaryBonusVoList, new SalaryBonusComparator());
		
		PaymentVO paymentvo = new PaymentVO();
		paymentvo.setType(GeneralUtils.MODULE_SALARY_BONUS);
		model.addAttribute("paymentForm", paymentvo);
		model.addAttribute("salaryList", salaryBonusVoList);
		model.addAttribute("idList", ids);
		model.addAttribute("totalamount", totalamount);
		model.addAttribute("lastdate", salaryBonusVoList.get(0).getDateString());
		model.addAttribute("posturl", "/admin/payment/createSalaryBonusPayment");
		return "createPaySalaryBonus";
	}
	
	@RequestMapping(value = "/createSalaryBonusPayment", method = RequestMethod.POST)
    public String saveSalaryBonusPayment(
    		@RequestParam(value = "referenceIds", required=false) List<String> ids,
    		@RequestParam(value = "totalamount", required=false) BigDecimal totalamount,
    		@RequestParam(value = "lastdate", required=false) String lastdate,
    		@ModelAttribute("paymentForm") @Validated PaymentVO paymentVo, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		logger.debug("saveSalaryBonusPayment() : " + paymentVo.toString());
		GeneralUtils.validate(paymentVo, "paymentForm", result);
		List<Long> salaryIdList = new ArrayList<Long>();
		List<Long> bonusIdList = new ArrayList<Long>();
		if(ids == null || ids.size() < 1){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select at least one record!");
			return "redirect:/salarybonus/listSalaryBonus";
		}else{
			for(String id : ids){
				String[] idArray = id.split("-");
				if(idArray.length != 2 || !GeneralUtils.isInteger(idArray[0])){
					redirectAttributes.addFlashAttribute("css", "danger");
					redirectAttributes.addFlashAttribute("msg", "Invalid Selection, Please try again.");
					return "redirect:/salarybonus/listSalaryBonus";
				}else{
					Long salaryBonusId = Long.parseLong(idArray[0]);
					if(idArray[1].equals(TypeEnum.SALARY.getType())) {
						salaryIdList.add(salaryBonusId);
					}else if(idArray[1].equals(TypeEnum.BONUS.getType())) {
						bonusIdList.add(salaryBonusId);
					}
				}
			}
		}
		List<SalaryBonusVO> salaryBonusVoList = new ArrayList<SalaryBonusVO>();
		if(!salaryIdList.isEmpty()){
			salaryBonusVoList.addAll(salaryBonusService.findSalaryByIdList(salaryIdList));
		}
		
		if(!bonusIdList.isEmpty()){
			salaryBonusVoList.addAll(salaryBonusService.findBonusByIdList(bonusIdList));
		}
		
		if(salaryBonusVoList.isEmpty()){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Invalid Selection, Please try again.");
			return "redirect:/salarybonus/listSalaryBonus";
		}
		
		for(SalaryBonusVO salaryBonusVo : salaryBonusVoList) {
			if(salaryBonusVo.getType().equals(GeneralUtils.TYPE_BONUS)){
				salaryBonusVo.setDateString(GeneralUtils.convertDateToString(salaryBonusVo.getDate(), GeneralUtils.BONUS_DATE_FORMAT));
			}else if(salaryBonusVo.getType().equals(GeneralUtils.TYPE_SALARY)){
				salaryBonusVo.setDateString(GeneralUtils.convertDateToString(salaryBonusVo.getDate(), GeneralUtils.SALARY_DATE_FORMAT));
			}
		}
		if (!result.hasErrors()) {
			boolean hasErrors = false;
			if(!validateInputAmount(totalamount, paymentVo)){
				hasErrors = true;
				result.rejectValue("cashamount", "error.notequal.paymentform.totalamount");
				result.rejectValue("chequeamount", "error.notequal.paymentform.totalamount");
				result.rejectValue("directoramount", "error.notequal.paymentform.totalamount");
			}
			
			if(!hasErrors){
				paymentVo.setPaymentDate(GeneralUtils.convertStringToDate(paymentVo.getPaymentdateString(), GeneralUtils.STANDARD_DATE_FORMAT));
				if(paymentVo.getPaymentmodecheque()){
					paymentVo.setChequedate(GeneralUtils.convertStringToDate(paymentVo.getChequedateString(), GeneralUtils.STANDARD_DATE_FORMAT));
				}
				
				salaryBonusService.saveSalaryBonusPayment(paymentVo, salaryIdList, bonusIdList);
				
				
				redirectAttributes.addFlashAttribute("css", "success");
				redirectAttributes.addFlashAttribute("msg", "Payment saved successfully!");
		        return "redirect:/salarybonus/listSalaryBonus";  
			}
		}
		Collections.sort(salaryBonusVoList, new SalaryBonusComparator());
		
		model.addAttribute("paymentForm", paymentVo);
		model.addAttribute("salaryList", salaryBonusVoList);
		model.addAttribute("idList", ids);
		model.addAttribute("totalamount", totalamount);
		model.addAttribute("lastdate", salaryBonusVoList.get(0).getDateString());
		model.addAttribute("posturl", "/admin/payment/createSalaryBonusPayment");
		return "createPaySalaryBonus";
    }  
	/* Salary Bonus Payment End */
	
	/* Bounce Cheque Payment Start */
	@RequestMapping(value = "/createPayBounceCheque", method = RequestMethod.POST)
	public String createPayBounceCheque(@RequestParam(value = "bounceBtn", required=false) String id, 
			RedirectAttributes redirectAttributes, Model model) {
		ChequeVO chequeVo = chequeService.findById(Long.parseLong(id));
		BigDecimal totalamount = chequeVo.getChequeAmt();
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(Integer.parseInt(id));
		PaymentVO paymentvo = new PaymentVO();
		List<PaymentDetailTO> paymentDetailTOList = chequeService.getPaymentDetailListByChequeId(Long.parseLong(id));
		if(paymentDetailTOList != null && !paymentDetailTOList.isEmpty()) {
			PaymentRsTO paymentRs = paymentDetailTOList.get(0).getPaymentRsTOSet().iterator().next();
			if(paymentRs instanceof ExpensePaymentRsTO) {
				paymentvo.setType(GeneralUtils.MODULE_EXPENSE);
			} else if(paymentRs instanceof InvoicePaymentRsTO) {
				paymentvo.setType(GeneralUtils.MODULE_INVOICE);
			} else if(paymentRs instanceof GrantPaymentRsTO) {
				paymentvo.setType(GeneralUtils.MODULE_GRANT);
			} else if(paymentRs instanceof SalaryPaymentRsTO) {
				paymentvo.setType(GeneralUtils.MODULE_SALARY);
			} else if(paymentRs instanceof BonusPaymentRsTO) {
				paymentvo.setType(GeneralUtils.MODULE_BONUS);
			}
		}
		model.addAttribute("paymentForm", paymentvo);
		model.addAttribute("bounced", "true");
		model.addAttribute("cheque", chequeVo);
		model.addAttribute("idList", idList);
		model.addAttribute("totalamount", totalamount);
		model.addAttribute("lastdate", chequeVo.getChequeDateString());
		model.addAttribute("posturl", "/admin/payment/createBounceChequePayment");
		return "createPayBounceCheque";
	}  
	
	@RequestMapping(value = "/createBounceChequePayment", method = RequestMethod.POST)
    public String saveBounceChequePayment(
    		@RequestParam(value = "referenceIds", required=false) List<Integer> chequeIdList,
    		@RequestParam(value = "totalamount", required=false) BigDecimal totalamount,
    		@RequestParam(value = "lastdate", required=false) String lastdate,
    		@ModelAttribute("paymentForm") @Validated PaymentVO paymentVo, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		logger.debug("saveBounceChequePayment() : " + paymentVo.toString());
		GeneralUtils.validate(paymentVo, "paymentForm", result);
		List<Long> idList = new ArrayList<Long>();
		for(Integer id : chequeIdList){
			idList.add(Long.valueOf(id));
		}
		List<ChequeVO> chequeVoList = chequeService.findByIdList(idList);
		if(chequeVoList == null || chequeVoList.isEmpty()) {
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Invalid bounce cheque selected!");
			return "redirect:/cheque/listCheque";
		}
		
		if (!result.hasErrors()) {
			if(!validateInputAmount(totalamount, paymentVo)){
				result.rejectValue("cashamount", "error.notequal.paymentform.chequetotalamount");
				result.rejectValue("chequeamount", "error.notequal.paymentform.chequetotalamount");
				if(paymentVo.getType().equals("invoice"))
					result.rejectValue("giroamount", "error.notequal.paymentform.chequetotalamount");
				else if(paymentVo.getType().equals("expense"))
					result.rejectValue("directoramount", "error.notequal.paymentform.chequetotalamount");
			}
			
			try{
				if(paymentVo.getBouncedateString() != null && !paymentVo.getBouncedateString().isEmpty() 
						&& paymentVo.getBouncedateString().length() <= 10){
					Date bounceChequeDate = new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).parse(paymentVo.getBouncedateString());
					paymentVo.setBounceDate(bounceChequeDate);
					if(!validateInputDate(lastdate, GeneralUtils.STANDARD_DATE_FORMAT, paymentVo.getBouncedateString())){
						result.rejectValue("bouncedateString", "error.paymentform.bouncedate.before.chequedate");
					}
				}else{
					result.rejectValue("bouncedateString", "error.notvalid.paymentform.bouncedate");
				}
				
			}catch(Exception ex){
				result.rejectValue("bouncedateString", "error.notvalid.paymentform.bouncedate");
			}
			
			if(paymentVo.getBounceDate() != null && !validateInputDate(paymentVo.getBouncedateString(), GeneralUtils.STANDARD_DATE_FORMAT, paymentVo.getPaymentdateString())){
				result.rejectValue("paymentdateString", "error.paymentform.paymentdate.before.chequebounceddate");
			}
			
			if(paymentVo.getBounceDate() != null && 
					paymentVo.getPaymentmodecheque() && !validateInputDate(paymentVo.getBouncedateString(), GeneralUtils.STANDARD_DATE_FORMAT, paymentVo.getChequedateString())){
				result.rejectValue("chequedateString", "error.paymentform.chequedate.before.chequebounceddate");
			}
			
			if(!result.hasErrors()){
				paymentVo.setReferenceType(paymentVo.getType());
				try{ 
					paymentVo.setPaymentDate(new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).parse(paymentVo.getPaymentdateString()));
					if(paymentVo.getPaymentmodecheque())
						paymentVo.setChequedate(new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).parse(paymentVo.getChequedateString()));
				}catch(Exception e) {
					logger.info("Error parsing date string");
				}
				List<PaymentDetailTO> paymentDetailTOList = chequeService.getPaymentDetailListByChequeId(Long.valueOf(chequeIdList.get(0)));
				chequeService.saveBounceCheque(paymentVo, idList, new ArrayList<PaymentRsTO>(paymentDetailTOList.get(0).getPaymentRsTOSet()));
				redirectAttributes.addFlashAttribute("css", "success");
				redirectAttributes.addFlashAttribute("msg", "Cheque bounced successfully!");
		        return "redirect:/cheque/listCheque";  
			}
		}
		ChequeVO vo = chequeVoList.get(0);
		model.addAttribute("paymentForm", paymentVo);
		model.addAttribute("bounced", "true");
		model.addAttribute("cheque", vo);
		model.addAttribute("idList", chequeIdList);
		model.addAttribute("totalamount", totalamount);
		model.addAttribute("lastdate", vo.getChequeDateString());
		model.addAttribute("posturl", "/admin/payment/createBounceChequePayment");
		return "createPayBounceCheque";
    }
	
	/* Bounce Cheque Payment End */
	
	
	private boolean validateInputDate(String lastdateString, String lastdateformat, String dateString) {
		try {
			Date lastdate = new SimpleDateFormat(lastdateformat).parse(lastdateString);
			Date date = new SimpleDateFormat(GeneralUtils.STANDARD_DATE_FORMAT).parse(dateString);
			
			if(date.compareTo(lastdate) >= 0) {
				return true;
			}
		} catch (ParseException e) {
			logger.info("Error parsing date.");
		}
		return false;
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
	
	class SalaryBonusComparator implements Comparator<SalaryBonusVO> {
		@Override
		public int compare(SalaryBonusVO o1, SalaryBonusVO o2) {
			if(o1.getDate() == null && o2.getDate() == null){
				return 0;
			}else if(o1.getDate() == null && o2.getDate() != null){
				return -1;
			}else if(o1.getDate() != null && o2.getDate() == null){
				return 1;
			}
			return o1.getDate().compareTo(o2.getDate()) * -1;
		}
	}
	
}
