package com.admin.cheque.vo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.cheque.vo.ChequeVO;
import com.admin.dao.ChequeDAO;
import com.admin.dao.PaymentDetailDAO;
import com.admin.expense.service.ExpenseService;
import com.admin.grant.service.GrantService;
import com.admin.helper.GeneralUtils;
import com.admin.invoice.service.InvoiceService;
import com.admin.payment.vo.PaymentVO;
import com.admin.salarybonus.service.SalaryBonusService;
import com.admin.to.BonusPaymentRsTO;
import com.admin.to.ChequeTO;
import com.admin.to.ExpensePaymentRsTO;
import com.admin.to.GrantPaymentRsTO;
import com.admin.to.InvoicePaymentRsTO;
import com.admin.to.PaymentDetailTO;
import com.admin.to.PaymentRsTO;
import com.admin.to.SalaryPaymentRsTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class ChequeService {
	
	private ChequeDAO chequeDAO;
	private PaymentDetailDAO paymentDetailDAO;
	private ExpenseService expenseService;
	private InvoiceService invoiceService;
	private GrantService grantService;
	private SalaryBonusService salaryBonusService;
	private ChequeConverter chequeConverter;
	@Autowired
	public ChequeService(ChequeDAO chequeDAO, 
			PaymentDetailDAO paymentDetailDAO,
			ExpenseService expenseService,
			InvoiceService invoiceService,
			GrantService grantService,
			SalaryBonusService salaryBonusService,
			ChequeConverter chequeConverter) {
		this.chequeDAO = chequeDAO;
		this.paymentDetailDAO = paymentDetailDAO;
		this.expenseService = expenseService;
		this.invoiceService = invoiceService;
		this.grantService = grantService;
		this.salaryBonusService = salaryBonusService;
		this.chequeConverter = chequeConverter;
	}
	
	public ChequeVO findById(Long id) {
		List<ChequeVO> chequeVOList = findByIdList(Arrays.asList(id));
		if(chequeVOList != null && !chequeVOList.isEmpty()) {
			return chequeVOList.get(0);
		}
		return new ChequeVO();
	}
	
	public List<ChequeVO> findByIdList(List<Long> idList){
		List<ChequeTO> chequeTOList = chequeDAO.findByChequeIdIn(idList);
		return chequeConverter.convertToChequeVOList(chequeTOList);
	}
	
	public List<ChequeVO> getChequeList() {
		List<ChequeVO> voList = new ArrayList<ChequeVO>();
		List<PaymentDetailTO> paymentDetailTOList = paymentDetailDAO.findByChequeTOIsNotNull();
		if(paymentDetailTOList != null && !paymentDetailTOList.isEmpty()) {
			for(PaymentDetailTO payment : paymentDetailTOList) {
				ChequeVO vo = chequeConverter.convertToChequeVOList(Arrays.asList(payment.getChequeTO())).get(0);
				PaymentRsTO paymentRs = payment.getPaymentRsTOSet().iterator().next();
				if(paymentRs instanceof ExpensePaymentRsTO) 
					vo.setPaidFor(GeneralUtils.MODULE_EXPENSE);
				else if(paymentRs instanceof InvoicePaymentRsTO) 
					vo.setPaidFor(GeneralUtils.MODULE_INVOICE);
				else if(paymentRs instanceof GrantPaymentRsTO) 
					vo.setPaidFor(GeneralUtils.MODULE_GRANT);
				else if(paymentRs instanceof SalaryPaymentRsTO) 
					vo.setPaidFor(GeneralUtils.MODULE_SALARY);
				else if(paymentRs instanceof BonusPaymentRsTO) 
					vo.setPaidFor(GeneralUtils.MODULE_BONUS);
				voList.add(vo);
			}
		}
		return voList;
	}
	
	public List<PaymentDetailTO> getPaymentDetailListByChequeId(Long chequeId) {
		return paymentDetailDAO.findByChequeTO_chequeId(chequeId);
	}
	
	public Date getEarliestChequeDate(ChequeVO chequeVo) {
		Date firstdate = null;
		Date date = null;
		List<PaymentDetailTO> paymentDetailTOList = getPaymentDetailListByChequeId(chequeVo.getChequeId());
		if(paymentDetailTOList != null && !paymentDetailTOList.isEmpty()) {
			for(PaymentDetailTO payment : paymentDetailTOList) {
				ChequeVO vo = chequeConverter.convertToChequeVOList(Arrays.asList(payment.getChequeTO())).get(0);
				PaymentRsTO paymentRs = payment.getPaymentRsTOSet().iterator().next();
				if(paymentRs instanceof ExpensePaymentRsTO) {
					vo.setPaidFor(GeneralUtils.MODULE_EXPENSE);
					date = ((ExpensePaymentRsTO) paymentRs).getExpenseTO().getExpenseDate();
				} else if(paymentRs instanceof InvoicePaymentRsTO) {
					vo.setPaidFor(GeneralUtils.MODULE_INVOICE);
					date = ((InvoicePaymentRsTO) paymentRs).getInvoiceTO().getInvoiceDate();
				} else if(paymentRs instanceof GrantPaymentRsTO) {
					vo.setPaidFor(GeneralUtils.MODULE_GRANT);
					date = ((GrantPaymentRsTO) paymentRs).getGrantTO().getGrantDate();
				} else if(paymentRs instanceof SalaryPaymentRsTO) {
					vo.setPaidFor(GeneralUtils.MODULE_SALARY);
					date = ((SalaryPaymentRsTO) paymentRs).getSalaryTO().getSalaryDate();
				} else if(paymentRs instanceof BonusPaymentRsTO) {
					vo.setPaidFor(GeneralUtils.MODULE_BONUS);
					date = ((BonusPaymentRsTO) paymentRs).getBonusTO().getBonusDate();
				}
				if(firstdate == null || date.before(firstdate)) {
					firstdate = date;
				}
			}
		}
		return firstdate;
	}
	
	public void updateCheque(ChequeVO vo) {
		if(vo != null && vo.getChequeId() != null){
			ChequeTO to = chequeDAO.findByChequeId(vo.getChequeId());
			to.setChequeNum(vo.getChequeNum());
			to.setChequeDate(vo.getChequeDate());
			chequeDAO.save(to);
		}
	}
	
	public void updateToBounceCheque(List<Long> idList, Date bounceDate) {
		if(idList != null && !idList.isEmpty()) {
			for(Long id : idList) {
				ChequeTO to = chequeDAO.findByChequeId(id);
				to.setBounceChequeInd(GeneralUtils.YES_IND);
				to.setBounceDate(bounceDate);
				chequeDAO.save(to);
			}
		}
	}
	
	public void saveBounceCheque(PaymentVO paymentVo, List<Long> chequeIdList, List<PaymentRsTO> paymentRsList) {
		//chequeIdList - to be bounce cheque
		updateToBounceCheque(chequeIdList, paymentVo.getBounceDate());
		//paymentRsList - to find invoice,expense,salary,bonus involved in bounced cheque
		for(PaymentRsTO paymentRs : paymentRsList) {
			if(paymentRs instanceof ExpensePaymentRsTO) 
				expenseService.saveExpensePaymentByExpense(paymentVo, Arrays.asList(((ExpensePaymentRsTO) paymentRs).getExpenseTO()));
			else if(paymentRs instanceof InvoicePaymentRsTO)
				invoiceService.saveInvoicePaymentByInvoice(paymentVo, Arrays.asList(((InvoicePaymentRsTO) paymentRs).getInvoiceTO()));
			else if(paymentRs instanceof GrantPaymentRsTO)
				grantService.saveGrantPaymentByGrant(paymentVo, Arrays.asList(((GrantPaymentRsTO) paymentRs).getGrantTO()));
			else if(paymentRs instanceof SalaryPaymentRsTO)
				salaryBonusService.saveSalaryPaymentBySalary(paymentVo, Arrays.asList(((SalaryPaymentRsTO) paymentRs).getSalaryTO()));
			else if(paymentRs instanceof BonusPaymentRsTO)
				salaryBonusService.saveBonusPaymentByBonus(paymentVo, Arrays.asList(((BonusPaymentRsTO) paymentRs).getBonusTO()));
		}
	}
}
