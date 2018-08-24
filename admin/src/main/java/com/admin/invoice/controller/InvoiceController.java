package com.admin.invoice.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.admin.common.vo.JsonResponseVO;
import com.admin.file.vo.FileMetaVO;
import com.admin.grant.service.GrantService;
import com.admin.helper.GeneralUtils;
import com.admin.invoice.service.InvoiceService;
import com.admin.invoice.vo.InvoiceSearchCriteria;
import com.admin.invoice.vo.InvoiceStatusEnum;
import com.admin.invoice.vo.InvoiceUploadVO;
import com.admin.invoice.vo.InvoiceVO;
import com.admin.payment.controller.PaymentController;
import com.admin.payment.service.PaymentService;
import com.admin.payment.vo.PaymentDetailVO;
import com.admin.payment.vo.PaymentRsVO;
import com.admin.to.GrantTO;
import com.admin.validator.InvoiceSearchValidator;

@PropertySources({
	@PropertySource(value = "classpath:admin-dev-config.properties", ignoreResourceNotFound = false),
	@PropertySource(value = "file:/var/www/vhosts/ziumlight.com/config/application-${spring.profiles.active}.properties", ignoreResourceNotFound=true)
})

@Controller  
@EnableWebMvc
@Scope("request")
@RequestMapping(value = "/invoice")
@SessionAttributes({"invoiceUploadVo", "statusList"})
public class InvoiceController {
	private static final Logger logger = Logger.getLogger(InvoiceController.class);
	
	private PaymentController paymentController;
	private InvoiceService invoiceService;
	private GrantService grantService;
	private PaymentService paymentService;
	private InvoiceSearchValidator invoiceSearchValidator;
	
	private ServletContext context;
	@Value("${file.fileTemplatePath}")
	private String fileTemplatePath;
	private static final String uploadPassword = "uploadExcelFile1232";

	@Autowired
	public InvoiceController(PaymentController paymentController,
			InvoiceService invoiceService,
			GrantService grantService,
			PaymentService paymentService,
			InvoiceSearchValidator invoiceSearchValidator,
			ServletContext context){
		this.paymentController = paymentController;
		this.invoiceService = invoiceService;
		this.grantService = grantService;
		this.paymentService = paymentService;
		this.invoiceSearchValidator = invoiceSearchValidator;
		this.context = context;
	}
	
	@RequestMapping(value = "/listInvoice", method = RequestMethod.GET)  
    public String listInvoice(Model model) {  
    	logger.debug("loading listInvoice");
    	
    	InvoiceSearchCriteria searchCriteria = new InvoiceSearchCriteria();
    	
    	Map<String,String> statusList = new LinkedHashMap<String,String>();
    	statusList.put(InvoiceStatusEnum.PAID.toString(), InvoiceStatusEnum.PAID.getStatus());
    	statusList.put(InvoiceStatusEnum.PENDING.toString(), InvoiceStatusEnum.PENDING.getStatus());
    	model.addAttribute("exportForm", searchCriteria);
    	model.addAttribute("invoiceUploadVo", new InvoiceUploadVO());
    	model.addAttribute("statusList", statusList);
        return "listInvoice";  
    }
	
	@RequestMapping(value = "/getInvoiceList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String getInvoiceList() {
		logger.debug("get Invoice List");
		List<InvoiceVO> invoiceList = invoiceService.getAllInvoice();
		List<InvoiceVO> grantList = grantService.getAllGrant();
		if(!grantList.isEmpty()){
			invoiceList.addAll(grantList);
		}
		return GeneralUtils.convertListToJSONString(invoiceList);
	}
	
	@RequestMapping(value = "/getMessengerList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<InvoiceVO> getMessengerList() {
		logger.debug("getting messenger list");
		List<InvoiceVO> invoiceList = invoiceService.getAllInvoice();
		Set<String> set = new HashSet<String>();
		for(InvoiceVO vo : invoiceList) {
			set.add(vo.getMessenger());
		}
		invoiceList.clear();
		for(String s: set) {
			InvoiceVO invoice = new InvoiceVO();
			invoice.setMessenger(s);
			invoiceList.add(invoice);
		}
		return invoiceList;
	}
	
	@InitBinder("exportForm")
	protected void initBinder(WebDataBinder binder) {
		binder.setValidator(invoiceSearchValidator);
	}
	
