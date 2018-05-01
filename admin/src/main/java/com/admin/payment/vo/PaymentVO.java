package com.admin.payment.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

import com.admin.validator.annotation.ValidDate;


public class PaymentVO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String referenceType;
	private Date bounceDate;
	private String bouncedateString;
	private Date paymentDate;
	
	@ValidDate(dateFormat="dd/MM/yyyy", message="error.notvalid.paymentform.paymentdate")
	private String paymentdateString;
	private Boolean paymentmodecash;
	@Digits(integer=6, fraction=2, message="error.decimal.twodecimcalpoint")
    @Min(value=0L, message="error.notvalid.paymentform.cashamount")
	private BigDecimal cashamount;
	private Boolean paymentmodecheque;
	private String chequeId;
	private String chequeno;
	@Digits(integer=6, fraction=2, message="error.decimal.twodecimcalpoint")
    @Min(value=0L, message="error.notvalid.paymentform.chequeamount")
	private BigDecimal chequeamount;
	private Date chequedate;
	@ValidDate(dateFormat="dd/MM/yyyy",nullable=true, message="error.notvalid.paymentform.chequedate")
	private String chequedateString;
	private Boolean paymentmodedirector;
	@Digits(integer=6, fraction=2, message="error.decimal.twodecimcalpoint")
	@Min(value=0L, message="error.notvalid.paymentform.directoramount")
	private BigDecimal directoramount;
	private Boolean paymentmodegiro;
	@Digits(integer=6, fraction=2, message="error.decimal.twodecimcalpoint")
	@Min(value=0L, message="error.notvalid.paymentform.giroamount")
	private BigDecimal giroamount;
	@Digits(integer=6, fraction=2, message="error.decimal.twodecimcalpoint")
	@Min(value=0L, message="error.notvalid.paymentform.paytodirectoramount")
	private BigDecimal paytodirectoramount;
	private Boolean paymentmodePayToDirector;
	private String type;
	
	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public Date getBounceDate() {
		return bounceDate;
	}

	public void setBounceDate(Date bounceDate) {
		this.bounceDate = bounceDate;
	}

	public String getBouncedateString() {
		return bouncedateString;
	}

	public void setBouncedateString(String bouncedateString) {
		this.bouncedateString = bouncedateString;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}
	
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	public String getPaymentdateString() {
		return paymentdateString;
	}
	public void setPaymentdateString(String paymentdateString) {
		this.paymentdateString = paymentdateString;
	}
	public Boolean getPaymentmodecash() {
		return paymentmodecash;
	}
	public void setPaymentmodecash(Boolean paymentmodecash) {
		this.paymentmodecash = paymentmodecash;
	}
	public BigDecimal getCashamount() {
		return cashamount;
	}
	public void setCashamount(BigDecimal cashamount) {
		this.cashamount = cashamount;
	}

	public Boolean getPaymentmodecheque() {
		return paymentmodecheque;
	}

	public void setPaymentmodecheque(Boolean paymentmodecheque) {
		this.paymentmodecheque = paymentmodecheque;
	}

	public String getChequeId() {
		return chequeId;
	}

	public void setChequeId(String chequeId) {
		this.chequeId = chequeId;
	}

	public String getChequeno() {
		return chequeno;
	}

	public void setChequeno(String chequeno) {
		this.chequeno = chequeno;
	}

	public BigDecimal getChequeamount() {
		return chequeamount;
	}

	public void setChequeamount(BigDecimal chequeamount) {
		this.chequeamount = chequeamount;
	}

	public Date getChequedate() {
		return chequedate;
	}

	public void setChequedate(Date chequedate) {
		this.chequedate = chequedate;
	}

	public String getChequedateString() {
		return chequedateString;
	}

	public void setChequedateString(String chequedateString) {
		this.chequedateString = chequedateString;
	}
	
	public Boolean getPaymentmodedirector() {
		return paymentmodedirector;
	}

	public void setPaymentmodedirector(Boolean paymentmodedirector) {
		this.paymentmodedirector = paymentmodedirector;
	}

	public BigDecimal getDirectoramount() {
		return directoramount;
	}

	public void setDirectoramount(BigDecimal directoramount) {
		this.directoramount = directoramount;
	}

	public Boolean getPaymentmodegiro() {
		return paymentmodegiro;
	}

	public void setPaymentmodegiro(Boolean paymentmodegiro) {
		this.paymentmodegiro = paymentmodegiro;
	}

	public BigDecimal getGiroamount() {
		return giroamount;
	}

	public void setGiroamount(BigDecimal giroamount) {
		this.giroamount = giroamount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getPaytodirectoramount() {
		return paytodirectoramount;
	}

	public void setPaytodirectoramount(BigDecimal paytodirectoramount) {
		this.paytodirectoramount = paytodirectoramount;
	}

	public Boolean getPaymentmodePayToDirector() {
		return paymentmodePayToDirector;
	}

	public void setPaymentmodePayToDirector(Boolean paymentmodePayToDirector) {
		this.paymentmodePayToDirector = paymentmodePayToDirector;
	}

	@Override
	public String toString() {
		return "PaymentVO [referenceType=" + referenceType + ", paymentDate=" + paymentDate + ", paymentdateString="
				+ paymentdateString + ", paymentmodecash=" + paymentmodecash + ", cashamount=" + cashamount
				+ ", paymentmodecheque=" + paymentmodecheque + ", chequeId=" + chequeId + ", chequeno=" + chequeno
				+ ", chequeamount=" + chequeamount + ", chequedate=" + chequedate + ", chequedateString="
				+ chequedateString + ", paymentmodedirector=" + paymentmodedirector + ", directoramount="
				+ directoramount + ", paymentmodegiro=" + paymentmodegiro + ", giroamount=" + giroamount + "]";
	}

}
