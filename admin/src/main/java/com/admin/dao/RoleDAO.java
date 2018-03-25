package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.RoleTO;
@Transactional(readOnly = true)
public interface RoleDAO extends BaseDAO<RoleTO> {
	RoleTO findByRoleId(Long roleId);
	List<RoleTO> findByRoleIdIn(List<Long> roleIdList);
}
