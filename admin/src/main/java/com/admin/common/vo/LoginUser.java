package com.admin.common.vo;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.admin.to.RoleTO;
import com.admin.to.UserTO;

public class LoginUser extends User {
	private List<RoleTO> roleList;
	private UserTO userTO;
	private static final long serialVersionUID = 1L;
	
	public LoginUser(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities, List<RoleTO> roleList, UserTO userTO) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.roleList = roleList;
		this.userTO = userTO;
	}

	public UserTO getUserTO() {
		return userTO;
	}

	public List<RoleTO> getRoleList() {
		return roleList;
	}
}
