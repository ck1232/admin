package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.SubModulePermissionTO;
@Transactional(readOnly = true)
public interface SubmodulePermissionDAO extends BaseDAO<SubModulePermissionTO> {
	SubModulePermissionTO findByPermissionId(Long permissionId);
	List<SubModulePermissionTO> findByRoleTO_RoleIdAndSubModuleTO_SubModuleIdAndDeleteInd(Long roleId, Long submoduleId, String deleteInd);
	List<SubModulePermissionTO> findByRoleTO_RoleIdInAndDeleteInd(List<Long> roleIdList, String deleteInd);
	List<SubModulePermissionTO> findByPermissionType_TypeIdInAndDeleteInd(List<Long> typeIdList, String deleteInd);
	List<SubModulePermissionTO> findByPermissionIdIn(List<Long> permissionIdList);
}