	@RequestMapping(value = "/viewInvoice", method = RequestMethod.POST)
	public String viewInvoice(@RequestParam("viewBtn") String id, Model model, final RedirectAttributes redirectAttributes) {
		logger.debug("id = " + id);
		InvoiceVO invoiceVO = null;
		String[] splitId = id.split("-");
		if(splitId[0] != null && splitId[1] != null){
			if(splitId[1].toLowerCase().equals("invoice")) {
				invoiceVO = invoiceService.findById(Long.valueOf(splitId[0]));
			}else if(splitId[1].toLowerCase().equals("grant")) {
				invoiceVO = grantService.findById(Long.valueOf(splitId[0]));
			}
		}
		if (invoiceVO == null) {
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Invoice not found!");
			return "redirect:listInvoice";
		}
		model.addAttribute("invoice", invoiceVO);
		
		List<PaymentDetailVO> paymentList = invoiceVO.getPaymentDetailVOList();
		model.addAttribute("paymentList", paymentList);
		
		List<Long> rsIdList = new ArrayList<Long>();
		for(PaymentDetailVO paymentDetailVO : paymentList) {
			rsIdList.addAll(paymentDetailVO.getPaymentRsIdList());
		}
		List<PaymentRsVO> paymentRsList = paymentService.findByPaymentRsIdList(rsIdList, invoiceVO.getType());
		List<Long> invoiceIdList = GeneralUtils.convertListToLongList(paymentRsList, "referenceId", true);
		invoiceIdList.remove(Long.parseLong(splitId[0]));
		List<InvoiceVO> otherList = invoiceService.findByIdList(invoiceIdList);
		model.addAttribute("otherList", otherList);
		return "viewInvoice";
	}
	
	@RequestMapping(value = "/updateGrant", method = RequestMethod.POST)
	public String getGrantToUpdate(@RequestParam("editBtn") String id, Model model, 
			final RedirectAttributes redirectAttributes) {
		String[] splitId = id.split("-");
		if(splitId[0] != null && splitId[1] != null){
			InvoiceVO grantVO = grantService.findById(Long.parseLong(splitId[0]));
			if(grantVO.getGrantId() == null){
				redirectAttributes.addFlashAttribute("css", "danger");
				redirectAttributes.addFlashAttribute("msg", "Grant not found!");
				return "redirect:listInvoice";
			}
			logger.debug("Loading update grant page for " + grantVO.toString());
			model.addAttribute("grantForm", grantVO);
		}
		return "updateGrant";
	}
	
	@RequestMapping(value = "/updateGrantToDb", method = RequestMethod.POST)
	public String updateGrant(@ModelAttribute("grantForm") InvoiceVO grantVO,
			BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		
		logger.debug("updateGrant() : " + grantVO.toString());
		GeneralUtils.validate(grantVO, "grantForm" ,result);
		if (result.hasErrors()) {
			return "updateGrant";
		} else {
			grantVO.setInvoiceDate(GeneralUtils.convertStringToDate(grantVO.getInvoicedateString(), "dd/MM/yyyy"));
			grantService.updateGrant(grantVO);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Grant updated successfully!");
		}
		
		return "redirect:listInvoice";
	}
	
	@RequestMapping(value = "/payInvoice", method = RequestMethod.POST)
    public String payInvoice(@RequestParam("payBtn") String id, Model model,
    		final RedirectAttributes redirectAttributes) {
		List<String> idList = new ArrayList<String>();
		idList.add(id);
		return paymentController.createPayInvoice(idList, redirectAttributes, model);
    } 
	
	@RequestMapping(value = "/createGrant", method = RequestMethod.GET)
    public String showAddGrantForm(Model model) {  
    	logger.debug("loading showAddGrantForm");
    	InvoiceVO invoiceVO = new InvoiceVO();
    	model.addAttribute("grantForm", invoiceVO);
        return "createGrant";  
    }  
	
	@RequestMapping(value = "/createGrant", method = RequestMethod.POST)
    public String saveGrant(@ModelAttribute("grantForm") InvoiceVO invoiceVO, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		GeneralUtils.validate(invoiceVO, "grantForm" ,result);
		logger.debug("saveGrant() : " + invoiceVO.toString());
		if (result.hasErrors()) {
			return "createGrant";
		} else {
			invoiceVO.setStatus(GeneralUtils.STATUS_PENDING);
			grantService.saveGrant(invoiceVO);
			redirectAttributes.addFlashAttribute("css", "success");
			redirectAttributes.addFlashAttribute("msg", "Grant added successfully!");
		}
        return "redirect:listInvoice";  
    }  
	
