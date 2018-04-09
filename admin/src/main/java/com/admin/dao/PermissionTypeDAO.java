package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.PermissionTypeTO;
@Transactional(readOnly = true)
public interface PermissionTypeDAO extends BaseDAO<PermissionTypeTO> {
	PermissionTypeTO findByTypeId(Long typeId);
	List<PermissionTypeTO> findByTypeIdIn(List<Long> typeIdList);
}
