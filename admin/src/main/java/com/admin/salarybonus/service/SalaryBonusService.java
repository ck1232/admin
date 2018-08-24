package com.admin.salarybonus.service;

import java.util.Date;
import java.util.List;

import com.admin.payment.vo.PaymentVO;
import com.admin.salarybonus.vo.SalaryBonusVO;
import com.admin.to.BonusTO;
import com.admin.to.SalaryTO;

public interface SalaryBonusService {

	List<SalaryBonusVO> convertBonusToSalaryBonusVOList(List<BonusTO> asList);

	List<SalaryBonusVO> convertSalaryToSalaryBonusVOList(List<SalaryTO> asList);

	void deleteSalaryBonus(List<String> ids);

	SalaryBonusVO findBonusById(Long valueOf);

	List<SalaryBonusVO> findBonusByIdList(List<Long> idList);

	SalaryBonusVO findSalaryById(Long valueOf);

	List<SalaryBonusVO> findSalaryByIdList(List<Long> idList);

	List<SalaryBonusVO> getAllSalaryByEmpId(Long employeeId);

	List<SalaryBonusVO> getAllBonusByEmpId(Long employeeId);

	List<SalaryBonusVO> getAllSalaryBonusList();

	void saveBonusPaymentByBonus(PaymentVO paymentVo, List<BonusTO> asList);

	void saveBonusPaymentByBonusId(PaymentVO paymentVo, List<Long> idLongList);

	Long saveSalaryBonus(SalaryBonusVO salaryBonusVO);

	void saveSalaryBonusPayment(PaymentVO paymentVo, List<Long> salaryIdList, List<Long> bonusIdList);

	void saveSalaryPaymentBySalary(PaymentVO paymentVo, List<SalaryTO> asList);

	void saveSalaryPaymentBySalaryId(PaymentVO paymentVo, List<Long> idLongList);

	void updateSalaryBonus(SalaryBonusVO salaryBonusVO);

	List<SalaryBonusVO> getAllBonusVo();

	List<SalaryBonusVO> getAllBonusVo(Date dateAsOf, Date endDate);

	List<SalaryBonusVO> getAllSalaryVo(Date dateAsOf, Date endDate);

}
