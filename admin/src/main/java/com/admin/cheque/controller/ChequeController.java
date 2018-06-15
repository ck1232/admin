package com.admin.cheque.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

import com.admin.cheque.vo.ChequeVO;
import com.admin.cheque.vo.service.ChequeService;
import com.admin.expense.service.ExpenseService;
import com.admin.expense.vo.ExpenseVO;
import com.admin.helper.GeneralUtils;
import com.admin.invoice.service.InvoiceService;
import com.admin.invoice.vo.InvoiceVO;
import com.admin.salarybonus.service.SalaryBonusService;
import com.admin.salarybonus.vo.SalaryBonusVO;
import com.admin.to.BonusPaymentRsTO;
import com.admin.to.BonusTO;
import com.admin.to.ExpensePaymentRsTO;
import com.admin.to.ExpenseTO;
import com.admin.to.InvoicePaymentRsTO;
import com.admin.to.InvoiceTO;
import com.admin.to.PaymentDetailTO;
import com.admin.to.PaymentRsTO;
import com.admin.to.SalaryPaymentRsTO;
import com.admin.to.SalaryTO;
import com.admin.validator.ChequeFormValidator;


@Controller  
@EnableWebMvc
@Scope("request")
@RequestMapping(value = "/cheque")
public class ChequeController {
	private static final Logger logger = Logger.getLogger(ChequeController.class);
	
	private ChequeService chequeService;
	private ExpenseService expenseService;
	private SalaryBonusService salaryBonusService;
	private InvoiceService invoiceService;
	private ChequeFormValidator chequeFormValidator;
	
	@Autowired
	public ChequeController(ChequeService chequeService,
			ExpenseService expenseService,
			SalaryBonusService salaryBonusService,
			InvoiceService invoiceService,
			ChequeFormValidator chequeFormValidator) {
		this.chequeService = chequeService;
		this.expenseService = expenseService;
		this.salaryBonusService = salaryBonusService;
		this.invoiceService = invoiceService;
		this.chequeFormValidator = chequeFormValidator;
	}
	
	@RequestMapping(value = "/listCheque", method = RequestMethod.GET)  
    public String listCheque(Model model) {  
    	logger.debug("loading listCheque");
        return "listCheque";  
    }

	
	@RequestMapping(value = "/getChequeList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getChequeList() {
		logger.debug("getting cheque list");
		List<ChequeVO> chequeList = chequeService.getChequeList();
		return GeneralUtils.convertListToJSONString(chequeList);
	}
	
	@RequestMapping(value = "/viewCheque", method = RequestMethod.POST)
	public String viewEmployee(@RequestParam("viewBtn") String id, Model model) {
		logger.debug("id = " + id);
		ChequeVO chequeVo = chequeService.findById(Long.valueOf(id));
		if (chequeVo == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "Cheque not found");
		}else{
			chequeVo.setChequeDateString(GeneralUtils.convertDateToString(chequeVo.getChequeDate(), "dd MMM yyyy"));
			model.addAttribute("cheque", chequeVo);
			List<PaymentDetailTO> paymentDetailTOList = chequeService.getPaymentDetailListByChequeId(Long.valueOf(id));
			if(paymentDetailTOList != null && !paymentDetailTOList.isEmpty()) {
				List<ExpenseVO> expenseList = new ArrayList<ExpenseVO>();
				List<SalaryBonusVO> salaryList = new ArrayList<SalaryBonusVO>();
				List<SalaryBonusVO> bonusList = new ArrayList<SalaryBonusVO>();
				List<InvoiceVO> invoiceList = new ArrayList<InvoiceVO>();
				for(PaymentDetailTO payment : paymentDetailTOList) {
					PaymentRsTO paymentRs = payment.getPaymentRsTOSet().iterator().next();
					if(paymentRs instanceof ExpensePaymentRsTO) {
						ExpenseTO expenseTO = ((ExpensePaymentRsTO) paymentRs).getExpenseTO();
						expenseList.addAll(expenseService.convertToExpenseVOList(Arrays.asList(expenseTO)));
						
					} else if(paymentRs instanceof InvoicePaymentRsTO) {
						InvoiceTO invoiceTO = ((InvoicePaymentRsTO) paymentRs).getInvoiceTO();
						invoiceList.addAll(invoiceService.convertToInvoiceVOList(Arrays.asList(invoiceTO)));
						
					} else if(paymentRs instanceof SalaryPaymentRsTO) {
						SalaryTO salaryTO = ((SalaryPaymentRsTO) paymentRs).getSalaryTO();
						salaryList.addAll(salaryBonusService.convertSalaryToSalaryBonusVOList(Arrays.asList(salaryTO)));
						
					} else if(paymentRs instanceof BonusPaymentRsTO) {
						BonusTO bonusTO = ((BonusPaymentRsTO) paymentRs).getBonusTO();
						bonusList.addAll(salaryBonusService.convertBonusToSalaryBonusVOList(Arrays.asList(bonusTO)));
					}
				}
				if(!expenseList.isEmpty()) {
					model.addAttribute("expenseList", expenseList);
				}
				if(!salaryList.isEmpty()) {
					model.addAttribute("salaryList", salaryList);
				}
				if(!bonusList.isEmpty()) {
					model.addAttribute("bonusList", bonusList);
				}
				if(!invoiceList.isEmpty()) {
					model.addAttribute("invoiceList", invoiceList);
				}
			}
		}
		return "viewCheque";

	}
	
	@RequestMapping(value = "/updateCheque", method = RequestMethod.POST)
	public String getChequeToUpdate(@RequestParam("editBtn") String id, Model model) {
		
		ChequeVO chequeVo = chequeService.findById(Long.valueOf(id));
		logger.debug("Loading update cheque page for " + chequeVo.toString());
		String firstDate = GeneralUtils.convertDateToString(chequeService.getEarliestChequeDate(chequeVo), "dd/MM/yyyy");
		model.addAttribute("chequeForm", chequeVo);
		model.addAttribute("firstDate", firstDate);
		return "updateCheque";
	}
	
	@InitBinder("chequeForm")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(chequeFormValidator);
	}
	
	@RequestMapping(value = "/updateChequeToDb", method = RequestMethod.POST)
	public String updateCheque(@RequestParam("firstDate") String firstDate,
			@ModelAttribute("chequeForm") @Validated ChequeVO chequeVo,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
//		logger.debug("updateCheque() : " + chequeVo.toString());
		if(!chequeVo.getChequeDateString().isEmpty() && GeneralUtils.convertStringToDate(chequeVo.getChequeDateString(), "dd/MM/yyyy").before(GeneralUtils.convertStringToDate(firstDate, "dd/MM/yyyy"))) {
			result.rejectValue("chequeDateString", "error.notvalid.paymentform.chequedate");
		}
		if (!result.hasErrors()) {
			chequeVo.setChequeDate(GeneralUtils.convertStringToDate(chequeVo.getChequeDateString(), "dd/MM/yyyy"));
			chequeService.updateCheque(chequeVo);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Cheque updated successfully!");
			return "redirect:listCheque";
		}
		model.addAttribute("firstDate", firstDate);
		ChequeVO dbchequeVo = chequeService.findById(Long.valueOf(chequeVo.getChequeId()));
		chequeVo.setChequeAmt(dbchequeVo.getChequeAmt());
		chequeVo.setDebitDate(dbchequeVo.getDebitDate());
		chequeVo.setDebitDateString(GeneralUtils.convertDateToString(dbchequeVo.getDebitDate(), "dd/MM/yyyy"));
		chequeVo.setRemarks(dbchequeVo.getRemarks());
		return "updateCheque";
	}
	
	
	
}