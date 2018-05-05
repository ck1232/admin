package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.ExpensePaymentRsTO;
@Transactional(readOnly = true)
public interface ExpensePaymentRsDAO extends BaseDAO<ExpensePaymentRsTO> {
	List<ExpensePaymentRsTO> findByPaymentRsIdIn(List<Long> paymentRsIdList);
//	List<UserRoleTO> findByUserTO_UserIdInAndDeleteInd(List<Long> userId, String deleteInd);
//	List<UserRoleTO> findByRoleTO_RoleIdInAndDeleteInd(List<Long> roleId, String deleteInd);
}
