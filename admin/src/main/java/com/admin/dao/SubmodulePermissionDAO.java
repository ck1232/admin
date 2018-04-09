package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.SubModulePermissionTO;
@Transactional(readOnly = true)
public interface SubmodulePermissionDAO extends BaseDAO<SubModulePermissionTO> {
	SubModulePermissionTO findByPermissionId(Long permissionId);
	List<SubModulePermissionTO> findByRoleTO_RoleIdAndSubModuleTO_SubModuleId(Long roleId, Long submoduleId);
	List<SubModulePermissionTO> findByPermissionIdIn(List<Long> permissionIdList);
}
