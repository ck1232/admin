package com.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.admin.to.ExpenseTypeTO;
@Transactional(readOnly = true)
public interface ExpenseTypeDAO extends JpaRepository<ExpenseTypeTO, Long> {
	ExpenseTypeTO findByExpenseTypeId(Long expenseTypeId);
}
