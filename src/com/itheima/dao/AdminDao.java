package com.itheima.dao;

import java.util.List;

import com.itheima.domain.Admin;

public interface AdminDao {

	Admin getByAdminnameAndPwd(String adminname, String password) throws Exception;

	List<Admin> findAll()throws Exception;

	void update(Admin a) throws Exception;

	void delete(String aid)throws Exception;

	void add(Admin a)throws Exception;

}