	@RequestMapping(value = "/createGrantAndPay", method = RequestMethod.POST)
    public String saveGrantAndPay(@ModelAttribute("grantForm") InvoiceVO invoiceVO, 
    		BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
		GeneralUtils.validate(invoiceVO, "grantForm" ,result);
		List<String> idList = new ArrayList<String>();
		logger.debug("saveGrantAndPay() : " + invoiceVO.toString());
		if (result.hasErrors()) {
			return "createGrant";
		} else {
			invoiceVO.setStatus(GeneralUtils.STATUS_PENDING);
			GrantTO grantTO = grantService.saveGrant(invoiceVO);			
			idList.add(grantTO.getGrantId().toString()+"-grant");
		}
		return paymentController.createPayInvoice(idList, redirectAttributes, model);
    }  
	
	@RequestMapping(value = "/createBadDebt", method = RequestMethod.POST)
	public String createBadDebt(@RequestParam(value = "checkboxId", required=false) List<String> ids,
			final RedirectAttributes redirectAttributes) {
		if(ids == null || ids.size() < 1){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select at least one record!");
			return "redirect:listInvoice";
		}
		List<Long> idList = new ArrayList<Long>();
		for (String id : ids) {
			String[] splitId = id.split("-");
			if(splitId[0] != null && splitId[1] != null){
				if(splitId[1].toLowerCase().equals(GeneralUtils.MODULE_INVOICE)) {
					idList.add(Long.parseLong(splitId[0]));
					logger.debug("bad debt invoice: "+ id);
				}else if(splitId[1].toLowerCase().equals(GeneralUtils.MODULE_GRANT)) {
					logger.debug("bad debt grant: "+ id);
					redirectAttributes.addFlashAttribute("css", "danger");
					redirectAttributes.addFlashAttribute("msg", "Grant(s) cannot be set as BAD DEBT!");
					return "redirect:listInvoice";
				}
			}
			
		}
		if(!idList.isEmpty()){
			invoiceService.updateBadDebt(idList);
		}
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Invoice(s) set as BAD DEBT successfully!");
		return "redirect:listInvoice";
	}
	
	@RequestMapping(value = "/deleteInvoice", method = RequestMethod.POST)
	public String deleteInvoice(@RequestParam(value = "checkboxId", required=false) List<String> ids,
			final RedirectAttributes redirectAttributes) {
		if(ids == null || ids.isEmpty()){
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please select at least one record!");
			return "redirect:listInvoice";
		}
		List<Long> invoiceIdList = new ArrayList<Long>();
		List<Long> grantIdList = new ArrayList<Long>();
		for (String id : ids) {
			String[] splitId = id.split("-");
			if(splitId[0] != null && splitId[1] != null){
				if(splitId[1].toLowerCase().equals(GeneralUtils.MODULE_INVOICE)) {
					invoiceIdList.add(Long.parseLong(splitId[0]));
					logger.debug("deleted invoice: "+ id);
				}else if(splitId[1].toLowerCase().equals(GeneralUtils.MODULE_GRANT)) {
					grantIdList.add(Long.parseLong(splitId[0]));
					logger.debug("deleted grant: "+ id);
				}
			}
		}
		if(!invoiceIdList.isEmpty())	invoiceService.deleteInvoice(invoiceIdList);
		if(!grantIdList.isEmpty())		grantService.deleteGrant(grantIdList);
		
		redirectAttributes.addFlashAttribute("css", "success");
		redirectAttributes.addFlashAttribute("msg", "Invoice/Grant(s) deleted successfully!");
		return "redirect:listInvoice";
	}
	
	@RequestMapping(value="/downloadExcel", method = RequestMethod.POST)
    public String downloadExcel(@ModelAttribute("exportForm") @Validated InvoiceSearchCriteria searchCriteria, 
    		BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response,
    		final RedirectAttributes redirectAttributes) {
		if (!result.hasErrors()) {
			List<InvoiceVO> invoiceList = invoiceService.searchInvoice(searchCriteria);
			
			if(invoiceList != null && !invoiceList.isEmpty()){
				logger.debug("invoice record:"+ invoiceList.size());
				String statementPeriod = invoiceService.getStatementPeriod(searchCriteria);
				downloadExcel(invoiceList, statementPeriod, request, response);
				try {
					response.flushBuffer();
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("error", e);
				}
				return null;
			}
			else{
				redirectAttributes.addFlashAttribute("css", "danger");
				redirectAttributes.addFlashAttribute("msg", "No invoice result is found!");
		    	
		    	model.addAttribute("exportForm", searchCriteria);
				return "redirect:listInvoice";
			}
				
		}
    	
    	model.addAttribute("exportForm", searchCriteria);
        return "listInvoice"; 
    }
	
