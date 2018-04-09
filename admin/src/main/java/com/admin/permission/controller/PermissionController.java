package com.admin.permission.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
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

import com.admin.helper.GeneralUtils;
import com.admin.permission.service.PermissionService;
import com.admin.permission.vo.SubModulePermissionTypeVO;
import com.admin.permission.vo.SubModulePermissionVO;
import com.admin.role.service.RoleService;
import com.admin.role.vo.RoleVO;
import com.admin.submodule.service.SubmoduleService;
import com.admin.submodule.vo.SubModuleVO;
import com.admin.validator.PermissionTypeFormValidator;

@Controller  
@EnableWebMvc
@Scope("request")
@RequestMapping(value = "/admin")
public class PermissionController {
	private static final Logger logger = Logger.getLogger(PermissionController.class);
	
	private PermissionService permissionService;
	private SubmoduleService submoduleService;
	private RoleService roleService;
	private PermissionTypeFormValidator permissionTypeFormValidator;
	
	@Autowired
	public PermissionController(PermissionService permissionService, SubmoduleService submoduleService,
			RoleService roleService,
			PermissionTypeFormValidator permissionTypeFormValidator){
		this.permissionService = permissionService;
		this.submoduleService = submoduleService;
		this.roleService = roleService;
		this.permissionTypeFormValidator = permissionTypeFormValidator;
	}
	
	@RequestMapping("/listPermission")  
    public String listPermission() {  
    	logger.debug("loading listPermission");
        return "listPermission";  
    } 
	
	@RequestMapping(value = "/updateSubmodulePermission", method = RequestMethod.POST)
	public String getRoleToUpdatePermission(@RequestParam("editBtn") String id, Model model) {
		logger.debug("Loading grant role permission page");
		SubModuleVO submodule = submoduleService.findById(Long.parseLong(id));
//		List<SubModulePermissionTypeVO> submodulepermissiontypeList = permissionService.getSubmodulepermissiontype(Integer.valueOf(id));
		model.addAttribute("submodule", submodule);
		model.addAttribute("permissionList", submodule.getPermissionTypeList());
		return "updateSubmodulePermission";
	}
	
	@RequestMapping(value = "/updateSubmodulePermission/{id}", method = RequestMethod.GET)
	public String getRoleToUpdatePermissionForRedirect(@PathVariable String id, Model model) {
		logger.debug("Loading grant role permission page");
		SubModuleVO submodule = submoduleService.findById(Long.parseLong(id));
//		List<SubModulePermissionTypeVO> submodulepermissiontypeList = permissionService.getSubmodulepermissiontype(Integer.valueOf(id));
		model.addAttribute("submodule", submodule);
		model.addAttribute("permissionList", submodule.getPermissionTypeList());
		return "updateSubmodulePermission";
	}
	
