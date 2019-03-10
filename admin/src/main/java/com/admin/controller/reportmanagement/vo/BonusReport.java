package com.admin.controller.reportmanagement.vo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.admin.helper.ReportUtils;
import com.admin.helper.vo.ExcelColumn.ColumnStyle;
import com.admin.helper.vo.ReportMapping;
import com.admin.payment.lookup.controller.PaymentModeLookup;
import com.admin.payment.lookup.vo.PaymentModeVO;
import com.admin.payment.service.PaymentService;
import com.admin.payment.vo.PaymentDetailVO;
import com.admin.salarybonus.service.SalaryBonusService;
import com.admin.salarybonus.vo.SalaryBonusVO;
@Component
public class BonusReport implements ReportInterface {

	private SalaryBonusService bonusService;
	private PaymentService paymentService;
	private PaymentModeLookup paymentModeLookup;
	
	@Autowired
	public BonusReport(SalaryBonusService bonusService,
			PaymentService paymentService,
			PaymentModeLookup paymentModeLookup) {
		this.bonusService = bonusService;
		this.paymentService = paymentService;
		this.paymentModeLookup = paymentModeLookup;
	}

	@Override
	public Workbook exportReport(Workbook workbook, Date dateAsOf, Date endDate,
			Map<String, Object> additionalMap) {
		
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(dateAsOf);
	    int year = cal.get(Calendar.YEAR);
	    SalaryBonusReportVO salaryReportVo;
	    
	    List<SalaryBonusReportVO> paidBonusReportList = new ArrayList<SalaryBonusReportVO>();
		List<SalaryBonusReportVO> unpaidBonusReportList = new ArrayList<SalaryBonusReportVO>();
	    PaymentModeVO chequeModeVo = paymentModeLookup.getPaymentModeByValueMap().get("Cheque");
		List<SalaryBonusVO> dbVoList = bonusService.getAllBonusVo();
		if(dbVoList != null && !dbVoList.isEmpty()) {
			for(SalaryBonusVO vo : dbVoList) {
				List<PaymentDetailVO> paymentDetailList = paymentService.getAllPaymentByRefTypeAndRefId(vo, dateAsOf, endDate);
				
				if(paymentDetailList != null && !paymentDetailList.isEmpty()) {
					for(PaymentDetailVO paymentVO : paymentDetailList) {
						salaryReportVo = new SalaryBonusReportVO();
						salaryReportVo.setSalarybonus(vo);
						if(paymentVO.getPaymentMode() == chequeModeVo.getPaymentModeId() && 
								(paymentVO.getBounceChequeInd() != null && paymentVO.getBounceChequeInd().equals("Y")))
							continue;
						if(dateAsOf.before(paymentVO.getPaymentDate()) && endDate.after(paymentVO.getPaymentDate())){
							salaryReportVo.setPaymentDetail(paymentVO);
							paidBonusReportList.add(salaryReportVo);
						}
					}
				}else{
					if((dateAsOf.before(vo.getDate()) || dateAsOf.equals(vo.getDate())) && (endDate.after(vo.getDate()) || endDate.equals(vo.getDate()))) {
						salaryReportVo = new SalaryBonusReportVO();
						salaryReportVo.setSalarybonus(vo);
						unpaidBonusReportList.add(salaryReportVo);
					}
				}
			}
		}
		ReportMapping reportMapping = new ReportMapping();
		reportMapping.addDateMonthYearMapping("Month", "salarybonus.date");
		reportMapping.addTextMapping("Name", "salarybonus.name");
		reportMapping.addTextMapping("Type", "salarybonus.employeeTypeString");
		reportMapping.addDateMapping("DOB", "salarybonus.dob");
		reportMapping.addTextMapping("Nationality", "salarybonus.nationality");
		reportMapping.addMoneyMapping("Bonus", "salarybonus.bonusAmt");
		reportMapping.addMoneyMapping("Employee CPF", "salarybonus.employeeCpf");
		reportMapping.addMoneyMapping("Employer CPF", "salarybonus.employerCpf");
		reportMapping.addMoneyMapping("Take Home Salary", "salarybonus.takehomeAmt");
		reportMapping.addTextMapping("Mode of Payment", "paymentDetail.paymentModeString");
		reportMapping.addTextMapping("Cheque No.", "paymentDetail.chequeNum");
		Sheet sheet = workbook.createSheet("Bonus");
		ReportUtils.writeRow(sheet, (year-1) + " Bonus paid in " + year + " (already recognise in " + (year-1) + " Income Statement)", 0, ColumnStyle.Bold);
		ReportUtils.writeData(sheet, paidBonusReportList, reportMapping, "");
		ReportUtils.writeBlankRows(sheet, 2);
		ReportUtils.writeRow(sheet, year + " Bonus not paid - To add as accruals as it will be paid in " + (year+1), 0, ColumnStyle.Bold);
		ReportUtils.writeData(sheet, unpaidBonusReportList, reportMapping, "");
		return workbook;
	}

}
