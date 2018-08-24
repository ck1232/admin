package com.admin.dao;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.ExpenseTO;
@Transactional(readOnly = true)
public interface ExpenseDAO extends BaseDAO<ExpenseTO> {
	ExpenseTO findByExpenseId(Long expenseId);
	List<ExpenseTO> findByExpenseIdIn(List<Long> expenseIdList);
	List<ExpenseTO> findByExpenseDateBetweenAndExpenseTypeIdNotInAndDeleteIndOrderByExpenseDate(Date dateFrom, Date dateTill, List<Long> excludeTypeList ,String deleteInd);
	List<ExpenseTO> findByExpenseDateBetweenAndExpenseTypeIdAndDeleteIndOrderByExpenseDate(Date dateFrom, Date dateTill, Long expenseTypeId, String deleteInd);
	List<ExpenseTO> findByExpenseTypeIdInAndDeleteIndOrderByExpenseDate(List<Long> typeList, String deleteInd);
}
