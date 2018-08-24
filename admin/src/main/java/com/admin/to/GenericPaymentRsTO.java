package com.admin.to;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name = "payment_rs")
public class GenericPaymentRsTO extends PaymentRsTO {
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Column(name = "reference_id", nullable=false)
	private Long referenceId;
	
	@Column(name = "reference_type", nullable=false, insertable = false, updatable = false)
	private String referenceType;

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
}
