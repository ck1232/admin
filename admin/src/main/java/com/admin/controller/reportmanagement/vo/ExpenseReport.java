package com.admin.controller.reportmanagement.vo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.admin.expense.service.ExpenseService;
import com.admin.expense.vo.ExpenseVO;
import com.admin.helper.ReportUtils;
import com.admin.helper.vo.ReportMapping;
import com.admin.invoice.lookup.controller.ExpenseTypeLookup;
import com.admin.invoice.lookup.vo.ExpenseTypeVO;
import com.admin.payment.lookup.controller.PaymentModeLookup;
import com.admin.payment.lookup.vo.PaymentModeVO;
import com.admin.payment.service.PaymentService;
import com.admin.payment.vo.PaymentDetailVO;
@Component
public class ExpenseReport implements ReportInterface {
	
	private ExpenseService expenseService;
	private PaymentService paymentService;
	private ExpenseTypeLookup expenseTypeLookup;
	private PaymentModeLookup paymentModeLookup;
	
	@Autowired
	public ExpenseReport(ExpenseService expenseService,
			PaymentService paymentService,
			ExpenseTypeLookup expenseTypeLookup,
			PaymentModeLookup paymentModeLookup) {
		this.expenseService = expenseService;
		this.paymentService = paymentService;
		this.expenseTypeLookup = expenseTypeLookup;
		this.paymentModeLookup = paymentModeLookup;
	}



	@Override
	public Workbook exportReport(Workbook workbook, Date dateAsOf, Date endDate,
			Map<String, Object> additionalMap) {
		ReportMapping reportMapping = initReportMapping();
		LinkedHashMap<String, List<ExpenseReportVO>> expenseReportMap = new LinkedHashMap<String, List<ExpenseReportVO>>();
		ExpenseTypeVO expenseTypeVO = expenseTypeLookup.getExpenseTypeByValueMap().get("China Stock Payment");
		PaymentModeVO chequeModeVo = paymentModeLookup.getPaymentModeByValueMap().get("Cheque");
		List<Long> typeList = new ArrayList<Long>();
		typeList.add(expenseTypeVO.getExpenseTypeId());
		List<ExpenseVO> dbVoList = expenseService.getAllExpenseExcludeParamType(dateAsOf, endDate, typeList);
		if(dbVoList != null && !dbVoList.isEmpty()) {
			List<ExpenseReportVO> expenseReportList = new ArrayList<ExpenseReportVO>();
			for(ExpenseVO vo : dbVoList) {
				List<PaymentDetailVO> paymentDetailList = paymentService.getAllPaymentByRefTypeAndRefId("expense", vo.getExpenseId());
				ExpenseReportVO expenseReportVo;
				if(paymentDetailList != null && !paymentDetailList.isEmpty()) {
					for(PaymentDetailVO paymentVO : paymentDetailList) {
						expenseReportVo = new ExpenseReportVO();
						expenseReportVo.setExpense(vo);
						if(paymentVO.getPaymentMode() == chequeModeVo.getPaymentModeId() && 
								(paymentVO.getBounceChequeInd() != null && paymentVO.getBounceChequeInd().equals("Y")))
							continue;
						expenseReportVo.setPaymentDetail(paymentVO);
						expenseReportList.add(expenseReportVo);
					}
				}else{
					expenseReportVo = new ExpenseReportVO();
					expenseReportVo.setExpense(vo);
					expenseReportList.add(expenseReportVo);
				}
			}
			
			Calendar cal = Calendar.getInstance();
			for(ExpenseReportVO expensevo : expenseReportList){
				cal.setTime(expensevo.getExpense().getExpenseDate());
				String month = new SimpleDateFormat("MMM").format(cal.getTime());
				if(expenseReportMap.containsKey(month)){
					expenseReportMap.get(month).add(expensevo);
				}else{
					List<ExpenseReportVO> toPutList = new ArrayList<ExpenseReportVO>();
					toPutList.add(expensevo);
					expenseReportMap.put(month, toPutList);
				}
			}
			
			for(String key : expenseReportMap.keySet()){
				Sheet sheet = workbook.createSheet(key);
				ReportUtils.writeData(sheet, expenseReportMap.get(key), reportMapping, "");
			}
		}else{
			Sheet sheet = workbook.createSheet("Expense");
			ReportUtils.writeData(sheet, new ArrayList<ExpenseReportVO>(), reportMapping, "");
		}
		return workbook;
	}
	
	private ReportMapping initReportMapping(){
		ReportMapping reportMapping = new ReportMapping();
		reportMapping.addDateMapping("Date", "expense.expenseDate");
		reportMapping.addTextMapping("Expense Type", "expense.expenseType");
		reportMapping.addTextMapping("Invoice No", "expense.invoiceNo");
		reportMapping.addTextMapping("Description", "expense.description");
		reportMapping.addTextMapping("Mode of Payment", "paymentDetail.paymentModeString");
		reportMapping.addMoneyMapping("Amount", "paymentDetail.paymentAmt");
		reportMapping.addTextMapping("Supplier", "expense.supplier");
		reportMapping.addDateMapping("Date Paid", "paymentDetail.paymentDate");
		reportMapping.addTextMapping("Cheque No", "paymentDetail.chequeNum");
		reportMapping.addDateMapping("Date deducted (per Bank)", "paymentDetail.debitDate");
		reportMapping.addTextMapping("Remark", "paymentDetail.remarks");
		return reportMapping;
	}
	
}
