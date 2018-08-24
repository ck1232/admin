package com.admin.user.service;

import java.util.List;

import com.admin.user.vo.UserVO;

public interface UserService {

	void deleteUser(Long id);

	UserVO findById(Long longValue);

	List<UserVO> findByIdList(List<Long> idList);

	List<UserVO> getAllUsers();

	UserVO findByUserName(String userid);

	void resetPassword(String userid, String password);

	void saveUser(UserVO userVO);

	void updateUser(UserVO userVO);

}
