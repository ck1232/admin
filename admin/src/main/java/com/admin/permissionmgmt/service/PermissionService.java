package com.admin.permissionmgmt.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.dao.PermissionDAO;
import com.admin.dao.SubModuleDAO;
import com.admin.helper.GeneralUtils;
import com.admin.permissionmgmt.vo.SubModulePermissionTypeVO;
import com.admin.to.PermissionTypeTO;
import com.admin.to.SubModuleTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class PermissionService {
	
	private PermissionDAO permissionDAO;
	private SubModuleDAO subModuleDAO;
	@Autowired
	public PermissionService(PermissionDAO permissionDAO, SubModuleDAO subModuleDAO) {
		this.permissionDAO = permissionDAO;
		this.subModuleDAO = subModuleDAO;
	}

	//Submodulepermissiontype functions START
	public SubModulePermissionTypeVO findById(Long id) {
		PermissionTypeTO permissionTypeTO = permissionDAO.findByTypeId(id);
		List<SubModulePermissionTypeVO> permissionTypeVOList = convertToSubModulePermissionTypeVOList(Arrays.asList(permissionTypeTO));
		if(permissionTypeVOList != null && !permissionTypeVOList.isEmpty()){
			return permissionTypeVOList.get(0);
		}
		return new SubModulePermissionTypeVO();
	}
	
	public void saveSubmodulepermissiontype(SubModulePermissionTypeVO submodulePermissionTypeVO) {
		SubModuleTO submoduleTO = subModuleDAO.findBySubModuleId(submodulePermissionTypeVO.getSubmoduleId());
		List<PermissionTypeTO> permissionTypeTOList = convertToPermissionTypeTOList(Arrays.asList(submodulePermissionTypeVO), submoduleTO);
		permissionDAO.save(permissionTypeTOList);
	}
	
	
	public void deleteSubmodulepermissiontype(Long id){
		deleteSubmodulepermissiontype(Arrays.asList(id));
	}
	
	public void deleteSubmodulepermissiontype(List<Long> idList) {
		List<PermissionTypeTO> permissionTypeTOList = permissionDAO.findByTypeIdIn(idList);
		if(permissionTypeTOList != null && !permissionTypeTOList.isEmpty()){
			for(PermissionTypeTO permissionTypeTO : permissionTypeTOList){
				permissionTypeTO.setDeleteInd(GeneralUtils.DELETED);
			}
			permissionDAO.save(permissionTypeTOList);
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
	
}
