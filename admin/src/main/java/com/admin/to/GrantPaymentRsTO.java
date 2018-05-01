package com.admin.to;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

@Entity
@DiscriminatorValue("grant")
public class GrantPaymentRsTO extends PaymentRsTO {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reference_id", nullable=false)
	@ForeignKey( name = "none" )
	private GrantTO grantTO;

	public GrantTO getGrantTO() {
		return grantTO;
	}

	public void setGrantTO(GrantTO grantTO) {
		this.grantTO = grantTO;
	}

}
