package com.admin.salarybonus.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
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

import com.admin.employee.service.EmployeeService;
import com.admin.employee.vo.EmployeeVO;
import com.admin.helper.GeneralUtils;
import com.admin.payment.controller.PaymentController;
import com.admin.payment.service.PaymentService;
import com.admin.payment.vo.PaymentDetailVO;
import com.admin.payment.vo.PaymentRsVO;
import com.admin.salarybonus.service.SalaryBonusService;
import com.admin.salarybonus.vo.SalaryBonusVO;
import com.admin.salarybonus.vo.TypeEnum;
import com.admin.validator.SalaryBonusFormValidator;


@Controller  
@EnableWebMvc
@Scope("request")
@RequestMapping(value = "/salarybonus")
public class SalaryBonusController {
	private static final Logger logger = Logger.getLogger(SalaryBonusController.class);
	
	private PaymentController paymentController;
	private SalaryBonusService salaryBonusService;
	private PaymentService paymentService;
	private EmployeeService employeeService;
	private SalaryBonusFormValidator salaryBonusFormValidator;
	
	Map<Long,String> employeeList;
	Map<String,String> typeList;
	
	@Autowired
	public SalaryBonusController(PaymentController paymentController,
			SalaryBonusService salaryBonusService,
			PaymentService paymentService,
			EmployeeService employeeService,
			SalaryBonusFormValidator salaryBonusFormValidator) {
		this.paymentController = paymentController;
		this.salaryBonusService = salaryBonusService;
		this.paymentService = paymentService;
		this.employeeService = employeeService;
		this.salaryBonusFormValidator = salaryBonusFormValidator;
	}
	
	@RequestMapping("/listSalaryBonus")  
    public String listSalaryBonus() {  
    	logger.debug("loading listSalaryBonus");
        return "listSalaryBonus";  
    }  
	
	@RequestMapping(value = "/getSalaryBonusList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getSalaryBonusList() {
		logger.debug("getting salary and bonus list");
		List<SalaryBonusVO> salaryBonusVoList = salaryBonusService.getAllSalaryBonusList();
		return GeneralUtils.convertListToJSONString(salaryBonusVoList);
	}
	
	@RequestMapping(value = "/deleteSalaryBonus", method = RequestMethod.POST)
	public String deleteSalaryBonus(@RequestParam(value = "checkboxId", required=false) List<String> ids,
			final RedirectAttributes redirectAttributes) {
		if(ids == null || ids.size() < 1){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select at least one record!");
			return "redirect:listSalaryBonus";
		}
		salaryBonusService.deleteSalaryBonus(ids);
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Salary/Bonus(s) deleted successfully!");
		return "redirect:listSalaryBonus";
	}
	
	@RequestMapping(value = "/createSalaryBonus", method = RequestMethod.GET)
    public String showAddSalaryBonusForm(Model model) {  
    	logger.debug("loading showAddSalaryBonusForm");
    	SalaryBonusVO salaryBonusVo = new SalaryBonusVO();
    	initData();
    	model.addAttribute("salaryBonusForm", salaryBonusVo);
    	model.addAttribute("employeeList", employeeList);
    	model.addAttribute("typeList", typeList);
        return "createSalaryBonus";  
    }  
	
	//initialise for employment type dropdown
	private void initData(){
		employeeList = new LinkedHashMap<Long,String>();
		List<EmployeeVO> voList = employeeService.getAllEmployeeInAscendingName();
		if(voList != null && voList.size() > 0){
			for(EmployeeVO vo : voList) {
				employeeList.put(vo.getEmployeeId(), vo.getName());
			}
		}
		typeList = new LinkedHashMap<String,String>(); 
		typeList.put(TypeEnum.SALARY.toString(),TypeEnum.SALARY.getType());
		typeList.put(TypeEnum.BONUS.toString(),TypeEnum.BONUS.getType());
	}
	
