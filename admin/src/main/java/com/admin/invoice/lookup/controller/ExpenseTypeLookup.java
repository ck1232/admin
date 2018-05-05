package com.admin.invoice.lookup.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.admin.invoice.lookup.service.ExpenseTypeService;
import com.admin.invoice.lookup.vo.ExpenseTypeVO;

@Lazy
@Component
public class ExpenseTypeLookup {
	private static final Logger logger = Logger.getLogger(ExpenseTypeLookup.class);
	private ExpenseTypeService expenseTypeService;
	private List<ExpenseTypeVO> expenseTypeList;
	private Map<String, ExpenseTypeVO> expenseTypeByValueMap = new HashMap<String, ExpenseTypeVO>();
	private Map<Long, ExpenseTypeVO> expenseTypeByIdMap = new HashMap<Long, ExpenseTypeVO>();
	private Map<String, String> expenseTypeMap = new LinkedHashMap<String,String>();
	
	@Autowired
	public ExpenseTypeLookup(ExpenseTypeService expenseTypeService){
		logger.debug("ExpenseTypeLookup initialized");
		this.expenseTypeService = expenseTypeService;
		load();
	}
	private void load() {
		expenseTypeList = expenseTypeService.getExpenseType();
		if(expenseTypeList != null){
			for(ExpenseTypeVO expenseType : expenseTypeList){
				expenseTypeByValueMap.put(expenseType.getExpenseType(), expenseType);
				expenseTypeByIdMap.put(expenseType.getExpenseTypeId(), expenseType);
				expenseTypeMap.put(expenseType.getExpenseTypeId().toString(), expenseType.getExpenseType());
			}
		}
	}
	public List<ExpenseTypeVO> getExpenseTypeList() {
		return expenseTypeList;
	}
	public void setExpenseTypeList(List<ExpenseTypeVO> expenseTypeList) {
		this.expenseTypeList = expenseTypeList;
	}
	public Map<String, ExpenseTypeVO> getExpenseTypeByValueMap() {
		return expenseTypeByValueMap;
	}
	public void setExpenseTypeByValueMap(Map<String, ExpenseTypeVO> expenseTypeByValueMap) {
		this.expenseTypeByValueMap = expenseTypeByValueMap;
	}
	public Map<Long, ExpenseTypeVO> getExpenseTypeByIdMap() {
		return expenseTypeByIdMap;
	}
	public void setExpenseTypeByIdMap(Map<Long, ExpenseTypeVO> expenseTypeByIdMap) {
		this.expenseTypeByIdMap = expenseTypeByIdMap;
	}
	public Map<String, String> getExpenseTypeMap() {
		return expenseTypeMap;
	}
	public void setExpenseTypeMap(Map<String, String> expenseTypeMap) {
		this.expenseTypeMap = expenseTypeMap;
	}
	
	public String getExpenseTypeById(Long id){
		return expenseTypeByIdMap.get(id).getExpenseType();
	}
}
