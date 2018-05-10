package com.admin.salarybonus.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.admin.employee.vo.EmployeeVO;
import com.admin.helper.vo.BaseVO;
import com.admin.payment.vo.PaymentDetailVO;
import com.admin.payment.vo.PaymentRsVO;
import com.admin.validator.annotation.ValidDate;

public class SalaryBonusVO extends BaseVO implements Serializable {
    private Long id;

    private Date date;
    @NotNull(message="error.notempty.salarybonusform.date")
    @ValidDate(dateFormat="dd/MM/yyyy", message="error.notvalid.salarybonusform.date")
    private String dateString;
    
    private EmployeeVO employeeVO;
    
    @Digits(integer=6, fraction=2, message="error.decimal.twodecimcalpoint")
    @Min(value=0L, message="error.negative.salarybonusform.basicsalary")
    private BigDecimal basicSalaryAmt;

    private BigDecimal overTimeAmt;

    private BigDecimal overTimeHours;

    private String overTimeRemarks;

    private BigDecimal allowance;
    
    private BigDecimal medical;
    
    private BigDecimal leaveBalance;

    private BigDecimal leaveTaken;

    private BigDecimal unpaidLeaveAmt;

    private String unpaidLeaveRemarks;

    private BigDecimal employeeCpf;

    private BigDecimal employerCpf;

    private BigDecimal cdacAmt;

    private BigDecimal sdlAmt;

    private BigDecimal fwLevy;
    
    private BigDecimal grossAmt;
    
    private String grossAmtString;
    
	private BigDecimal takehomeAmt;
	
	private String takehomeAmtString;
	
	@Digits(integer=6, fraction=2, message="error.decimal.twodecimcalpoint")
	@Min(value=0L, message="error.negative.salarybonusform.bonusamount")
	private BigDecimal bonusAmt;
	private String bonusAmtString;
	@NotEmpty(message="error.notempty.salarybonusform.type")
	private String type;

    private String status;
    
    private List<PaymentRsVO> paymentRsVOList;
    
    private List<PaymentDetailVO> paymentDetailVOList;

    private static final long serialVersionUID = 1L;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

