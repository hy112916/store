package com.itheima.service;

import java.util.List;

import com.itheima.domain.Admin;

public interface AdminService {

	Admin login(String adminname, String password) throws Exception ;

	List<Admin> findAll() throws Exception ;

	void update(Admin a) throws Exception ;

	void delete(String aid) throws Exception ;

	void add(Admin a) throws Exception;

}
