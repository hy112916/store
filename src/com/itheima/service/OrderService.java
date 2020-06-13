package com.itheima.service;

import java.util.Collection;
import java.util.List;

import com.itheima.domain.Order;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.domain.ProductSale;

public interface OrderService {

	void save(Order order)throws Exception;

	PageBean<Order> findMyOrdersByPage(int pageNumber, int pageSize, String uid)throws Exception;

	Order getById(String oid)throws Exception;

	void update(Order order)throws Exception;

	List<Order> findAllByState(String state)throws Exception;

	List<Order> findAllByStateCid(String state, String cid)throws Exception;

	List<Order> findMyOrders(String uid) throws Exception;

	List<Order> findMyOrdersWithCid(String uid, String cid) throws Exception;

	List<Order> findRecentOrdersWithCid(String cid) throws Exception;

	List<ProductSale> findRecentOrdersTop5() throws Exception;

	List<Order> findExceptionOrders()throws Exception;

	List<Product> findMyOrdersProduct(String uid) throws Exception;

}
