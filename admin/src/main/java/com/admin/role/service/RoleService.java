package com.admin.role.service;

import java.util.List;

import com.admin.role.vo.RoleVO;
import com.admin.to.RoleTO;

public interface RoleService {

	void deleteRole(List<Long> idList);

	RoleVO findById(Long parseLong);

	List<String> getAllowedUrlByRoleName(List<RoleTO> roleList);

	List<RoleVO> getAllRoles();

	void saveRole(RoleVO role);

	void updateRole(RoleVO role);

}
