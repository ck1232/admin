package com.admin.role.service;

import java.util.List;

import com.admin.role.vo.UserRoleVO;

public interface RoleAssignService {

	void deleteRoleByUserId(Long userId);

	void saveNewUserRole(List<UserRoleVO> userRoleVOList);

	void deleteRoleByUserIdList(List<Long> idList);

	List<UserRoleVO> findByRoleIdList(List<Long> idList);

	List<UserRoleVO> findByUserId(Long longValue);

}
