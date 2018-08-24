package com.admin.invoice.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.dao.InvoiceDAO;
import com.admin.file.vo.FileMetaVO;
import com.admin.helper.GeneralUtils;
import com.admin.invoice.controller.ExcelFileHelper;
import com.admin.invoice.vo.InvoiceSearchCriteria;
import com.admin.invoice.vo.InvoiceStatusEnum;
import com.admin.invoice.vo.InvoiceUploadVO;
import com.admin.invoice.vo.InvoiceVO;
import com.admin.payment.service.PaymentService;
import com.admin.payment.vo.PaymentVO;
import com.admin.to.InvoicePaymentRsTO;
import com.admin.to.InvoiceTO;
import com.admin.to.PaymentDetailTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class InvoiceServiceImpl implements InvoiceService {
	private static final Logger logger = Logger.getLogger(InvoiceServiceImpl.class);

	private ExcelFileHelper excelFileHelper;
	private PaymentService paymentService;
	private InvoiceDAO invoiceDAO;
	
	
	
	@Autowired
	public InvoiceServiceImpl(PaymentService paymentService,
			InvoiceDAO invoiceDAO) {
		this.paymentService = paymentService;
		this.invoiceDAO = invoiceDAO;
	}
	
	public InvoiceVO findById(Long id) {
		List<InvoiceVO> invoiceVOList = findByIdList(Arrays.asList(id));
		if(invoiceVOList != null && !invoiceVOList.isEmpty()) {
			return invoiceVOList.get(0);
		}
		return new InvoiceVO();
	}
	
	public List<InvoiceVO> findByIdList(List<Long> idList){
		List<InvoiceTO> invoiceTOList = invoiceDAO.findByInvoiceIdIn(idList);
		return convertToInvoiceVOList(invoiceTOList);
	}

	public List<InvoiceVO> getAllInvoice() {
		List<InvoiceTO> invoiceTOList = invoiceDAO.findByDeleteInd(GeneralUtils.NOT_DELETED);
		return convertToInvoiceVOList(invoiceTOList);
	}
	
	public List<InvoiceVO> searchInvoice(InvoiceSearchCriteria searchCriteria) {
		List<InvoiceVO> invoiceList = getAllInvoice();
		List<InvoiceVO> filteredList = new ArrayList<InvoiceVO>();
		
		for(InvoiceVO invoice : invoiceList) {
			if(!searchCriteria.getMessenger().trim().equalsIgnoreCase(invoice.getMessenger())) continue;
			if(!searchCriteria.getStatus().equalsIgnoreCase("ALL")&& !searchCriteria.getStatus().trim().equalsIgnoreCase(invoice.getStatus())) continue;
			if(!searchCriteria.getInvoicedatefrom().isEmpty()){
				try {
					if(null == invoice.getInvoiceDate()) continue;
					Date datefrom = new SimpleDateFormat("MM/dd/yyyy").parse(searchCriteria.getInvoicedatefrom());
					if(invoice.getInvoiceDate().compareTo(datefrom) < 0) continue; 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if(!searchCriteria.getInvoicedateto().isEmpty()){
				try {
					if(null == invoice.getInvoiceDate()) continue;
					Date dateto = new SimpleDateFormat("MM/dd/yyyy").parse(searchCriteria.getInvoicedateto());
					Calendar c = Calendar.getInstance();
					c.setTime(dateto);
					c.add(Calendar.DATE, 1);
					if(invoice.getInvoiceDate().compareTo(c.getTime()) > 0) continue; 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			filteredList.add(invoice);
		}
		return filteredList;
	}
	
	public void saveInvoice(InvoiceVO invoiceVO) {
		List<InvoiceTO> invoiceTOList = convertToInvoiceTOList(Arrays.asList(invoiceVO));
		invoiceDAO.save(invoiceTOList);
	}
	
	public void updateInvoice(InvoiceVO vo) {
		if(vo != null && vo.getInvoiceId() != null){
			InvoiceTO to = invoiceDAO.findByInvoiceId(vo.getInvoiceId());
			to.setMessenger(vo.getMessenger());
			to.setInvoiceDate(vo.getInvoiceDate());
			to.setTotalAmt(vo.getTotalAmt());
			to.setStatus(vo.getStatus());
			invoiceDAO.save(to);
		}
	}
	
	public void updateBadDebt(List<Long> idList) {
		List<InvoiceTO> invoiceTOList = invoiceDAO.findByInvoiceIdIn(idList);
		if(!invoiceTOList.isEmpty()) {
			for(InvoiceTO invoiceTO : invoiceTOList) {
				invoiceTO.setStatus(GeneralUtils.STATUS_BAD_DEBT);
			}
			invoiceDAO.save(invoiceTOList);
		}
	}
	
	public void deleteInvoice(List<Long> idList) {
		List<InvoiceTO> invoiceTOList = invoiceDAO.findByInvoiceIdIn(idList);
		if(invoiceTOList != null && !invoiceTOList.isEmpty()){
			for(InvoiceTO invoiceTO : invoiceTOList){
				invoiceTO.setDeleteInd(GeneralUtils.DELETED);
			}
			invoiceDAO.save(invoiceTOList);
		}
	}
	
	
	public String getStatementPeriod(InvoiceSearchCriteria searchCriteria) {
		SimpleDateFormat sdfWithoutSlash = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat sdfWithSlash = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		String dateFrom;
		String dateTo;
		
		try{
			if(!searchCriteria.getInvoicedatefrom().equals("")) {
				dateFrom = sdfWithoutSlash.format(sdfWithSlash.parse(searchCriteria.getInvoicedatefrom()));
			}else{
				dateFrom = sdfWithoutSlash.format(date);
			}
			
			if(!searchCriteria.getInvoicedateto().equals("")) {
				dateTo = sdfWithoutSlash.format(sdfWithSlash.parse(searchCriteria.getInvoicedateto()));
			}else{
				dateTo = sdfWithoutSlash.format(date);
			}
			
			if(dateFrom.equals(dateTo)) {
				return "of " + dateTo;
			}else if(!dateFrom.equals(dateTo)) {
				return "from " + dateFrom + " to " + dateTo;
			}
			
		}catch(Exception e) {
			logger.info("Error formatting statement period for invoice exporting.");
		}
		return "";
	}
	
	public HSSFWorkbook writeToFile(File inputfile, List<InvoiceVO> invoiceList, String statementPeriod) {
		excelFileHelper = new ExcelFileHelper();
		HSSFWorkbook wb = excelFileHelper.writeToFile(inputfile, invoiceList, statementPeriod);
		return wb;
	}
	
	public int saveInvoiceFromUploadFile(InvoiceUploadVO invoice) {
		excelFileHelper = new ExcelFileHelper();
		InvoiceVO invoicedata;
		int fileUploadCount = 0;
		for(FileMetaVO file : invoice.getInvoiceList()) {
			logger.debug("extract start");
			invoicedata = excelFileHelper.readFromFile(file.getBytes());
			logger.debug("extract end");
			logger.debug("save start");
			if(invoicedata != null){
				invoicedata.setStatus(GeneralUtils.STATUS_PENDING);
				InvoiceVO savedInvoice = findById(invoicedata.getInvoiceId());
				if(invoicedata.getInvoiceId() != null && savedInvoice.getInvoiceId() == null) {
					saveInvoice(invoicedata);
					fileUploadCount++;
				}else if(invoicedata.getInvoiceId() != null && savedInvoice.getInvoiceId() != null){
					updateInvoice(invoicedata);
					fileUploadCount++;
				}
			}
		}
		logger.debug("save end");
		return fileUploadCount;
	}
	
	public void saveInvoicePaymentByInvoiceId(PaymentVO paymentVo, List<Long> invoiceidList) {
		List<InvoiceTO> invoiceList = invoiceDAO.findByInvoiceIdIn(invoiceidList);;
		saveInvoicePaymentByInvoice(paymentVo, invoiceList);
	}
	
	public void saveInvoicePaymentByInvoice(PaymentVO paymentVo, List<InvoiceTO> invoiceList) {
		List<PaymentDetailTO> paymentDetailTOList = paymentService.genPaymentDetail(paymentVo);
		
		if(!invoiceList.isEmpty() && !paymentDetailTOList.isEmpty()) {
			for(InvoiceTO invoiceTO : invoiceList) {
				for(PaymentDetailTO paymentDetailTO : paymentDetailTOList) {
					InvoicePaymentRsTO paymentRsTO = new InvoicePaymentRsTO();
					paymentRsTO.setInvoiceTO(invoiceTO);
					paymentRsTO.setPaymentDetailTO(paymentDetailTO);
					if(invoiceTO.getInvoicePaymentRsTOSet() == null) 
						invoiceTO.setInvoicePaymentRsTOSet(new HashSet<InvoicePaymentRsTO>());
					
					invoiceTO.getInvoicePaymentRsTOSet().add(paymentRsTO);
				}
				invoiceTO.setStatus(InvoiceStatusEnum.PAID.toString());
			}
		}
		invoiceDAO.save(invoiceList);
	}

	public List<InvoiceVO> convertToInvoiceVOList(List<InvoiceTO> toList) {
		List<InvoiceVO> voList = new ArrayList<InvoiceVO>();
		if(toList != null && !toList.isEmpty()) {
			for(InvoiceTO to : toList) {
				InvoiceVO vo = new InvoiceVO();
				vo.setInvoiceId(to.getInvoiceId());
				vo.setMessenger(to.getMessenger());
				vo.setInvoiceDate(to.getInvoiceDate());
				vo.setInvoicedateString(GeneralUtils.convertDateToString(to.getInvoiceDate(), "dd/MM/yyyy"));
				vo.setTotalAmt(to.getTotalAmt());
				vo.setTotalAmtString("$"+to.getTotalAmt());
				vo.setStatus(to.getStatus());
				vo.setType(GeneralUtils.MODULE_INVOICE);
				vo.setPaymentRsVOList(paymentService.convertToPaymentRsVOList(new ArrayList<InvoicePaymentRsTO>(to.getInvoicePaymentRsTOSet())));
				voList.add(vo);
			}
		}
		return voList;
	}
	
	private List<InvoiceTO> convertToInvoiceTOList(List<InvoiceVO> voList) {
		List<InvoiceTO> toList = new ArrayList<InvoiceTO>();
		if(voList != null && !voList.isEmpty()){
			List<Long> idList = GeneralUtils.convertListToLongList(voList, "invoiceId", false);
			Map<Long, InvoiceTO> invoiceTOMap = new HashMap<Long, InvoiceTO>();
			if(!idList.isEmpty()){
				List<InvoiceTO> invoiceTOList = invoiceDAO.findByInvoiceIdIn(idList);
				invoiceTOMap = GeneralUtils.convertListToLongMap(invoiceTOList, "invoiceId");
			}
			
			for(InvoiceVO vo : voList){
				InvoiceTO to = new InvoiceTO();
				InvoiceTO dbTO = invoiceTOMap.get(vo.getInvoiceId());
				if(dbTO != null){ //update
					to = dbTO;
				}
				to.setInvoiceId(vo.getInvoiceId());
				to.setMessenger(vo.getMessenger());
				to.setInvoiceDate(vo.getInvoiceDate());
				to.setTotalAmt(vo.getTotalAmt());
				to.setStatus(vo.getStatus());
				toList.add(to);
			}
		}
		return toList;
	}

	@Override
	public List<InvoiceVO> getAllInvoice(Date dateAsOf, Date endDate) {
		List<InvoiceTO> invoiceList = invoiceDAO.findByInvoiceDateBetweenAndDeleteInd(dateAsOf, endDate, GeneralUtils.NOT_DELETED);
		return convertToInvoiceVOList(invoiceList);
	}
}
