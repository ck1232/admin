package com.admin.permission.service;

import java.util.Collection;
import java.util.List;

import com.admin.permission.vo.SubModulePermissionTypeVO;
import com.admin.permission.vo.SubModulePermissionVO;
import com.admin.to.PermissionTypeTO;
import com.admin.to.RoleTO;
import com.admin.to.SubModulePermissionTO;
import com.admin.to.SubModuleTO;

public interface PermissionService {

	Collection<? extends PermissionTypeTO> convertToPermissionTypeTOList(
			List<SubModulePermissionTypeVO> permissionTypeList, SubModuleTO to);

	List<SubModulePermissionTypeVO> convertToSubModulePermissionTypeVOList(List<PermissionTypeTO> arrayList);

	List<SubModulePermissionVO> convertToSubModulePermissionVOList(List<SubModulePermissionTO> toList);

	void deleteSubmodulePermissionByRoleIdAndSubmoduleId(Long parseLong, Long parseLong2);

	void deleteSubmodulePermissionByRoleIdList(List<Long> idList);

	void deleteSubmodulepermissiontype(List<Long> idList);

	SubModulePermissionTypeVO findById(Long id);

	List<SubModulePermissionVO> findSubmodulePermissionByRoleIdList(List<Long> roleIdList);

	List<SubModulePermissionVO> findSubmodulePermissionBytypeIdList(List<Long> idList);

	List<SubModuleTO> getSubmoduleByRoleTOList(List<RoleTO> roleList);

	void saveNewSubmodulepermission(List<SubModulePermissionVO> submodulePermissionVOList);

	void saveSubmodulepermissiontype(SubModulePermissionTypeVO subModulePermissionTypeVO);

}
