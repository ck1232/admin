package com.admin.role.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.admin.helper.GeneralUtils;
import com.admin.permission.service.PermissionService;
import com.admin.permission.vo.SubModulePermissionVO;
import com.admin.role.service.RoleAssignService;
import com.admin.role.service.RoleService;
import com.admin.role.vo.RoleVO;
import com.admin.role.vo.UserRoleVO;
import com.admin.user.service.UserService;
import com.admin.user.vo.UserVO;


@Controller  
@EnableWebMvc
@Scope("request")
@RequestMapping(value = "/admin")
public class RoleAssignController {
	private static final Logger logger = Logger.getLogger(RoleAssignController.class);
	
	private UserService userService;
	private RoleService roleService;
	private RoleAssignService roleAssignService;
	private PermissionService permissionService;
	
	@Autowired
	public RoleAssignController(UserService userService,
			RoleService roleService,
			RoleAssignService roleAssignService,
			PermissionService permissionService) {
		this.userService = userService;
		this.roleService = roleService;
		this.roleAssignService = roleAssignService;
		this.permissionService = permissionService;
	}
	
	@RequestMapping(value = "/assignRole", method = RequestMethod.POST)
	public String getUserToAssignRole(@RequestParam("assignRoleBtn") Integer id, Model model) {
		if(id != null){
			UserVO userVO = userService.findById(id.longValue());
			List<UserRoleVO> userRoleVOList = roleAssignService.findByUserId(id.longValue());
			List<RoleVO> roleVOList = roleService.getAllRoles();
			List<Long> roleIdList = GeneralUtils.convertListToLongList(userRoleVOList, "roleId", true);
			for(RoleVO vo : roleVOList) {
				if(!roleIdList.contains(vo.getRoleId())) {
					UserRoleVO userRoleVO = new UserRoleVO();
					userRoleVO.setUserId(id.longValue());
					userRoleVO.setRoleId(vo.getRoleId());
					userRoleVO.setRoleName(vo.getName());
					userRoleVO.setChecked(GeneralUtils.NO_IND);
					userRoleVOList.add(userRoleVO);
				}
			}
			
			List<SubModulePermissionVO> smpList = permissionService.findSubmodulePermissionByRoleIdList(roleIdList);
			
			model.addAttribute("user", userVO);
			model.addAttribute("roleList", userRoleVOList);
			model.addAttribute("submoduleList", smpList);
		}
		return "assignRole";
	}
	
	@RequestMapping(value = "/assignRole/{id}", method = RequestMethod.GET)
	public String getUserToAssignRoleForRedirect(@PathVariable Integer id, Model model) {
		if(id != null){
			UserVO userVO = userService.findById(id.longValue());
			List<UserRoleVO> userRoleVOList = roleAssignService.findByUserId(id.longValue());
			List<RoleVO> roleVOList = roleService.getAllRoles();
			List<Long> roleIdList = GeneralUtils.convertListToLongList(userRoleVOList, "roleId", true);
			for(RoleVO vo : roleVOList) {
				if(!roleIdList.contains(vo.getRoleId())) {
					UserRoleVO userRoleVO = new UserRoleVO();
					userRoleVO.setUserId(id.longValue());
					userRoleVO.setRoleId(vo.getRoleId());
					userRoleVO.setRoleName(vo.getName());
					userRoleVO.setChecked(GeneralUtils.NO_IND);
					userRoleVOList.add(userRoleVO);
				}
			}
			
			List<SubModulePermissionVO> smpList = permissionService.findSubmodulePermissionByRoleIdList(roleIdList);
			
			model.addAttribute("user", userVO);
			model.addAttribute("roleList", userRoleVOList);
			model.addAttribute("submoduleList", smpList);
		}
		return "assignRole";
	}
	
	@RequestMapping(value = "/saveRoleToUser", method = RequestMethod.POST)
	public String saveRoleToUser(@ModelAttribute("user") UserVO user, 
			@RequestParam(value="userRole", required=false) List<String> ids,
			Model model, final RedirectAttributes redirectAttributes) {
		
		logger.debug("saveRoleToUser() : user = " + user.getUserId());
		roleAssignService.deleteRoleByUserId(user.getUserId());
		
		if(ids != null && !ids.isEmpty()){
			List<UserRoleVO> userRoleVOList = new ArrayList<UserRoleVO>();
			for(String roleId: ids){
				UserRoleVO userRole = new UserRoleVO();
				userRole.setUserId(user.getUserId());
				userRole.setRoleId(Long.parseLong(roleId));
				userRoleVOList.add(userRole);
			}
			roleAssignService.saveNewUserRole(userRoleVOList);
		}
	
		// Add message to flash scope
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Role saved to user successfully!");
		
		return "redirect:assignRole/"+user.getUserId();
	}
	
}