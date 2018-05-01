package com.admin.to;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

@Entity
@DiscriminatorValue("invoice")
public class InvoicePaymentRsTO extends PaymentRsTO {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reference_id", nullable=false)
	@ForeignKey( name = "none" )
	private InvoiceTO invoiceTO;

	public InvoiceTO getInvoiceTO() {
		return invoiceTO;
	}

	public void setInvoiceTO(InvoiceTO invoiceTO) {
		this.invoiceTO = invoiceTO;
	}

	

}
