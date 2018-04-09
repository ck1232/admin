package com.admin.permission.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.dao.PermissionTypeDAO;
import com.admin.dao.RoleDAO;
import com.admin.dao.SubModuleDAO;
import com.admin.dao.SubmodulePermissionDAO;
import com.admin.helper.GeneralUtils;
import com.admin.permission.vo.SubModulePermissionTypeVO;
import com.admin.permission.vo.SubModulePermissionVO;
import com.admin.to.PermissionTypeTO;
import com.admin.to.RoleTO;
import com.admin.to.SubModulePermissionTO;
import com.admin.to.SubModuleTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class PermissionService {
	
	private PermissionTypeDAO permissionTypeDAO;
	private SubModuleDAO subModuleDAO;
	private SubmodulePermissionDAO submodulePermissionDAO;
	private RoleDAO roleDAO;
	@Autowired
	public PermissionService(PermissionTypeDAO permissionTypeDAO, 
			SubModuleDAO subModuleDAO,
			SubmodulePermissionDAO submodulePermissionDAO,
			RoleDAO roleDAO) {
		this.permissionTypeDAO = permissionTypeDAO;
		this.subModuleDAO = subModuleDAO;
		this.submodulePermissionDAO = submodulePermissionDAO;
		this.roleDAO = roleDAO;
	}

	//Submodulepermissiontype functions START
	public SubModulePermissionTypeVO findById(Long id) {
		PermissionTypeTO permissionTypeTO = permissionTypeDAO.findByTypeId(id);
		List<SubModulePermissionTypeVO> permissionTypeVOList = convertToSubModulePermissionTypeVOList(Arrays.asList(permissionTypeTO));
		if(permissionTypeVOList != null && !permissionTypeVOList.isEmpty()){
			return permissionTypeVOList.get(0);
		}
		return new SubModulePermissionTypeVO();
	}
	
	public void saveSubmodulepermissiontype(SubModulePermissionTypeVO submodulePermissionTypeVO) {
		SubModuleTO submoduleTO = subModuleDAO.findBySubModuleId(submodulePermissionTypeVO.getSubmoduleId());
		List<PermissionTypeTO> permissionTypeTOList = convertToPermissionTypeTOList(Arrays.asList(submodulePermissionTypeVO), submoduleTO);
		permissionTypeDAO.save(permissionTypeTOList);
	}
	
	
	public void deleteSubmodulepermissiontype(Long id){
		deleteSubmodulepermissiontype(Arrays.asList(id));
	}
	
	public void deleteSubmodulepermissiontype(List<Long> idList) {
		List<PermissionTypeTO> permissionTypeTOList = permissionTypeDAO.findByTypeIdIn(idList);
		if(permissionTypeTOList != null && !permissionTypeTOList.isEmpty()){
			for(PermissionTypeTO permissionTypeTO : permissionTypeTOList){
				permissionTypeTO.setDeleteInd(GeneralUtils.DELETED);
			}
			permissionTypeDAO.save(permissionTypeTOList);
		}
	}
	
	
	public List<SubModulePermissionTypeVO> convertToSubModulePermissionTypeVOList(List<PermissionTypeTO> toList) {
		List<SubModulePermissionTypeVO> voList = new ArrayList<SubModulePermissionTypeVO>();
		if(toList != null && !toList.isEmpty()){
			for(PermissionTypeTO to : toList){
				SubModulePermissionTypeVO vo = new SubModulePermissionTypeVO();
				vo.setTypeId(to.getTypeId());
				vo.setSubmoduleId(to.getSubModuleTO().getSubModuleId());
				vo.setPermissionType(to.getPermissionType());
				vo.setSeqNum(to.getSeqNum());
				vo.setUrl(to.getUrl());
				voList.add(vo);
			}
		}
		return voList;
	}
	
	
	public List<PermissionTypeTO> convertToPermissionTypeTOList(List<SubModulePermissionTypeVO> voList, SubModuleTO submoduleTO) {
		List<PermissionTypeTO> toList = new ArrayList<PermissionTypeTO>();
		if(voList != null && !voList.isEmpty()) {
			Map<Long, PermissionTypeTO> permissionTypeTOMap = GeneralUtils.convertListToLongMap(new ArrayList<PermissionTypeTO>(submoduleTO.getPermissionTypeTOSet()), "typeId");
			for(SubModulePermissionTypeVO vo : voList) {
				PermissionTypeTO to = new PermissionTypeTO();
				PermissionTypeTO dbTO = permissionTypeTOMap.get(vo.getTypeId());
				if(dbTO != null)  //update
					to = dbTO;
				to.setTypeId(vo.getTypeId());
				to.setPermissionType(vo.getPermissionType());
				to.setSubModuleTO(submoduleTO);
				to.setSeqNum(vo.getSeqNum());
				to.setUrl(vo.getUrl());
				toList.add(to);
			}
		}
		return toList;
		
	}

	//Submodulepermissiontype functions END
	
	
	
	//Submodulepermission functions START
	public List<SubModuleTO> getSubmoduleByRoleTOList(List<RoleTO> roleList) {
		List<SubModuleTO> toList = new ArrayList<SubModuleTO>();
		Set<SubModuleTO> toSet = new HashSet<SubModuleTO>();
		if(roleList != null && !roleList.isEmpty()) {
			for(RoleTO role: roleList) {
				Set<SubModulePermissionTO> subModulePermissionSet = role.getSubModulePermissionSet();
				if(subModulePermissionSet != null && !subModulePermissionSet.isEmpty()) {
					for(SubModulePermissionTO subModulePermissionTO : subModulePermissionSet) {
						SubModuleTO subModuleTO = subModulePermissionTO.getSubModuleTO();
						if(subModuleTO != null) {
							toSet.add(subModuleTO);
						}
					}
				}
			}
		}
		toList.addAll(toSet);
		return toList;
	}
	
	public void saveNewSubmodulepermission(List<SubModulePermissionVO> voList) {
		if(voList != null && !voList.isEmpty()) {
			List<SubModulePermissionTO> submodulePermissionTOList = new ArrayList<SubModulePermissionTO>();
			for(SubModulePermissionVO vo : voList) {
				SubModulePermissionTO to = new SubModulePermissionTO();
				to.setRoleTO(roleDAO.findByRoleId(voList.get(0).getRoleId()));
				to.setSubModuleTO(subModuleDAO.findBySubModuleId(voList.get(0).getSubmoduleId()));
				to.setPermissionType(permissionTypeDAO.findByTypeId(vo.getPermissionTypeId()));
				submodulePermissionTOList.add(to);
			}
			submodulePermissionDAO.save(submodulePermissionTOList);
		}
		
	}
	
	public void deleteSubmodulePermissionByRoleIdAndSubmoduleId(Long roleId, Long submoduleId) {
		List<SubModulePermissionTO> submodulePermissionTOList = submodulePermissionDAO.findByRoleTO_RoleIdAndSubModuleTO_SubModuleId(roleId, submoduleId);
		if(submodulePermissionTOList != null && !submodulePermissionTOList.isEmpty()) {
			for(SubModulePermissionTO submodulePermissionTO : submodulePermissionTOList) {
				submodulePermissionTO.setDeleteInd(GeneralUtils.DELETED);
			}
			submodulePermissionDAO.save(submodulePermissionTOList);
		}
	}
	
	public List<SubModulePermissionVO> convertToSubModulePermissionVOList(List<SubModulePermissionTO> toList) {
		List<SubModulePermissionVO> voList = new ArrayList<SubModulePermissionVO>();
		if(toList != null && !toList.isEmpty()){
			for(SubModulePermissionTO to : toList){
				SubModulePermissionVO vo = new SubModulePermissionVO();
				vo.setPermissionId(to.getPermissionId());
				vo.setRoleId(to.getRoleTO().getRoleId());
				vo.setRoleName(to.getRoleTO().getName());
				vo.setSubmoduleId(to.getSubModuleTO().getSubModuleId());
				vo.setSubmoduleName(to.getSubModuleTO().getName());
				vo.setPermissionTypeId(to.getPermissionType().getTypeId());
				vo.setPermissionName(to.getPermissionType().getPermissionType());
				voList.add(vo);
			}
		}
		return voList;
	}
	
	
	public List<SubModulePermissionTO> convertToSubmodulePermissionTOList(List<SubModulePermissionVO> voList, SubModuleTO submoduleTO) {
		List<SubModulePermissionTO> toList = new ArrayList<SubModulePermissionTO>();
		if(voList != null && !voList.isEmpty()) {
			Map<Long, SubModulePermissionTO> submodulePermissionTOMap = GeneralUtils.convertListToLongMap(new ArrayList<SubModulePermissionTO>(submoduleTO.getSubModulePermissionSet()), "permissionId");
			for(SubModulePermissionVO vo : voList) {
				SubModulePermissionTO to = new SubModulePermissionTO();
				SubModulePermissionTO dbTO = submodulePermissionTOMap.get(vo.getPermissionId());
				if(dbTO != null)  //update
					to = dbTO;
				to.setPermissionId(vo.getPermissionId());
				to.setRoleTO(roleDAO.findByRoleId(vo.getRoleId()));
				to.setPermissionType(permissionTypeDAO.findByTypeId(vo.getPermissionTypeId()));
				to.setSubModuleTO(submoduleTO);
				toList.add(to);
			}
		}
		return toList;
		
	}

	//Submodulepermission functions END
	
}
