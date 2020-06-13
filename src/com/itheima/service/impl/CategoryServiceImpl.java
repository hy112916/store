package com.itheima.service.impl;

import java.util.List;

import com.itheima.constant.Constant;
import com.itheima.dao.CategoryDao;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.JedisUtils;
import com.itheima.utils.JsonUtil;

import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements CategoryService {

	@Override
	/**
	 * 后台展示所有分类
	 */
	public List<Category> findList() throws Exception {
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		return cd.findAll();
	}

	@Override
	/**
	 * 查询所有分类
	 */
	public String findAll() throws Exception {
		/*//1.调用dao 查询所有分类
		CategoryDao cd = new CategoryDaoImpl();
		List<Category> list = cd.findAll();*/
		
		List<Category> list=findList();
		
		//2.将list转换成json字符串
		if(list!=null && list.size()>0){
			return JsonUtil.list2json(list);
		}
		return null;
	}

	@Override
	/**
	 * 从redis中获取所有的分类
	 */
	public String findAllFromRedis() throws Exception {
		/*
		//1.获取jedis
		Jedis jedis = JedisUtils.getJedis();
		
		//2.从redis中获取数据
		String value = jedis.get(Constant.STORE_CATEGORY_LIST);
		
		//3.判断数据是否为空
		if(value == null){
			//3.1若为空 ,调用findAll() 将查询的结果放入redis return
			value = findAll();
			
			jedis.set(Constant.STORE_CATEGORY_LIST, value);
			System.out.println("从mysql中获取");
			return value;
		}
		//3.2若不为空,return
		System.out.println("从redis中获取");
		return value;
		*/
		
		Jedis j =null;
		String value=null;
		try {
			//1.从redis获取分类信息
			try {
				//1.1获取连接
				j = JedisUtils.getJedis();
				
				//1.2 获取数据 判断数据是否为空
				value = j.get(Constant.STORE_CATEGORY_LIST);
				
				//1.3 若不为空,直接返回数据
				if(value!=null){
					System.out.println("缓存中有数据");
					return value;
				}
			} catch (Exception e) {
			}
			
			//2 redis中 若无数据,则从mysql数据库中获取  别忘记将数据并放入redis中
			value = findAll();
					
			
			//3.将value放入redis中
			try {
				j.set(Constant.STORE_CATEGORY_LIST, value);
				System.out.println("已经将数据放入缓存中");
			} catch (Exception e) {
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			//释放jedis
			JedisUtils.closeJedis(j);
		}
		
		return value;
	}

	@Override
	/**
	 * 添加分类
	 */
	public void save(Category c) throws Exception {
		//1.调用dao 完成添加
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.save(c);
		
		//2.更新redis
		Jedis j = null;
		try {
			j=JedisUtils.getJedis();
			//清除redis中数据
			j.del(Constant.STORE_CATEGORY_LIST);
		} finally {
			JedisUtils.closeJedis(j);
		}
	}
	
	public Category getById(String cid) throws Exception{
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		return cd.getById(cid);
	}

	@Override
	public void update(Category category) throws Exception {
		// TODO Auto-generated method stub
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.update(category);
	}

	@Override
	public void delete(Category category) throws Exception {
		// TODO Auto-generated method stub
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.delete(category);
	}

	
}
