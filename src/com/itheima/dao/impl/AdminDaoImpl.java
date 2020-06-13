package com.itheima.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.constant.Constant;
import com.itheima.dao.AdminDao;
import com.itheima.domain.Admin;
import com.itheima.domain.Product;
import com.itheima.domain.User;
import com.itheima.utils.DataSourceUtils;;

public class AdminDaoImpl implements AdminDao {
	public Admin getByAdminnameAndPwd(String adminname, String password) throws Exception{
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from admin where adminname = ? and password = ? limit 1";
		return qr.query(sql, new BeanHandler<>(Admin.class), adminname,password);
	}

	@Override
	public List<Admin> findAll() throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from admin where state=? order by adminname desc";
		return qr.query(sql, new BeanListHandler<>(Admin.class),0);
	}

	@Override
	public void update(Admin a) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update admin set adminname=?,password=?,telephone=?,email=?,cid=?,state=? where aid=?";
		qr.update(sql, a.getAdminname(),a.getPassword(),a.getTelephone(),a.getEmail(),a.getCid(),a.getState(),a.getAid());
	}

	@Override
	public void delete(String aid) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "delete from admin where aid=?";
		qr.update(sql, aid);
	}

	@Override
	public void add(Admin a) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql="insert into admin values(?,?,?,?,?,?,?);";
		qr.update(sql, a.getAid(),a.getAdminname(),a.getPassword(),a.getTelephone(),a.getEmail(),a.getState(),a.getCid());
	}
	
	

}
