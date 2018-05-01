package com.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.admin.to.PaymentModeTO;
@Transactional(readOnly = true)
public interface PaymentModeDAO extends JpaRepository<PaymentModeTO, Long> {
	PaymentModeTO findByPaymentModeId(Long paymentModeId);
}
