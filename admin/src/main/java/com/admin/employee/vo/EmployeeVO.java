package com.admin.employee.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.admin.helper.vo.BaseVO;
import com.admin.validator.annotation.ValidDate;

public class EmployeeVO extends BaseVO implements Serializable {
    private Long employeeId;
    
    @NotEmpty(message="error.notempty.employeeform.name")
    private String name;
    
    @NotEmpty(message="error.notempty.employeeform.employmenttype")
    private String employeeType;
    
    private String employeeTypeString;

    private Date dob;
    
    @ValidDate(dateFormat="dd/MM/yyyy", nullable=true, message="error.notvalid.employeeform.startdate")
    private String dobString;

    @NotEmpty(message="error.notempty.employeeform.nationality")
    private String nationality;
    
    @NotNull(message="error.notempty.employeeform.basicsalary")
    @Digits(integer=6, fraction=2, message="error.decimal.twodecimcalpoint")
    @Min(value=0L, message="error.negative.employeeform.basicsalary")
    private BigDecimal basicSalary;
    
    private String basicSalaryString;

    private Date employmentStartDate;
    
    @ValidDate(dateFormat="dd/MM/yyyy", nullable=true, message="error.notvalid.employeeform.startdate")
    private String employmentStartDateString;

    private Date employmentEndDate;
    
    @ValidDate(dateFormat="dd/MM/yyyy", nullable=true, message="error.notvalid.employeeform.enddate")
    private String employmentEndDateString;

    private String cdacInd;
    
    private Boolean cdacIndBoolean;

    private static final long serialVersionUID = 1L;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType == null ? null : employeeType.trim();
    }

    public String getEmployeeTypeString() {
		return employeeTypeString;
	}

	public void setEmployeeTypeString(String employeeTypeString) {
		this.employeeTypeString = employeeTypeString == null ? null : employeeTypeString.trim();
	}

	public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getDobString() {
		return dobString;
	}

	public void setDobString(String dobString) {
		this.dobString = dobString == null ? null : dobString.trim();
	}

	public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality == null ? null : nationality.trim();
    }

    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }

    public String getBasicSalaryString() {
		return basicSalaryString;
	}

	public void setBasicSalaryString(String basicSalaryString) {
		this.basicSalaryString = basicSalaryString;
	}

	public Date getEmploymentStartDate() {
        return employmentStartDate;
    }

    public void setEmploymentStartDate(Date employmentStartDate) {
        this.employmentStartDate = employmentStartDate;
    }

    public String getEmploymentStartDateString() {
		return employmentStartDateString;
	}

	public void setEmploymentStartDateString(String employmentStartDateString) {
		this.employmentStartDateString = employmentStartDateString == null ? null : employmentStartDateString.trim();
	}

	public Date getEmploymentEndDate() {
        return employmentEndDate;
    }

    public void setEmploymentEndDate(Date employmentEndDate) {
        this.employmentEndDate = employmentEndDate;
    }

    public String getEmploymentEndDateString() {
		return employmentEndDateString;
	}

	public void setEmploymentEndDateString(String employmentEndDateString) {
		this.employmentEndDateString = employmentEndDateString == null ? null : employmentEndDateString.trim();
	}

	public String getCdacInd() {
        return cdacInd;
    }

    public void setCdacInd(String cdacInd) {
        this.cdacInd = cdacInd == null ? null : cdacInd.trim();
    }

    public Boolean getCdacIndBoolean() {
		return cdacIndBoolean;
	}

	public void setCdacIndBoolean(Boolean cdacIndBoolean) {
		this.cdacIndBoolean = cdacIndBoolean;
	}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", employeeId=").append(employeeId);
        sb.append(", name=").append(name);
        sb.append(", employeeType=").append(employeeType);
        sb.append(", dob=").append(dob);
        sb.append(", nationality=").append(nationality);
        sb.append(", basicSalary=").append(basicSalary);
        sb.append(", basicSalaryString=").append(basicSalaryString);
        sb.append(", employmentStartDate=").append(employmentStartDate);
        sb.append(", employmentEndDate=").append(employmentEndDate);
        sb.append(", cdacInd=").append(cdacInd);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}