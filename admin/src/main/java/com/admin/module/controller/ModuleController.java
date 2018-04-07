package com.admin.module.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

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

import com.admin.helper.GeneralUtils;
import com.admin.module.service.ModuleService;
import com.admin.module.vo.ModuleVO;
import com.admin.validator.ModuleFormValidator;

@Controller  
@EnableWebMvc
@Scope("request")
@RequestMapping(value = "/admin")
public class ModuleController {
	private static final Logger logger = Logger.getLogger(ModuleController.class);
	
	private ModuleService moduleService;
	private ModuleFormValidator moduleFormValidator;

	@Autowired
	public ModuleController(ModuleService moduleService,
			ModuleFormValidator moduleFormValidator){
		this.moduleService = moduleService;
		this.moduleFormValidator = moduleFormValidator;
	}
	
	@RequestMapping("/listModule")  
    public String listModule(HttpSession session) {  
    	logger.debug("loading listModule");
        return "listModule";  
    } 
	
	@RequestMapping(value = "/getModuleList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getModuleList() {
		logger.debug("getting Module list");
		List<ModuleVO> moduleList = moduleService.getAllModules();
		return GeneralUtils.convertListToJSONString(moduleList);
	}
	
	@RequestMapping(value = "/createModule", method = RequestMethod.GET)
    public String showAddModuleForm(Model model) {  
    	logger.debug("loading showAddModuleForm");
    	ModuleVO moduleVO = new ModuleVO();
    	moduleVO.setDeleteInd(GeneralUtils.NOT_DELETED);
    	
    	model.addAttribute("moduleForm", moduleVO);
        return "createModule";  
    }  
	
	@InitBinder("moduleForm")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(moduleFormValidator);
	}
	
	@RequestMapping(value = "/createModule", method = RequestMethod.POST)
    public String saveModule(@ModelAttribute("moduleForm") @Validated ModuleVO moduleVO, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {  
    	
		logger.debug("saveModule() : " + moduleVO.toString());
		if (result.hasErrors()) {
			return "createModule";
		} else {
			// Add message to flash scope
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Module added successfully!");
		}
		moduleService.saveModule(moduleVO);
		
        return "redirect:listModule";  
    }  
	
	@RequestMapping(value = "/deleteModule", method = RequestMethod.POST)
	public String deleteModule(@RequestParam(value = "checkboxId", required=false) List<String> ids,
			final RedirectAttributes redirectAttributes) {
		if(ids == null || ids.size() < 1){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select at least one record!");
			return "redirect:listModule";
		}
		for (String id : ids) {
			moduleService.deleteModule(Long.parseLong(id));
			logger.debug("deleted "+ id);
		}
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Module(s) deleted successfully!");
		return "redirect:listModule";
	}
	
	@RequestMapping(value = "/updateModule", method = RequestMethod.POST)
	public String getModuleToUpdate(@RequestParam("editBtn") String id, Model model) {
		logger.debug("id = " + id);
		ModuleVO moduleVO = moduleService.findById(Long.parseLong(id));
		if (moduleVO == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "Module not found");
		}
		model.addAttribute("moduleForm", moduleVO);
		return "updateModule";
	}
	
	@RequestMapping(value = "/updateModuleToDb", method = RequestMethod.POST)
	public String updateModule(@ModelAttribute("moduleForm") @Validated ModuleVO moduleVO,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		
		logger.debug("updateModule() : " + moduleVO.toString());
		
		if (result.hasErrors()) {
			return "updateModule";
		} else {
			moduleService.updateModule(moduleVO);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Module updated successfully!");
		}
		
		return "redirect:listModule";
	}
	
}
