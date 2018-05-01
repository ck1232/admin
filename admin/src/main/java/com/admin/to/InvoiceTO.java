package com.admin.to;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "invoice")
public class InvoiceTO extends BaseTO  {
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "invoice_id", nullable=false)
	private Long invoiceId;

	@Column(name = "messenger", nullable=false)
	private String messenger;
    
	@Column(name = "invoice_date", nullable=false)
	private Date invoiceDate;
	
	@Column(name="total_amt", nullable=false)
	private BigDecimal totalAmt;

	@Column(name="status", nullable=false)
    private String status;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "invoiceTO", cascade=CascadeType.ALL)
	private Set<InvoicePaymentRsTO> invoicePaymentRsTOSet;
	
	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger == null ? null : messenger.trim();
    }

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public BigDecimal getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(BigDecimal totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}


	public Set<InvoicePaymentRsTO> getInvoicePaymentRsTOSet() {
		return invoicePaymentRsTOSet;
	}

	public void setInvoicePaymentRsTOSet(Set<InvoicePaymentRsTO> invoicePaymentRsTOSet) {
		this.invoicePaymentRsTOSet = invoicePaymentRsTOSet;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", invoiceId=").append(invoiceId);
        sb.append(", messenger=").append(messenger);
        sb.append(", invoiceDate=").append(invoiceDate);
        sb.append(", totalAmt=").append(totalAmt);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
	
}
