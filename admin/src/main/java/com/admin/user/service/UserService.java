package com.admin.user.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.admin.dao.UserDAO;
import com.admin.helper.GeneralUtils;
import com.admin.to.UserTO;
import com.admin.user.vo.UserVO;

@Service
@Scope("prototype")
@Transactional(rollbackFor=Exception.class, propagation = Propagation.REQUIRED)
public class UserService {
	
//	private RoleAssignmentService roleAssignmentService;
	private UserDAO userDAO;
	@Autowired
	public UserService(
//			RoleAssignmentService roleAssignmentService,
			UserDAO userDAO) {
//		this.roleAssignmentService = roleAssignmentService;
		this.userDAO = userDAO;
	}
	
	public UserVO findById(Long id) {
		UserTO user = userDAO.findByUserId(id);
		List<UserVO> userVOList = convertToUserVOList(Arrays.asList(user));
		if(userVOList != null && !userVOList.isEmpty()){
			return userVOList.get(0);
		}
		return new UserVO();
	}
	
	public UserVO findByUserName(String userName) {
		List<UserTO> dbObjList = userDAO.findByUserNameAndDeleteInd(userName, GeneralUtils.NOT_DELETED);
		List<UserVO> voList = convertToUserVOList(dbObjList);
		if(voList != null && voList.size() > 0){
			UserVO user = voList.get(0);
			return user;
		}else{
			return null;
		}
	}
	

	public List<UserVO> getAllUsers() {
		List<UserTO> userList = userDAO.findByDeleteInd(GeneralUtils.NOT_DELETED);
		return convertToUserVOList(userList);
	}
	
	public void saveUser(UserVO user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(hashedPassword);
		List<UserTO> userTOList = convertToUserTOList(Arrays.asList(user));
		userDAO.save(userTOList);
	}
	
	public void resetPassword(String userName, String password){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		List<UserTO> userList = userDAO.findByUserNameAndDeleteInd(userName, GeneralUtils.NOT_DELETED);
		if(userList != null && !userList.isEmpty()){
			for(UserTO userTO : userList){
				userTO.setPassword(hashedPassword);
			}
			userDAO.save(userList);
		}
	}
	
	public void deleteUser(Long id) {
		deleteUser(Arrays.asList(id));
//		roleAssignmentService.deleteRoleListByUserId(id.intValue());
	}
	
	public void deleteUser(List<Long> idList) {
		List<UserTO> userToList = userDAO.findByUserIdIn(idList);
		if(userToList != null && !userToList.isEmpty()){
			for(UserTO userTo : userToList){
				userTo.setDeleteInd(GeneralUtils.DELETED);
			}
			userDAO.save(userToList);
		}
	}
	
	public void updateUser(UserVO vo) {
		if(vo != null && vo.getUserId() != null){
			UserTO userTO = userDAO.findByUserId(vo.getUserId().longValue());
			userTO.setUserName(vo.getUserName());
			userTO.setName(vo.getName());
			userTO.setEmailAddress(vo.getEmailAddress());
			userTO.setEnabled(vo.getEnabled() == null ? "N": vo.getEnabled());
			userDAO.save(userTO);
		}
	}
	
	private List<UserVO> convertToUserVOList(List<UserTO> toList) {
		List<UserVO> volist = new ArrayList<UserVO>();
		if(toList != null && !toList.isEmpty()){
			for(UserTO to : toList){
				UserVO vo = new UserVO();
				vo.setUserId(to.getUserId());
				vo.setUserName(to.getUserName());
				vo.setPassword(to.getPassword());
				vo.setStatus(to.getStatus());
				vo.setName(to.getName());
				vo.setEmailAddress(to.getEmailAddress());
				vo.setLastLogin(to.getLastLogin());
				vo.setEnabled(to.getEnabled());
				vo.setEnabledBoolean(to.getEnabled().equals("Y")? Boolean.TRUE : Boolean.FALSE);
				volist.add(vo);
			}
		}
		return volist;
	}
	
	private List<UserTO> convertToUserTOList(List<UserVO> voList) {
		List<UserTO> userTOlist = new ArrayList<UserTO>();
		if(voList != null && voList.size() > 0){
			for(UserVO vo : voList){
				UserTO to = new UserTO();
				to.setEmailAddress(vo.getEmailAddress());
				to.setEnabled(vo.getEnabledBoolean() == Boolean.TRUE ? "Y" : "N");
				to.setLastLogin(vo.getLastLogin());
				to.setName(vo.getName());
				to.setPassword(vo.getPassword());
				to.setStatus(vo.getStatus());
				to.setUserId(vo.getUserId()==null?null:vo.getUserId().longValue());
				to.setUserName(vo.getUserName());
				userTOlist.add(to);
			}
		}
		return userTOlist;
	}
}
