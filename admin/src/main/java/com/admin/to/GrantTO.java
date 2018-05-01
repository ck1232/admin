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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "invoice_grant")
public class GrantTO extends BaseTO  {
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "grant_id", nullable=false)
	private Long grantId;

	@Column(name = "organisation", nullable=false)
	private String organisation;
    
	@Column(name = "grant_date", nullable=false)
	private Date grantDate;
	
	@Column(name="total_amt", nullable=false)
	private BigDecimal totalAmt;

	@Column(name="status", nullable=false)
    private String status;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "grantTO", cascade=CascadeType.ALL)
	private Set<GrantPaymentRsTO> grantPaymentRsTOSet;
	
	public Long getGrantId() {
		return grantId;
	}

	public void setGrantId(Long grantId) {
		this.grantId = grantId;
	}

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation == null ? null : organisation.trim();
    }

	public Date getGrantDate() {
		return grantDate;
	}

	public void setGrantDate(Date grantDate) {
		this.grantDate = grantDate;
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

	public Set<GrantPaymentRsTO> getGrantPaymentRsTOSet() {
		return grantPaymentRsTOSet;
	}

	public void setGrantPaymentRsTOSet(Set<GrantPaymentRsTO> grantPaymentRsTOSet) {
		this.grantPaymentRsTOSet = grantPaymentRsTOSet;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", grantId=").append(grantId);
        sb.append(", organisation=").append(organisation);
        sb.append(", grantDate=").append(grantDate);
        sb.append(", totalAmt=").append(totalAmt);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
	
}
