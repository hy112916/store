package com.itheima.service.impl;
import java.util.List;

import com.itheima.constant.Constant;
import com.itheima.dao.OrderDao;
import com.itheima.dao.ProductDao;
import com.itheima.dao.UserDao;
import com.itheima.dao.AdminDao;
import com.itheima.dao.impl.AdminDaoImpl;
import com.itheima.domain.Order;
import com.itheima.domain.Admin;
import com.itheima.utils.BeanFactory;
import com.itheima.service.AdminService;

public class AdminServiceImpl implements AdminService{
	public Admin login(String adminname, String password) throws Exception {
		// TODO Auto-generated method stub
		AdminDao ad= (AdminDao) BeanFactory.getBean("AdminDao");
		
		return ad.getByAdminnameAndPwd(adminname,password);
	}

	@Override
	public List<Admin> findAll() throws Exception {
		// TODO Auto-generated method stub
		AdminDao aDao= (AdminDao) BeanFactory.getBean("AdminDao");
		return aDao.findAll();
	}

	@Override
	public void update(Admin a) throws Exception {
		// TODO Auto-generated method stub
		AdminDao aDao= (AdminDao) BeanFactory.getBean("AdminDao");
		aDao.update(a);
	}

	@Override
	public void delete(String aid) throws Exception {
		// TODO Auto-generated method stub
		AdminDao aDao= (AdminDao) BeanFactory.getBean("AdminDao");
		aDao.delete(aid);
	}

	@Override
	public void add(Admin a) throws Exception {
		// TODO Auto-generated method stub
		AdminDao aDao= (AdminDao) BeanFactory.getBean("AdminDao");
		aDao.add(a);
	}

}
