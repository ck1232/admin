package com.admin.dao;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.admin.to.InvoiceTO;
@Transactional(readOnly = true)
public interface InvoiceDAO extends BaseDAO<InvoiceTO> {
	InvoiceTO findByInvoiceId(Long invoiceId);
	List<InvoiceTO> findByInvoiceIdIn(List<Long> invoiceIdList);
	List<InvoiceTO> findByInvoiceDateBetweenAndStatusAndDeleteInd(Date dateFrom, Date dateTill, String status, String deleteInd);
	List<InvoiceTO> findByInvoiceDateBetweenAndDeleteInd(Date dateFrom, Date dateTill, String deleteInd);
}
