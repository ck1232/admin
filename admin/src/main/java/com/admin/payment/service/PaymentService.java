package com.admin.payment.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.cheque.vo.ChequeVO;
import com.admin.cheque.vo.service.ChequeService;
import com.admin.dao.ChequeDAO;
import com.admin.dao.GrantPaymentRsDAO;
import com.admin.dao.InvoicePaymentRsDAO;
import com.admin.dao.PaymentDetailDAO;
import com.admin.helper.GeneralUtils;
import com.admin.payment.controller.PaymentController;
import com.admin.payment.lookup.controller.PaymentModeLookup;
import com.admin.payment.vo.PaymentDetailVO;
import com.admin.payment.vo.PaymentRsVO;
import com.admin.payment.vo.PaymentVO;
import com.admin.to.ChequeTO;
import com.admin.to.GrantPaymentRsTO;
import com.admin.to.InvoicePaymentRsTO;
import com.admin.to.PaymentDetailTO;
import com.admin.to.PaymentRsTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class PaymentService {
	
	private PaymentModeLookup paymentModeLookup;
	private InvoicePaymentRsDAO invoicePaymentRsDAO;
	private GrantPaymentRsDAO grantPaymentRsDAO;
	private PaymentDetailDAO paymentDetailDAO;
	private ChequeDAO chequeDAO;
	private ChequeService chequeService;
	
	@Autowired
	public PaymentService(PaymentModeLookup paymentModeLookup,
			InvoicePaymentRsDAO invoicePaymentRsDAO,
			GrantPaymentRsDAO grantPaymentRsDAO,
			PaymentDetailDAO paymentDetailDAO,
			ChequeDAO chequeDAO,
			ChequeService chequeService) {
		this.paymentModeLookup = paymentModeLookup;
		this.invoicePaymentRsDAO = invoicePaymentRsDAO;
		this.grantPaymentRsDAO = grantPaymentRsDAO;
		this.paymentDetailDAO = paymentDetailDAO;
		this.chequeDAO = chequeDAO;
		this.chequeService = chequeService;
	}
	
	public List<PaymentRsVO> findByPaymentRsIdList(List<Long> paymentRsIdList, String type) {
		List<PaymentRsVO> paymentRsVOList = new ArrayList<PaymentRsVO>();
		if(type.equals(GeneralUtils.MODULE_INVOICE)) {
			List<InvoicePaymentRsTO> invoicePaymentRsTOList = invoicePaymentRsDAO.findByPaymentRsIdIn(paymentRsIdList);
			paymentRsVOList = convertToPaymentRsVOList(invoicePaymentRsTOList);
		}else if(type.equals(GeneralUtils.MODULE_GRANT)) {
			List<GrantPaymentRsTO> grantPaymentRsTOList = grantPaymentRsDAO.findByPaymentRsIdIn(paymentRsIdList);
			paymentRsVOList = convertToPaymentRsVOList(grantPaymentRsTOList);
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
			paymentDetailVO.setChequeVO(chequeService.convertToChequeVOList(Arrays.asList(chequeTO)).get(0));
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
					List<ChequeVO> chequeVOList = chequeService.convertToChequeVOList(Arrays.asList(to.getChequeTO()));
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
					List<ChequeTO> chequeTOList = chequeService.convertToChequeTOList(Arrays.asList(vo.getChequeVO()));
					if(!chequeTOList.isEmpty())
						to.setChequeTO(chequeTOList.get(0));
				}
				toList.add(to);
			}
		}
		return toList;
	}

	
}
