package com.admin.submodule.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.admin.common.vo.ModuleVO;
import com.admin.common.vo.SubModuleVO;
import com.admin.helper.GeneralUtils;
import com.admin.module.service.ModuleService;
import com.admin.submodule.service.SubModuleService;
import com.admin.validator.SubmoduleFormValidator;

@Controller  
@EnableWebMvc
@Scope("request")
@RequestMapping(value = "/admin")
public class SubmoduleManagementController {
	private static final Logger logger = Logger.getLogger(SubmoduleManagementController.class);
	
	private ModuleService moduleService;
	private SubModuleService submoduleService;
	private SubmoduleFormValidator submoduleFormValidator;
	
	@Autowired
	public SubmoduleManagementController(ModuleService moduleService, SubModuleService submoduleService,
			SubmoduleFormValidator submoduleFormValidator){
		this.moduleService = moduleService;
		this.submoduleService = submoduleService;
		this.submoduleFormValidator = submoduleFormValidator;
	}
	
	@RequestMapping(value = "/getSubmoduleListByModule", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getSubmoduleListByModule(@RequestParam("moduleid") String id, Model model) {
		logger.debug("getting submodules list by module");
		List<SubModuleVO> submoduleList = moduleService.findById(Long.parseLong(id)).getSubModuleList();
		return GeneralUtils.convertListToJSONString(submoduleList);
	}
	
	@RequestMapping(value = "/getSubmoduleListOrderByModule", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getSubmoduleListOrderByParentId() {
		logger.debug("getting all submodules list order by module");
		List<ModuleVO> moduleList = moduleService.getAllModules();
		List<SubModuleVO> submoduleList = new ArrayList<SubModuleVO>();
		if(moduleList != null && !moduleList.isEmpty()){
			for(ModuleVO moduleVO : moduleList){
				if(moduleVO.getSubModuleList() != null && !moduleVO.getSubModuleList().isEmpty())
					submoduleList.addAll(moduleVO.getSubModuleList());
			}
		}
		return GeneralUtils.convertListToJSONString(submoduleList);
	}
	
	@RequestMapping(value = "/listSubmodule", method = RequestMethod.POST)
	public String listSubmodule(@RequestParam("editBtn") String id, Model model) {
		logger.debug("id = " + id);
		ModuleVO module = moduleService.findById(Long.parseLong(id));
		if (module == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "Module not found");
		}
		model.addAttribute("module", module);
		return "listSubmodule";
	}
	
	@RequestMapping(value = "/listSubmodule/{id}", method = RequestMethod.GET)
	public String listSubmoduleForRedirect(@PathVariable String id, Model model) {
		logger.debug("id = " + id);
		ModuleVO module = moduleService.findById(Long.parseLong(id));
		if (module == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "Module not found");
		}
		model.addAttribute("module", module);
		return "listSubmodule";
	}
	
	@RequestMapping(value = "/createSubmodule", method = RequestMethod.POST)
    public String showAddSubmoduleForm(@RequestParam("moduleid") String id, Model model, final RedirectAttributes redirectAttributes) {  
    	logger.debug("loading showAddSubmoduleForm");
    	
    	ModuleVO moduleVO = moduleService.findById(new Long(id));
    	if(moduleVO != null && moduleVO.getSubModuleList() != null && moduleVO.getSubModuleList().size() >= 10){
    		redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Only 10 submodules are allowed in each module!");
			return "redirect:listSubmodule/"+id;
    	}
    	SubModuleVO submodule = new SubModuleVO();
    	submodule.setParentId(Long.parseLong(id));
    	model.addAttribute("submodule", submodule);
        return "createSubmodule";  
    }  
	
