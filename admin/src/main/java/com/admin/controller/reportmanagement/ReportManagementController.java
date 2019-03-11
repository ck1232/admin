package com.admin.controller.reportmanagement;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.admin.controller.reportmanagement.vo.BonusReport;
import com.admin.controller.reportmanagement.vo.ChinaStockPaymentReport;
import com.admin.controller.reportmanagement.vo.ChinaStockReport;
import com.admin.controller.reportmanagement.vo.ExpenseReport;
import com.admin.controller.reportmanagement.vo.InvoiceReport;
import com.admin.controller.reportmanagement.vo.ReportInterface;
import com.admin.controller.reportmanagement.vo.SalaryReport;
import com.admin.controller.reportmanagement.vo.SummaryReport;
import com.admin.helper.GeneralUtils;


@Controller  
@EnableWebMvc
@RequestMapping(value = "/report")
public class ReportManagementController {
	private static final Logger logger = Logger.getLogger(ReportManagementController.class);
	
	private ExpenseReport expenseReport;
	private SalaryReport salaryReport;
	private BonusReport bonusReport;
	private InvoiceReport invoiceReport;
	private ChinaStockReport chinaStockReport;
	private ChinaStockPaymentReport chinaPaymentReport;
	private SummaryReport summaryReport;
	
	@Autowired
	public ReportManagementController( 
			ExpenseReport expenseReport, SalaryReport salaryReport, 
			BonusReport bonusReport, InvoiceReport invoiceReport,
			ChinaStockReport chinaStockReport, SummaryReport summaryReport,
			ChinaStockPaymentReport chinaPaymentReport) {
		this.expenseReport = expenseReport;
		this.salaryReport = salaryReport;
		this.bonusReport = bonusReport;
		this.invoiceReport = invoiceReport;
		this.chinaStockReport = chinaStockReport;
		this.summaryReport = summaryReport;
		this.chinaPaymentReport = chinaPaymentReport;
	}
	
	@RequestMapping(value = "/viewReportGen", method = RequestMethod.GET)
	public String viewReportGen(Model model) {
		//get list of years
//		List<String> yearList = new ArrayList<String>();
//		for(int i=0;i>-5;i--){
//			yearList.add(GeneralUtils.convertDateToString(GeneralUtils.getNewDate(i), "yyyy"));
//		}
//		model.addAttribute("yearList", yearList);
		initData(model);
		ExpenseControlReportCriteria reportCriteria = new ExpenseControlReportCriteria();
		model.addAttribute("expenseControlForm", reportCriteria);
		return "viewReportGen";
	}
	
	private void initData(Model model) {
		List<String> allReportTypeList = new ArrayList<String>();
		if(ReportTypeEnum.values().length > 0){
			for(ReportTypeEnum reportType:ReportTypeEnum.values()){
				allReportTypeList.add(reportType.getName());
			}
		}
		model.addAttribute("reportList", allReportTypeList);
	}
	
