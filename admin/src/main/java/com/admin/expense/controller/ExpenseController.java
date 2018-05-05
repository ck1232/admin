package com.admin.expense.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.admin.expense.service.ExpenseService;
import com.admin.expense.vo.ExpenseStatusEnum;
import com.admin.expense.vo.ExpenseVO;
import com.admin.helper.GeneralUtils;
import com.admin.invoice.lookup.controller.ExpenseTypeLookup;
import com.admin.payment.controller.PaymentController;
import com.admin.payment.service.PaymentService;
import com.admin.payment.vo.PaymentDetailVO;
import com.admin.payment.vo.PaymentRsVO;
import com.admin.to.ExpenseTO;
import com.admin.validator.ExpenseFormValidator;


@Controller  
@EnableWebMvc
@Scope("request")
@RequestMapping(value = "/expense")
public class ExpenseController {
	private static final Logger logger = Logger.getLogger(ExpenseController.class);
	
	private PaymentController paymentController;
	private ExpenseService expenseService;
	private PaymentService paymentService;
	private ExpenseTypeLookup expenseTypeLookup;
	private ExpenseFormValidator expenseFormValidator;
	
	@Autowired
	public ExpenseController(PaymentController paymentController,
			ExpenseService expenseService,
			PaymentService paymentService,
			ExpenseTypeLookup expenseTypeLookup,
			ExpenseFormValidator expenseFormValidator) {
		this.paymentController = paymentController;
		this.expenseService = expenseService;
		this.paymentService = paymentService;
		this.expenseTypeLookup = expenseTypeLookup;
		this.expenseFormValidator = expenseFormValidator;
	}
	
	
	@RequestMapping(value = "/listExpense", method = RequestMethod.GET)  
    public String listExpense(Model model) {  
    	logger.debug("loading listExpense");
        return "listExpense";  
    }

	
	@RequestMapping(value = "/getExpenseList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getExpenseList() {
		logger.debug("getting expense list");
		List<ExpenseVO> expenseVoList = expenseService.getAllExpense();
		return GeneralUtils.convertListToJSONString(expenseVoList);
	}
	
	@RequestMapping(value = "/viewExpense", method = RequestMethod.POST)
	public String viewExpense(@RequestParam("viewBtn") String id, Model model, final RedirectAttributes redirectAttributes) {
		logger.debug("id = " + id);
		ExpenseVO expenseVO = expenseService.findById(Long.parseLong(id));
		if (expenseVO == null) {
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Expense not found!");
			return "redirect:listInvoice";
		}
		model.addAttribute("expense", expenseVO);
		
		List<PaymentDetailVO> paymentList = expenseVO.getPaymentDetailVOList();
		model.addAttribute("paymentList", paymentList);
		
		List<Long> rsIdList = new ArrayList<Long>();
		for(PaymentDetailVO paymentDetailVO : paymentList) {
			rsIdList.addAll(paymentDetailVO.getPaymentRsIdList());
		}
		
		List<PaymentRsVO> paymentRsList = paymentService.findByPaymentRsIdList(rsIdList, GeneralUtils.MODULE_EXPENSE);
		List<Long> expenseIdList = GeneralUtils.convertListToLongList(paymentRsList, "referenceId", true);
		expenseIdList.remove(Long.parseLong(id));
		List<ExpenseVO> otherList = expenseService.findByIdList(expenseIdList);
		model.addAttribute("otherList", otherList);
		return "viewExpense";
	}
	
