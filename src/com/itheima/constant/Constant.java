package com.itheima.constant;

public interface Constant {
	/**
	 * 用户未激活
	 */
	int USER_IS_NOT_ACTIVE = 0;
	
	
	/**
	 * 用户已激活
	 */
	int USER_IS_ACTIVE = 1;
	
	
	/**
	 * 记住用户名
	 */
	String SAVE_NAME ="ok";
	
	/**
	 * redis中存储分类列表的key
	 */
	String STORE_CATEGORY_LIST="STORE_CATEGORY_LIST";
	
	/**
	 * redis的服务器地址
	 */
	String REDIS_HOST = "192.168.17.136";
	
	/**
	 * redis的服务器端口号
	 */
	int  REDIS_PORT =  6379;
	
	/**
	 * 热门商品
	 */
	int PRODUCT_IS_HOT = 1;
	
	/**
	 * 商品未下架
	 */
	int PRODUCT_IS_UP = 0;
	
	/**
	 * 商品已下架
	 */
	int PRODUCT_IS_DOWN = 1;
	
	/**
	 * 订单状态 未付款
	 */
	int ORDER_WEIFUKUAN=0;
	
	/**
	 * 订单状态 已付款
	 */
	int ORDER_YIFUKUAN=1;
	
	/**
	 * 订单状态 已发货
	 */
	int ORDER_YIFAHUO=2;
	
	/**
	 * 订单状态 已完成
	 */
	int ORDER_YIWANCHENG=3;
}
