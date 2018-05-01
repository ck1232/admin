package com.admin.grant.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.dao.GrantDAO;
import com.admin.helper.GeneralUtils;
import com.admin.invoice.vo.InvoiceStatusEnum;
import com.admin.invoice.vo.InvoiceVO;
import com.admin.payment.service.PaymentService;
import com.admin.payment.vo.PaymentVO;
import com.admin.to.GrantPaymentRsTO;
import com.admin.to.GrantTO;
import com.admin.to.PaymentDetailTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class GrantService {
//	private static final Logger logger = Logger.getLogger(GrantService.class);

	private GrantDAO grantDAO;
	private PaymentService paymentService;
	
	@Autowired
	public GrantService(GrantDAO grantDAO,
			PaymentService paymentService) {
		this.grantDAO = grantDAO;
		this.paymentService = paymentService;
	}
	
	public InvoiceVO findById(Long id) {
		List<InvoiceVO> invoiceVOList = findByIdList(Arrays.asList(id));
		if(invoiceVOList != null && !invoiceVOList.isEmpty()) {
			return invoiceVOList.get(0);
		}
		return new InvoiceVO();
	}
	
	public List<InvoiceVO> findByIdList(List<Long> idList){
		List<GrantTO> invoiceTOList = grantDAO.findByGrantIdIn(idList);
		return convertToInvoiceVOList(invoiceTOList);
	}

	public List<InvoiceVO> getAllGrant() {
		List<GrantTO> invoiceTOList = grantDAO.findByDeleteInd(GeneralUtils.NOT_DELETED);
		return convertToInvoiceVOList(invoiceTOList);
	}
	
	public GrantTO saveGrant(InvoiceVO grantVO) {
		List<GrantTO> grantTOList = convertToGrantTOList(Arrays.asList(grantVO));
		if(!grantTOList.isEmpty())
			return grantDAO.save(grantTOList.get(0));
		return new GrantTO();
	}
	
	public void updateGrant(InvoiceVO vo) {
		if(vo != null && vo.getGrantId() != null){
			GrantTO grantTO = grantDAO.findByGrantId(vo.getGrantId());
			grantTO.setOrganisation(vo.getMessenger());
			grantTO.setGrantDate(vo.getInvoiceDate());
			grantTO.setTotalAmt(vo.getTotalAmt());
			grantDAO.save(grantTO);
		}
	}
	
	public void deleteGrant(List<Long> idList) {
		List<GrantTO> grantTOList = grantDAO.findByGrantIdIn(idList);
		if(grantTOList != null && !grantTOList.isEmpty()){
			for(GrantTO grantTO : grantTOList){
				grantTO.setDeleteInd(GeneralUtils.DELETED);
			}
			grantDAO.save(grantTOList);
		}
	}
	
	public void saveGrantPayment(PaymentVO paymentVo, List<Long> grantidList) {
		List<PaymentDetailTO> paymentDetailTOList = paymentService.genPaymentDetail(paymentVo);
		
		List<GrantTO> grantList = grantDAO.findByGrantIdIn(grantidList);
		if(!grantList.isEmpty() && !paymentDetailTOList.isEmpty()) {
			for(GrantTO grantTO : grantList) {
				for(PaymentDetailTO paymentDetailTO : paymentDetailTOList) {
					GrantPaymentRsTO paymentRsTO = new GrantPaymentRsTO();
					paymentRsTO.setGrantTO(grantTO);
					paymentRsTO.setPaymentDetailTO(paymentDetailTO);
					if(grantTO.getGrantPaymentRsTOSet() == null) 
						grantTO.setGrantPaymentRsTOSet(new HashSet<GrantPaymentRsTO>());
					
					grantTO.getGrantPaymentRsTOSet().add(paymentRsTO);
				}
				grantTO.setStatus(InvoiceStatusEnum.PAID.toString());
			}
		}
		grantDAO.save(grantList);
	}
	
	private List<InvoiceVO> convertToInvoiceVOList(List<GrantTO> toList) {
		List<InvoiceVO> voList = new ArrayList<InvoiceVO>();
		if(toList != null && !toList.isEmpty()) {
			for(GrantTO to : toList) {
				InvoiceVO vo = new InvoiceVO();
				vo.setGrantId(to.getGrantId());
				vo.setMessenger(to.getOrganisation());
				vo.setInvoiceDate(to.getGrantDate());
				vo.setInvoicedateString(GeneralUtils.convertDateToString(to.getGrantDate(), "dd/MM/yyyy"));
				vo.setTotalAmt(to.getTotalAmt());
				vo.setTotalAmtString("$"+to.getTotalAmt());
				vo.setStatus(to.getStatus());
				vo.setType(GeneralUtils.MODULE_GRANT);
				if(to.getGrantPaymentRsTOSet() != null && !to.getGrantPaymentRsTOSet().isEmpty())
					vo.setPaymentRsVOList(paymentService.convertToPaymentRsVOList(new ArrayList<GrantPaymentRsTO>(to.getGrantPaymentRsTOSet())));
				voList.add(vo);
			}
		}
		return voList;
	}
	
	public List<GrantTO> convertToGrantTOList(List<InvoiceVO> voList) {
		List<GrantTO> toList = new ArrayList<GrantTO>();
		if(voList != null && !voList.isEmpty()){
			List<Long> idList = GeneralUtils.convertListToLongList(voList, "grantId", false);
			Map<Long, GrantTO> grantTOMap = new HashMap<Long, GrantTO>();
			if(!idList.isEmpty()){
				List<GrantTO> grantTOList = grantDAO.findByGrantIdIn(idList);
				grantTOMap = GeneralUtils.convertListToLongMap(grantTOList, "grantId");
			}
			
			for(InvoiceVO vo : voList){
				GrantTO to = new GrantTO();
				GrantTO dbTO = grantTOMap.get(vo.getGrantId());
				if(dbTO != null){ //update
					to = dbTO;
				}
				to.setOrganisation(vo.getMessenger());
				to.setGrantDate(GeneralUtils.convertStringToDate(vo.getInvoicedateString(), "dd/MM/yyyy"));
				to.setTotalAmt(vo.getTotalAmt());
				to.setStatus(vo.getStatus());
				toList.add(to);
			}
		}
		return toList;
	}
}
