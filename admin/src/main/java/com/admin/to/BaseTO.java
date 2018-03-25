package com.admin.to;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import com.admin.config.SQLListener;
@MappedSuperclass
@EntityListeners(SQLListener.class)
public abstract class BaseTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name = "delete_ind", nullable=false)
	private String deleteInd;

	@Column(name = "created_by", nullable=false, updatable = false)
	private String createdBy;

	@Column(name = "created_on", nullable=false, updatable = false)
	private Date createdOn;

	@Column(name = "updated_by", nullable=false)
	private String updatedBy;

	@Column(name = "updated_on", nullable=false)
	private Date updatedOn;

	@Version
	@Column(name = "version", nullable=false)
	private Integer version;
	
	public String getDeleteInd() {
		return deleteInd;
	}

	public void setDeleteInd(String deleteInd) {
		this.deleteInd = deleteInd;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
