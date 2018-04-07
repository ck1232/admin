package com.admin.to;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Where;
@Entity
@DynamicUpdate
@Table(name = "submodule_permission_type")
public class PermissionTypeTO extends BaseTO {
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "type_id", nullable=false)
	private Long typeId;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "submodule_id", nullable=false)
	@Where(clause="delete_ind='N'")
	@ForeignKey( name = "none" )
	private SubModuleTO subModuleTO;
	
	@Column(name = "permission_type", nullable=false)
	private String permissionType;
	
	@Column(name = "seq_num", nullable=false)
	private Long seqNum;
	
	@Column(name = "url", nullable=false)
	private String url;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public SubModuleTO getSubModuleTO() {
		return subModuleTO;
	}

	public void setSubModuleTO(SubModuleTO subModuleTO) {
		this.subModuleTO = subModuleTO;
	}

	public String getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(String permissionType) {
		this.permissionType = permissionType;
	}

	public Long getSeqNum() {
		return seqNum;
	}

	public void setSeqNum(Long seqNum) {
		this.seqNum = seqNum;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
