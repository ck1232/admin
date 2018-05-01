package com.admin.to;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DiscriminatorOptions;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "payment_rs")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "reference_type", discriminatorType = DiscriminatorType.STRING, columnDefinition = "varchar(255)")
@DiscriminatorOptions(force = true)
public class PaymentRsTO extends BaseTO {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "payment_rs_id", nullable=false)
	private Long paymentRsId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "payment_detail_id", nullable=false)
	@ForeignKey( name = "none" )
	private PaymentDetailTO paymentDetailTO;

	public Long getPaymentRsId() {
		return paymentRsId;
	}

	public void setPaymentRsId(Long paymentRsId) {
		this.paymentRsId = paymentRsId;
	}

	public PaymentDetailTO getPaymentDetailTO() {
		return paymentDetailTO;
	}

	public void setPaymentDetailTO(PaymentDetailTO paymentDetailTO) {
		this.paymentDetailTO = paymentDetailTO;
	}


}
