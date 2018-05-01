package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.PaymentRsTO;
@Transactional(readOnly = true)
public interface PaymentRsDAO extends BaseDAO<PaymentRsTO> {
	List<PaymentRsTO> findByPaymentRsIdIn(List<Long> paymentRsIdList);
//	List<UserRoleTO> findByUserTO_UserIdInAndDeleteInd(List<Long> userId, String deleteInd);
//	List<UserRoleTO> findByRoleTO_RoleIdInAndDeleteInd(List<Long> roleId, String deleteInd);
}
