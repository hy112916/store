package com.itheima.web.servlet;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.itheima.domain.Admin;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.service.impl.CategoryServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import com.itheima.web.servlet.base.BaseServlet;

/**
 * 后台分类管理模块
 */
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * 添加分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String save(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.封装category对象
			Category c = new Category();
			c.setCid(UUIDUtils.getId());
			c.setCname(request.getParameter("cname"));
			
			//2.调用service完成添加操作
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			cs.save(c);
			Admin a=(Admin)request.getSession().getAttribute("admin");
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 添加分类["+c.getCid()+"] ");
			//3.重定向
			response.sendRedirect("/admin/category/list.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}
	
	/**
	 * 跳转到添加页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/admin/category/add.jsp";
	}
	/**
	 * 展示所有分类
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.调用service 获取所有的分类
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			List<Category> list=cs.findList();
			Admin a=(Admin)request.getSession().getAttribute("admin");
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 查询所有分类 ");
			//2.将返回值放入request域中 请求转发
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		
		return "/admin/category/list.jsp";
	}
	public String update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			CategoryService cs = new CategoryServiceImpl();
			Category category =cs.getById(request.getParameter("cid"));
			category.setCname(request.getParameter("cname"));
			cs.update(category);
			Admin a=(Admin)request.getSession().getAttribute("admin");
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 更新分类["+category.getCid()+"] ");
			response.sendRedirect("/admin/category/list.jsp");
		} catch (Exception e) {
		}
		return null;
	}
	public void deleteById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html;charset=utf-8");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			CategoryService cs = new CategoryServiceImpl();
			Category category =cs.getById(request.getParameter("cid"));
			cs.delete(category);
			Admin a=(Admin)request.getSession().getAttribute("admin");
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 删除分类["+category.getCid()+"] ");
			response.sendRedirect("/admin/category/list.jsp");
		} catch (Exception e) {
		}
		return ;
	}

}
