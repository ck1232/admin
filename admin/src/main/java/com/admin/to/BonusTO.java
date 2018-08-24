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
@Table(name = "employee_bonus")
public class BonusTO extends BaseTO implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "bonus_id", nullable=false)
    private Long bonusId;

	@Column(name = "bonus_date", nullable=false)
    private Date bonusDate;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id", nullable=false)
	@Where(clause="delete_ind='N'")
	@ForeignKey( name = "none" )
	private EmployeeTO employeeTO;

	@Column(name = "bonus_amt", nullable=false)
    private BigDecimal bonusAmt;

	@Column(name = "employee_cpf", nullable=true)
    private BigDecimal employeeCpf;

	@Column(name = "employer_cpf", nullable=true)
    private BigDecimal employerCpf;

	@Column(name = "status", nullable=true)
    private String status;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "bonusTO", cascade=CascadeType.ALL)
	private Set<BonusPaymentRsTO> bonusPaymentRsTOSet;

    private static final long serialVersionUID = 1L;

    public Long getBonusId() {
        return bonusId;
    }

    public void setBonusId(Long bonusId) {
        this.bonusId = bonusId;
    }

    public Date getBonusDate() {
        return bonusDate;
    }

    public void setBonusDate(Date bonusDate) {
        this.bonusDate = bonusDate;
    }

    public EmployeeTO getEmployeeTO() {
		return employeeTO;
	}

	public void setEmployeeTO(EmployeeTO employeeTO) {
		this.employeeTO = employeeTO;
	}

	public BigDecimal getBonusAmt() {
        return bonusAmt;
    }

    public void setBonusAmt(BigDecimal bonusAmt) {
        this.bonusAmt = bonusAmt;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Set<BonusPaymentRsTO> getBonusPaymentRsTOSet() {
		return bonusPaymentRsTOSet;
	}

	public void setBonusPaymentRsTOSet(Set<BonusPaymentRsTO> bonusPaymentRsTOSet) {
		this.bonusPaymentRsTOSet = bonusPaymentRsTOSet;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", bonusId=").append(bonusId);
        sb.append(", bonusDate=").append(bonusDate);
        sb.append(", employeeId=").append(employeeTO.getEmployeeId());
        sb.append(", bonusAmt=").append(bonusAmt);
        sb.append(", employeeCpf=").append(employeeCpf);
        sb.append(", employerCpf=").append(employerCpf);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}