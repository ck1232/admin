package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.UserRoleTO;
@Transactional(readOnly = true)
public interface UserRoleDAO extends BaseDAO<UserRoleTO> {
	List<UserRoleTO> findByUserTO_UserIdAndDeleteInd(Long userId, String deleteInd);
}
