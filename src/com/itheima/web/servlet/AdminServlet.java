package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.itheima.constant.Constant;
import com.itheima.domain.Admin;
import com.itheima.domain.Category;
import com.itheima.domain.Product;
import com.itheima.service.AdminService;
import com.itheima.service.CategoryService;
import com.itheima.service.ProductService;
import com.itheima.service.impl.AdminServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import com.itheima.web.servlet.base.BaseServlet;

public class AdminServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().invalidate();
		
		response.sendRedirect(request.getContextPath()+"/jsp/AdminIndex.jsp");
		return null;
	}
	
	/**
	 * 用户登录
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取用户名和密码
			String adminname = request.getParameter("adminname");
			String password	= request.getParameter("password");
			//2.调用service完成登录 返回值:Admin
			AdminService as = new AdminServiceImpl();
			Admin admin=as.login(adminname,password);
			//3.判断admin根据结果生成提示
			if(admin == null){
				//用户名和密码不匹配
				request.setAttribute("msg", "用户名和密码不匹配");;
				return request.getContextPath()+"/jsp/adminmsg.jsp";
			}
			//登录成功 保存用户登录状态
			request.getSession().setAttribute("admin", admin);
			/////////////////记住用户名//////////////////////
			//判断是否勾选了记住用户名
			if(Constant.SAVE_NAME.equals(request.getParameter("savename"))){
				Cookie c = new Cookie("saveName", URLEncoder.encode(adminname, "utf-8"));
				
				c.setMaxAge(Integer.MAX_VALUE);
				c.setPath(request.getContextPath()+"/");
				
				response.addCookie(c);
			}
			///////////////////////////////////////
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+admin.getAid()+"] IP 地址["+ip+"] 登陆");
			
			if (admin.getState()==1) {
				response.sendRedirect(request.getContextPath()+"/admin/home.jsp");
			}else {
				response.sendRedirect(request.getContextPath()+"/seller/home.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "管理员登录失败");
			return request.getContextPath()+"/jsp/adminmsg.jsp";
		}
		
		return null;
	}
	
	/**
	 * 跳转到登录页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return request.getContextPath()+"/jsp/AdminIndex.jsp";
	}
	
	
	//
	public String infoUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		return request.getContextPath()+"/jsp/info.jsp";
	}
	
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.调用service 查询admin
			AdminService as = (AdminService) BeanFactory.getBean("AdminService");
			List<Admin> list = as.findAll();
			
			//2.将返回值放入request中,请求转发
			request.setAttribute("list", list);
			Admin a=(Admin)request.getSession().getAttribute("admin");
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 查询所有销售员列表");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/admin/seller/list.jsp";
	}
	
	
	public String update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			AdminService as = (AdminService) BeanFactory.getBean("AdminService");
			response.setContentType("text/html;charest=utf-8");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			Admin a = new Admin();
			String aid=request.getParameter("aid");
			String adminname=request.getParameter("adminname");
			String password=request.getParameter("password");
			String telephone=request.getParameter("telephone");
			String email=request.getParameter("email");
			String cid=request.getParameter("cid");
			Integer state=0;
			a.setAid(aid);
			a.setAdminname(adminname);
			a.setPassword(password);
			a.setTelephone(telephone);
			a.setEmail(email);
			a.setCid(cid);
			a.setState(state);
			as.update(a);
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 更新销售员["+a.getAid()+"]信息");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "更新失败");
			return "/jsp/adminmsg.jsp";
		}
		return "/admin/seller/list.jsp";
	}
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		try {
			AdminService as = (AdminService) BeanFactory.getBean("AdminService");
			response.setContentType("text/html;charest=utf-8");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			Admin a = new Admin();
			String aid=request.getParameter("aid");
			String adminname=request.getParameter("adminname");
			String password=request.getParameter("password");
			String telephone=request.getParameter("telephone");
			String email=request.getParameter("email");
			String cid=request.getParameter("cid");
			Integer state=0;
			a.setAid(aid);
			a.setAdminname(adminname);
			a.setPassword(password);
			a.setTelephone(telephone);
			a.setEmail(email);
			a.setCid(cid);
			a.setState(state);
			as.add(a);
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 增加销售员["+a.getAid()+"]");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "添加失败");
			return "/jsp/adminmsg.jsp";
		}
		return "/admin/seller/list.jsp";
	}
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//调用categoryservice 查询所有分类
			AdminService as = (AdminService) BeanFactory.getBean("AdminService");
			
			request.setAttribute("list", as.findAll());
		} catch (Exception e) {
		}
		return "/admin/seller/add.jsp";
		
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			AdminService as = (AdminService) BeanFactory.getBean("AdminService");
			as.delete(request.getParameter("aid"));
			Admin a=(Admin)request.getSession().getAttribute("admin");
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 删除销售员["+request.getParameter("aid")+"]");
		} catch (Exception e) {
		}
		return "/admin/seller/list.jsp";
	}


}
