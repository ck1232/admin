package com.admin.to;

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
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "role")
public class RoleTO extends BaseTO  {
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "role_id", nullable=false)
	private Long roleId;

	@Column(name = "name", nullable=false)
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "roleTO", cascade=CascadeType.ALL)
	private Set<UserRoleTO> userRoleTOSet;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "roleTO", cascade=CascadeType.ALL)
	private Set<SubModulePermissionTO> subModulePermissionSet;

	public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Set<UserRoleTO> getUserRoleTOSet() {
		return userRoleTOSet;
	}

	public void setUserRoleTOSet(Set<UserRoleTO> userRoleTOSet) {
		this.userRoleTOSet = userRoleTOSet;
	}

	public Set<SubModulePermissionTO> getSubModulePermissionSet() {
		return subModulePermissionSet;
	}

	public void setSubModulePermissionSet(Set<SubModulePermissionTO> subModulePermissionSet) {
		this.subModulePermissionSet = subModulePermissionSet;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", roleId=").append(roleId);
        sb.append(", name=").append(name);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}
