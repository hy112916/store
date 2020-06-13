package com.itheima.dao;

import java.util.List;

import com.itheima.domain.User;

public interface UserDao {

	void save(User user) throws Exception;

	User getByCode(String code) throws Exception;

	void update(User user) throws Exception;
	

	User getByUsernameAndPwd(String username, String password) throws Exception;

	void updateMoney(String uid,Double money) throws Exception;

	User getById(String uid) throws Exception;

	List<User> findAll() throws Exception;

	List<User> findAllExUid(String uid) throws Exception;

}
