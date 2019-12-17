package com.itheima.dao;

import java.util.List;

import com.itheima.domain.Category;

public interface CategoryDao {

	List<Category> findAll() throws Exception;

	void save(Category c) throws Exception;

}
