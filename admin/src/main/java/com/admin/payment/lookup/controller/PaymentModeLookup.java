package com.admin.payment.lookup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.admin.payment.lookup.service.PaymentModeService;
import com.admin.payment.lookup.vo.PaymentModeVO;

@Lazy
@Component
public class PaymentModeLookup {
	private static final Logger logger = Logger.getLogger(PaymentModeLookup.class);
	private PaymentModeService paymentModeService;
	private List<PaymentModeVO> paymentModeList;
	private Map<String, PaymentModeVO> paymentModeByValueMap = new HashMap<String, PaymentModeVO>();
	private Map<Long, PaymentModeVO> paymentModeByIdMap = new HashMap<Long, PaymentModeVO>();
	
	@Autowired
	public PaymentModeLookup(PaymentModeService paymentModeService){
		logger.debug("PaymentModeLookup initialized");
		this.paymentModeService = paymentModeService;
		load();
	}
	private void load() {
		paymentModeList = paymentModeService.getPaymentMode();
		if(paymentModeList != null){
			for(PaymentModeVO paymentMode : paymentModeList){
				paymentModeByValueMap.put(paymentMode.getPaymentMode(), paymentMode);
				paymentModeByIdMap.put(paymentMode.getPaymentModeId(), paymentMode);
			}
		}
	}
	public List<PaymentModeVO> getPaymentModeList() {
		return paymentModeList;
	}
	public void setPaymentModeList(List<PaymentModeVO> paymentModeList) {
		this.paymentModeList = paymentModeList;
	}
	public Map<String, PaymentModeVO> getPaymentModeByValueMap() {
		return paymentModeByValueMap;
	}
	public void setPaymentModeByValueMap(
			Map<String, PaymentModeVO> paymentModeByValueMap) {
		this.paymentModeByValueMap = paymentModeByValueMap;
	}
	public Map<Long, PaymentModeVO> getPaymentModeByIdMap() {
		return paymentModeByIdMap;
	}
	public void setPaymentModeByIdMap(Map<Long, PaymentModeVO> paymentModeByIdMap) {
		this.paymentModeByIdMap = paymentModeByIdMap;
	}
	
	public String getPaymentModeById(Long id){
		return paymentModeByIdMap.get(id).getPaymentMode();
	}
}
