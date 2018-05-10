package com.admin.to;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

@Entity
@DiscriminatorValue("salary")
public class SalaryPaymentRsTO extends PaymentRsTO {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reference_id", nullable=false)
	@ForeignKey( name = "none" )
	private SalaryTO salaryTO;

	public SalaryTO getSalaryTO() {
		return salaryTO;
	}

	public void setSalaryTO(SalaryTO salaryTO) {
		this.salaryTO = salaryTO;
	}



}
