package com.admin.to;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "employee")
public class EmployeeTO extends BaseTO  {
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "employee_id", nullable=false)
	private Long employeeId;

	@Column(name = "name", nullable=false)
	private String name;
    
	@Column(name = "employee_type", nullable=false)
	private String employeeType;
	
	@Column(name="dob", nullable=true)
	private Date dob;

	@Column(name="nationality", nullable=false)
    private String nationality;
	
	@Column(name="basic_salary", nullable=false)
    private BigDecimal basicSalary;
	
	@Column(name="employment_start_date", nullable=true)
    private Date employmentStartDate;
	
	@Column(name="employment_end_date", nullable=true)
    private Date employmentEndDate;
	
	@Column(name="cdac_ind", nullable=false)
    private String cdacInd;
	
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
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

    public Date getEmploymentStartDate() {
        return employmentStartDate;
    }

    public void setEmploymentStartDate(Date employmentStartDate) {
        this.employmentStartDate = employmentStartDate;
    }

    public Date getEmploymentEndDate() {
        return employmentEndDate;
    }

    public void setEmploymentEndDate(Date employmentEndDate) {
        this.employmentEndDate = employmentEndDate;
    }

    public String getCdacInd() {
        return cdacInd;
    }

    public void setCdacInd(String cdacInd) {
        this.cdacInd = cdacInd == null ? null : cdacInd.trim();
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
