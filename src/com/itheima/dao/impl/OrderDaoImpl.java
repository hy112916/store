package com.itheima.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.itheima.dao.OrderDao;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.domain.ProductSale;
import com.itheima.utils.DataSourceUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	/**
	 * 保存订单
	 */
	public void save(Order o) throws Exception {
		QueryRunner qr = new QueryRunner();
		/**
		 * `oid` varchar(32) NOT NULL,
			  `ordertime` datetime DEFAULT NULL,
			  `total` double DEFAULT NULL,
			  
			  `state` int(11) DEFAULT NULL,
			  `address` varchar(30) DEFAULT NULL,
			  `name` varchar(20) DEFAULT NULL,
			  
			  `telephone` varchar(20) DEFAULT NULL,
			  `uid` varchar(32) DEFAULT NULL,
		 */
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(), sql, o.getOid(),o.getOrdertime(),o.getTotal(),
				o.getState(),o.getAddress(),o.getName(),
				o.getTelephone(),o.getUser().getUid());
	}

	@Override
	/**
	 * 保存订单项
	 */
	public void saveItem(OrderItem oi) throws Exception {
		QueryRunner qr = new QueryRunner();
		/*
		 * `itemid` varchar(32) NOT NULL,
				  `count` int(11) DEFAULT NULL,
				  `subtotal` double DEFAULT NULL,
				  
				  `pid` varchar(32) DEFAULT NULL,
				  `oid` varchar(32) DEFAULT NULL,
		 */
		String sql = "insert into orderitem values(?,?,?,?,?)";
		qr.update(DataSourceUtils.getConnection(), sql, oi.getItemid(),oi.getCount(),oi.getSubtotal(),
				oi.getProduct().getPid(),oi.getOrder().getOid());
	}

	@Override
	/**
	 * 获取我的订单的总条数
	 */
	public int getTotalRecord(String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from orders where uid = ?";
		return ((Long)qr.query(sql, new ScalarHandler(), uid)).intValue();
	}

	@Override
	/**
	 * 获取我的订单 当前页数据
	 */
	public List<Order> findMyOrdersByPage(PageBean<Order> pb, String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		//查询所有订单(基本信息)
		String sql="select * from orders where uid = ? order by ordertime desc limit ?,?";
		List<Order> list = qr.query(sql, new BeanListHandler<>(Order.class), uid,pb.getStartIndex(),pb.getPageSize());
		
		//遍历订单集合 获取每一个订单,查询每个订单订单项
		for (Order order : list) {
			sql="SELECT * FROM orderitem oi,product p WHERE oi.pid = p.pid AND oi.oid = ?";
			List<Map<String, Object>> maplist = qr.query(sql, new MapListHandler(), order.getOid());
			
			//遍历maplist 获取每一个订单项详情,封装成orderitem,将其加入当前订单的订单项列表中
			for (Map<String, Object> map : maplist) {
				//1.封装成orderitem
				//a.创建orderitem
				OrderItem oi = new OrderItem();
				
				//b.封装orderitem
				BeanUtils.populate(oi, map);
				
				//c.手动封装product
				Product p = new Product();
				
				BeanUtils.populate(p, map);
				
				oi.setProduct(p);
				
				//2.将orderitem放入order的订单项列表
				order.getItems().add(oi);
			}
		}
		return list;
	}

	@Override
	/**
	 * 订单详情
	 */
	public Order getById(String oid) throws Exception {
		//1.查询订单基本信息
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql ="select * from orders where oid = ?";
		Order order = qr.query(sql, new BeanHandler<>(Order.class), oid);
		
		//2.查询订单项
		sql ="SELECT * FROM orderitem oi,product p WHERE oi.pid = p.pid AND oi.oid = ?";
		//所有的订单项详情
		List<Map<String, Object>> maplist = qr.query(sql, new MapListHandler(), oid);
		
		//遍历 获取每一个订单项详情 封装成orderitem 加入到当前订单的items中
		for (Map<String, Object> map : maplist) {
			//创建ordreitem
			OrderItem oi = new OrderItem();
			
			//封装
			BeanUtils.populate(oi, map);
			
			//手动封装product
			Product p = new Product();
			BeanUtils.populate(p, map);
			
			oi.setProduct(p);
			//将orderitem加入到订单的items中
			order.getItems().add(oi);
		}
		return order;
	}

	@Override
	public void update(Order order) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		/**
		 * `oid` varchar(32) NOT NULL,
			  `ordertime` datetime DEFAULT NULL,
			  `total` double DEFAULT NULL,
			  
			  `state` int(11) DEFAULT NULL,
			  `address` varchar(30) DEFAULT NULL,
			  `name` varchar(20) DEFAULT NULL,
			  
			  `telephone` varchar(20) DEFAULT NULL,
			  `uid` varchar(32) DEFAULT NULL,
		 */
		String sql="update orders set state = ?,address = ?,name =?,telephone = ? where oid = ?";
		qr.update(sql,order.getState(),order.getAddress(),order.getName(),
				order.getTelephone(),order.getOid());
		
	}

	@Override
	/**
	 * 后台查询订单列表
	 */
	public List<Order> findAllByState(String state) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders ";
		
		//判断state是否为空
		if(null==state || state.trim().length()==0){
			sql +=" order by ordertime desc";
			return qr.query(sql, new BeanListHandler<>(Order.class));
		}
		
		sql += " where state = ? order by ordertime desc";
		return qr.query(sql, new BeanListHandler<>(Order.class),state);
	}

	@Override
	public List<Order> findAllByStateCid(String state, String cid) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders o,orderitem i,product p";
		//判断state是否为空
		if(null==state || state.trim().length()==0){
			sql +=" where o.oid=i.oid and i.pid=p.pid and cid=? order by ordertime desc";
			return qr.query(sql, new BeanListHandler<>(Order.class),cid);
		}
		
		sql += " where o.oid=i.oid and i.pid=p.pid and state = ? and cid=? order by ordertime desc";
		return qr.query(sql, new BeanListHandler<>(Order.class),state,cid);
	}

	@Override
	public List<Order> findMyOrders(String uid) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where uid=?";
		return qr.query(sql, new BeanListHandler<>(Order.class),uid);
	}

	@Override
	public List<Order> findMyOrdersWithCid(String uid, String cid) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders o,orderitem i,product p where o.oid=i.oid and i.pid=p.pid and uid=? and cid=?";
		return qr.query(sql, new BeanListHandler<>(Order.class),uid,cid);
	}

	@Override
	public List<Order> findRecentOrdersWithCid(String cid) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders o,orderitem i,product p where o.oid=i.oid and i.pid=p.pid and cid=? and ordertime between (SELECT DATE_ADD(now(),INTERVAL -1 MONTH)) and now()";
		return qr.query(sql, new BeanListHandler<>(Order.class),cid);
	}

	@Override
	public List<ProductSale> findRecentOrdersTop5() throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select p.pid,p.pname,SUM(i.count) number from orders o,orderitem i,product p where o.oid=i.oid and i.pid=p.pid  and ordertime between (SELECT DATE_ADD(now(),INTERVAL -1 MONTH)) and now() GROUP BY p.pid order by number desc LIMIT 0,5";
		return qr.query(sql, new BeanListHandler<>(ProductSale.class));
	}

	@Override
	public List<Order> findExceptionOrders() throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders o,orderitem i where o.oid=i.oid and i.count>10";
		return qr.query(sql, new BeanListHandler<>(Order.class));
	}

	@Override
	public List<Product> findMyOrdersProduct(String uid) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders o,orderitem i,product p where o.oid=i.oid and i.pid=p.pid and uid=? order by ordertime desc limit 3";
		return qr.query(sql, new BeanListHandler<>(Product.class),uid);
	}

}