    public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString == null ? null : dateString.trim();
	}

	public EmployeeVO getEmployeeVO() {
		return employeeVO;
	}

	public void setEmployeeVO(EmployeeVO employeeVO) {
		this.employeeVO = employeeVO;
	}

	public BigDecimal getBasicSalaryAmt() {
        return basicSalaryAmt;
    }

    public void setBasicSalaryAmt(BigDecimal basicSalaryAmt) {
        this.basicSalaryAmt = basicSalaryAmt;
    }

    public BigDecimal getOverTimeAmt() {
        return overTimeAmt;
    }

    public void setOverTimeAmt(BigDecimal overTimeAmt) {
        this.overTimeAmt = overTimeAmt;
    }

    public BigDecimal getOverTimeHours() {
        return overTimeHours;
    }

    public void setOverTimeHours(BigDecimal overTimeHours) {
        this.overTimeHours = overTimeHours;
    }

    public String getOverTimeRemarks() {
        return overTimeRemarks;
    }

    public void setOverTimeRemarks(String overTimeRemarks) {
        this.overTimeRemarks = overTimeRemarks == null ? null : overTimeRemarks.trim();
    }

    public BigDecimal getAllowance() {
        return allowance;
    }

    public void setAllowance(BigDecimal allowance) {
        this.allowance = allowance;
    }

    public BigDecimal getMedical() {
		return medical;
	}

	public void setMedical(BigDecimal medical) {
		this.medical = medical;
	}

	public BigDecimal getLeaveBalance() {
		return leaveBalance;
	}

	public void setLeaveBalance(BigDecimal leaveBalance) {
		this.leaveBalance = leaveBalance;
	}

	public BigDecimal getLeaveTaken() {
		return leaveTaken;
	}

	public void setLeaveTaken(BigDecimal leaveTaken) {
		this.leaveTaken = leaveTaken;
	}

	public BigDecimal getUnpaidLeaveAmt() {
        return unpaidLeaveAmt;
    }

    public void setUnpaidLeaveAmt(BigDecimal unpaidLeaveAmt) {
        this.unpaidLeaveAmt = unpaidLeaveAmt;
    }

    public String getUnpaidLeaveRemarks() {
        return unpaidLeaveRemarks;
    }

    public void setUnpaidLeaveRemarks(String unpaidLeaveRemarks) {
        this.unpaidLeaveRemarks = unpaidLeaveRemarks == null ? null : unpaidLeaveRemarks.trim();
    }

    public BigDecimal getEmployeeCpf() {
        return employeeCpf;
    }

    public void setEmployeeCpf(BigDecimal employeeCpf) {
        this.employeeCpf = employeeCpf;
    }

    public BigDecimal getEmployerCpf() {
        return employerCpf;
    }

    public void setEmployerCpf(BigDecimal employerCpf) {
        this.employerCpf = employerCpf;
    }

    public BigDecimal getCdacAmt() {
        return cdacAmt;
    }

    public void setCdacAmt(BigDecimal cdacAmt) {
        this.cdacAmt = cdacAmt;
    }

    public BigDecimal getSdlAmt() {
        return sdlAmt;
    }

    public void setSdlAmt(BigDecimal sdlAmt) {
        this.sdlAmt = sdlAmt;
    }

    public BigDecimal getFwLevy() {
        return fwLevy;
    }

    public void setFwLevy(BigDecimal fwLevy) {
        this.fwLevy = fwLevy;
    }

    public BigDecimal getGrossAmt() {
		return grossAmt;
	}

	public void setGrossAmt(BigDecimal grossAmt) {
		this.grossAmt = grossAmt;
	}

	public String getGrossAmtString() {
		return grossAmtString;
	}

	public void setGrossAmtString(String grossAmtString) {
		this.grossAmtString = grossAmtString;
	}

	public BigDecimal getTakehomeAmt() {
		return takehomeAmt;
	}

	public void setTakehomeAmt(BigDecimal takehomeAmt) {
		this.takehomeAmt = takehomeAmt;
	}

	public String getTakehomeAmtString() {
		return takehomeAmtString;
	}

	public void setTakehomeAmtString(String takehomeAmtString) {
		this.takehomeAmtString = takehomeAmtString;
	}

	public BigDecimal getBonusAmt() {
		return bonusAmt;
	}

	public void setBonusAmt(BigDecimal bonusAmt) {
		this.bonusAmt = bonusAmt;
	}

	public String getBonusAmtString() {
		return bonusAmtString;
	}

	public void setBonusAmtString(String bonusAmtString) {
		this.bonusAmtString = bonusAmtString;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public List<PaymentRsVO> getPaymentRsVOList() {
		return paymentRsVOList;
	}

	public void setPaymentRsVOList(List<PaymentRsVO> paymentRsVOList) {
		this.paymentRsVOList = paymentRsVOList;
	}

	public List<PaymentDetailVO> getPaymentDetailVOList() {
		paymentDetailVOList = new ArrayList<PaymentDetailVO>();
		if(paymentRsVOList != null && !paymentRsVOList.isEmpty()) {
			for(PaymentRsVO paymentRsVO : paymentRsVOList) {
				PaymentDetailVO paymentDetailVO = paymentRsVO.getPaymentDetailVO();
				if(paymentDetailVO != null)
					paymentDetailVOList.add(paymentDetailVO);
			}
		}
		return paymentDetailVOList;
	}

	public void setPaymentDetailVOList(List<PaymentDetailVO> paymentDetailVOList) {
		this.paymentDetailVOList = paymentDetailVOList;
	}
	
	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", employeeId=").append(employeeVO.getEmployeeId());
        sb.append(", basicSalaryAmt=").append(basicSalaryAmt);
        sb.append(", overTimeAmt=").append(overTimeAmt);
        sb.append(", overTimeHours=").append(overTimeHours);
        sb.append(", overTimeRemarks=").append(overTimeRemarks);
        sb.append(", allowance=").append(allowance);
        sb.append(", medical=").append(medical);
        sb.append(", leaveBalance=").append(leaveBalance);
        sb.append(", leaveTaken=").append(leaveTaken);
        sb.append(", unpaidLeaveAmt=").append(unpaidLeaveAmt);
        sb.append(", unpaidLeaveRemarks=").append(unpaidLeaveRemarks);
        sb.append(", employeeCpf=").append(employeeCpf);
        sb.append(", employerCpf=").append(employerCpf);
        sb.append(", cdacAmt=").append(cdacAmt);
        sb.append(", sdlAmt=").append(sdlAmt);
        sb.append(", fwLevy=").append(fwLevy);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}