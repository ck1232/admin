package com.admin.salarybonus.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.dao.BonusDAO;
import com.admin.dao.SalaryDAO;
import com.admin.employee.service.EmployeeService;
import com.admin.employee.vo.EmployeeVO;
import com.admin.expense.vo.ExpenseStatusEnum;
import com.admin.helper.GeneralUtils;
import com.admin.payment.service.PaymentService;
import com.admin.payment.vo.PaymentVO;
import com.admin.salarybonus.vo.SalaryBonusVO;
import com.admin.salarybonus.vo.TypeEnum;
import com.admin.to.BonusPaymentRsTO;
import com.admin.to.BonusTO;
import com.admin.to.PaymentDetailTO;
import com.admin.to.SalaryPaymentRsTO;
import com.admin.to.SalaryTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class SalaryBonusServiceImpl implements SalaryBonusService{
	
	private SalaryDAO salaryDAO;
	private BonusDAO bonusDAO;
	private EmployeeService employeeService;
	private PaymentService paymentService;
	
	@Autowired
	public SalaryBonusServiceImpl(SalaryDAO salaryDAO,
			BonusDAO bonusDAO,
			EmployeeService employeeService,
			PaymentService paymentService) {
		this.salaryDAO = salaryDAO;
		this.bonusDAO = bonusDAO;
		this.employeeService = employeeService;
		this.paymentService = paymentService;
	}
	
	//get all salary and bonus start
		public List<SalaryBonusVO> getAllSalaryBonusList() {
			List<SalaryBonusVO> salaryBonusVOList = new ArrayList<SalaryBonusVO>();
			salaryBonusVOList.addAll(getAllSalaryVO());
			salaryBonusVOList.addAll(getAllBonusVO());
			return salaryBonusVOList;
		}

	public List<SalaryBonusVO> getAllSalaryVO(){
		List<SalaryBonusVO> salaryBonusVoList = new ArrayList<SalaryBonusVO>();
		List<SalaryTO> salaryTOList = salaryDAO.findByDeleteInd(GeneralUtils.NOT_DELETED);
		if(salaryTOList != null && !salaryTOList.isEmpty()){
			salaryBonusVoList.addAll(convertSalaryToSalaryBonusVOList(salaryTOList));
		}
		return salaryBonusVoList;
	}
	
	public List<SalaryBonusVO> getAllBonusVO(){
		List<SalaryBonusVO> salaryBonusVoList = new ArrayList<SalaryBonusVO>();
		List<BonusTO> bonusTOList = bonusDAO.findByDeleteInd(GeneralUtils.NOT_DELETED);
		if(bonusTOList != null && !bonusTOList.isEmpty()){
			salaryBonusVoList.addAll(convertBonusToSalaryBonusVOList(bonusTOList));
		}
		return salaryBonusVoList;
	}
	
	//get by id start
	public SalaryBonusVO findSalaryById(Long id) {
		List<SalaryBonusVO> salaryTOList = findSalaryByIdList(Arrays.asList(id));
		if(salaryTOList != null && !salaryTOList.isEmpty()){
			return salaryTOList.get(0);
		}
		return new SalaryBonusVO();
	}
	
	public List<SalaryBonusVO> findSalaryByIdList(List<Long> idList) {
		List<SalaryTO> salaryTOList = salaryDAO.findBySalaryIdInOrderBySalaryDateDesc(idList);
		return convertSalaryToSalaryBonusVOList(salaryTOList);
	}
	
	public List<SalaryBonusVO> getAllSalaryByEmpId(Long employeeId) {
		List<SalaryTO> salaryTOList = salaryDAO.findByEmployeeTO_employeeIdOrderBySalaryDateDesc(employeeId);
		return convertSalaryToSalaryBonusVOList(salaryTOList);
	}
	
	public SalaryBonusVO findBonusById(Long id) {
		List<SalaryBonusVO> bonusTOList = findBonusByIdList(Arrays.asList(id));
		if(bonusTOList != null && !bonusTOList.isEmpty()){
			return bonusTOList.get(0);
		}
		return new SalaryBonusVO();
	}
	
	public List<SalaryBonusVO> findBonusByIdList(List<Long> idList) {
		List<BonusTO> bonusTOList = bonusDAO.findByBonusIdInOrderByBonusDateDesc(idList);
		return convertBonusToSalaryBonusVOList(bonusTOList);
	}
	
	public List<SalaryBonusVO> getAllBonusByEmpId(Long employeeId) {
		List<BonusTO> bonusTOList = bonusDAO.findByEmployeeTO_employeeIdOrderByBonusDateDesc(employeeId);
		return convertBonusToSalaryBonusVOList(bonusTOList);
	}
	
	public Long saveSalaryBonus(SalaryBonusVO vo) {
		if(vo != null){
			vo.setStatus(ExpenseStatusEnum.UNPAID.toString());
			EmployeeVO employeeVO = employeeService.findById(vo.getEmployeeVO().getEmployeeId());
			if(vo.getType().equals(TypeEnum.SALARY.toString())) {
				List<SalaryTO> salaryTOList = convertToSalaryTOList(Arrays.asList(vo), employeeVO);
				salaryDAO.save(salaryTOList);
				return salaryTOList.get(0).getSalaryId();
			}else if(vo.getType().equals(TypeEnum.BONUS.toString())) {
				List<BonusTO> bonusTOList = convertToBonusTOList(Arrays.asList(vo), employeeVO);
				bonusDAO.save(bonusTOList);
				return bonusTOList.get(0).getBonusId();
			}
		}
		return null;
	}
	
	public void updateSalaryBonus(SalaryBonusVO vo) {
		if(vo != null && vo.getId() != null){
			if(vo.getType().equals("Salary")) {
				SalaryTO to = salaryDAO.findBySalaryId(vo.getId());
				to.setSalaryDate(GeneralUtils.convertStringToDate(vo.getDateString(), "dd/MM/yyyy"));
				to.setBasicSalaryAmt(vo.getBasicSalaryAmt());
				to.setOverTimeAmt(vo.getOverTimeAmt());
				to.setOverTimeHours(vo.getOverTimeHours());
				to.setOverTimeRemarks(vo.getOverTimeRemarks());
				to.setAllowance(vo.getAllowance());
				to.setMedical(vo.getMedical());
				to.setLeaveBalance(vo.getLeaveBalance());
				to.setLeaveTaken(vo.getLeaveTaken());
				to.setUnpaidLeaveAmt(vo.getUnpaidLeaveAmt());
				to.setUnpaidLeaveRemarks(vo.getUnpaidLeaveRemarks());
				to.setCdacAmt(vo.getCdacAmt());
				to.setSdlAmt(vo.getSdlAmt());
				to.setFwLevy(vo.getFwLevy());
				to.setEmployeeCpf(vo.getEmployeeCpf());
				to.setEmployerCpf(vo.getEmployerCpf());
				salaryDAO.save(to);
				
			}else if(vo.getType().equals("Bonus")) {
				BonusTO to = bonusDAO.findByBonusId(vo.getId());
				to.setBonusDate(vo.getDate());
				to.setBonusAmt(vo.getBonusAmt());
				to.setEmployeeCpf(vo.getEmployeeCpf());
				to.setEmployerCpf(vo.getEmployerCpf());
				bonusDAO.save(to);
			}
		}
	}
	
	//delete salary and bonus from idList
	public void deleteSalaryBonus(List<String> idList) {
		for(String id : idList) {
			String[] splitId = id.split("-");
			if(splitId[0] != null && splitId[1] != null){
				if(splitId[1].toLowerCase().equals("salary")) {
					deleteSalary(Arrays.asList(Long.valueOf(splitId[0])));
				}else if(splitId[1].toLowerCase().equals("bonus")) {
					deleteBonus(Arrays.asList(Long.valueOf(splitId[0])));
				}
			}
		}
	}
	
	public void deleteSalary(List<Long> idList) {
		List<SalaryTO> salaryTOList = salaryDAO.findBySalaryIdIn(idList);
		if(salaryTOList != null && !salaryTOList.isEmpty()){
			for(SalaryTO salaryTO : salaryTOList){
				salaryTO.setDeleteInd(GeneralUtils.DELETED);
			}
			salaryDAO.save(salaryTOList);
		}
	}
	
	public void deleteBonus(List<Long> idList) {
		List<BonusTO> bonusTOList = bonusDAO.findByBonusIdIn(idList);
		if(bonusTOList != null && !bonusTOList.isEmpty()){
			for(BonusTO bonusTO : bonusTOList){
				bonusTO.setDeleteInd(GeneralUtils.DELETED);
			}
			bonusDAO.save(bonusTOList);
		}
	}	
	
	public void saveSalaryPaymentBySalaryId(PaymentVO paymentVo, List<Long> salaryidList) {
		List<SalaryTO> salaryList = salaryDAO.findBySalaryIdIn(salaryidList);
		saveSalaryPaymentBySalary(paymentVo, salaryList);
		
	}
	
	public void saveSalaryPaymentBySalary(PaymentVO paymentVo, List<SalaryTO> salaryList) {
		List<PaymentDetailTO> paymentDetailTOList = paymentService.genPaymentDetail(paymentVo);
		if(!salaryList.isEmpty() && !paymentDetailTOList.isEmpty()) {
			for(SalaryTO salaryTO : salaryList) {
				for(PaymentDetailTO paymentDetailTO : paymentDetailTOList) {
					SalaryPaymentRsTO paymentRsTO = new SalaryPaymentRsTO();
					paymentRsTO.setSalaryTO(salaryTO);
					paymentRsTO.setPaymentDetailTO(paymentDetailTO);
					if(salaryTO.getSalaryPaymentRsTOSet() == null) 
						salaryTO.setSalaryPaymentRsTOSet(new HashSet<SalaryPaymentRsTO>());
					
					salaryTO.getSalaryPaymentRsTOSet().add(paymentRsTO);
				}
				salaryTO.setStatus(ExpenseStatusEnum.PAID.toString());
			}
		}
		salaryDAO.save(salaryList);
	}
	
	public void saveBonusPaymentByBonusId(PaymentVO paymentVo, List<Long> bonusidList) {
		List<BonusTO> bonusList = bonusDAO.findByBonusIdIn(bonusidList);
		saveBonusPaymentByBonus(paymentVo, bonusList);
		
	}
	
	public void saveBonusPaymentByBonus(PaymentVO paymentVo, List<BonusTO> bonusList) {
		List<PaymentDetailTO> paymentDetailTOList = paymentService.genPaymentDetail(paymentVo);
		if(!bonusList.isEmpty() && !paymentDetailTOList.isEmpty()) {
			for(BonusTO bonusTO : bonusList) {
				for(PaymentDetailTO paymentDetailTO : paymentDetailTOList) {
					BonusPaymentRsTO paymentRsTO = new BonusPaymentRsTO();
					paymentRsTO.setBonusTO(bonusTO);
					paymentRsTO.setPaymentDetailTO(paymentDetailTO);
					if(bonusTO.getBonusPaymentRsTOSet() == null) 
						bonusTO.setBonusPaymentRsTOSet(new HashSet<BonusPaymentRsTO>());
					
					bonusTO.getBonusPaymentRsTOSet().add(paymentRsTO);
				}
				bonusTO.setStatus(ExpenseStatusEnum.PAID.toString());
				
			}
		}
		bonusDAO.save(bonusList);
	}
	
	public void saveSalaryBonusPayment(PaymentVO paymentVo, List<Long> salaryidList, List<Long> bonusidList) {
		List<PaymentDetailTO> paymentDetailTOList = paymentService.genPaymentDetail(paymentVo);
		List<SalaryTO> salaryList = salaryDAO.findBySalaryIdIn(salaryidList);
		List<BonusTO> bonusList = bonusDAO.findByBonusIdIn(bonusidList);
		
		if(!salaryList.isEmpty() && !paymentDetailTOList.isEmpty()) {
			for(SalaryTO salaryTO : salaryList) {
				for(PaymentDetailTO paymentDetailTO : paymentDetailTOList) {
					SalaryPaymentRsTO paymentRsTO = new SalaryPaymentRsTO();
					paymentRsTO.setSalaryTO(salaryTO);
					paymentRsTO.setPaymentDetailTO(paymentDetailTO);
					if(salaryTO.getSalaryPaymentRsTOSet() == null) 
						salaryTO.setSalaryPaymentRsTOSet(new HashSet<SalaryPaymentRsTO>());
					
					salaryTO.getSalaryPaymentRsTOSet().add(paymentRsTO);
				}
				salaryTO.setStatus(ExpenseStatusEnum.PAID.toString());
				
			}
		}
		
		if(!bonusList.isEmpty() && !paymentDetailTOList.isEmpty()) {
			for(BonusTO bonusTO : bonusList) {
				for(PaymentDetailTO paymentDetailTO : paymentDetailTOList) {
					BonusPaymentRsTO paymentRsTO = new BonusPaymentRsTO();
					paymentRsTO.setBonusTO(bonusTO);
					paymentRsTO.setPaymentDetailTO(paymentDetailTO);
					if(bonusTO.getBonusPaymentRsTOSet() == null) 
						bonusTO.setBonusPaymentRsTOSet(new HashSet<BonusPaymentRsTO>());
					
					bonusTO.getBonusPaymentRsTOSet().add(paymentRsTO);
				}
				bonusTO.setStatus(ExpenseStatusEnum.PAID.toString());
				
			}
		}
		salaryDAO.save(salaryList);
		bonusDAO.save(bonusList);
	}
	
	public List<SalaryBonusVO> convertSalaryToSalaryBonusVOList(List<SalaryTO> toList) {
		List<SalaryBonusVO> voList = new ArrayList<SalaryBonusVO>();
		if(toList != null && !toList.isEmpty()){
			for(SalaryTO to : toList){
				SalaryBonusVO vo = new SalaryBonusVO();
				vo.setId(to.getSalaryId());
				vo.setDate(to.getSalaryDate());
				vo.setDateString(GeneralUtils.convertDateToString(to.getSalaryDate(), "MM-yyyy"));
				vo.setEmployeeVO(employeeService.convertToEmployeeVOList(Arrays.asList(to.getEmployeeTO())).get(0));
				vo.setBasicSalaryAmt(to.getBasicSalaryAmt());
				vo.setOverTimeAmt(to.getOverTimeAmt());
				vo.setOverTimeHours(to.getOverTimeHours());
				vo.setOverTimeRemarks(to.getOverTimeRemarks());
				vo.setAllowance(to.getAllowance());
				vo.setMedical(to.getMedical());
				vo.setEmployeeCpf(to.getEmployeeCpf());
				vo.setEmployerCpf(to.getEmployerCpf());
				vo.setLeaveBalance(to.getLeaveBalance());
				vo.setLeaveTaken(to.getLeaveTaken());
				vo.setUnpaidLeaveAmt(to.getUnpaidLeaveAmt());
				vo.setUnpaidLeaveRemarks(to.getUnpaidLeaveRemarks());
				vo.setCdacAmt(to.getCdacAmt());
				vo.setSdlAmt(to.getSdlAmt());
				vo.setFwLevy(to.getFwLevy());
				vo.setGrossAmt(calculateGrossAmount(to));
				vo.setGrossAmtString("$"+vo.getGrossAmt());
				vo.setTakehomeAmt(calculateTakeHomeAmount(vo, to));
				vo.setTakehomeAmtString("$"+vo.getTakehomeAmt());
				vo.setType(GeneralUtils.TYPE_SALARY);
				vo.setStatus(to.getStatus());
				if(to.getSalaryPaymentRsTOSet() != null)
					vo.setPaymentRsVOList(paymentService.convertToPaymentRsVOList(new ArrayList<SalaryPaymentRsTO>(to.getSalaryPaymentRsTOSet())));
				voList.add(vo);
			}
		}
		return voList;
	}
	
	public List<SalaryBonusVO> convertBonusToSalaryBonusVOList(List<BonusTO> toList) {
		List<SalaryBonusVO> voList = new ArrayList<SalaryBonusVO>();
		if(toList != null && !toList.isEmpty()){
			for(BonusTO to : toList) {
				SalaryBonusVO vo = new SalaryBonusVO();
				vo.setId(to.getBonusId());
				vo.setDate(to.getBonusDate());
				vo.setDateString(GeneralUtils.convertDateToString(to.getBonusDate(), "yyyy"));
				vo.setEmployeeVO(employeeService.convertToEmployeeVOList(Arrays.asList(to.getEmployeeTO())).get(0));
				vo.setBonusAmt(to.getBonusAmt());
				vo.setBonusAmtString("$"+to.getBonusAmt());
				vo.setEmployeeCpf(to.getEmployeeCpf());
				vo.setEmployerCpf(to.getEmployerCpf());
				vo.setTakehomeAmt(calculateTakeHomeAmountBonus(vo, to));
				vo.setTakehomeAmtString("$"+vo.getTakehomeAmt());
				vo.setType(GeneralUtils.TYPE_BONUS);
				vo.setStatus(to.getStatus());
				if(to.getBonusPaymentRsTOSet() != null)
					vo.setPaymentRsVOList(paymentService.convertToPaymentRsVOList(new ArrayList<BonusPaymentRsTO>(to.getBonusPaymentRsTOSet())));
				voList.add(vo);
			}
		}
		return voList;
	}
	
	private List<SalaryTO> convertToSalaryTOList(List<SalaryBonusVO> voList, EmployeeVO employeeVO) {
		List<SalaryTO> toList = new ArrayList<SalaryTO>();
		if(voList != null && !voList.isEmpty()) {
			List<Long> idList = GeneralUtils.convertListToLongList(voList, "salaryId", false);
			Map<Long, SalaryTO> salaryTOMap = new HashMap<Long, SalaryTO>();
			if(!idList.isEmpty()){
				List<SalaryTO> salaryTOList = salaryDAO.findBySalaryIdIn(idList);
				salaryTOMap = GeneralUtils.convertListToLongMap(salaryTOList, "salaryId");
			}
			for(SalaryBonusVO vo : voList) {
				SalaryTO to = new SalaryTO();
				SalaryTO dbTO = salaryTOMap.get(vo.getId());
				if(dbTO != null){ //update
					to = dbTO;
				}
				to.setSalaryDate(GeneralUtils.convertStringToDate(vo.getDateString(), "dd/MM/yyyy"));
				to.setEmployeeTO(employeeService.convertToEmployeeTOList(Arrays.asList(employeeVO)).get(0));
				to.setBasicSalaryAmt(vo.getBasicSalaryAmt());
				to.setOverTimeAmt(vo.getOverTimeAmt());
				to.setOverTimeHours(vo.getOverTimeHours());
				to.setOverTimeRemarks(vo.getOverTimeRemarks());
				to.setAllowance(vo.getAllowance());
				to.setMedical(vo.getMedical());
				to.setLeaveBalance(vo.getLeaveBalance());
				to.setLeaveTaken(vo.getLeaveTaken());
				to.setUnpaidLeaveAmt(vo.getUnpaidLeaveAmt());
				to.setUnpaidLeaveRemarks(vo.getUnpaidLeaveRemarks());
				to.setEmployeeCpf(vo.getEmployeeCpf());
				to.setEmployerCpf(vo.getEmployerCpf());
				to.setCdacAmt(vo.getCdacAmt());
				to.setSdlAmt(vo.getSdlAmt());
				to.setFwLevy(vo.getFwLevy());
				to.setStatus(vo.getStatus());
				toList.add(to);
			}
		}
		
		return toList;
	}
	
	private List<BonusTO> convertToBonusTOList(List<SalaryBonusVO> voList, EmployeeVO employeeVO) {
		List<BonusTO> toList = new ArrayList<BonusTO>();
		if(voList != null && !voList.isEmpty()) {
			List<Long> idList = GeneralUtils.convertListToLongList(voList, "bonusId", false);
			Map<Long, BonusTO> bonusTOMap = new HashMap<Long, BonusTO>();
			if(!idList.isEmpty()){
				List<BonusTO> bonusTOList = bonusDAO.findByBonusIdIn(idList);
				bonusTOMap = GeneralUtils.convertListToLongMap(bonusTOList, "bonusId");
			}
			for(SalaryBonusVO vo : voList) {
				BonusTO to = new BonusTO();
				BonusTO dbTO = bonusTOMap.get(vo.getId());
				if(dbTO != null){ //update
					to = dbTO;
				}
				to.setBonusDate(vo.getDate());
				to.setEmployeeTO(employeeService.convertToEmployeeTOList(Arrays.asList(employeeVO)).get(0));
				to.setBonusAmt(vo.getBonusAmt());
				to.setEmployeeCpf(vo.getEmployeeCpf());
				to.setEmployerCpf(vo.getEmployerCpf());
				to.setStatus(vo.getStatus());
				toList.add(to);
			}
		}
		
		return toList;
	}
	
	//calculate gross amount from EmployeeSalary
	//gross amount = basic salary amount + overtime amount + allowance + medical
	private BigDecimal calculateGrossAmount(SalaryTO salary) {
		BigDecimal grossamount = BigDecimal.ZERO;
		if(salary.getBasicSalaryAmt() != null)
			grossamount = grossamount.add(salary.getBasicSalaryAmt());
		if(salary.getOverTimeAmt() != null)
			grossamount = grossamount.add(salary.getOverTimeAmt());
		if(salary.getAllowance() != null)
			grossamount = grossamount.add(salary.getAllowance());
		if(salary.getMedical() != null)
			grossamount = grossamount.add(salary.getMedical());
		if(salary.getUnpaidLeaveAmt() != null){
			grossamount = grossamount.subtract(salary.getUnpaidLeaveAmt());
		}
		return grossamount;
	}
	
	//calculate take home amount from EmployeeSalary and gross amount
	//take home amount = gross amount - employee cpf
	private BigDecimal calculateTakeHomeAmount(SalaryBonusVO vo, SalaryTO to) {
		BigDecimal takehomeamount = vo.getGrossAmt();
		if(to.getEmployeeCpf() != null)
			takehomeamount = takehomeamount.subtract(to.getEmployeeCpf());
		return takehomeamount;
	}
	
	private BigDecimal calculateTakeHomeAmountBonus(SalaryBonusVO vo, BonusTO to) {
		BigDecimal takehomeamount = vo.getBonusAmt();
		if(to.getEmployeeCpf() != null)
			takehomeamount = takehomeamount.subtract(to.getEmployeeCpf());
		return takehomeamount;
	}

	@Override
	public List<SalaryBonusVO> getAllBonusVo() {
		List<BonusTO> bonusList = bonusDAO.findByDeleteInd(GeneralUtils.NOT_DELETED);
		return convertBonusToSalaryBonusVOList(bonusList);
	}

	@Override
	public List<SalaryBonusVO> getAllBonusVo(Date dateAsOf, Date endDate) {
		List<BonusTO> bonusList = bonusDAO.findByBonusDateBetweenAndDeleteIndOrderByBonusDate(dateAsOf, endDate, GeneralUtils.NOT_DELETED);
		return convertBonusToSalaryBonusVOList(bonusList);
	}

	@Override
	public List<SalaryBonusVO> getAllSalaryVo(Date dateAsOf, Date endDate) {
		List<SalaryTO> salaryList = salaryDAO.findBySalaryDateBetweenAndDeleteIndOrderBySalaryDate(dateAsOf, endDate, GeneralUtils.NOT_DELETED);
		return convertSalaryToSalaryBonusVOList(salaryList);
	}
	
}
