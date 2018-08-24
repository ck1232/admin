package com.admin.invoice.service;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.admin.invoice.vo.InvoiceSearchCriteria;
import com.admin.invoice.vo.InvoiceUploadVO;
import com.admin.invoice.vo.InvoiceVO;
import com.admin.payment.vo.PaymentVO;
import com.admin.to.InvoiceTO;

public interface InvoiceService {
	InvoiceVO findById(Long id);
	List<InvoiceVO> findByIdList(List<Long> idList);
	List<InvoiceVO> getAllInvoice();
	List<InvoiceVO> searchInvoice(InvoiceSearchCriteria searchCriteria);
	void saveInvoice(InvoiceVO invoiceVO);
	void updateInvoice(InvoiceVO vo);
	void updateBadDebt(List<Long> idList);
	void deleteInvoice(List<Long> idList);
	String getStatementPeriod(InvoiceSearchCriteria searchCriteria);
	HSSFWorkbook writeToFile(File inputfile, List<InvoiceVO> invoiceList, String statementPeriod);
	int saveInvoiceFromUploadFile(InvoiceUploadVO invoice);
	void saveInvoicePaymentByInvoiceId(PaymentVO paymentVo, List<Long> invoiceidList);
	void saveInvoicePaymentByInvoice(PaymentVO paymentVo, List<InvoiceTO> invoiceList);
	List<InvoiceVO> convertToInvoiceVOList(List<InvoiceTO> toList) ;
	List<InvoiceVO> getAllInvoice(Date dateAsOf, Date endDate);
}