	public void downloadExcel(List<InvoiceVO> invoiceList, String statementPeriod, 
			HttpServletRequest request, HttpServletResponse response) {
		String dataDirectory = context.getRealPath("/WEB-INF/template/");
		if(dataDirectory == null){
//			dataDirectory = "C:/Inetpub/vhosts/ziumlight.com/template";
			dataDirectory = fileTemplatePath;
		}
        File file = new File(dataDirectory+"/invoice_summary_template.xls");
        logger.debug(file.getPath());
        
        HSSFWorkbook wb = invoiceService.writeToFile(file, invoiceList, statementPeriod);
        if(wb != null){
        	response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=invoice_summary.xls");
            try{
            	wb.write(response.getOutputStream());
                response.getOutputStream().flush();
            } 
            catch (IOException ex) {
            	logger.error(ex);
                ex.printStackTrace();
            }
        }
    }
	
	@RequestMapping(value = "/sortFile", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JsonResponseVO sortFile(@RequestBody List<String> orderList,
			@ModelAttribute("invoiceUploadVo") InvoiceUploadVO invoiceUploadVo) {
		if(invoiceUploadVo.getInvoiceList() != null && invoiceUploadVo.getInvoiceList().size() > 0){
			for(FileMetaVO file : invoiceUploadVo.getInvoiceList()){
				int index = orderList.indexOf(file.getFileName());
				if(index < 0){
					file.setSequence(0);
				}
				else{
					file.setSequence(index + 1);
				}
			}
			reshuffleFiles(invoiceUploadVo.getInvoiceList());
		}
		return new JsonResponseVO("success");
	}
	
	private void reshuffleFiles(LinkedList<FileMetaVO> files) {
		if(files != null && files.size() > 0){
			Collections.sort(files,new FileCompare());
			for(int i=0;i<files.size(); i++){
				files.get(i).setSequence(i+1);
			}
		}
	}
	
	private boolean checkFileFormat(String filename) {
		String[] split = filename.split(Pattern.quote("."));
		String format = split[split.length-1];
		if(format.equals("xls") || format.equals("xlsx")){
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "/uploadFile",method = RequestMethod.POST)
	public @ResponseBody LinkedList<FileMetaVO> upload(MultipartHttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("invoiceUploadVo") InvoiceUploadVO invoiceUploadVo) {
        Iterator<String> itr =  request.getFileNames();
        MultipartFile mpf = null;
        while(itr.hasNext()){
            mpf = request.getFile(itr.next()); 
            if(!checkFileFormat(mpf.getOriginalFilename())){
            	return null;
            }
            
            FileMetaVO fileMeta = new FileMetaVO();
            fileMeta.setFileName(mpf.getOriginalFilename());
            fileMeta.setFileSize(mpf.getSize()/1024+" Kb");
            fileMeta.setFileType(mpf.getContentType());

            try {
               fileMeta.setBytes(mpf.getBytes());
           } catch (IOException e) {
               e.printStackTrace();
           }
            if(invoiceUploadVo != null){
            	if(invoiceUploadVo.getInvoiceList() == null){
            		invoiceUploadVo.setInvoiceList(new LinkedList<FileMetaVO>());
            	}
            	fileMeta.setSequence(invoiceUploadVo.getInvoiceList().size() + 1);
            	invoiceUploadVo.getInvoiceList().add(fileMeta);
            	return invoiceUploadVo.getInvoiceList();
            }
       }
        reshuffleFiles(invoiceUploadVo.getInvoiceList());
        return null;
	}
	
	@RequestMapping(value = "/removeUploadFile",method = RequestMethod.POST)
	public @ResponseBody JsonResponseVO removeUploadFile(HttpServletRequest request,@RequestParam(value="fileName", required=false) String fileName,
			HttpServletResponse response, @ModelAttribute("invoiceUploadVo") InvoiceUploadVO invoiceUploadVo) {
		if(invoiceUploadVo != null && invoiceUploadVo.getInvoiceList() != null && invoiceUploadVo.getInvoiceList().size() > 0 && fileName != null && !fileName.trim().isEmpty()){
			Iterator<FileMetaVO> iterator = invoiceUploadVo.getInvoiceList().iterator();
			while(iterator.hasNext()){
				FileMetaVO file = iterator.next();
				if(file.getFileName().compareToIgnoreCase(fileName) == 0){
					iterator.remove();
					break;
				}
			}
			reshuffleFiles(invoiceUploadVo.getInvoiceList());
		}
		return new JsonResponseVO("success");
	}
	
	@RequestMapping(value = "/uploadInvoice", method = RequestMethod.POST)
	public String saveInvoice(final RedirectAttributes redirectAttributes,
			@ModelAttribute("invoiceUploadVo") InvoiceUploadVO invoiceUploadVo, Model model) {
		if(invoiceUploadVo == null || invoiceUploadVo.getInvoiceList() == null || invoiceUploadVo.getInvoiceList().isEmpty()) {
			redirectAttributes.addFlashAttribute("css", "danger");
			redirectAttributes.addFlashAttribute("msg", "Please upload at least one excel file!");
		}else{
			try{
				int fileUploadCount = invoiceService.saveInvoiceFromUploadFile(invoiceUploadVo);
				logger.debug("upload done");
				redirectAttributes.addFlashAttribute("css", "success");
				redirectAttributes.addFlashAttribute("msg", fileUploadCount + " invoice(s) added successfully!");
			}catch(Exception ex){
				logger.debug("error:",ex);
				redirectAttributes.addFlashAttribute("css", "danger");
				redirectAttributes.addFlashAttribute("msg", "Upload failed");
			}
			
			model.addAttribute("invoiceUploadVo", new InvoiceUploadVO());
			
		}
		return "redirect:listInvoice";
	}
	
	@RequestMapping(value = "/saveExcelInvoice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JsonResponseVO saveExcelInvoice(@RequestParam(value = "invoiceId") Integer invoiceId,
			@RequestParam(value = "messenger") String messenger,
			@RequestParam(value = "dateString") String invoiceDateString,
			@RequestParam(value = "amt") BigDecimal totalAmt,
			@RequestParam(value = "password", required = false) String password){
		if(password.compareTo(uploadPassword) != 0){
			return new JsonResponseVO("error uploading");
		}
		try{
			Date invoiceDate = GeneralUtils.convertStringToDate(invoiceDateString, "dd/MM/yyyy");
			Calendar cal = Calendar.getInstance();
			cal.setTime(invoiceDate);
			int year = cal.get(Calendar.YEAR);
			if(year < 2000){
				cal.add(Calendar.YEAR, 2000);
			}
			invoiceDate = cal.getTime();
			InvoiceVO invoiceVO = new InvoiceVO();
			invoiceVO.setInvoiceId(invoiceId.longValue());
			invoiceVO.setDeleteInd(GeneralUtils.NOT_DELETED);
			invoiceVO.setInvoiceDate(invoiceDate);
			invoiceVO.setMessenger(messenger);
			invoiceVO.setTotalAmt(totalAmt);
			
			InvoiceVO savedInvoice = invoiceService.findById(invoiceVO.getInvoiceId());
			if(invoiceVO.getInvoiceId() != null && savedInvoice == null) {
				invoiceVO.setStatus(GeneralUtils.STATUS_PENDING);
				invoiceService.saveInvoice(invoiceVO);
			}else if(invoiceVO.getInvoiceId() != null && savedInvoice != null){
				if(savedInvoice.getStatus() != null &&
						savedInvoice.getStatus().compareTo(InvoiceStatusEnum.PAID.getStatus()) == 0){
					invoiceVO.setTotalAmt(savedInvoice.getTotalAmt());
				}
				invoiceVO.setStatus(savedInvoice.getStatus());
				invoiceService.updateInvoice(invoiceVO);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			return new JsonResponseVO("uploading failed");
		}
		return new JsonResponseVO("uploading success");
	}
	
	class FileCompare implements Comparator<FileMetaVO>{

		@Override
		public int compare(FileMetaVO o1, FileMetaVO o2) {
			if(o1.getSequence() != null && o2.getSequence() != null){
				return o1.getSequence().compareTo(o2.getSequence());
			}else if(o1.getSequence() != null){
				return 1;
			}else if(o2.getSequence() != null){
				return -1;
			}
			return 0;
		}
		
	}
	
	
}
