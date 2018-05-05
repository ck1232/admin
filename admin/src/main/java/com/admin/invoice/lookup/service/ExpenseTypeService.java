package com.admin.invoice.lookup.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.dao.ExpenseTypeDAO;
import com.admin.invoice.lookup.vo.ExpenseTypeVO;
import com.admin.to.ExpenseTypeTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class ExpenseTypeService {
	private ExpenseTypeDAO expenseTypeDAO;
	
	@Autowired
	public ExpenseTypeService(ExpenseTypeDAO expenseTypeDAO) {
		this.expenseTypeDAO = expenseTypeDAO;
	}
	public List<ExpenseTypeVO> getExpenseType(){
		List<ExpenseTypeTO> expenseTypeTOList = expenseTypeDAO.findAll();
		return convertToExpenseTypeVOList(expenseTypeTOList);
	}

	private List<ExpenseTypeVO> convertToExpenseTypeVOList(List<ExpenseTypeTO> toList) {
		List<ExpenseTypeVO> voList = new ArrayList<ExpenseTypeVO>();
		if(toList != null && !toList.isEmpty()) {
			for(ExpenseTypeTO to : toList) {
				ExpenseTypeVO vo = new ExpenseTypeVO();
				vo.setExpenseType(to.getExpenseType());
				vo.setExpenseTypeId(to.getExpenseTypeId());
				voList.add(vo);
			}
		}
		return voList;
	}
	
}
