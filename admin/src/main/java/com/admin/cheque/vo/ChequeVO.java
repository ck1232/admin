package com.admin.cheque.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.admin.helper.vo.BaseVO;

public class ChequeVO extends BaseVO implements Serializable {
    private Long chequeId;

    private String chequeNum;

    private Date chequeDate;
    
    private String chequeDateString;

    private BigDecimal chequeAmt;
    
    private String chequeAmtString;

    private Date debitDate;
    
    private String debitDateString;

    private String remarks;

    private String bounceChequeInd;
    
    private Date bounceDate;
    
    private String bounceDateString;
    
    private String paidFor;

    private static final long serialVersionUID = 1L;

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
        this.chequeNum = chequeNum == null ? null : chequeNum.trim();
    }

    public Date getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(Date chequeDate) {
        this.chequeDate = chequeDate;
    }

    public String getChequeDateString() {
		return chequeDateString;
	}

	public void setChequeDateString(String chequeDateString) {
		this.chequeDateString = chequeDateString;
	}

	public BigDecimal getChequeAmt() {
        return chequeAmt;
    }

    public void setChequeAmt(BigDecimal chequeAmt) {
        this.chequeAmt = chequeAmt;
    }

    public String getChequeAmtString() {
		return chequeAmtString;
	}

	public void setChequeAmtString(String chequeAmtString) {
		this.chequeAmtString = chequeAmtString;
	}

	public Date getDebitDate() {
        return debitDate;
    }

    public void setDebitDate(Date debitDate) {
        this.debitDate = debitDate;
    }

    public String getDebitDateString() {
		return debitDateString;
	}

	public void setDebitDateString(String debitDateString) {
		this.debitDateString = debitDateString;
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
        this.bounceChequeInd = bounceChequeInd == null ? null : bounceChequeInd.trim();
    }

    public Date getBounceDate() {
		return bounceDate;
	}

	public void setBounceDate(Date bounceDate) {
		this.bounceDate = bounceDate;
	}

	public String getBounceDateString() {
		return bounceDateString;
	}

	public void setBounceDateString(String bounceDateString) {
		this.bounceDateString = bounceDateString;
	}

	public String getPaidFor() {
		return paidFor;
	}

	public void setPaidFor(String paidFor) {
		this.paidFor = paidFor == null ? null : paidFor.trim();
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
        sb.append(", chequeAmtString=").append(chequeAmtString);
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