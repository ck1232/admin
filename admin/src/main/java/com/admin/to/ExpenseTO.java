package com.admin.to;

import java.io.Serializable;
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

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "expense")
public class ExpenseTO extends BaseTO implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "expense_id", nullable=false)
    private Long expenseId;

	@Column(name = "expense_type_id", nullable=false)
    private Long expenseTypeId;

	@Column(name = "invoice_no", nullable=true)
    private String invoiceNo;

	@Column(name = "description", nullable=true)
    private String description;

	@Column(name = "expense_date", nullable=false)
    private Date expenseDate;

	@Column(name = "supplier", nullable=false)
    private String supplier;

	@Column(name = "total_amt", nullable=false)
    private BigDecimal totalAmt;

	@Column(name = "remarks", nullable=true)
    private String remarks;

	@Column(name = "status", nullable=false)
    private String status;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "expenseTO", cascade=CascadeType.ALL)
	private Set<ExpensePaymentRsTO> expensePaymentRsTOSet;


    private static final long serialVersionUID = 1L;

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public Long getExpenseTypeId() {
        return expenseTypeId;
    }

    public void setExpenseTypeId(Long expenseTypeId) {
        this.expenseTypeId = expenseTypeId;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Set<ExpensePaymentRsTO> getExpensePaymentRsTOSet() {
		return expensePaymentRsTOSet;
	}

	public void setExpensePaymentRsTOSet(Set<ExpensePaymentRsTO> expensePaymentRsTOSet) {
		this.expensePaymentRsTOSet = expensePaymentRsTOSet;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", expenseId=").append(expenseId);
        sb.append(", expenseTypeId=").append(expenseTypeId);
        sb.append(", invoiceNo=").append(invoiceNo);
        sb.append(", description=").append(description);
        sb.append(", expenseDate=").append(expenseDate);
        sb.append(", supplier=").append(supplier);
        sb.append(", totalAmt=").append(totalAmt);
        sb.append(", remarks=").append(remarks);
        sb.append(", status=").append(status);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}