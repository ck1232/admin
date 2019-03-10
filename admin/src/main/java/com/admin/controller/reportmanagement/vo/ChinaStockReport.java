package com.admin.controller.reportmanagement.vo;

import java.util.ArrayList;
import java.util.Date;
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
public class ChinaStockReport implements ReportInterface {
	
	private ExpenseService expenseService;
	private PaymentService paymentService;
	private ExpenseTypeLookup expenseTypeLookup;
	private PaymentModeLookup paymentModeLookup;
	@Autowired
	public ChinaStockReport(ExpenseService expenseService,
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
		ExpenseTypeVO expenseTypeVO = expenseTypeLookup.getExpenseTypeByValueMap().get("Stock(China)");
		PaymentModeVO chequeModeVo = paymentModeLookup.getPaymentModeByValueMap().get("Cheque");
		List<ExpenseVO> dbVoList = expenseService.getAllExpense(dateAsOf, endDate, expenseTypeVO.getExpenseTypeId());
		List<ExpenseReportVO> expenseReportList = new ArrayList<ExpenseReportVO>();
		if(dbVoList != null && !dbVoList.isEmpty()) {
			for(ExpenseVO vo : dbVoList) {
				List<PaymentDetailVO> paymentDetailList = paymentService.getAllPaymentByRefTypeAndRefId(vo);
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
					PaymentDetailVO paymentVO = new PaymentDetailVO();
					paymentVO.setPaymentAmt(vo.getTotalAmt());
					expenseReportVo.setPaymentDetail(paymentVO);
					expenseReportList.add(expenseReportVo);
				}
			}
			
		}
		ReportMapping reportMapping = new ReportMapping();
		reportMapping.addDateMapping("Date", "expense.expenseDate");
		reportMapping.addTextMapping("Invoice", "expense.invoiceNo");
		reportMapping.addTextMapping("Description", "expense.description");
		reportMapping.addTextMapping("Mode of Payment", "paymentDetail.paymentModeString");
		reportMapping.addChinaMoneyMapping("Amount (RMB)", "paymentDetail.paymentAmt");
		reportMapping.addTextMapping("Cheque No.", "paymentDetail.chequeNum");
		Sheet sheet = workbook.createSheet("RMB Purchase");
		ReportUtils.writeData(sheet, expenseReportList, reportMapping, "");
		return workbook;
	}
	
}
