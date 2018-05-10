package com.admin.to;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

@Entity
@DiscriminatorValue("bonus")
public class BonusPaymentRsTO extends PaymentRsTO {

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reference_id", nullable=false)
	@ForeignKey( name = "none" )
	private BonusTO bonusTO;

	public BonusTO getBonusTO() {
		return bonusTO;
	}

	public void setBonusTO(BonusTO bonusTO) {
		this.bonusTO = bonusTO;
	}

}
