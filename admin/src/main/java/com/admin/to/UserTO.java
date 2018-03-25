package com.admin.to;

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
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "user")
public class UserTO extends BaseTO  {
	@Transient
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id", nullable=false)
    private Long userId;

	@Column(name = "user_name", nullable=false)
    private String userName;

	@Column(name = "password", nullable=false)
    private String password;

	@Column(name = "status", nullable=true)
    private String status;

	@Column(name = "name", nullable=false)
    private String name;

	@Column(name = "email_address", nullable=true)
    private String emailAddress;

	@Column(name = "last_login", nullable=true)
    private Date lastLogin;

	@Column(name = "enabled", nullable=false)
    private String enabled;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "pk.user", cascade=CascadeType.ALL)
	private Set<UserRoleTO> userRoleTOSet;
	
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress == null ? null : emailAddress.trim();
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    public Set<UserRoleTO> getUserRoleTOSet() {
		return userRoleTOSet;
	}

	public void setUserRoleTOSet(Set<UserRoleTO> userRoleTOSet) {
		this.userRoleTOSet = userRoleTOSet;
	}

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", userName=").append(userName);
        sb.append(", password=").append(password);
        sb.append(", status=").append(status);
        sb.append(", name=").append(name);
        sb.append(", emailAddress=").append(emailAddress);
        sb.append(", lastLogin=").append(lastLogin);
        sb.append(", enabled=").append(enabled);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        sb.append(", from super class ");
        sb.append(super.toString());
        return sb.toString();
    }
}
