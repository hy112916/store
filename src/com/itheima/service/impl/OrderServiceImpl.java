package com.itheima.service.impl;

import java.util.List;

import com.itheima.dao.OrderDao;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.domain.ProductSale;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService {

	@Override
	/**
	 * 保存订单
	 */
	public void save(Order order) throws Exception{
		try {
			//获取dao
			OrderDao od  = (OrderDao) BeanFactory.getBean("OrderDao");
			//1.开启事务
			DataSourceUtils.startTransaction();
			
			//2.向orders表中插入一条
			od.save(order);
			
			//3.向orderitem中插入n条
			for (OrderItem oi : order.getItems()) {
				od.saveItem(oi);
			}
			
			//4.事务控制
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}

	@Override
	/**
	 * 我的订单
	 */
	public PageBean<Order> findMyOrdersByPage(int pageNumber, int pageSize, String uid) throws Exception {
		OrderDao od  = (OrderDao) BeanFactory.getBean("OrderDao");
		
		//1.创建pagebean
		PageBean<Order> pb = new PageBean<>(pageNumber, pageSize);
		
		//2.查询总条数  设置总条数 
		int totalRecord = od.getTotalRecord(uid);
		pb.setTotalRecord(totalRecord);
		
		//3.查询当前页数据 设置当前页数据
		List<Order> data = od.findMyOrdersByPage(pb,uid);
		pb.setData(data);
		return pb;
	}

	@Override
	/**
	 * 订单详情
	 */
	public Order getById(String oid) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		return od.getById(oid);
	}

	@Override
	/**
	 * 修改订单
	 */
	public void update(Order order) throws Exception {
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		od.update(order);
	}

	@Override
	/**
	 * 后台查询订单列表
	 */
	public List<Order> findAllByState(String state) throws Exception {

		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		return od.findAllByState(state);
	}

	@Override
	public List<Order> findAllByStateCid(String state, String cid) throws Exception {
		// TODO Auto-generated method stub
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		return od.findAllByStateCid(state,cid);
	}

	@Override
	public List<Order> findMyOrders(String uid) throws Exception {
		// TODO Auto-generated method stub
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		return od.findMyOrders(uid);
	}

	@Override
	public List<Order> findMyOrdersWithCid(String uid, String cid) throws Exception {
		// TODO Auto-generated method stub
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		return od.findMyOrdersWithCid(uid,cid);
	}

	@Override
	public List<Order> findRecentOrdersWithCid(String cid) throws Exception {
		// TODO Auto-generated method stub
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		return od.findRecentOrdersWithCid(cid);
	}

	@Override
	public List<ProductSale> findRecentOrdersTop5() throws Exception {
		// TODO Auto-generated method stub
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		return od.findRecentOrdersTop5();
	}

	@Override
	public List<Order> findExceptionOrders() throws Exception {
		// TODO Auto-generated method stub
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		return od.findExceptionOrders();
	}

	@Override
	public List<Product> findMyOrdersProduct(String uid) throws Exception {
		// TODO Auto-generated method stub
		OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
		return od.findMyOrdersProduct(uid);
	}

}
