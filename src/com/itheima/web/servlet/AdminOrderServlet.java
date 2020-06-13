package com.itheima.web.servlet;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.itheima.constant.Constant;
import com.itheima.domain.Admin;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.JsonUtil;
import com.itheima.web.servlet.base.BaseServlet;
import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.MailUtils;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * 后台订单模块
 */
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 修改订单状态
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updateState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取oid
			String oid = request.getParameter("oid");
			
			//2.调用service 获取订单
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
			
			//3.设置订单的状态,更新
			UserService us = new UserServiceImpl();
			User user = us.getById(order.getUid());
			order.setState(Constant.ORDER_YIFAHUO);
			os.update(order);
			String emailMsg=user.getName()+"您好：您的订单"+oid+"已发货";
			MailUtils.sendMail(user.getEmail(), emailMsg);
			//4.重定向
			Admin a=(Admin)request.getSession().getAttribute("admin");
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 修改订单["+order.getOid()+"] ");
			response.sendRedirect(request.getContextPath()+"/adminOrder?method=findAllByState&state=1");
		} catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 展示订单详情
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String showDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//0.设置编码
			response.setContentType("text/html;charset=utf-8");
			
			//1.获取oid
			String oid = request.getParameter("oid");
			
			//2.调用service 获取订单
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			Order order = os.getById(oid);
			
			//3.获取订单的订单项列表 转成json 写回浏览器
			if(order != null){
				List<OrderItem> list = order.getItems();
				if(list != null && list.size()>0){
					//response.getWriter().println(JsonUtil.list2json(list));
					Admin a=(Admin)request.getSession().getAttribute("admin");
					Logger logger = Logger.getLogger("adminlog");
					String ip=InetAddress.getLocalHost().getHostAddress();
					logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 查询订单["+order.getOid()+"]详情 ");
					JsonConfig config = JsonUtil.configJson(new String[]{"order","pdate","pdesc","itemid"});
					response.getWriter().println(JSONArray.fromObject(list, config));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 后台按照状态查询订单列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAllByState(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取state
			String state = request.getParameter("state");
			String sec="";
			if(state==null) {
				sec="所有";
			}
			else{
			switch(state) {
			case "0":sec="未付款";
			case "1":sec="已付款";
			case "2":sec="已发货";
			case "3":sec="已完成";
			
			}
			}
			//2.调用service 获取不同的列表
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			List<Order> list=os.findAllByState(state);
			Admin a=(Admin)request.getSession().getAttribute("admin");
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 查询所有"+sec+"订单列表");
			//3.将list放入request域中,请求转发
			request.setAttribute("list", list);
			
		} catch (Exception e) {
		}
		return "/admin/order/list.jsp";
	}
	
	public String findAllByStateCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取state\cid
			String state = request.getParameter("state");
			Admin a=(Admin)request.getSession().getAttribute("admin");
			
			//2.调用service 获取不同的列表
			OrderService os = (OrderService) BeanFactory.getBean("OrderService");
			List<Order> list=os.findAllByStateCid(state,a.getCid());
			
			String sec="";
			if(state==null) {
				sec="所有";
			}
			else{
				switch(state) {
					case "0":sec="未付款";break;
					case "1":sec="已付款";break;
					case "2":sec="已发货";break;
					case "3":sec="已完成";break;
				}
			}
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 查询所有["+a.getCid()+"]类"+sec+"订单列表");
			//3.将list放入request域中,请求转发
			request.setAttribute("list", list);
			
		} catch (Exception e) {
		}
		return "/seller/order/list.jsp";
	}

}
