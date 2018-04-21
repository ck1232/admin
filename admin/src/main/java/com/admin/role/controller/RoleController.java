package com.admin.role.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.admin.helper.GeneralUtils;
import com.admin.role.service.RoleAssignService;
import com.admin.role.service.RoleService;
import com.admin.role.vo.RoleVO;
import com.admin.role.vo.UserRoleVO;
import com.admin.validator.RoleFormValidator;


@Controller  
@EnableWebMvc
@Scope("request")
@RequestMapping(value = "/admin")
public class RoleController {
	private static final Logger logger = Logger.getLogger(RoleController.class);
	
	private RoleService roleService;
	private RoleAssignService roleAssignService;
	private RoleFormValidator roleFormValidator;
	
	@Autowired
	public RoleController(RoleService roleService, 
			RoleAssignService roleAssignService,
			RoleFormValidator roleFormValidator) {
		this.roleService = roleService;
		this.roleAssignService = roleAssignService;
		this.roleFormValidator = roleFormValidator;
	}
	
	
	@RequestMapping("/listRole")  
    public String listRole() {  
    	logger.debug("loading listRole");
        return "listRole";  
    }  
	
	@RequestMapping(value = "/getRoleList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getRoleList() {
		logger.debug("getting role list");
		List<RoleVO> roleList = roleService.getAllRoles();
		return GeneralUtils.convertListToJSONString(roleList);
	}
	
	@RequestMapping(value = "/createRole", method = RequestMethod.GET)
    public String showAddRoleForm(Model model) {  
    	logger.debug("loading showAddRoleForm");
    	RoleVO role = new RoleVO();
    	model.addAttribute("roleForm", role);
        return "createRole";  
    }  
	
	@InitBinder("roleForm")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(roleFormValidator);
	}
	
	@RequestMapping(value = "/createRole", method = RequestMethod.POST)
    public String saveRole(@ModelAttribute("roleForm") @Validated RoleVO role, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {  
    	
		logger.debug("saveRole() : " + role.toString());
		if (result.hasErrors()) {
			return "createRole";
		} else {
			List<RoleVO> roleList = roleService.getAllRoles();
			for(RoleVO r: roleList){
				if(role.getName().equals(r.getName())) { //if exist name
					result.rejectValue("name", "error.exist.roleform.name");
					return "createRole";
				}
			}
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Role added successfully!");
		}
		roleService.saveRole(role);
		
        return "redirect:listRole";  
    }  
	
	@RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
	public String deleteRole(@RequestParam(value = "checkboxId", required=false) List<String> ids,
			final RedirectAttributes redirectAttributes) {
		
		if(ids == null || ids.size() < 1){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select at least one record!");
			return "redirect:listRole";
		}
		
		List<Long> idList = new ArrayList<Long>();
		for(String s : ids) 
			idList.add(Long.parseLong(s));
		
		List<UserRoleVO> userRoleList = roleAssignService.findByRoleIdList(idList);
		if(userRoleList == null || !userRoleList.isEmpty()) {
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please remove users from the role(s) before delete!");
			return "redirect:listRole";
		}
		
		roleService.deleteRole(idList);
			
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Role(s) deleted successfully!");
		return "redirect:listRole";
	}
	
	@RequestMapping(value = "/updateRole", method = RequestMethod.POST)
	public String getRoleToUpdate(@RequestParam("editBtn") String id, Model model) {
		
		RoleVO role = roleService.findById(Long.parseLong(id));
		logger.debug("Loading update role page for " + role.toString());
		
		model.addAttribute("roleForm", role);
		
		return "updateRole";
	}
	
	@RequestMapping(value = "/updateRoleToDb", method = RequestMethod.POST)
	public String updateRole(@ModelAttribute("roleForm") @Validated RoleVO role,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		
		logger.debug("updateRole() : " + role.toString());
		
		if (result.hasErrors()) {
			return "updateRole";
		} else {
			List<RoleVO> roleList = roleService.getAllRoles();
			RoleVO currentRole = roleService.findById(role.getRoleId().longValue());
			for(RoleVO r: roleList){
				if(!currentRole.getName().equals(r.getName()) && role.getName().equals(r.getName())) { //if exist name
					result.rejectValue("name", "error.exist.roleform.name");
					return "updateRole";
				}
			}
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Role updated successfully!");
		}
		roleService.updateRole(role);
		
		return "redirect:listRole";
	}
	
}