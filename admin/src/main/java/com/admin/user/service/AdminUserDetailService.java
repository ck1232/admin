package com.admin.user.service;

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
import com.admin.to.RoleTO;
import com.admin.to.UserRoleTO;
import com.admin.to.UserTO;
import com.admin.user.vo.LoginUserVO;
@Service("userDetailsService")
public class AdminUserDetailService implements UserDetailsService {
	
	private UserDAO userDAO;
	
	@Autowired
	public AdminUserDetailService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserTO> userTOList = userDAO.findByUserNameAndDeleteInd(username, GeneralUtils.NOT_DELETED);
		if(userTOList != null && !userTOList.isEmpty()){
			UserTO userTO = userTOList.get(0);
			List<RoleTO> roleList = getRoleTOList(userTO.getUserRoleTOSet());
			List<GrantedAuthority> authorities = buildUserAuthority(roleList);
			return buildUserForAuthentication(userTO, authorities, roleList);
		}
		throw new UsernameNotFoundException("Invalid username or password.");
	}

	private User buildUserForAuthentication(UserTO userTO, List<GrantedAuthority> authorities, List<RoleTO> roleList) {
		boolean isEnable = GeneralUtils.YES_IND.equals(userTO.getEnabled()) ? true : false;
		return new LoginUserVO(userTO.getUserName(), userTO.getPassword(), isEnable , true, true, true, authorities, roleList, userTO);
	}
	
	private List<RoleTO> getRoleTOList(Set<UserRoleTO> userRoleTOSet){
		List<RoleTO> roleTOList = new ArrayList<RoleTO>();
		if(userRoleTOSet != null && !userRoleTOSet.isEmpty()){
			for (UserRoleTO userRole : userRoleTOSet) {
				roleTOList.add(userRole.getRoleTO());
			}
		}
		return roleTOList;
	}

	private List<GrantedAuthority> buildUserAuthority(List<RoleTO> roleList) {
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		List<String> roleNameList = GeneralUtils.convertListToStringList(roleList, "name", true);
		if(roleNameList != null && !roleNameList.isEmpty()){
			for (String roleName : roleNameList) {
				setAuths.add(new SimpleGrantedAuthority(roleName));
			}
		}

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		return Result;
	}

}