	@RequestMapping(value = "/getRoleToPermission", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getRoleListToPermission(@RequestParam("editBtn") Integer submoduleid) {
		logger.debug("getting role list");
		SubModuleVO submodule = submoduleService.findById(submoduleid.longValue());
		List<SubModulePermissionVO> rolesToPermissionList = submodule.getSubmodulePermissionList(); //list of roles with permission
		//group by roles
		Map<Long, SubModulePermissionVO> rolesToPermissionMap = new HashMap<Long, SubModulePermissionVO>();
		for(SubModulePermissionVO vo : rolesToPermissionList) {
			SubModulePermissionVO mapVO =  rolesToPermissionMap.get(vo.getRoleId());
			if(mapVO == null) {
				vo.setPermissionTypeIdList(vo.getPermissionTypeId().toString());
				rolesToPermissionMap.put(vo.getRoleId(), vo);
			}else {
				if(mapVO.getPermissionName() != null && !mapVO.getPermissionName().isEmpty()) {
					mapVO.setPermissionName(mapVO.getPermissionName()+","+vo.getPermissionName());
				}else {
					mapVO.setPermissionName(mapVO.getPermissionName());
				}
				if(mapVO.getPermissionTypeId()!=null) {
					mapVO.setPermissionTypeIdList(mapVO.getPermissionTypeId()+","+vo.getPermissionTypeId());
				}else {
					mapVO.setPermissionTypeIdList(mapVO.getPermissionTypeId().toString());
				}
			}
		}
		rolesToPermissionList.clear();
		rolesToPermissionList.addAll(rolesToPermissionMap.values());

		
		//retrieve all roles with no permission and set to none for permission
		List<RoleVO> roleVOList = roleService.getAllRoles(); //get list of all roles
		List<Long> rolesToPermissionIdList = GeneralUtils.convertListToLongList(rolesToPermissionList, "roleId", true);
		List<Long> roleVOIdList = GeneralUtils.convertListToLongList(roleVOList, "roleId", true);
		roleVOIdList.removeAll(rolesToPermissionIdList); // list of roles with no permission
		for(RoleVO roleVO : roleVOList) {
			if(roleVOIdList.contains(roleVO.getRoleId())) { // no permission
				SubModulePermissionVO submodulePermissionVO = new SubModulePermissionVO();
				submodulePermissionVO.setRoleId(roleVO.getRoleId());
				submodulePermissionVO.setRoleName(roleVO.getName());
				submodulePermissionVO.setSubmoduleId(submoduleid.longValue());
				submodulePermissionVO.setSubmoduleName(submodule.getName());
				submodulePermissionVO.setPermissionName(GeneralUtils.NONE);
				rolesToPermissionList.add(submodulePermissionVO);
			}
		}
		
		/*List<RolesToPermissionCustomDbObject> rolesToPermissionList = permissionService.getRolesToPermission(submoduleid);

		List<RolesToPermissionCustomDbObject> permissionList = new ArrayList<RolesToPermissionCustomDbObject>();
		Map<Integer, RolesToPermissionCustomDbObject> permissionMap = new HashMap<Integer, RolesToPermissionCustomDbObject>();
		for(RolesToPermissionCustomDbObject obj : rolesToPermissionList){
			RolesToPermissionCustomDbObject roleToPermission = permissionMap.get(obj.getRoleId());
			if(roleToPermission == null){
				permissionMap.put(obj.getRoleId(), obj);
			}else{
				if(roleToPermission.getPermission() != null && !roleToPermission.getPermission().isEmpty()){
					roleToPermission.setPermission(roleToPermission.getPermission()+","+obj.getPermission());
				}else{
					roleToPermission.setPermission(obj.getPermission());
				}
				
				if(roleToPermission.getPermissionId() != null && !roleToPermission.getPermissionId().isEmpty()){
					roleToPermission.setPermissionId(roleToPermission.getPermissionId()+","+obj.getPermissionId());
				}else{
					roleToPermission.setPermissionId(obj.getPermissionId());
				}
			}
		}
		for(RolesToPermissionCustomDbObject obj : permissionMap.values()){
			permissionList.add(obj);
		}*/
		
		return GeneralUtils.convertListToJSONString(rolesToPermissionList);
	}
	
	
	
	@RequestMapping(value = "/saveSubmodulePermissionToDb", method = RequestMethod.POST)
	public String savePermissionToDb(@RequestParam("submoduleid") String submoduleid, 
			@RequestParam("roleid") String roleid, 
			@RequestParam(value="submodulePermission", required=false) List<String> submodulePermissionList, 
			final RedirectAttributes redirectAttributes) {
		
		permissionService.deleteSubmodulePermissionByRoleIdAndSubmoduleId(Long.parseLong(roleid), Long.parseLong(submoduleid));
		if(submodulePermissionList != null && !submodulePermissionList.isEmpty()){
			List<SubModulePermissionVO> submodulePermissionVOList = new ArrayList<SubModulePermissionVO>();
			for(String submodulePermission: submodulePermissionList){
				SubModulePermissionVO submodulepermission = new SubModulePermissionVO();
				submodulepermission.setRoleId(Long.parseLong(roleid));
				submodulepermission.setSubmoduleId(Long.parseLong(submoduleid));
				submodulepermission.setPermissionTypeId(Long.parseLong(submodulePermission));
				submodulePermissionVOList.add(submodulepermission);
			}
			permissionService.saveNewSubmodulepermission(submodulePermissionVOList);
		}
		
		/*permissionService.deleteSubmodulepermission(Integer.valueOf(roleid), Integer.valueOf(submoduleid));
		
		if(submodulePermissionList != null && submodulePermissionList.size() > 0){
			for(String submodulePermission: submodulePermissionList){
				SubModulePermissionVO submodulepermission = new SubModulePermissionVO();
				submodulepermission.setRoleId(new Integer(roleid));
				submodulepermission.setSubmoduleId(new Integer(submoduleid));
				submodulepermission.setPermissionTypeId(Integer.valueOf(submodulePermission));
				permissionService.saveSubmodulepermission(submodulepermission);
			}
		}*/
		
		redirectAttributes.addFlashAttribute("css","success");
		redirectAttributes.addFlashAttribute("msg", "Permission saved to role successfully.");
		
		return "redirect:updateSubmodulePermission/" + submoduleid;
	}
	
	@RequestMapping(value = "/updatePermissionType", method = RequestMethod.POST)
	public String loadupdatePermissionTypeForm(@RequestParam("loadEditPermissionTypeBtn") String id, Model model) {
		logger.debug("Loading update permission type page");
		SubModuleVO subModuleVO = submoduleService.findById(Long.parseLong(id));
		model.addAttribute("submodule", subModuleVO);
		return "updatePermissionType";
	}
	
	@RequestMapping(value = "/updatePermissionType/{id}", method = RequestMethod.GET)
	public String loadupdatePermissionTypeFormForRedirect(@PathVariable("id") String id, Model model) {
		logger.debug("Loading update permission type page");
		SubModuleVO subModuleVO = submoduleService.findById(Long.parseLong(id));
		model.addAttribute("submodule", subModuleVO);
		return "updatePermissionType";
	}
	
	
	@RequestMapping(value = "/getPermissionTypeList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getPermissionTypeList(@RequestParam("submoduleid") String submoduleid) {
		logger.debug("getting permission type list");
		SubModuleVO submodule = submoduleService.findById(Long.parseLong(submoduleid));
		return GeneralUtils.convertListToJSONString(submodule.getPermissionTypeList());
	}
	
	@RequestMapping(value = "/createPermissionType", method = RequestMethod.POST)
    public String showAddPermissionTypeForm(@RequestParam("submoduleid") String submoduleid, Model model) {  
    	logger.debug("loading add permission type form");
    	SubModulePermissionTypeVO submodulepermissiontype = new SubModulePermissionTypeVO();
    	submodulepermissiontype.setSubmoduleId(Long.parseLong(submoduleid));
    	SubModuleVO subModuleVO = submoduleService.findById(Long.parseLong(submoduleid));
    	model.addAttribute("submodulepermissiontypeForm", submodulepermissiontype);
    	model.addAttribute("submodule", subModuleVO);
        return "createPermissionType";  
    }
	
	@InitBinder("submodulepermissiontypeForm")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(permissionTypeFormValidator);
	}
	
	
	@RequestMapping(value = "/savePermissionTypeToDb", method = RequestMethod.POST)
    public String savePermissionTypeToDb(@ModelAttribute("submodulepermissiontypeForm") @Validated SubModulePermissionTypeVO subModulePermissionTypeVO, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {  
    	
		logger.debug("savePermissionTypeToDb() : " + subModulePermissionTypeVO.getPermissionType());
		
		if (result.hasErrors()) {
			SubModuleVO subModuleVO = submoduleService.findById(subModulePermissionTypeVO.getSubmoduleId().longValue());
			model.addAttribute("submodule", subModuleVO);
			return "createPermissionType";
		} else {
			boolean pass = true;
			SubModuleVO submodule = submoduleService.findById(subModulePermissionTypeVO.getSubmoduleId().longValue());
			List<SubModulePermissionTypeVO> permissiontypeList = submodule.getPermissionTypeList();
			if(permissiontypeList != null) {
				for(SubModulePermissionTypeVO smpt: permissiontypeList){
					if(subModulePermissionTypeVO.getPermissionType().equals(smpt.getPermissionType())) { //if exist name
						result.rejectValue("permissiontype", "error.exist.permissiontypeform.permissiontype");;
						pass = false;
						break;
					}
				}
				for(SubModulePermissionTypeVO smpt: permissiontypeList){
					if(subModulePermissionTypeVO.getUrl().equals(smpt.getUrl())) { //if exist url
						result.rejectValue("url", "error.exist.permissiontypeform.url");;
						pass = false;
						break;
					}
				}
				if(!NumberUtils.isNumber(subModulePermissionTypeVO.getSeqNum().toString())){
					result.rejectValue("seqno", "error.numeric.permissiontypeform.seqno");;
					pass = false;
				}
			}
			if(!pass){
				SubModuleVO subModuleVO = submoduleService.findById(subModulePermissionTypeVO.getSubmoduleId().longValue());
				model.addAttribute("submodule", subModuleVO);
				return "createPermissionType";
			}
			
			permissionService.saveSubmodulepermissiontype(subModulePermissionTypeVO);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Permission type added successfully!");
		}

        return "redirect:updatePermissionType/" + subModulePermissionTypeVO.getSubmoduleId();  
    }  
	
	
	@RequestMapping(value = "/updatePermissionTypeDetail", method = RequestMethod.POST)
	public String getPermissionTypeDetailToUpdate(@RequestParam("editBtn") String id, Model model) {
		logger.debug("id = " + id);
		SubModulePermissionTypeVO subModulePermissionTypeVO = permissionService.findById(Long.parseLong(id));
		if (subModulePermissionTypeVO == null) {
			model.addAttribute("css", "danger");
			model.addAttribute("msg", "Permission type not found");
		}
		model.addAttribute("submodulepermissiontypeForm", subModulePermissionTypeVO);
		return "updatePermissionTypeDetail";
	}
	
	
	@RequestMapping(value = "/updatePermissionTypeDetailToDb", method = RequestMethod.POST)
	public String updateSubmodulepermissiontypeDetail(@ModelAttribute("submodulepermissiontypeForm") @Validated SubModulePermissionTypeVO subModulePermissionTypeVO,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		
		logger.debug("updateSubmodulepermissiontypeDetail() : " + subModulePermissionTypeVO.toString());
		
		if (result.hasErrors()) {
			return "updatePermissionTypeDetail";
		} else {
			permissionService.saveSubmodulepermissiontype(subModulePermissionTypeVO);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Permission type updated successfully!");
		}
		
		return "redirect:updatePermissionType/" + subModulePermissionTypeVO.getSubmoduleId();
	}
	
	
	@RequestMapping(value = "/deletePermissionType", method = RequestMethod.POST)
	public String deletePermissionType(@RequestParam(value = "checkboxId", required=false) List<String> ids,
			@RequestParam("submoduleid") String submoduleid,
			final RedirectAttributes redirectAttributes) {
		if(ids == null || ids.size() < 1){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select at least one record!");
			return "redirect:updatePermissionType/" + submoduleid;
		}
		for (String id : ids) {
			permissionService.deleteSubmodulepermissiontype(Long.parseLong(id));
			logger.debug("deleted "+ id);
		}
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Permission type(s) deleted successfully!");
		return "redirect:updatePermissionType/" + submoduleid;
	}
	
	@RequestMapping(value = "/savePermissionTypeSeqToDb", method = RequestMethod.POST)
	public String savePermissionTypeSeqToDb(@RequestParam("permissionTypeid") String id, 
			@RequestParam("seqno") String seqno,
			final RedirectAttributes redirectAttributes) {
		SubModulePermissionTypeVO submodulepermissiontype = permissionService.findById(Long.parseLong(id));
		submodulepermissiontype.setSeqNum(Long.parseLong(seqno));
		permissionService.saveSubmodulepermissiontype(submodulepermissiontype);
		
		redirectAttributes.addFlashAttribute("css","success");
		redirectAttributes.addFlashAttribute("msg", "Sequence number saved successfully.");
		
		return "redirect:updatePermissionType/" + submodulepermissiontype.getSubmoduleId();
	}
	
}
