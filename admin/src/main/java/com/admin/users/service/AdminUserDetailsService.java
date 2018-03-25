package com.admin.users.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.admin.dao.UserDAO;
import com.admin.helper.GeneralUtils;
import com.admin.to.UserRoleTO;
import com.admin.to.UserTO;
@Service("userDetailsService")
public class AdminUserDetailsService implements UserDetailsService {
	
	private UserDAO userDAO;
	
	@Autowired
	public AdminUserDetailsService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserTO> userTOList = userDAO.findByUserNameAndDeleteInd(username, GeneralUtils.NOT_DELETED);
		if(userTOList != null && !userTOList.isEmpty()){
			UserTO userTO = userTOList.get(0);
			List<GrantedAuthority> authorities = buildUserAuthority(userTO.getUserRoleTOSet());
			return buildUserForAuthentication(userTO, authorities);
		}
		return null;
	}

	private User  buildUserForAuthentication(UserTO userTO, List<GrantedAuthority> authorities) {
		boolean isEnable = GeneralUtils.YES_IND.equals(userTO.getEnabled()) ? false : true;
		return new User(userTO.getUserName(), userTO.getPassword(), isEnable , true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRoleTO> userRoleTOSet) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		if(userRoleTOSet != null && !userRoleTOSet.isEmpty()){
			for (UserRoleTO userRole : userRoleTOSet) {
				String roleName = userRole.getPk().getRole().getName();
				setAuths.add(new SimpleGrantedAuthority(roleName));
			}
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		return Result;
	}

}
