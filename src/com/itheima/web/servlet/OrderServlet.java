package com.itheima.web.servlet;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.itheima.constant.Constant;
import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.domain.User;
import com.itheima.service.OrderService;
import com.itheima.service.ProductService;
import com.itheima.service.UserService;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.PaymentUtil;
import com.itheima.utils.UUIDUtils;
import com.itheima.web.servlet.base.BaseServlet;

/**
 * 订单模块
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	
	/**
	 * 获取订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取oid
			String oid = request.getParameter("oid");
			
			//2.调用service 查询单个订单 
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
			User user=(User)request.getSession().getAttribute("user");
			Logger logger = Logger.getLogger("userlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("用户["+user.getUid()+"] IP 地址["+ip+"] 查询订单["+oid+"]详情");
			//3.请求转发
			request.setAttribute("bean",order);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "查询订单详情失败");
			return "/jsp/msg.jsp";
		}
		return "/jsp/order_info.jsp";
	}
	
	
	/**
	 * 我的订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findMyOrdersByPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取pageNumber 设置pagesize 获取userid
			int pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
			int pageSize=3;
			
			User user=(User)request.getSession().getAttribute("user");
			if(user == null){
				//未登录 提示
				request.setAttribute("msg", "请先登录");
				return "/jsp/msg.jsp";
			}
			
			//2.调用service获取当前页所有数据  pagebean
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			PageBean<Order> bean = os.findMyOrdersByPage(pageNumber,pageSize,user.getUid());
			Logger logger = Logger.getLogger("userlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("用户["+user.getUid()+"] IP 地址["+ip+"] 查询我的订单");
			//3.将pagebean放入request域中,请求转发 order_list.jsp
			request.setAttribute("pb", bean);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "获取我的订单失败");
			return "/jsp/msg.jsp";
		}
		return "/jsp/order_list.jsp";
	}
	
	/**
	 * 保存订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//-1.从session中获取user
			User user=(User) request.getSession().getAttribute("user");
			UserService us = new UserServiceImpl();
			if(user == null){
				//未登录 提示
				request.setAttribute("msg", "请先登录!");
				return "/jsp/msg.jsp";
			}
			
			//0.获取购物车
			Cart cart=(Cart) request.getSession().getAttribute("cart");
			
			//1.封装订单对象
			//1.1创建对象
			Order order = new Order();
			
			//1.2设置oid 
			order.setOid(UUIDUtils.getId());
			//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//1.3设置ordertime
			order.setOrdertime(new Date());
			
			//1.4设置total 购物车中
			order.setTotal(cart.getTotal());
			
			//1.5设置state
			order.setState(Constant.ORDER_WEIFUKUAN);
			
			//1.6设置user
			order.setUser(user);
			order.setAddress((String)request.getSession().getAttribute("address"));
			order.setTelephone((String)request.getSession().getAttribute("telephone)"));
			order.setName((String)request.getSession().getAttribute("name"));
			//1.7设置items(订单项列表) 遍历购物项列表
			for (CartItem	ci : cart.getCartItems()) {
				//1.7.1封装成orderitem
				//a.创建orderitem
				OrderItem oi = new OrderItem();
				
				//b.设置itemid uuid
				oi.setItemid(UUIDUtils.getId());
				
				//c.设置count 从ci中获取
				oi.setCount(ci.getCount());
				
				//d.设置subtotal 从ci中获取
				oi.setSubtotal(ci.getSubtotal());
				
				//e.设置product 从ci中获取
				oi.setProduct(ci.getProduct());
				
				//f.设置order 
				oi.setOrder(order);
				
				//1.7.2 将orderitem加入order 的items中
				order.getItems().add(oi);
			}
			
			
			//2.调用orderservice完成保存操作
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			os.save(order);
			Logger logger = Logger.getLogger("userlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("用户["+user.getUid()+"] IP 地址["+ip+"] 提交订单["+order.getOid()+"]");
			//2.9 清空购物车
			//request.getSession().getAttribute("cart")
			cart.clearCart();
			//3.请求转发到 order_info.jsp
			request.setAttribute("bean", order);
		} catch (Exception e) {
		}
		return "/jsp/order_info.jsp";
	}

	/**
	 * 在线支付
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			User user=(User) request.getSession().getAttribute("user");
			UserService us = new UserServiceImpl();
			
			if(user == null){
				//未登录 提示
				request.setAttribute("msg", "请先登录!");
				return "/jsp/msg.jsp";
			}
			String comfirmpw =request.getParameter("password");
			if(!comfirmpw.equals(user.getPassword())) {
				request.setAttribute("msg", "密码错误");
				return "/jsp/msg.jsp";
			}
			Double cash=user.getMoney();
			OrderService s=(OrderService) BeanFactory.getBean("OrderService");
			Order order = s.getById(request.getParameter("oid"));
			if(cash<order.getTotal()) {
				request.setAttribute("msg", "余额不足,请联系站长充值");
				return "/jsp/msg.jsp";
			}
			user.setMoney(cash-order.getTotal());
			request.getSession().setAttribute("user",user);
			us.updateMoney(user.getUid(),cash-order.getTotal());
			String address = request.getParameter("address");
			String name = request.getParameter("name");
			String telephone = request.getParameter("telephone");
			order.setAddress(address);
			order.setName(name);
			order.setTelephone(telephone);
			order.setState(Constant.ORDER_YIFUKUAN);
			s.update(order);
			List<OrderItem> items=order.getItems();
			ProductService ps = new ProductServiceImpl();
			int Notice=0;
			for(OrderItem item:items) {
				Product p = item.getProduct();
				p.setStock(p.getStock()-item.getCount());
				p.setSale(p.getSale()+item.getCount());
				ps.updateSS(p);
				if(item.getCount()>10) {
					Notice=1;
				}
			}
			
			Logger logger = Logger.getLogger("userlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("用户["+user.getUid()+"] IP 地址["+ip+"] 支付订单["+request.getParameter("oid")+"]");
			if(Notice==1) {
				Logger logger2 = Logger.getLogger("exceptionlog");
				logger2.info("用户["+user.getUid()+"] IP 地址["+ip+"] 异常订单["+request.getParameter("oid")+"]");
			}
			request.setAttribute("msg", "支付成功");
			return "/jsp/msg.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "支付失败");
			return "/jsp/msg.jsp";
		}
		
	}
	
	
}
