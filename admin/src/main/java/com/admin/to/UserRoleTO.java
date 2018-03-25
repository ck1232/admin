package com.admin.to;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
@AssociationOverrides({
		@AssociationOverride(name = "pk.user", 
			joinColumns = @JoinColumn(name = "USER_ID")),
		@AssociationOverride(name = "pk.role", 
			joinColumns = @JoinColumn(name = "ROLE_ID")) })
public class UserRoleTO extends BaseTO {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private UserRoleId pk = new UserRoleId();
	
	public UserRoleId getPk() {
		return pk;
	}

	public void setPk(UserRoleId pk) {
		this.pk = pk;
	}
}
