package com.admin.to;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	
	@Embeddable
	public class UserRoleId implements Serializable{
		
		private static final long serialVersionUID = 1L;

		@ManyToOne
		private UserTO user;
		
		@ManyToOne
		private RoleTO role;

		public UserTO getUser() {
			return user;
		}

		public void setUser(UserTO user) {
			this.user = user;
		}

		public RoleTO getRole() {
			return role;
		}

		public void setRole(RoleTO role) {
			this.role = role;
		}
	}
}
