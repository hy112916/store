package com.itheima.service;

import java.util.List;

import com.itheima.domain.User;

public interface UserService {

	void regist(User user) throws Exception;

	User active(String code) throws Exception;

	User login(String username, String password) throws Exception;

	void update(User user) throws Exception;
	
	void updateMoney(String uid, Double money) throws Exception;
	
	User getById(String uid) throws Exception;

	List<User> findAll() throws Exception;

	List<User> findAllExUid(String uid)throws Exception;

}
