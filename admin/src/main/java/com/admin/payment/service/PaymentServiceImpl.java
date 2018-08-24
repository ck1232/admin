package com.admin.payment.service;

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
import com.admin.cheque.vo.service.ChequeConverter;
import com.admin.dao.BonusPaymentRsDAO;
import com.admin.dao.ChequeDAO;
import com.admin.dao.ExpensePaymentRsDAO;
import com.admin.dao.GenericPaymentRsDAO;
import com.admin.dao.GrantPaymentRsDAO;
import com.admin.dao.InvoicePaymentRsDAO;
import com.admin.dao.PaymentDetailDAO;
import com.admin.dao.SalaryPaymentRsDAO;
import com.admin.helper.GeneralUtils;
import com.admin.payment.controller.PaymentController;
import com.admin.payment.lookup.controller.PaymentModeLookup;
import com.admin.payment.vo.PaymentDetailVO;
import com.admin.payment.vo.PaymentRsVO;
import com.admin.payment.vo.PaymentVO;
import com.admin.to.BonusPaymentRsTO;
import com.admin.to.ChequeTO;
import com.admin.to.ExpensePaymentRsTO;
import com.admin.to.GenericPaymentRsTO;
import com.admin.to.GrantPaymentRsTO;
import com.admin.to.InvoicePaymentRsTO;
import com.admin.to.PaymentDetailTO;
import com.admin.to.PaymentRsTO;
import com.admin.to.SalaryPaymentRsTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class PaymentServiceImpl implements PaymentService{
	
	private PaymentModeLookup paymentModeLookup;
	private InvoicePaymentRsDAO invoicePaymentRsDAO;
	private GrantPaymentRsDAO grantPaymentRsDAO;
	private ExpensePaymentRsDAO expensePaymentRsDAO;
	private SalaryPaymentRsDAO salaryPaymentRsDAO;
	private BonusPaymentRsDAO bonusPaymentRsDAO;
	private PaymentDetailDAO paymentDetailDAO;
	private ChequeConverter chequeConverter;
	private ChequeDAO chequeDAO;
	private GenericPaymentRsDAO genericPaymentRsDAO;
	
	@Autowired
	public PaymentServiceImpl(PaymentModeLookup paymentModeLookup,
			InvoicePaymentRsDAO invoicePaymentRsDAO,
			GrantPaymentRsDAO grantPaymentRsDAO,
			ExpensePaymentRsDAO expensePaymentRsDAO,
			SalaryPaymentRsDAO salaryPaymentRsDAO,
			BonusPaymentRsDAO bonusPaymentRsDAO,
			PaymentDetailDAO paymentDetailDAO,
			GenericPaymentRsDAO genericPaymentRsDAO,
			ChequeDAO chequeDAO,
			ChequeConverter chequeConverter) {
		this.paymentModeLookup = paymentModeLookup;
		this.invoicePaymentRsDAO = invoicePaymentRsDAO;
		this.grantPaymentRsDAO = grantPaymentRsDAO;
		this.expensePaymentRsDAO = expensePaymentRsDAO;
		this.salaryPaymentRsDAO = salaryPaymentRsDAO;
		this.bonusPaymentRsDAO = bonusPaymentRsDAO;
		this.paymentDetailDAO = paymentDetailDAO;
		this.chequeDAO = chequeDAO;
		this.chequeConverter = chequeConverter;
		this.genericPaymentRsDAO = genericPaymentRsDAO;
	}
	
	public List<PaymentRsVO> findByPaymentRsIdList(List<Long> paymentRsIdList, String type) {
		List<PaymentRsVO> paymentRsVOList = new ArrayList<PaymentRsVO>();
		if(type.equals(GeneralUtils.MODULE_INVOICE)) {
			List<InvoicePaymentRsTO> invoicePaymentRsTOList = invoicePaymentRsDAO.findByPaymentRsIdIn(paymentRsIdList);
			paymentRsVOList = convertToPaymentRsVOList(invoicePaymentRsTOList);
		}else if(type.equals(GeneralUtils.MODULE_GRANT)) {
			List<GrantPaymentRsTO> grantPaymentRsTOList = grantPaymentRsDAO.findByPaymentRsIdIn(paymentRsIdList);
			paymentRsVOList = convertToPaymentRsVOList(grantPaymentRsTOList);
		}else if(type.equals(GeneralUtils.MODULE_EXPENSE)) {
			List<ExpensePaymentRsTO> expensePaymentRsTOList = expensePaymentRsDAO.findByPaymentRsIdIn(paymentRsIdList);
			paymentRsVOList = convertToPaymentRsVOList(expensePaymentRsTOList);
		}else if(type.equals(GeneralUtils.MODULE_SALARY)) {
			List<SalaryPaymentRsTO> salaryPaymentRsTOList = salaryPaymentRsDAO.findByPaymentRsIdIn(paymentRsIdList);
			paymentRsVOList = convertToPaymentRsVOList(salaryPaymentRsTOList);
		}else if(type.equals(GeneralUtils.MODULE_BONUS)) {
			List<BonusPaymentRsTO> bonusPaymentRsTOList = bonusPaymentRsDAO.findByPaymentRsIdIn(paymentRsIdList);
			paymentRsVOList = convertToPaymentRsVOList(bonusPaymentRsTOList);
		}
		return paymentRsVOList; 
	}
	
	public List<PaymentDetailTO> savePaymentDetailList(List<PaymentDetailVO> voList) {
		if(voList != null && !voList.isEmpty()) {
			List<PaymentDetailTO> toList = convertToPaymentDetailTOList(voList);
			toList = paymentDetailDAO.save(toList);
			return toList;
		}
		return new ArrayList<PaymentDetailTO>();
	}
	
	public List<PaymentDetailTO> savePaymentDetailListTO(List<PaymentDetailTO> toList) {
		if(toList != null && !toList.isEmpty()) {
			toList = paymentDetailDAO.save(toList);
			return toList;
		}
		return new ArrayList<PaymentDetailTO>();
	}
	
	public List<PaymentDetailTO> genPaymentDetail(PaymentVO paymentVo) {
		List<PaymentDetailVO> paymentDetailList = new ArrayList<PaymentDetailVO>();
		if(paymentVo.getPaymentmodecash()){
			PaymentDetailVO paymentDetailVO = this.convertPaymentToPaymentDetailVOList(Arrays.asList(paymentVo), GeneralUtils.PAYMENT_MODE_CASH).get(0);
			paymentDetailList.add(paymentDetailVO);
		}
		
		if(paymentVo.getPaymentmodecheque()){
			ChequeTO chequeTO = convertPaymentToChequeTOList(Arrays.asList(paymentVo)).get(0);
			chequeTO = chequeDAO.save(chequeTO);
			PaymentDetailVO paymentDetailVO = this.convertPaymentToPaymentDetailVOList(Arrays.asList(paymentVo), GeneralUtils.PAYMENT_MODE_CHEQUE).get(0);
			paymentDetailVO.setChequeVO(chequeConverter.convertToChequeVOList(Arrays.asList(chequeTO)).get(0));
			paymentDetailList.add(paymentDetailVO);
		}
		if(PaymentController.outMoneyModuleList.contains(paymentVo.getType()) && paymentVo.getPaymentmodedirector()){
			PaymentDetailVO paymentDetailVO = this.convertPaymentToPaymentDetailVOList(Arrays.asList(paymentVo), GeneralUtils.PAYMENT_MODE_BY_DIRECTOR).get(0);
			paymentDetailList.add(paymentDetailVO);
		}
		if(PaymentController.inMoneyModuleList.contains(paymentVo.getType()) && paymentVo.getPaymentmodegiro()){
			PaymentDetailVO paymentDetailVO = this.convertPaymentToPaymentDetailVOList(Arrays.asList(paymentVo), GeneralUtils.PAYMENT_MODE_GIRO).get(0);
			paymentDetailList.add(paymentDetailVO);
		}
		
		if(PaymentController.inMoneyModuleList.contains(paymentVo.getType()) && paymentVo.getPaymentmodePayToDirector()){
			PaymentDetailVO paymentDetailVO = this.convertPaymentToPaymentDetailVOList(Arrays.asList(paymentVo), GeneralUtils.PAYMENT_MODE_TO_DIRECTOR).get(0);
			paymentDetailList.add(paymentDetailVO);
		}
		
		if(!paymentDetailList.isEmpty()) {
			return savePaymentDetailList(paymentDetailList);
		}
		return new ArrayList<PaymentDetailTO>();
	}
	
	
	public List<PaymentRsVO> convertToPaymentRsVOList(List<? extends PaymentRsTO> toList) {
		List<PaymentRsVO> voList = new ArrayList<PaymentRsVO>();
		if(toList != null && !toList.isEmpty()) {
			for(PaymentRsTO to : toList) {
				PaymentRsVO vo = new PaymentRsVO();
				vo.setPaymentRsId(to.getPaymentRsId());
				if(to instanceof InvoicePaymentRsTO) {
					InvoicePaymentRsTO invoicePaymentRsTO = (InvoicePaymentRsTO)to;
					vo.setReferenceType(GeneralUtils.MODULE_INVOICE);
					vo.setReferenceId(invoicePaymentRsTO.getInvoiceTO().getInvoiceId());
				}else if(to instanceof GrantPaymentRsTO) {
					GrantPaymentRsTO grantPaymentRsTO = (GrantPaymentRsTO)to;
					vo.setReferenceType(GeneralUtils.MODULE_INVOICE);
					vo.setReferenceId(grantPaymentRsTO.getGrantTO().getGrantId());
				}else if(to instanceof ExpensePaymentRsTO) {
					ExpensePaymentRsTO expensePaymentRsTO = (ExpensePaymentRsTO)to;
					vo.setReferenceType(GeneralUtils.MODULE_EXPENSE);
					vo.setReferenceId(expensePaymentRsTO.getExpenseTO().getExpenseId());
				}else if(to instanceof SalaryPaymentRsTO) {
					SalaryPaymentRsTO salaryPaymentRsTO = (SalaryPaymentRsTO)to;
					vo.setReferenceType(GeneralUtils.MODULE_SALARY);
					vo.setReferenceId(salaryPaymentRsTO.getSalaryTO().getSalaryId());
				}else if(to instanceof BonusPaymentRsTO) {
					BonusPaymentRsTO bonusPaymentRsTO = (BonusPaymentRsTO)to;
					vo.setReferenceType(GeneralUtils.MODULE_BONUS);
					vo.setReferenceId(bonusPaymentRsTO.getBonusTO().getBonusId());
				}
				List<PaymentDetailVO> paymentDetailVOList = convertToPaymentDetailVOList(Arrays.asList(to.getPaymentDetailTO()));
				if(!paymentDetailVOList.isEmpty())
					vo.setPaymentDetailVO(paymentDetailVOList.get(0));
				voList.add(vo);
			}
		}
		return voList;
	}

	
//	public List<SubModulePermissionTO> convertToSubmodulePermissionTOList(List<SubModulePermissionVO> voList, SubModuleTO submoduleTO) {
//		List<SubModulePermissionTO> toList = new ArrayList<SubModulePermissionTO>();
//		if(voList != null && !voList.isEmpty()) {
//			Map<Long, SubModulePermissionTO> submodulePermissionTOMap = GeneralUtils.convertListToLongMap(new ArrayList<SubModulePermissionTO>(submoduleTO.getSubModulePermissionSet()), "permissionId");
//			for(SubModulePermissionVO vo : voList) {
//				SubModulePermissionTO to = new SubModulePermissionTO();
//				SubModulePermissionTO dbTO = submodulePermissionTOMap.get(vo.getPermissionId());
//				if(dbTO != null)  //update
//					to = dbTO;
//				to.setPermissionId(vo.getPermissionId());
//				to.setRoleTO(roleDAO.findByRoleId(vo.getRoleId()));
//				to.setPermissionType(permissionTypeDAO.findByTypeId(vo.getPermissionTypeId()));
//				to.setSubModuleTO(submoduleTO);
//				toList.add(to);
//			}
//		}
//		return toList;
//		
//	}
	
	private List<PaymentDetailVO> convertToPaymentDetailVOList(List<PaymentDetailTO> toList) {
		List<PaymentDetailVO> voList = new ArrayList<PaymentDetailVO>();
		if(toList != null && !toList.isEmpty()) {
			for(PaymentDetailTO to : toList) {
				PaymentDetailVO vo = new PaymentDetailVO();
				vo.setPaymentDetailId(to.getPaymentDetailId());
				vo.setPaymentDate(to.getPaymentDate());
				vo.setPaymentDateString(GeneralUtils.convertDateToString(to.getPaymentDate(), GeneralUtils.STANDARD_DATE_FORMAT));
				vo.setPaymentMode(to.getPaymentMode());
				vo.setPaymentModeString(paymentModeLookup.getPaymentModeById(to.getPaymentMode()));
				vo.setPaymentAmt(to.getPaymentAmt());
				vo.setPaymentAmtString("$"+ to.getPaymentAmt());
				if(to.getChequeTO() != null) {
					List<ChequeVO> chequeVOList = chequeConverter.convertToChequeVOList(Arrays.asList(to.getChequeTO()));
					if(!chequeVOList.isEmpty())
						vo.setChequeVO(chequeVOList.get(0));
				}
				List<Long> idList = new ArrayList<Long>();
				if(!to.getPaymentRsTOSet().isEmpty())
				for(PaymentRsTO paymentRsTO: to.getPaymentRsTOSet()) {
					idList.add(paymentRsTO.getPaymentRsId());
				}
				vo.setPaymentRsIdList(idList);
				voList.add(vo);
			}
		}
		return voList;
	}
	
	public List<PaymentDetailVO> convertPaymentToPaymentDetailVOList(List<PaymentVO> voList, String paymentType) {
		List<PaymentDetailVO> paymentDetailVOList = new ArrayList<PaymentDetailVO>();
		if(voList != null && !voList.isEmpty()){
			for(PaymentVO vo : voList){
				PaymentDetailVO paymentDetailVO = new PaymentDetailVO();
				if(paymentType.equals(GeneralUtils.PAYMENT_MODE_CASH)) 
					paymentDetailVO.setPaymentAmt(vo.getCashamount());
				
				else if(paymentType.equals(GeneralUtils.PAYMENT_MODE_CHEQUE)) 
					paymentDetailVO.setPaymentAmt(vo.getChequeamount());
				
				else if(paymentType.equals(GeneralUtils.PAYMENT_MODE_BY_DIRECTOR)) 
					paymentDetailVO.setPaymentAmt(vo.getDirectoramount());
				
				else if(paymentType.equals(GeneralUtils.PAYMENT_MODE_TO_DIRECTOR)) 
					paymentDetailVO.setPaymentAmt(vo.getPaytodirectoramount());
				
				else if(paymentType.equals(GeneralUtils.PAYMENT_MODE_GIRO)) 
					paymentDetailVO.setPaymentAmt(vo.getGiroamount());
				
				paymentDetailVO.setPaymentDate(vo.getPaymentDate());
				paymentDetailVO.setPaymentMode(paymentModeLookup.getPaymentModeByValueMap().get(paymentType).getPaymentModeId());
				paymentDetailVOList.add(paymentDetailVO);
			}
		}
		return paymentDetailVOList;
	}
	
	public List<ChequeTO> convertPaymentToChequeTOList(List<PaymentVO> voList) {
		List<ChequeTO> toList = new ArrayList<ChequeTO>();
		if(voList != null && !voList.isEmpty()){
			for(PaymentVO vo : voList){
				ChequeTO to = new ChequeTO();
				to.setChequeNum(vo.getChequeno());
				to.setChequeDate(vo.getChequedate());
				to.setChequeAmt(vo.getChequeamount());
				to.setBounceChequeInd(GeneralUtils.UNBOUNCED);
				toList.add(to);
			}
		}
		return toList;
	}
	
	public List<PaymentDetailTO> convertToPaymentDetailTOList(List<PaymentDetailVO> voList) {
		List<PaymentDetailTO> toList = new ArrayList<PaymentDetailTO>();
		if(voList != null && !voList.isEmpty()){
			for(PaymentDetailVO vo : voList){
				PaymentDetailTO to = new PaymentDetailTO();
				to.setPaymentDate(vo.getPaymentDate());
				to.setPaymentMode(vo.getPaymentMode());
				to.setPaymentAmt(vo.getPaymentAmt());
				if(vo.getChequeVO() != null) {
					List<ChequeTO> chequeTOList = chequeConverter.convertToChequeTOList(Arrays.asList(vo.getChequeVO()));
					if(!chequeTOList.isEmpty())
						to.setChequeTO(chequeTOList.get(0));
				}
				toList.add(to);
			}
		}
		return toList;
	}

	@Override
	public List<PaymentDetailVO> getAllPaymentByRefTypeAndRefId(String type, Long id, Date dateAsOf, Date endDate) {
		List<PaymentDetailTO> list = new ArrayList<PaymentDetailTO>();
		List<GenericPaymentRsTO> paymentRsList = genericPaymentRsDAO.findByReferenceTypeAndReferenceIdAndPaymentDetailTO_PaymentDateBetweenAndDeleteInd(type, id, dateAsOf, endDate, GeneralUtils.NOT_DELETED);
		if(paymentRsList != null && !paymentRsList.isEmpty()) {
			for(GenericPaymentRsTO to : paymentRsList) {
				PaymentDetailTO detail = to.getPaymentDetailTO();
				if(detail != null) {
					list.add(detail);
				}
			}
		}
		return convertToPaymentDetailVOList(list);
	}

	@Override
	public List<PaymentDetailVO> getAllPaymentByRefTypeAndRefId(String type, Long id) {
		List<PaymentDetailTO> list = new ArrayList<PaymentDetailTO>();
		List<GenericPaymentRsTO> paymentRsList = genericPaymentRsDAO.findByReferenceTypeAndReferenceIdAndDeleteInd(type, id, GeneralUtils.NOT_DELETED);
		if(paymentRsList != null && !paymentRsList.isEmpty()) {
			for(GenericPaymentRsTO to : paymentRsList) {
				PaymentDetailTO detail = to.getPaymentDetailTO();
				if(detail != null) {
					list.add(detail);
				}
			}
		}
		return convertToPaymentDetailVOList(list);
	}

	
}
