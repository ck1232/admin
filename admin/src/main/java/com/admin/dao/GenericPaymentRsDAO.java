package com.admin.dao;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.GenericPaymentRsTO;
@Transactional(readOnly = true)
public interface GenericPaymentRsDAO extends BaseDAO<GenericPaymentRsTO> {
	List<GenericPaymentRsTO> findByReferenceTypeAndReferenceIdAndDeleteInd(String type, Long refId, String deleteInd);
	List<GenericPaymentRsTO> findByReferenceTypeAndReferenceIdAndPaymentDetailTO_PaymentDateBetweenAndDeleteInd(String type, Long refId, Date dateFrom, Date dateTill, String deleteInd);
//	List<UserRoleTO> findByUserTO_UserIdInAndDeleteInd(List<Long> userId, String deleteInd);
//	List<UserRoleTO> findByRoleTO_RoleIdInAndDeleteInd(List<Long> roleId, String deleteInd);
}
