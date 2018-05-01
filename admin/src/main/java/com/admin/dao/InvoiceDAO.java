package com.admin.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.InvoiceTO;
@Transactional(readOnly = true)
public interface InvoiceDAO extends BaseDAO<InvoiceTO> {
	InvoiceTO findByInvoiceId(Long invoiceId);
	List<InvoiceTO> findByInvoiceIdIn(List<Long> invoiceIdList);
}
