package com.admin.controller.reportmanagement.vo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.admin.grant.service.GrantService;
import com.admin.helper.GeneralUtils;
import com.admin.helper.ReportUtils;
import com.admin.helper.vo.ExcelColumn.ColumnStyle;
import com.admin.helper.vo.ReportMapping;
import com.admin.invoice.service.InvoiceService;
import com.admin.invoice.vo.InvoiceVO;
import com.admin.payment.lookup.controller.PaymentModeLookup;
import com.admin.payment.lookup.vo.PaymentModeVO;
import com.admin.payment.service.PaymentService;
import com.admin.payment.vo.PaymentDetailVO;
import com.admin.payment.vo.PaymentRsVO;
@Component
public class InvoiceReport implements ReportInterface {

	private InvoiceService invoiceService;
	private GrantService grantService;
	private PaymentService paymentService;
	private PaymentModeLookup paymentModeLookup;
	
	List<TotalIncomeSummaryVO> summaryList;
	HashMap<String, TotalIncomeSummaryVO> summaryHashMap;
	public static final List<String> invoiceSummaryHeaders = Arrays.asList("GRANT","SALES","TOTAL");
	
	@Autowired
	public InvoiceReport(InvoiceService invoiceService,
			GrantService grantService,
			PaymentService paymentService,
			PaymentModeLookup paymentModeLookup) {
		this.invoiceService = invoiceService;
		this.grantService = grantService;
		this.paymentService = paymentService;
		this.paymentModeLookup = paymentModeLookup;
	}
	
	private List<PaymentDetailVO> getPaymentDetailVOList(InvoiceVO vo){
		List<PaymentDetailVO> list = new ArrayList<PaymentDetailVO>();
		List<PaymentRsVO> paymentRsVoList = vo.getPaymentRsVOList();
		if(paymentRsVoList != null && !paymentRsVoList.isEmpty()) {
			for(PaymentRsVO paymentVo : paymentRsVoList) {
				list.add(paymentVo.getPaymentDetailVO());
			}
		}
		return list;
	}

	@Override
	public Workbook exportReport(Workbook workbook, Date dateAsOf, Date endDate,
			Map<String, Object> additionalMap) {
		summaryList = new ArrayList<TotalIncomeSummaryVO>();
		summaryHashMap = new HashMap<String, TotalIncomeSummaryVO>();
		/*summaryHashMap.put("CPF", new TotalIncomeSummaryVO("CPF BOARD", "(credits for older worker)"));
		summaryHashMap.put("IRAS", new TotalIncomeSummaryVO("IRAS", "(WAGE CREDIT SCHEME)"));*/
		summaryHashMap.put("GRANT", new TotalIncomeSummaryVO("GRANT", ""));
		summaryHashMap.put("SALES", new TotalIncomeSummaryVO("SALES", ""));
		summaryHashMap.put("TOTAL", new TotalIncomeSummaryVO("TOTAL", ""));
		
		List<InvoiceReportVO> allInvoiceReportList = new ArrayList<InvoiceReportVO>();
		
		List<InvoiceVO> dbVoList = invoiceService.getAllInvoice(dateAsOf, endDate);
		List<InvoiceVO> grantList = grantService.getAllGrant(dateAsOf, endDate);
		if(!grantList.isEmpty()){
			dbVoList.addAll(grantList);
		}
		
		if(dbVoList != null && !dbVoList.isEmpty()) {
			PaymentModeVO chequeModeVo = paymentModeLookup.getPaymentModeByValueMap().get("Cheque");
			for(InvoiceVO vo : dbVoList) {
				List<PaymentDetailVO> paymentDetailList = getPaymentDetailVOList(vo);
//						paymentService.getAllPaymentByRefTypeAndRefId(vo.getType(), 
//						vo.getType().equals("invoice") ? vo.getInvoiceId() : vo.getGrantId());
				InvoiceReportVO invoiceReportVo = new InvoiceReportVO();
				invoiceReportVo.setInvoice(vo);
				invoiceReportVo.setPaymentDetailList(new ArrayList<PaymentDetailVO>());
				if(paymentDetailList != null && !paymentDetailList.isEmpty()) {
					for(PaymentDetailVO paymentVO : paymentDetailList) {
						if(paymentVO.getPaymentMode() == chequeModeVo.getPaymentModeId() && 
								(paymentVO.getBounceChequeInd() != null && paymentVO.getBounceChequeInd().equals("Y")))
							continue;
						invoiceReportVo.getPaymentDetailList().add(paymentVO);
					}
				}
				allInvoiceReportList.add(invoiceReportVo);
			}
			
			for(InvoiceReportVO report : allInvoiceReportList) {
				List<PaymentDetailVO> paymentDetailList = report.getPaymentDetailList();
				if(paymentDetailList != null && !paymentDetailList.isEmpty()) {
					String paymentMode = "";
					for(PaymentDetailVO vo : paymentDetailList){
						report.setChequeDate(vo.getPaymentDate());
						if(vo.getPaymentMode() != null && vo.getPaymentMode() == chequeModeVo.getPaymentModeId()) { // payment mode is cheque
							report.setChequeNo(vo.getChequeNum());
							report.setDebitDate(vo.getDebitDate());
						}
						paymentMode += vo.getPaymentModeString() + ",";
					}
					report.setPaymentMode(paymentMode.substring(0, paymentMode.length()-1));
					report.setPaymentAmt(report.getInvoice().getTotalAmt());
				}else if(report.getInvoice() != null && report.getInvoice().getStatus().compareToIgnoreCase(GeneralUtils.STATUS_BAD_DEBT) == 0){
					report.setPaymentMode(GeneralUtils.STATUS_BAD_DEBT);
				}
			}
			
			Collections.sort(allInvoiceReportList, new InvoiceReportComparator());	
			generateSummaryHashMap(allInvoiceReportList, dateAsOf, endDate);
			summaryList.addAll(summaryHashMap.values());
			summaryList = GeneralUtils.sortAccordingToSortList(summaryList, invoiceSummaryHeaders, "title");
		}
		Sheet sheet = workbook.createSheet("Invoice");
		ReportUtils.writeRow(sheet, "TOTAL / CONSOLIDATION", 0, ColumnStyle.Bold);
		ReportUtils.writeData(sheet, summaryList, initInvoiceSummaryReportMapping(endDate), "title");
		ReportUtils.writeBlankRows(sheet, 2);
		ReportUtils.writeRow(sheet, "DETAILS", 0, ColumnStyle.Bold);		
		ReportUtils.writeData(sheet, allInvoiceReportList, initInvoiceReportMapping(), "");
		return workbook;
	}
	
	

