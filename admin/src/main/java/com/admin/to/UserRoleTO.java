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

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "user_role")

public class UserRoleTO extends BaseTO {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_role_id", nullable=false)
	private Long userRoleId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable=false)
	@ForeignKey( name = "none" )
	private UserTO userTO;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id", nullable=false)
	@ForeignKey( name = "none" )
	private RoleTO roleTO;

	public Long getUserRoleId() {
		return userRoleId;
	}

	public void setUserRoleId(Long userRoleId) {
		this.userRoleId = userRoleId;
	}

	public UserTO getUserTO() {
		return userTO;
	}

	public void setUserTO(UserTO userTO) {
		this.userTO = userTO;
	}

	public RoleTO getRoleTO() {
		return roleTO;
	}

	public void setRoleTO(RoleTO roleTO) {
		this.roleTO = roleTO;
	}
}