	@InitBinder("submodule")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(submoduleFormValidator);
	}

	@RequestMapping(value = "/createSubmoduleToDb", method = RequestMethod.POST)
    public String saveSubmodule(@ModelAttribute("submodule") @Validated SubModuleVO submodule, 
    		BindingResult result, final RedirectAttributes redirectAttributes) {  
    	
		logger.debug("saveSubmodule() : " + submodule.toString());
		if (result.hasErrors()) {
			return "createSubmodule";
		} else {
			boolean pass = true;
			ModuleVO moduleVO = moduleService.findById(submodule.getParentId());
			List<SubModuleVO> submoduleList = moduleVO.getSubModuleList();
			if(submoduleList != null && !submoduleList.isEmpty()){
				for(SubModuleVO sm: submoduleList){
					if(submodule.getName().equals(sm.getName())) { //if exist name
						result.rejectValue("name", "error.exist.submoduleform.name");;
						pass = false;
						break;
					}
				}
				
				for(SubModuleVO sm: moduleVO.getSubModuleList()){
					if(submodule.getUrl().equals(sm.getUrl())) { //if exist url
						result.rejectValue("url", "error.exist.submoduleform.url");;
						pass = false;
						break;
					}
				}
			}
			if(!pass){
				return "createSubmodule";
			}
			moduleVO.getSubModuleList().add(submodule);
			moduleService.saveModule(moduleVO);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Submodule added successfully!");
			
		}
		return "redirect:listSubmodule/"+submodule.getParentId();
    }  
	
	@RequestMapping(value = "/deleteSubmodule", method = RequestMethod.POST)
	public String deleteSubmodule(@RequestParam(value = "checkboxId", required=false) List<String> ids,
			@RequestParam("moduleid") String moduleid, 
			Model model, final RedirectAttributes redirectAttributes) {
		if(ids == null || ids.size() < 1){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select at least one record!");
			return "redirect:listSubmodule/"+moduleid;
		}
		
		for (String id : ids) {
			submoduleService.deleteSubmodule(new Long(id));
			logger.debug("deleted "+ id);
		}
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Submodule(s) deleted successfully!");
		return "redirect:listSubmodule/"+moduleid;
	}
	
	@RequestMapping(value = "/updateSubmodule", method = RequestMethod.POST)
	public String getSubmoduleToUpdate(@RequestParam("editBtn") String id, Model model) {
		
		SubModuleVO submodule = submoduleService.findById(Long.parseLong(id));
		logger.debug("Loading update submodule page for " + submodule.toString());
		
		model.addAttribute("submodule", submodule);
		
		return "updateSubmodule";
	}
	
	@RequestMapping(value = "/updateSubmoduleToDb", method = RequestMethod.POST)
	public String updateSubmodule(@ModelAttribute("submodule") @Validated SubModuleVO submodule,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		
		logger.debug("updateSubmodule() : " + submodule.toString());
		
		if (result.hasErrors()) {
			return "updateSubmodule";
		} 
		
		boolean pass = true;
		ModuleVO moduleVO = moduleService.findById(submodule.getParentId());
		List<SubModuleVO> submoduleList = moduleVO.getSubModuleList();
		if(submoduleList != null && !submoduleList.isEmpty()) {
			for(SubModuleVO sm: submoduleList){
				if(!submodule.getSubmoduleId().equals(sm.getSubmoduleId()) && submodule.getName().equals(sm.getName())) { //if exist name
					result.rejectValue("name", "error.exist.submoduleform.name");;
					pass = false;
					break;
				}
			}
			for(SubModuleVO sm: submoduleList){
				if(!submodule.getSubmoduleId().equals(sm.getSubmoduleId()) && submodule.getUrl().equals(sm.getUrl())) { //if exist url
					result.rejectValue("url", "error.exist.submoduleform.url");;
					pass = false;
					break;
				}
			}
		}
		if(!pass){
			return "updateSubmodule";
		}
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Submodule updated successfully!");
		
		
		moduleVO.getSubModuleList().add(submodule);
		moduleService.saveModule(moduleVO);
		return "redirect:listSubmodule/"+submodule.getParentId();
	}
	
	@RequestMapping(value = "/viewSubmodule", method = RequestMethod.POST)
	public String viewSubmodule(@RequestParam("viewBtn") String id, Model model) {
		logger.debug("id = " + id);
		SubModuleVO submodule = submoduleService.findById(Long.parseLong(id));
		if (submodule == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "Submodule not found");
		}
		
		model.addAttribute("submodule", submodule);

		return "viewSubmodule";

	}
}
