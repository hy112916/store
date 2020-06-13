package com.itheima.web.servlet;

import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.itheima.constant.Constant;
import com.itheima.domain.Admin;
import com.itheima.domain.Category;
import com.itheima.domain.Product;
import com.itheima.domain.Statis;
import com.itheima.service.CategoryService;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.web.servlet.base.BaseServlet;

/**
 * 后台商品模块
 */
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 跳转到添加的页面上
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String addUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			//调用categoryservice 查询所有分类
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			
			request.setAttribute("list", cs.findList());
		} catch (Exception e) {
		}
		return "/admin/product/add.jsp";
	}
	
	public String addUI2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			//调用categoryservice 查询所有分类
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			
			request.setAttribute("list", cs.findList());
		} catch (Exception e) {
		}
		return "/seller/product/add.jsp";
	}
	
	/**
	 * 展示已上架商品列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.调用service 查询以上架商品
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			List<Product> list = ps.findAll();
			
			Admin a=(Admin)request.getSession().getAttribute("admin");
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 查询所有已上架商品列表");
			//2.将返回值放入request中,请求转发
			
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/admin/product/list.jsp";
	}
	
	public String findAllByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.调用service 查询以上架商品
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			Admin a=(Admin)request.getSession().getAttribute("admin");
			List<Product> list = ps.findAllByCid(a.getCid());
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 查询所有["+a.getCid()+"]类已上架商品列表");
			//2.将返回值放入request中,请求转发
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/seller/product/list.jsp";
	}
	public String findAllByCid2(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.调用service 查询上架商品
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			List<Product> list = ps.findAllByCid(request.getParameter("cid"));
			Admin a=(Admin)request.getSession().getAttribute("admin");
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 查询所有["+request.getParameter("cid")+"]类已上架商品列表");
			//2.将返回值放入request中,请求转发
			request.setAttribute("list", list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/seller/product/list.jsp";
	}
	
	
	public String update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			response.setContentType("text/html;charest=utf-8");
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			Product p = new Product();
			String pid=request.getParameter("pid");
			String pname=request.getParameter("pname");
			Double market_price=Double.valueOf(request.getParameter("market_price"));
			Double shop_price=Double.valueOf(request.getParameter("shop_price"));
			Integer is_hot=Integer.valueOf(request.getParameter("is_hot"));  
			String pdesc=request.getParameter("pdesc");
			Integer stock=Integer.valueOf(request.getParameter("stock"));
			Category category=new Category();
			category.setCid(request.getParameter("category"));
			Integer pflag=Constant.PRODUCT_IS_UP;	
			Integer sale=0;
			Date pdate=new Date();
			p.setPid(pid);
			p.setPname(pname);
			p.setMarket_price(market_price);
			p.setShop_price(shop_price);
			p.setIs_hot(is_hot);
			p.setPdesc(pdesc);
			p.setPdate(pdate);
			p.setPflag(pflag);
			p.setStock(stock);
			p.setSale(sale);
			p.setCategory(category);
			ps.update(p);
			Admin a=(Admin)request.getSession().getAttribute("admin");
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 更新商品["+pid+"]");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "更新失败");
			return "/jsp/adminmsg.jsp";
		}
		return "/admin/product/list.jsp";
	}
	
	public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			ps.delete(request.getParameter("pid"));
			Admin a=(Admin)request.getSession().getAttribute("admin");
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 删除商品["+request.getParameter("pid")+"]");
		} catch (Exception e) {
		}
		return "/admin/product/list.jsp";
	}
	
	public String statis(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
			List<Category> categorylist=cs.findList();
			request.setAttribute("categorylist",categorylist );
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			List<Statis> list = new ArrayList<Statis>();
			for(Category c:categorylist) {
				Statis s=new Statis();
				s.setCid(c.getCid());
				s.setSum(ps.caculateByCid(c.getCid()));
				list.add(s);
			}
			request.setAttribute("list", list);
			Admin a=(Admin)request.getSession().getAttribute("admin");
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 查询各个商品类别的销售业绩");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/admin/statis/list.jsp";
	}
	
	public String searchUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");

			request.setAttribute("list", cs.findList());
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/admin/statis/search.jsp";
	}

}