	@RequestMapping(value = "/generateExpenseControlReport", method = RequestMethod.POST)
	public String generateExpenseControlReport(@ModelAttribute("expenseControlForm") ExpenseControlReportCriteria reportCriteria,
			BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) {
		
		logger.debug("generateExpenseControlReport() : " + reportCriteria.toString());
		if(reportCriteria.getStartdateString() == "") {
			result.rejectValue("startdateString", "error.notempty.reportform.startdate");
		}
		if(reportCriteria.getEnddateString() == "") {
			result.rejectValue("enddateString", "error.notempty.reportform.enddate");
		}
		if(reportCriteria.getStartdateString() != "" && reportCriteria.getEnddateString() != ""){
			reportCriteria.setStartDate(GeneralUtils.convertStringToDate(reportCriteria.getStartdateString(), GeneralUtils.STANDARD_DATE_FORMAT));
			reportCriteria.setEndDate(GeneralUtils.convertStringToDate(reportCriteria.getEnddateString(), GeneralUtils.STANDARD_DATE_FORMAT));
			if(reportCriteria.getStartDate().after(reportCriteria.getEndDate())){
				result.rejectValue("startdateString", "error.reportform.startdate.after.enddate");
				result.rejectValue("enddateString", "error.reportform.startdate.before.enddate");
			}
		}
		if(reportCriteria.getType().isEmpty()) {
			result.rejectValue("type", "error.notempty.reportform.type");
		}
		if(result.hasErrors()) {
			initData(model);
			return "viewReportGen";
		}

		List<ReportTypeEnum> enumList = new ArrayList<ReportTypeEnum>();
		for(String reportType :reportCriteria.getType()){
			ReportTypeEnum reportEnum = ReportTypeEnum.getEnumFromName(reportType);
			if(reportEnum != null){
				enumList.add(reportEnum);
			}
		}
		
		List<ReportInterface> reportControllerList = getReportController(enumList);
		//create a blank workbook
		List<Workbook> wbList = new ArrayList<Workbook>();
		Workbook expensewb = new HSSFWorkbook();
		Workbook invoiceWb = new HSSFWorkbook();
		//generate report
		for(ReportInterface report :reportControllerList){
			if(report instanceof InvoiceReport){
				invoiceWb = report.exportReport(invoiceWb, reportCriteria.getStartDate(), reportCriteria.getEndDate(), null);
				wbList.add(invoiceWb);
				continue;
			}
			expensewb = report.exportReport(expensewb, reportCriteria.getStartDate(), reportCriteria.getEndDate(), null);
		}
		if(expensewb.getNumberOfSheets() > 0)
			wbList.add(expensewb);
		downloadExcelZip(wbList, request, response);
		return null;
	}
	
	private List<ReportInterface> getReportController(List<ReportTypeEnum> enumList) {
		List<ReportInterface> list = new ArrayList<ReportInterface>();
		if(enumList != null && enumList.size() > 0){
			for(ReportTypeEnum typeEnum : enumList){
				switch (typeEnum) {
				case EXPENSE:
					list.add(expenseReport);
					break;
				case BONUS:
					list.add(bonusReport);
					break;
				case CHINA_STOCK:
					list.add(chinaStockReport);
					list.add(chinaPaymentReport);
					break;
				case GRANT:
					break;
				case INVOICE:
					list.add(invoiceReport);
					break;
				case SALARY:
					list.add(salaryReport);
					break;
				case SUMMARY:
					list.add(summaryReport);
					break;
				default:
					break;
				}
			}
		}
		return list;
	}

	public void downloadExcelZip(List<Workbook> wbList, HttpServletRequest request, HttpServletResponse response) {
        if(wbList != null && !wbList.isEmpty()){
        	response.setContentType("application/zip");
        	response.addHeader("Content-Disposition", "attachment; filename=report.zip");
        	try{
        		ZipOutputStream zos = new ZipOutputStream(response.getOutputStream());
        		try{
            		for(Workbook wb : wbList) {
            			ByteArrayOutputStream bos = new ByteArrayOutputStream();
            			wb.write(bos);
            			byte[] bytes = bos.toByteArray();
            			zos.putNextEntry(new ZipEntry(wb.getSheetName(wb.getNumberOfSheets()-1)+".xls"));
            			zos.write(bytes);
            			zos.closeEntry();
            		}
                } 
                catch (IOException ex) {
                    ex.printStackTrace();
                }
        		zos.close();
        	}catch(IOException ex) {
                ex.printStackTrace();
            }
        	
        }
    }
	
	public void downloadExcel(Workbook wb, HttpServletRequest request, HttpServletResponse response) {
        if(wb != null){
        	response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=Expense_Control_"+ "2017"/*reportCriteria.getYear()*/+".xls");
            try{
            	wb.write(response.getOutputStream());
                response.getOutputStream().flush();
            } 
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}