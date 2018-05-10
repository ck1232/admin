package com.admin.to;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Where;

@Entity
@DynamicUpdate
@Table(name = "employee_salary")
public class SalaryTO extends BaseTO implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "salary_id", nullable=false)
    private Long salaryId;

	@Column(name = "salary_date", nullable=false)
    private Date salaryDate;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id", nullable=false)
	@Where(clause="delete_ind='N'")
	@ForeignKey( name = "none" )
	private EmployeeTO employeeTO;

	@Column(name = "basic_salary_amt", nullable=false)
    private BigDecimal basicSalaryAmt;

	@Column(name = "over_time_amt", nullable=true)
    private BigDecimal overTimeAmt;

	@Column(name = "over_time_hours", nullable=true)
    private BigDecimal overTimeHours;

	@Column(name = "over_time_remarks", nullable=true)
    private String overTimeRemarks;

	@Column(name = "allowance", nullable=true)
    private BigDecimal allowance;

	@Column(name = "medical", nullable=true)
    private BigDecimal medical;
	
	@Column(name = "leave_balance", nullable=true)
	private BigDecimal leaveBalance;

	@Column(name = "leave_taken", nullable=true)
    private BigDecimal leaveTaken;

	@Column(name = "unpaid_leave_amt", nullable=true)
    private BigDecimal unpaidLeaveAmt;

	@Column(name = "unpaid_leave_remarks", nullable=true)
    private String unpaidLeaveRemarks;

	@Column(name = "employee_cpf", nullable=true)
    private BigDecimal employeeCpf;

	@Column(name = "employer_cpf", nullable=true)
    private BigDecimal employerCpf;

	@Column(name = "cdac_amt", nullable=true)
    private BigDecimal cdacAmt;

	@Column(name = "sdl_amt", nullable=true)
    private BigDecimal sdlAmt;
	
	@Column(name = "fw_levy", nullable=true)
    private BigDecimal fwLevy;

	@Column(name = "status", nullable=false)
    private String status;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "salaryTO", cascade=CascadeType.ALL)
	private Set<SalaryPaymentRsTO> salaryPaymentRsTOSet;

    private static final long serialVersionUID = 1L;

    public Long getSalaryId() {
        return salaryId;
    }

    public void setSalaryId(Long salaryId) {
        this.salaryId = salaryId;
    }

    public Date getSalaryDate() {
        return salaryDate;
    }

    public void setSalaryDate(Date salaryDate) {
        this.salaryDate = salaryDate;
    }

    public EmployeeTO getEmployeeTO() {
		return employeeTO;
	}

	public void setEmployeeTO(EmployeeTO employeeTO) {
		this.employeeTO = employeeTO;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Set<SalaryPaymentRsTO> getSalaryPaymentRsTOSet() {
		return salaryPaymentRsTOSet;
	}

	public void setSalaryPaymentRsTOSet(Set<SalaryPaymentRsTO> salaryPaymentRsTOSet) {
		this.salaryPaymentRsTOSet = salaryPaymentRsTOSet;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", salaryId=").append(salaryId);
        sb.append(", salaryDate=").append(salaryDate);
        sb.append(", employeeId=").append(employeeTO.getEmployeeId());
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