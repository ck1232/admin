package com.admin.payment.lookup.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.dao.PaymentModeDAO;
import com.admin.payment.lookup.vo.PaymentModeVO;
import com.admin.to.PaymentModeTO;


@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class PaymentModeService {
	private PaymentModeDAO paymentModeDAO;

	@Autowired
	public PaymentModeService(PaymentModeDAO paymentModeDAO) {
		super();
		this.paymentModeDAO = paymentModeDAO;
	}
	public List<PaymentModeVO> getPaymentMode(){
		List<PaymentModeTO> paymentModeTOList = paymentModeDAO.findAll();
		return convertToPaymentModeVOList(paymentModeTOList);
	}

	private List<PaymentModeVO> convertToPaymentModeVOList(List<PaymentModeTO> toList) {
		List<PaymentModeVO> voList = new ArrayList<PaymentModeVO>();
		if(toList != null && !toList.isEmpty()) {
			for(PaymentModeTO to : toList) {
				PaymentModeVO vo = new PaymentModeVO();
				vo.setPaymentMode(to.getPaymentMode());
				vo.setPaymentModeId(to.getPaymentModeId());
				voList.add(vo);
			}
		}
		return voList;
	}
	
}