	private void generateSummaryHashMap(List<InvoiceReportVO> allInvoiceReportList, Date startDate, Date endDate) {
		for(InvoiceReportVO invoice : allInvoiceReportList) {
			Long invoiceId = invoice.getInvoice().getInvoiceId();
			if(invoiceId != null && invoiceId > 0) {
				addCurrentInvoice(summaryHashMap.get("SALES"), invoice, startDate, endDate);
			}else{
				addCurrentInvoice(summaryHashMap.get("GRANT"), invoice, startDate, endDate);
			}
			addCurrentInvoice(summaryHashMap.get("TOTAL"), invoice, startDate, endDate);
		}
		
		for(String key : summaryHashMap.keySet()) {
			TotalIncomeSummaryVO vo = summaryHashMap.get(key);
			vo.setOutstanding(vo.getIncome().subtract(vo.getMoneyReceived()));
			vo.setOutstanding(vo.getOutstanding().subtract(vo.getBadDebt()));
		}
		
	}


	private ReportMapping initInvoiceReportMapping() {
		ReportMapping reportMapping = new ReportMapping();
		reportMapping.addNumberMapping("Invoice No.", "invoice.invoiceId");
		reportMapping.addTextMapping("Name", "invoice.messenger");
		reportMapping.addDateMapping("Date of Invoice", "invoice.invoiceDate");
		reportMapping.addMoneyMapping("Amount", "invoice.totalAmt");
		reportMapping.addTextMapping("Payment Mode", "paymentMode");
		reportMapping.addDateMapping("Payment Date", "chequeDate");
		reportMapping.addTextMapping("Cheque No", "chequeNo");
		reportMapping.addMoneyMapping("Payment amount", "paymentAmt");
		reportMapping.addDateMapping("Date debited (per Bank)", "debitDate");;
		return reportMapping;
	}
	
	private ReportMapping initInvoiceSummaryReportMapping(Date date) {
		String dateAsOfString = GeneralUtils.convertDateToString(date, "dd MMM yyyy");
		ReportMapping reportMapping = new ReportMapping();
		reportMapping.addTextMapping("", "title");
		reportMapping.addTextMapping("Description", "description");
		reportMapping.addMoneyMapping("INCOME (A)", "income");
		reportMapping.addMoneyMapping("MONEY RECEIVED (B)", "moneyReceived");
		reportMapping.addMoneyMapping("BAD DEBT (C)", "badDebt");
		reportMapping.addMoneyMapping("OUTSTANDING AS AT " + dateAsOfString + "[(A)-(B)-(C)]", "outstanding");
		return reportMapping;
	}
	
	private void addCurrentInvoice(TotalIncomeSummaryVO vo, InvoiceReportVO invoice, Date startDate, Date endDate){
		vo.setIncome(vo.getIncome().add(invoice.getInvoice().getTotalAmt())); //set income
		if(invoice.getPaymentDetailList() != null && !invoice.getPaymentDetailList().isEmpty()){
			Date paymentDate = invoice.getChequeDate();
			if((startDate.before(paymentDate) || startDate.equals(paymentDate)) && (endDate.after(paymentDate) || endDate.equals(paymentDate))){
				vo.setMoneyReceived(vo.getMoneyReceived().add(invoice.getInvoice().getTotalAmt())); //set money received
			}
		}else if (invoice.getInvoice() != null && invoice.getInvoice().getStatus().compareToIgnoreCase(GeneralUtils.STATUS_BAD_DEBT) == 0){
			vo.setBadDebt(vo.getBadDebt().add(invoice.getInvoice().getTotalAmt()));
		}
	}
	
	class InvoiceReportComparator implements Comparator<InvoiceReportVO> {
		@Override
		public int compare(InvoiceReportVO o1, InvoiceReportVO o2) {
			if(o1.getInvoice().getInvoiceDate() == null && o2.getInvoice().getInvoiceDate() == null){
				return 0;
			}else if(o1.getInvoice().getInvoiceDate() == null && o2.getInvoice().getInvoiceDate() != null){
				return -1;
			}else if(o1.getInvoice().getInvoiceDate() != null && o2.getInvoice().getInvoiceDate() == null){
				return 1;
			}
			return o1.getInvoice().getInvoiceDate().compareTo(o2.getInvoice().getInvoiceDate()) * -1;
		}
	}

}
