package com.admin.expense.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.dao.ExpenseDAO;
import com.admin.expense.vo.ExpenseStatusEnum;
import com.admin.expense.vo.ExpenseVO;
import com.admin.helper.GeneralUtils;
import com.admin.invoice.lookup.controller.ExpenseTypeLookup;
import com.admin.payment.service.PaymentService;
import com.admin.payment.vo.PaymentVO;
import com.admin.to.ExpensePaymentRsTO;
import com.admin.to.ExpenseTO;
import com.admin.to.PaymentDetailTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class ExpenseService {
	
	private ExpenseDAO expenseDAO;
	private PaymentService paymentService;
	private ExpenseTypeLookup expenseTypeLookup;
	
	@Autowired
	public ExpenseService(ExpenseDAO expenseDAO,
			PaymentService paymentService,
			ExpenseTypeLookup expenseTypeLookup) {
		this.expenseDAO = expenseDAO;
		this.paymentService = paymentService;
		this.expenseTypeLookup = expenseTypeLookup;
	}
	
	public ExpenseVO findById(Long id) {
		List<ExpenseVO> expenseVOList = findByIdList(Arrays.asList(id));
		if(expenseVOList != null && !expenseVOList.isEmpty()) {
			return expenseVOList.get(0);
		}
		return new ExpenseVO();
	}
	
	public List<ExpenseVO> findByIdList(List<Long> idList){
		List<ExpenseTO> expenseTOList = expenseDAO.findByExpenseIdIn(idList);
		return convertToExpenseVOList(expenseTOList);
	}
		
	public List<ExpenseVO> getAllExpense() {
		List<ExpenseTO> expenseTOList = expenseDAO.findByDeleteInd(GeneralUtils.NOT_DELETED);
		return convertToExpenseVOList(expenseTOList);
	}
	
	public List<String> getAllSupplier() {
		List<ExpenseTO> expenseTOList = expenseDAO.findByDeleteInd(GeneralUtils.NOT_DELETED);
		
		Set<String> supplierSet = new HashSet<String>();
		for(ExpenseTO dbObj : expenseTOList) {
			if(dbObj.getSupplier() != null && !dbObj.getSupplier().isEmpty())
				supplierSet.add(dbObj.getSupplier());
		}
		return new ArrayList<String>(supplierSet);
	}
	
	public ExpenseTO saveExpense(ExpenseVO expenseVO) {
		List<ExpenseTO> expenseTOList = convertToExpenseTOList(Arrays.asList(expenseVO));
		if(!expenseTOList.isEmpty())
			return expenseDAO.save(expenseTOList).get(0);
		return new ExpenseTO();
	}
	
	public void updateExpense(ExpenseVO vo) {
		if(vo != null && vo.getExpenseId() != null){
			ExpenseTO to = expenseDAO.findByExpenseId(vo.getExpenseId());
			to.setExpenseDate(vo.getExpenseDate());
			to.setExpenseTypeId(vo.getExpenseTypeId());
			to.setInvoiceNo(vo.getInvoiceNo());
			to.setDescription(vo.getDescription());
			to.setTotalAmt(vo.getTotalAmt());
			to.setSupplier(vo.getSupplier());
			to.setRemarks(vo.getRemarks());
			expenseDAO.save(to);
		}
	}
	
	public void deleteExpense(List<Long> idList) {
		List<ExpenseTO> expenseTOList = expenseDAO.findByExpenseIdIn(idList);
		if(expenseTOList != null && !expenseTOList.isEmpty()){
			for(ExpenseTO expenseTO : expenseTOList){
				expenseTO.setDeleteInd(GeneralUtils.DELETED);
			}
			expenseDAO.save(expenseTOList);
		}
	}
	
	public void saveExpensePayment(PaymentVO paymentVo, List<Long> expenseidList) {
		List<PaymentDetailTO> paymentDetailTOList = paymentService.genPaymentDetail(paymentVo);
		
		List<ExpenseTO> expenseList = expenseDAO.findByExpenseIdIn(expenseidList);;
		if(!expenseList.isEmpty() && !paymentDetailTOList.isEmpty()) {
			for(ExpenseTO expenseTO : expenseList) {
				for(PaymentDetailTO paymentDetailTO : paymentDetailTOList) {
					ExpensePaymentRsTO paymentRsTO = new ExpensePaymentRsTO();
					paymentRsTO.setExpenseTO(expenseTO);
					paymentRsTO.setPaymentDetailTO(paymentDetailTO);
					if(expenseTO.getExpensePaymentRsTOSet() == null) 
						expenseTO.setExpensePaymentRsTOSet(new HashSet<ExpensePaymentRsTO>());
					
					expenseTO.getExpensePaymentRsTOSet().add(paymentRsTO);
				}
				expenseTO.setStatus(ExpenseStatusEnum.PAID.toString());
				
			}
		}
		expenseDAO.save(expenseList);
	}

	private List<ExpenseVO> convertToExpenseVOList(List<ExpenseTO> toList) {
		List<ExpenseVO> expenseVOList = new ArrayList<ExpenseVO>();
		if(toList != null && !toList.isEmpty()) {
			for(ExpenseTO to : toList) {
				ExpenseVO vo = new ExpenseVO();
				vo.setExpenseId(to.getExpenseId());
				vo.setExpensetype(expenseTypeLookup.getExpenseTypeById(to.getExpenseTypeId()));
				vo.setExpenseTypeId(to.getExpenseTypeId());
				vo.setInvoiceNo(to.getInvoiceNo());
				vo.setDescription(to.getDescription());
				vo.setExpenseDate(to.getExpenseDate());
				vo.setExpensedateString(GeneralUtils.convertDateToString(to.getExpenseDate(), "dd/MM/yyyy"));
				vo.setSupplier(to.getSupplier());
				vo.setTotalAmt(to.getTotalAmt());
				String currency = "$";
				if(vo.getExpensetype().equals("Stock(China)"))
					currency = "¥";
				vo.setTotalAmtString(currency+vo.getTotalAmt());
				vo.setRemarks(to.getRemarks());
				vo.setStatus(to.getStatus());
				if(to.getExpensePaymentRsTOSet() != null)
					vo.setPaymentRsVOList(paymentService.convertToPaymentRsVOList(new ArrayList<ExpensePaymentRsTO>(to.getExpensePaymentRsTOSet())));
				expenseVOList.add(vo);
			}
		}
		return expenseVOList;
	}
	
	private List<ExpenseTO> convertToExpenseTOList(List<ExpenseVO> voList) {
		List<ExpenseTO> toList = new ArrayList<ExpenseTO>();
		if(voList != null && !voList.isEmpty()){
			List<Long> idList = GeneralUtils.convertListToLongList(voList, "expenseId", false);
			Map<Long, ExpenseTO> expenseTOMap = new HashMap<Long, ExpenseTO>();
			if(!idList.isEmpty()){
				List<ExpenseTO> expenseTOList = expenseDAO.findByExpenseIdIn(idList);
				expenseTOMap = GeneralUtils.convertListToLongMap(expenseTOList, "expenseId");
			}
			
			for(ExpenseVO vo : voList){
				ExpenseTO to = new ExpenseTO();
				ExpenseTO dbTO = expenseTOMap.get(vo.getExpenseId());
				if(dbTO != null){ //update
					to = dbTO;
				}
				to.setExpenseId(vo.getExpenseId());
				to.setExpenseTypeId(vo.getExpenseTypeId());
				to.setInvoiceNo(vo.getInvoiceNo());
				to.setDescription(vo.getDescription());
				to.setExpenseDate(vo.getExpenseDate());
				to.setSupplier(vo.getSupplier());
				to.setTotalAmt(vo.getTotalAmt());
				to.setRemarks(vo.getRemarks());
				to.setStatus(vo.getStatus());
				toList.add(to);
			}
		}
		return toList;
	}

}
