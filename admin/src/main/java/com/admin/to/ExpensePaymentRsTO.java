package com.admin.to;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

@Entity
@DiscriminatorValue("expense")
public class ExpensePaymentRsTO extends PaymentRsTO {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reference_id", nullable=false)
	@ForeignKey( name = "none" )
	private ExpenseTO expenseTO;

	public ExpenseTO getExpenseTO() {
		return expenseTO;
	}

	public void setExpenseTO(ExpenseTO expenseTO) {
		this.expenseTO = expenseTO;
	}

}