	@InitBinder("expenseForm")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(expenseFormValidator);
	}
	
	@RequestMapping(value = "/updateExpense", method = RequestMethod.POST)
	public String getExpenseToUpdate(@RequestParam("editBtn") String id, Model model) {
		
		ExpenseVO expenseVO = expenseService.findById(Long.parseLong(id));
		logger.debug("Loading update expense page for " + expenseVO.toString());
		
		Map<String,String> expenseTypeList = expenseTypeLookup.getExpenseTypeMap();
    	model.addAttribute("expenseTypeList", expenseTypeList);
		model.addAttribute("expenseForm", expenseVO);
		return "updateExpense";
	}
	
	@RequestMapping(value = "/updateExpenseToDb", method = RequestMethod.POST)
	public String updateExpense(@ModelAttribute("expenseForm") @Validated ExpenseVO expenseVO,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		
		logger.debug("updateExpense() : " + expenseVO.toString());
		GeneralUtils.validate(expenseVO, "expenseForm" ,result);
		if (result.hasErrors()) {
			Map<String,String> expenseTypeList = expenseTypeLookup.getExpenseTypeMap();
	    	model.addAttribute("expenseTypeList", expenseTypeList);
			return "updateExpense";
		} else {
			expenseVO.setExpenseDate(GeneralUtils.convertStringToDate(expenseVO.getExpensedateString(), "dd/MM/yyyy"));
			expenseService.updateExpense(expenseVO);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Expense updated successfully!");
		}
		
		return "redirect:listExpense";
	}
	
	@RequestMapping(value = "/deleteExpense", method = RequestMethod.POST)
	public String deleteExpense(@RequestParam(value = "checkboxId", required=false) List<String> ids,
			final RedirectAttributes redirectAttributes) {
		if(ids == null || ids.isEmpty()){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select at least one record!");
			return "redirect:listExpense";
		}
		List<Long> idList = new ArrayList<Long>();
		for(String id : ids) {
			idList.add(Long.parseLong(id));
		}
		expenseService.deleteExpense(idList);
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Expense(s) deleted successfully!");
		return "redirect:listExpense";
	}
	
	@RequestMapping(value = "/createExpense", method = RequestMethod.GET)
    public String showAddExpenseForm(Model model) {  
    	logger.debug("loading showAddExpenseForm");
    	ExpenseVO expenseVO = new ExpenseVO();
    	Map<String,String> expenseTypeList = expenseTypeLookup.getExpenseTypeMap();
    	model.addAttribute("expenseForm", expenseVO);
    	model.addAttribute("expenseTypeList", expenseTypeList);
        return "createExpense";  
    }  
	
	@RequestMapping(value = "/getSupplierList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<ExpenseVO> getSupplierList() {
		logger.debug("getting supplier list");
		List<String> supplierList = expenseService.getAllSupplier();		
		List<ExpenseVO> expenseList = new ArrayList<ExpenseVO>();
		for(String supplier: supplierList) {
			ExpenseVO expense = new ExpenseVO();
			expense.setSupplier(supplier);
			expenseList.add(expense);
		}
		return expenseList;
	}
	
	@RequestMapping(value = "/createExpense", method = RequestMethod.POST)
    public String saveExpense(@ModelAttribute("expenseForm") @Validated ExpenseVO expenseVO, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		
		logger.debug("saveExpense() : " + expenseVO.toString());
		GeneralUtils.validate(expenseVO, "expenseForm" ,result);
		Date expenseDate = GeneralUtils.convertStringToDate(expenseVO.getExpensedateString(), "dd/MM/yyyy");
		if(expenseDate != null && expenseDate.after(new Date())) {
			result.rejectValue("expensedateString","error.expenseform.expensedate.cannot.after.sysdate");
		}
		if (!result.hasErrors()) {
			expenseVO.setExpenseDate(expenseDate);
			expenseVO.setStatus(ExpenseStatusEnum.UNPAID.toString());
			expenseService.saveExpense(expenseVO);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Expense added successfully!");
			return "redirect:listExpense";  
		}
		Map<String,String> expenseTypeList = expenseTypeLookup.getExpenseTypeMap();
    	model.addAttribute("expenseTypeList", expenseTypeList);
		return "createExpense";
    }  
	
	@RequestMapping(value = "/createExpenseAndPay", method = RequestMethod.POST)
    public String saveExpenseAndPay(@ModelAttribute("expenseForm") @Validated ExpenseVO expenseVO, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		List<String> idList = new ArrayList<String>();
		logger.debug("saveExpenseAndPay() : " + expenseVO.toString());
		if (result.hasErrors()) {
			Map<String,String> expenseTypeList = expenseTypeLookup.getExpenseTypeMap();
	    	model.addAttribute("expenseTypeList", expenseTypeList);
			return "createExpense";
		} else {
			try{
				expenseVO.setExpenseDate(new SimpleDateFormat("dd/MM/yyyy").parse(expenseVO.getExpensedateString()));
			}catch(Exception e) {
				logger.info("Error parsing expense date string");
			}
			expenseVO.setStatus(ExpenseStatusEnum.UNPAID.toString());
			ExpenseTO expenseTO = expenseService.saveExpense(expenseVO);	
			if(expenseTO.getExpenseId() != null) {
				idList.add(expenseTO.getExpenseId().toString());
			}
		}
		return paymentController.createPayExpense(idList, redirectAttributes, model);
    }  
	
	@RequestMapping(value = "/payExpense", method = RequestMethod.POST)
    public String payExpense(@RequestParam("payBtn") String id, Model model,
    		final RedirectAttributes redirectAttributes) {
		List<String> idList = new ArrayList<String>();
		idList.add(id);
		return paymentController.createPayExpense(idList, redirectAttributes, model);
    } 
	

}