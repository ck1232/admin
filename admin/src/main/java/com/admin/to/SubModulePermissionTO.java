package com.admin.to;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.Where;
@Entity
@DynamicUpdate
@Table(name = "submodule_permission")
public class SubModulePermissionTO extends BaseTO{
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "permission_id", nullable=false)
	private Long permissionId;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", nullable=false)
	@Where(clause="delete_ind='N'")
	@ForeignKey( name = "none" )
	private RoleTO roleTO;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "submodule_id", nullable=false)
	@Where(clause="delete_ind='N'")
	@ForeignKey( name = "none" )
	private SubModuleTO subModuleTO;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "permission_type_id", nullable=false)
	@Where(clause="delete_ind='N'")
	@ForeignKey( name = "none" )
	private PermissionTypeTO permissionType;

	public Long getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Long permissionId) {
		this.permissionId = permissionId;
	}

	public RoleTO getRoleTO() {
		return roleTO;
	}

	public void setRoleTO(RoleTO roleTO) {
		this.roleTO = roleTO;
	}

	public SubModuleTO getSubModuleTO() {
		return subModuleTO;
	}

	public void setSubModuleTO(SubModuleTO subModuleTO) {
		this.subModuleTO = subModuleTO;
	}

	public PermissionTypeTO getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(PermissionTypeTO permissionType) {
		this.permissionType = permissionType;
	}
}
