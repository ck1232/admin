package com.admin.employee.controller;

import java.util.ArrayList;
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
import com.admin.employee.vo.EmploymentTypeEnum;
import com.admin.helper.GeneralUtils;
import com.admin.salarybonus.service.SalaryBonusService;
import com.admin.salarybonus.vo.SalaryBonusVO;
import com.admin.validator.EmployeeFormValidator;


@Controller  
@EnableWebMvc
@Scope("request")
@RequestMapping(value = "/employee")
public class EmployeeController {
	private static final Logger logger = Logger.getLogger(EmployeeController.class);
	
	private EmployeeService employeeService;
	private SalaryBonusService salaryBonusService;
	private EmployeeFormValidator employeeFormValidator;
	
	Map<String,String> employmentTypeList;
	
	@Autowired
	public EmployeeController(EmployeeService employeeService,
			SalaryBonusService salaryBonusService,
			EmployeeFormValidator employeeFormValidator) {
		this.employeeService = employeeService;
		this.salaryBonusService = salaryBonusService;
		this.employeeFormValidator = employeeFormValidator;
	}
	
	@RequestMapping(value = "/listEmployee", method = RequestMethod.GET)  
    public String listEmployee(Model model) {  
    	logger.debug("loading listEmployee");
        return "listEmployee";  
    }

	
	@RequestMapping(value = "/getEmployeeList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getEmployeeList() {
		logger.debug("getting employee list");
		List<EmployeeVO> employeeList = employeeService.getAllEmployee();
		return GeneralUtils.convertListToJSONString(employeeList);
	}
	
	@RequestMapping(value = "/viewEmployee", method = RequestMethod.POST)
	public String viewEmployee(@RequestParam("viewBtn") String id, Model model) {
		logger.debug("id = " + id);
		EmployeeVO employeeVo = employeeService.findById(Long.valueOf(id));
		if (employeeVo == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "Employee not found");
		}else{
			model.addAttribute("employee", employeeVo);
			List<SalaryBonusVO> salaryList = salaryBonusService.getAllSalaryByEmpId(employeeVo.getEmployeeId());
			model.addAttribute("salaryList", salaryList);
			List<SalaryBonusVO> bonusList = salaryBonusService.getAllBonusByEmpId(employeeVo.getEmployeeId());
			model.addAttribute("bonusList", bonusList);
		}
		return "viewEmployee";
	}
	
	@InitBinder("employeeForm")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(employeeFormValidator);
	}
	
	@RequestMapping(value = "/updateEmployee", method = RequestMethod.POST)
	public String getEmployeeToUpdate(@RequestParam("editBtn") String id, Model model) {
		
		EmployeeVO employeeVo = employeeService.findById(Long.valueOf(id));
		logger.debug("Loading update employee page for " + employeeVo.toString());
		initData();
		model.addAttribute("employmentTypeList", employmentTypeList);
		model.addAttribute("employeeForm", employeeVo);
		return "updateEmployee";
	}
	
	@RequestMapping(value = "/updateEmployeeToDb", method = RequestMethod.POST)
	public String updateEmployee(@ModelAttribute("employeeForm") @Validated EmployeeVO employeeVo,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		
		logger.debug("updateEmployee() : " + employeeVo.toString());
		GeneralUtils.validate(employeeVo, "employeeForm" ,result);
		if (result.hasErrors()) {
			initData();
			model.addAttribute("employmentTypeList", employmentTypeList);
			return "updateEmployee";
		} else {
			employeeService.updateEmployee(employeeVo);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Expense updated successfully!");
		}
		
		return "redirect:listEmployee";
	}
	
	@RequestMapping(value = "/createEmployee", method = RequestMethod.GET)
    public String showAddEmployeeForm(Model model) {  
    	logger.debug("loading showAddEmployeeForm");
    	EmployeeVO employeeVo = new EmployeeVO();
    	initData();
    	model.addAttribute("employeeForm", employeeVo);
    	model.addAttribute("employmentTypeList", employmentTypeList);
        return "createEmployee";  
    }  
	
	@RequestMapping(value = "/createEmployee", method = RequestMethod.POST)
    public String saveEmployee(@ModelAttribute("employeeForm") @Validated EmployeeVO employeeVo, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		
		logger.debug("saveEmployee() : " + employeeVo.toString());
		GeneralUtils.validate(employeeVo, "employeeForm" ,result);
		if (result.hasErrors()) {
			initData();
	    	model.addAttribute("employmentTypeList", employmentTypeList);
			return "createEmployee";
		} else {
			employeeService.saveEmployee(employeeVo);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Employee added successfully!");
		}
        return "redirect:listEmployee";  
    }  
	
	@RequestMapping(value = "/deleteEmployee", method = RequestMethod.POST)
	public String deleteEmployee(@RequestParam(value = "checkboxId", required=false) List<String> ids,
			final RedirectAttributes redirectAttributes) {
		if(ids == null || ids.isEmpty()){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select at least one record!");
			return "redirect:listEmployee";
		}
		List<Long> idList = new ArrayList<Long>();
		for(String id : ids) {
			idList.add(Long.parseLong(id));
		}
		employeeService.deleteEmployee(idList);
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Employee(s) deleted successfully!");
		return "redirect:listEmployee";
	}
	
	//initialise for employment type dropdown
	private void initData(){
		employmentTypeList = new LinkedHashMap<String,String>();
    	employmentTypeList.put(EmploymentTypeEnum.FULL_LOCAL.toString(), EmploymentTypeEnum.FULL_LOCAL.getType());
    	employmentTypeList.put(EmploymentTypeEnum.FULL_FW.toString(), EmploymentTypeEnum.FULL_FW.getType());
    	employmentTypeList.put(EmploymentTypeEnum.PART_LOCAL.toString(), EmploymentTypeEnum.PART_LOCAL.getType());
    	employmentTypeList.put(EmploymentTypeEnum.PART_FW.toString(), EmploymentTypeEnum.PART_FW.getType());
    	employmentTypeList.put(EmploymentTypeEnum.DIRECTOR.toString(), EmploymentTypeEnum.DIRECTOR.getType());
	}
	
}