	@InitBinder("salaryBonusForm")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(salaryBonusFormValidator);
	}
	
	@RequestMapping(value = "/createSalaryBonus", method = RequestMethod.POST)
    public String saveSalaryBonus(@ModelAttribute("salaryBonusForm") @Validated SalaryBonusVO salaryBonusVO, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		
		logger.debug("saveSalaryBonus() : " + salaryBonusVO.toString());
		GeneralUtils.validate(salaryBonusVO, "salaryBonusForm" ,result);
		if (result.hasErrors()) {
			initData();
			model.addAttribute("employeeList", employeeList);
	    	model.addAttribute("typeList", typeList);
			return "createSalaryBonus";
		} else {
			salaryBonusVO.setDate(GeneralUtils.convertStringToDate(salaryBonusVO.getDateString(), "dd/MM/yyyy"));
			salaryBonusService.saveSalaryBonus(salaryBonusVO);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Salary/Bonus added successfully!");
		}
        return "redirect:listSalaryBonus";  
    }  
	
	@RequestMapping(value = "/createSalaryBonusAndPay", method = RequestMethod.POST)
    public String saveSalaryBonusAndPay(@ModelAttribute("salaryBonusForm") @Validated SalaryBonusVO salaryBonusVO, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		List<String> idList = new ArrayList<String>();
		Long salaryBonusId = null;
		logger.debug("saveSalaryBonus() : " + salaryBonusVO.toString());
		GeneralUtils.validate(salaryBonusVO, "salaryBonusForm" ,result);
		if (result.hasErrors()) {
			initData();
			model.addAttribute("employeeList", employeeList);
	    	model.addAttribute("typeList", typeList);
			return "createSalaryBonus";
		} else {
			salaryBonusVO.setDate(GeneralUtils.convertStringToDate(salaryBonusVO.getDateString(), "dd/MM/yyyy"));
			salaryBonusId = salaryBonusService.saveSalaryBonus(salaryBonusVO);
			
		}
		if(salaryBonusId != null) {
			idList.add(salaryBonusId.toString());
			if(salaryBonusVO.getType().equals(TypeEnum.SALARY.toString())) {
				return paymentController.createPaySalary(idList, redirectAttributes, model);
			}else if(salaryBonusVO.getType().equals(TypeEnum.BONUS.toString())) {
				return paymentController.createPayBonus(idList, redirectAttributes, model);
			}
		}
		return "redirect:listSalaryBonus";
    }  
	
	@RequestMapping(value = "/updateSalaryBonus", method = RequestMethod.POST)
	public String getSalaryBonusToUpdate(@RequestParam("editBtn") String id, Model model) {
		SalaryBonusVO salaryBonusVO = null;
		String[] splitId = id.split("-");
		if(splitId[0] != null && splitId[1] != null){
			if(splitId[1].toLowerCase().equals("salary")) {
				salaryBonusVO = salaryBonusService.findSalaryById(Long.valueOf(splitId[0]));
			}else if(splitId[1].toLowerCase().equals("bonus")) {
				salaryBonusVO = salaryBonusService.findBonusById(Long.valueOf(splitId[0]));
			}
		}
		salaryBonusVO.setDateString(GeneralUtils.convertDateToString(salaryBonusVO.getDate(), "dd/MM/yyyy"));

		logger.debug("Loading update salary bonus page for " + salaryBonusVO.toString());
		initData();
		model.addAttribute("salaryBonusForm", salaryBonusVO);
    	model.addAttribute("employeeList", employeeList);
    	model.addAttribute("typeList", typeList);
		return "updateSalaryBonus";
	}
	
	@RequestMapping(value = "/updateSalaryBonusToDb", method = RequestMethod.POST)
	public String updateSalaryBonus(@ModelAttribute("salaryBonusForm") @Validated SalaryBonusVO salaryBonusVO,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		
		logger.debug("updateSalaryBonus() : " + salaryBonusVO.toString());
		GeneralUtils.validate(salaryBonusVO, "salaryBonusForm" ,result);
		if (result.hasErrors()) {
			initData();
	    	model.addAttribute("employeeList", employeeList);
	    	model.addAttribute("typeList", typeList);
			return "updateSalaryBonus";
		} else {
			salaryBonusService.updateSalaryBonus(salaryBonusVO);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Salary/Bonus updated successfully!");
		}
		
		return "redirect:listSalaryBonus";
	}
	
	@RequestMapping(value = "/viewSalaryBonus", method = RequestMethod.POST)
	public String viewSalaryBonus(@RequestParam("viewBtn") String id, Model model, final RedirectAttributes redirectAttributes) {
		logger.debug("id = " + id);
		SalaryBonusVO salaryBonusVO = null;
		String[] splitId = id.split("-");
		if(splitId.length != 2 || !GeneralUtils.isInteger(splitId[0])){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Salary / Bonus not found!");
			return "redirect:listSalaryBonus";
		}else{
			if(splitId[1].toLowerCase().equals("salary")) {
				salaryBonusVO = salaryBonusService.findSalaryById(Long.valueOf(splitId[0]));
			}else if(splitId[1].toLowerCase().equals("bonus")) {
				salaryBonusVO = salaryBonusService.findBonusById(Long.valueOf(splitId[0]));
			}
		}
		if (salaryBonusVO == null) {
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Salary / Bonus not found!");
			return "redirect:listSalaryBonus";
		}
		model.addAttribute("salarybonus", salaryBonusVO);
		List<PaymentDetailVO> paymentList = salaryBonusVO.getPaymentDetailVOList();
		model.addAttribute("paymentList", paymentList);

		List<Long> rsIdList = new ArrayList<Long>();
		for(PaymentDetailVO paymentDetailVO : paymentList) {
			rsIdList.addAll(paymentDetailVO.getPaymentRsIdList());
		}
		List<PaymentRsVO> paymentRsList = paymentService.findByPaymentRsIdList(rsIdList, GeneralUtils.MODULE_SALARY);
		List<Long> salaryIdList = GeneralUtils.convertListToLongList(paymentRsList, "referenceId", true);
		if(splitId[1].toLowerCase().equals("salary")) 
			salaryIdList.remove(Long.parseLong(splitId[0]));
		List<SalaryBonusVO> otherList = salaryBonusService.findSalaryByIdList(salaryIdList);
		
		paymentRsList = paymentService.findByPaymentRsIdList(rsIdList, GeneralUtils.MODULE_BONUS);
		List<Long> bonusIdList = GeneralUtils.convertListToLongList(paymentRsList, "referenceId", true);
		if(splitId[1].toLowerCase().equals("bonus")) 
			bonusIdList.remove(Long.parseLong(splitId[0]));
		otherList.addAll(salaryBonusService.findBonusByIdList(bonusIdList));
		if(!otherList.isEmpty())
			Collections.sort(otherList, new SalaryBonusComparator());
		model.addAttribute("otherList", otherList);
		
		return "viewSalaryBonus";
	}
	
	@RequestMapping(value = "/paySalaryBonus", method = RequestMethod.POST)
    public String paySalaryBonus(@RequestParam("payBtn") String id, Model model,
    		final RedirectAttributes redirectAttributes) {
		String[] idArray = id.split(",");
		List<String> idList = new ArrayList<String>();
		idList.add(idArray[0]);
		if(idArray[1].equals(TypeEnum.SALARY.getType())) {
			return paymentController.createPaySalary(idList, redirectAttributes, model);
		}else if(idArray[1].equals(TypeEnum.BONUS.getType())) {
			return paymentController.createPayBonus(idList, redirectAttributes, model);
		}
		return "redirect:listSalaryBonus";
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