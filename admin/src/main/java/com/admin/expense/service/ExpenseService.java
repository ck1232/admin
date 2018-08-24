package com.admin.expense.service;

import java.util.Date;
import java.util.List;

import com.admin.expense.vo.ExpenseVO;
import com.admin.payment.vo.PaymentVO;
import com.admin.to.ExpenseTO;

public interface ExpenseService {
	ExpenseVO findById(Long id);
	List<ExpenseVO> findByIdList(List<Long> idList);
	List<ExpenseVO> getAllExpense();
	List<String> getAllSupplier();
	ExpenseTO saveExpense(ExpenseVO expenseVO);
	void updateExpense(ExpenseVO vo);
	void deleteExpense(List<Long> idList);
	void saveExpensePaymentByExpenseId(PaymentVO paymentVo, List<Long> expenseidList);
	void saveExpensePaymentByExpense(PaymentVO paymentVo, List<ExpenseTO> expenseList);
	List<ExpenseVO> convertToExpenseVOList(List<ExpenseTO> toList);
	List<ExpenseVO> getAllBadDebtExpense(Date dateAsOf, Date endDate);
	List<ExpenseVO> getAllExpenseExcludeParamType(Date dateAsOf, Date endDate, List<Long> typeList);
	List<ExpenseVO> getAllExpense(Date dateAsOf, Date endDate, Long expenseTypeId);
	List<ExpenseVO> getAllExpenseOfParamType(List<Long> typeList);
}
