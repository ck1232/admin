package com.admin.to;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Where;

@Entity
@DynamicUpdate
@Table(name = "payment_detail")
public class PaymentDetailTO extends BaseTO  {
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "payment_detail_id", nullable=false)
	private Long paymentDetailId;

	@Column(name = "payment_date", nullable=false)
	private Date paymentDate;
	
	@Column(name = "payment_mode", nullable=false)
	private Long paymentMode;
	
	@Column(name = "payment_amt", nullable=false)
	private BigDecimal paymentAmt;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cheque_id", nullable=true)
	@Where(clause="delete_ind='N'")
	@ForeignKey( name = "none" )
	private ChequeTO chequeTO;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "paymentDetailTO", cascade=CascadeType.ALL)
	private Set<PaymentRsTO> paymentRsTOSet;

	
	public Long getPaymentDetailId() {
        return paymentDetailId;
    }

    public void setPaymentDetailId(Long paymentDetailId) {
        this.paymentDetailId = paymentDetailId;
    }

    public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Long getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(Long paymentMode) {
		this.paymentMode = paymentMode;
	}

	public BigDecimal getPaymentAmt() {
		return paymentAmt;
	}

	public void setPaymentAmt(BigDecimal paymentAmt) {
		this.paymentAmt = paymentAmt;
	}

	public ChequeTO getChequeTO() {
		return chequeTO;
	}

	public void setChequeTO(ChequeTO chequeTO) {
		this.chequeTO = chequeTO;
	}

	public Set<PaymentRsTO> getPaymentRsTOSet() {
		return paymentRsTOSet;
	}

	public void setPaymentRsTOSet(Set<PaymentRsTO> paymentRsTOSet) {
		this.paymentRsTOSet = paymentRsTOSet;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", paymentDetailId=").append(paymentDetailId);
        sb.append(", paymentDate=").append(paymentDate);
        sb.append(", paymentMode=").append(paymentMode);
        sb.append(", paymentAmt=").append(paymentAmt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}
