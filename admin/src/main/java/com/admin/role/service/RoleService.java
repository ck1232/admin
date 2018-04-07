package com.admin.role.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.dao.RoleDAO;
import com.admin.helper.GeneralUtils;
import com.admin.role.vo.RoleVO;
import com.admin.to.PermissionTypeTO;
import com.admin.to.RoleTO;
import com.admin.to.SubModulePermissionTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class RoleService {
	
//	private RoleDbObjectMapper roleDbObjectMapper;
	private RoleDAO roleDAO;
//	private RoleAssignmentService roleAssignmentService;
//	private PermissionManagementService permissionManagementService;
	
	@Autowired
	public RoleService(RoleDAO roleDAO
//			RoleAssignmentService roleAssignmentService,
//			PermissionManagementService permissionManagementService
			) {
		this.roleDAO = roleDAO;
//		this.roleAssignmentService = roleAssignmentService;
//		this.permissionManagementService = permissionManagementService;
	}
	
	public RoleVO findById(Long id) {
		RoleTO roleTO = roleDAO.findByRoleId(id); 
		List<RoleVO> roleVOList = convertToRoleVOList(Arrays.asList(roleTO));
		if(roleVOList != null && !roleVOList.isEmpty()){
			return roleVOList.get(0);
		}
		return new RoleVO();
	}

	public List<RoleVO> getAllRoles() {
		List<RoleTO> roleTOList = roleDAO.findByDeleteInd(GeneralUtils.NOT_DELETED);
		return convertToRoleVOList(roleTOList);
	}
	
	public void saveRole(RoleVO roleVO) {
		List<RoleTO> roleTOList = convertToRoleTOList(Arrays.asList(roleVO));
		roleDAO.save(roleTOList);
	}
	
	public void deleteRole(Long id) {
		deleteRole(Arrays.asList(id));
//		roleAssignmentService.deleteUserRolebyRoleId(id.intValue());
//		permissionManagementService.deleteSubmodulepermissionByRoleId(id.intValue());
	}
	
	private void deleteRole(List<Long> idList) {
		List<RoleTO> roleTOList = roleDAO.findByRoleIdIn(idList);
		if(roleTOList != null && !roleTOList.isEmpty()){
			for(RoleTO roleTO : roleTOList){
				roleTO.setDeleteInd(GeneralUtils.DELETED);
			}
			roleDAO.save(roleTOList);
		}
		
	}

	public void updateRole(RoleVO vo) {
		if(vo != null && vo.getRoleId() != null){
			RoleTO roleTO = roleDAO.findByRoleId(vo.getRoleId().longValue());
			roleTO.setName(vo.getName());
			roleDAO.save(roleTO);
		}
	}
	
	private List<RoleVO> convertToRoleVOList(List<RoleTO> toList) {
		List<RoleVO> voList = new ArrayList<RoleVO>();
		if(toList != null && !toList.isEmpty()){
			for(RoleTO obj : toList){
				RoleVO vo = new RoleVO();
				vo.setName(obj.getName());
				vo.setRoleId(obj.getRoleId().intValue());
				voList.add(vo);
			}
		}
		return voList;
	}
	
	private List<RoleTO> convertToRoleTOList(List<RoleVO> voList) {
		List<RoleTO> roleTOList = new ArrayList<RoleTO>();
		if(voList != null && !voList.isEmpty()) {
			for(RoleVO vo : voList) {
				RoleTO to = new RoleTO();
				to.setName(vo.getName());
				to.setRoleId(vo.getRoleId().longValue());
				roleTOList.add(to);
			}
		}
		return roleTOList;
	}

	public List<String> getAllowedUrlByRoleName(List<RoleTO> roleList) {
		Set<String> allowedUrlList = new HashSet<String>();
		if(roleList != null && !roleList.isEmpty()) {
			for(RoleTO role : roleList) {
				Set<SubModulePermissionTO> subModulePermissionSet = role.getSubModulePermissionSet();
				if(subModulePermissionSet != null && !subModulePermissionSet.isEmpty()) {
					for(SubModulePermissionTO subModulePermissionTO : subModulePermissionSet) {
						PermissionTypeTO permissionTO = subModulePermissionTO.getPermissionType();
						if(permissionTO != null && permissionTO.getUrl() != null) {
							allowedUrlList.add(permissionTO.getUrl());
						}
					}
				}
			}
		}
		return new ArrayList<String>(allowedUrlList);
	}
}
