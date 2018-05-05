package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.ExpenseTO;
@Transactional(readOnly = true)
public interface ExpenseDAO extends BaseDAO<ExpenseTO> {
	ExpenseTO findByExpenseId(Long expenseId);
	List<ExpenseTO> findByExpenseIdIn(List<Long> expenseIdList);
}
