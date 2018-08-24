package com.admin.submodule.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.dao.ModuleDAO;
import com.admin.dao.SubModuleDAO;
import com.admin.helper.GeneralUtils;
import com.admin.permission.service.PermissionService;
import com.admin.submodule.vo.SubModuleVO;
import com.admin.to.ModuleTO;
import com.admin.to.PermissionTypeTO;
import com.admin.to.SubModulePermissionTO;
import com.admin.to.SubModuleTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class SubmoduleServiceImpl implements SubmoduleService{
	
	private SubModuleDAO submoduleDAO;
	private ModuleDAO moduleDAO;
	private PermissionService permissionService;
	
	@Autowired
	public SubmoduleServiceImpl(SubModuleDAO submoduleDAO,
			ModuleDAO moduleDAO,
			PermissionService permissionService) {
		this.submoduleDAO = submoduleDAO;
		this.moduleDAO = moduleDAO;
		this.permissionService = permissionService;
	}

	public SubModuleVO findById(Long id) {
		SubModuleTO submoduleTO = submoduleDAO.findBySubModuleId(id);
		List<SubModuleVO> submoduleVOList = convertToSubModuleVOList(Arrays.asList(submoduleTO));
		if(submoduleVOList != null && !submoduleVOList.isEmpty()){
			return submoduleVOList.get(0);
		}
		return new SubModuleVO();
	}
	
	public List<SubModuleVO> getAllSubmodules() {
		List<SubModuleTO> submoduleTOList = submoduleDAO.findByDeleteInd(GeneralUtils.NOT_DELETED);
		return convertToSubModuleVOList(submoduleTOList);
	}
	
	public List<SubModuleVO> getSubmodulesById(List<Long> subModuleList) {
		List<SubModuleTO> submoduleTOList = submoduleDAO.findBySubModuleIdIn(subModuleList);
		return convertToSubModuleVOList(submoduleTOList);
	}
	
	public void saveSubmodule(SubModuleVO subModuleVO) {
		ModuleTO moduleTO = moduleDAO.findByModuleId(subModuleVO.getParentId());
		List<SubModuleTO> submoduleTOList = this.convertToSubModuleTOList(Arrays.asList(subModuleVO), moduleTO);
		submoduleDAO.save(submoduleTOList);
	}
	
	public void deleteSubmodule(Long id) {
		deleteSubmodule(Arrays.asList(id));
//		permissionManagementService.deleteSubmodulepermissionBySubmoduleId(id.intValue());
//		permissionManagementService.deleteSubmodulepermissiontypeBySubmoduleId(id.intValue());
	}
	
	public void deleteSubmodule(List<Long> idList) {
		List<SubModuleTO> submoduleTOList = submoduleDAO.findBySubModuleIdIn(idList);
		if(submoduleTOList != null && !submoduleTOList.isEmpty()){
			for(SubModuleTO submoduleTO : submoduleTOList){
				submoduleTO.setDeleteInd(GeneralUtils.DELETED);
			}
			submoduleDAO.save(submoduleTOList);
		}
	}
	
	public List<SubModuleVO> convertToSubModuleVOList(List<SubModuleTO> toList) {
		List<SubModuleVO> voList = new ArrayList<SubModuleVO>();
		if(toList != null && !toList.isEmpty()){
			for(SubModuleTO to : toList){
				SubModuleVO vo = new SubModuleVO();
				vo.setIcon(to.getIcon());
				vo.setName(to.getName());
				vo.setSubmoduleId(to.getSubModuleId());
				vo.setUrl(to.getUrl());
				vo.setParentId(to.getModuleTO().getModuleId());
				vo.setParentModuleName(to.getModuleTO().getModuleName());
				vo.setPermissionTypeList(permissionService.convertToSubModulePermissionTypeVOList(new ArrayList<PermissionTypeTO>(to.getPermissionTypeTOSet())));
				vo.setSubmodulePermissionList(permissionService.convertToSubModulePermissionVOList(new ArrayList<SubModulePermissionTO>(to.getSubModulePermissionSet())));
				voList.add(vo);
			}
		}
		return voList;
	}
	
	public List<SubModuleTO> convertToSubModuleTOList(List<SubModuleVO> voList, ModuleTO moduleTO) {
		List<SubModuleTO> toList = new ArrayList<SubModuleTO>();
		if(voList != null && !voList.isEmpty()){
			Map<Long, SubModuleTO> submoduleTOMap = GeneralUtils.convertListToLongMap(moduleTO.getSubmoduleTOList(), "subModuleId");
			for(SubModuleVO vo : voList){
				SubModuleTO to = new SubModuleTO();
				SubModuleTO dbTO = submoduleTOMap.get(vo.getSubmoduleId());
				if(dbTO != null){ //update
					to = dbTO;
				}
				to.setIcon(vo.getIcon());
				to.setName(vo.getName());
				to.setModuleTO(moduleTO);
				to.setSubModuleId(vo.getSubmoduleId());
				to.setUrl(vo.getUrl());
				to.setPermissionTypeTOSet(new HashSet<PermissionTypeTO>(permissionService.convertToPermissionTypeTOList(vo.getPermissionTypeList(), to)));
				toList.add(to);
			}
		}
		return toList;
	}
}
