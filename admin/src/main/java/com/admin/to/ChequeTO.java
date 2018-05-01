package com.admin.to;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "cheque")
public class ChequeTO extends BaseTO  {
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cheque_id", nullable=false)
	private Long chequeId;

	@Column(name = "cheque_num", nullable=false)
	private String chequeNum;
    
	@Column(name = "cheque_date", nullable=false)
	private Date chequeDate;
	
	@Column(name = "cheque_amt", nullable=false)
	private BigDecimal chequeAmt;
	
	@Column(name = "debit_date", nullable=true)
	private Date debitDate;
	
	@Column(name = "remarks", nullable=true)
	private String remarks;
	
	@Column(name = "bounce_cheque_ind", nullable=false)
	private String bounceChequeInd;
	
	@Column(name = "bounce_date", nullable=true)
	private Date bounceDate;
	
	
	public Long getChequeId() {
		return chequeId;
	}

	public void setChequeId(Long chequeId) {
		this.chequeId = chequeId;
	}

	public String getChequeNum() {
		return chequeNum;
	}

	public void setChequeNum(String chequeNum) {
		this.chequeNum = chequeNum;
	}

	public Date getChequeDate() {
		return chequeDate;
	}

	public void setChequeDate(Date chequeDate) {
		this.chequeDate = chequeDate;
	}

	public BigDecimal getChequeAmt() {
		return chequeAmt;
	}

	public void setChequeAmt(BigDecimal chequeAmt) {
		this.chequeAmt = chequeAmt;
	}

	public Date getDebitDate() {
		return debitDate;
	}

	public void setDebitDate(Date debitDate) {
		this.debitDate = debitDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks == null ? null : remarks.trim();
	}

	public String getBounceChequeInd() {
		return bounceChequeInd;
	}

	public void setBounceChequeInd(String bounceChequeInd) {
		this.bounceChequeInd = bounceChequeInd;
	}

	public Date getBounceDate() {
		return bounceDate;
	}

	public void setBounceDate(Date bounceDate) {
		this.bounceDate = bounceDate;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", chequeId=").append(chequeId);
        sb.append(", chequeNum=").append(chequeNum);
        sb.append(", chequeDate=").append(chequeDate);
        sb.append(", chequeAmt=").append(chequeAmt);
        sb.append(", debitDate=").append(debitDate);
        sb.append(", remarks=").append(remarks);
        sb.append(", bounceChequeInd=").append(bounceChequeInd);
        sb.append(", bounceDate=").append(bounceDate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
	
}
