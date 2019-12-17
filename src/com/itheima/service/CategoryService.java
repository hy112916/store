package com.itheima.service;

import java.util.List;

import com.itheima.domain.Category;

public interface CategoryService {

	String findAll() throws Exception;

	String findAllFromRedis() throws Exception;

	List<Category> findList() throws Exception;

	void save(Category c) throws Exception;

}
