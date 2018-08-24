package com.admin.to;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

@Entity
@DiscriminatorValue("expense")
public class ExpensePaymentRsTO extends PaymentRsTO {
	@Transient
	private static final long serialVersionUID = 1L;
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
