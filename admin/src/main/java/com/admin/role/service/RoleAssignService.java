package com.admin.role.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.dao.RoleDAO;
import com.admin.dao.UserDAO;
import com.admin.dao.UserRoleDAO;
import com.admin.helper.GeneralUtils;
import com.admin.role.vo.UserRoleVO;
import com.admin.to.UserRoleTO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class RoleAssignService {
	
	private UserRoleDAO userRoleDAO;
	private RoleDAO roleDAO;
	private UserDAO userDAO;
	
	@Autowired
	public RoleAssignService(UserRoleDAO userRoleDAO,
			RoleDAO roleDAO,
			UserDAO userDAO) {
		this.userRoleDAO = userRoleDAO;
		this.roleDAO = roleDAO;
		this.userDAO = userDAO;
	}
	
	public List<UserRoleVO> findByUserId(Long userId) {
		List<UserRoleTO> userRoleTO = userRoleDAO.findByUserTO_UserIdAndDeleteInd(userId, GeneralUtils.NOT_DELETED); 
		return convertToUserRoleVOList(userRoleTO);
	}
	
	public void saveNewUserRole(List<UserRoleVO> voList) {
		if(voList != null && !voList.isEmpty()) {
			List<UserRoleTO> userRoleTOList = new ArrayList<UserRoleTO>();
			for(UserRoleVO vo : voList) {
				UserRoleTO to = new UserRoleTO();
				to.setRoleTO(roleDAO.findByRoleId(vo.getRoleId()));
				to.setUserTO(userDAO.findByUserId(vo.getUserId()));
				userRoleTOList.add(to);
			}
			userRoleDAO.save(userRoleTOList);
		}
	}
	
	public void deleteRoleByUserId(Long userId) {
		List<UserRoleTO> userRoleTOList = userRoleDAO.findByUserTO_UserIdAndDeleteInd(userId, GeneralUtils.NOT_DELETED); 
		if(userRoleTOList != null && !userRoleTOList.isEmpty()){
			for(UserRoleTO userRoleTO : userRoleTOList){
				userRoleTO.setDeleteInd(GeneralUtils.DELETED);
			}
			userRoleDAO.save(userRoleTOList);
		}
		
	}
	
	public List<UserRoleVO> convertToUserRoleVOList(List<UserRoleTO> toList) {
		List<UserRoleVO> voList = new ArrayList<UserRoleVO>();
		if(toList != null && !toList.isEmpty()){
			for(UserRoleTO to : toList){
				UserRoleVO vo = new UserRoleVO();
				vo.setUserRoleId(to.getUserRoleId());
				vo.setUserId(to.getUserTO().getUserId());
				vo.setRoleId(to.getRoleTO().getRoleId());
				vo.setRoleName(to.getRoleTO().getName());
				vo.setChecked(GeneralUtils.YES_IND);
				voList.add(vo);
			}
		}
		return voList;
	}
	
}
