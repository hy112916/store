package com.itheima.web.servlet;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.net.InetAddress;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.itheima.constant.Constant;
import com.itheima.domain.Admin;
import com.itheima.domain.Category;
import com.itheima.domain.Order;
import com.itheima.domain.Product;
import com.itheima.domain.ProductSale;
import com.itheima.domain.Score;
import com.itheima.domain.Statis;
import com.itheima.domain.User;
import com.itheima.service.AdminService;
import com.itheima.service.CategoryService;
import com.itheima.service.OrderService;
import com.itheima.service.ProductService;
import com.itheima.service.UserService;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import com.itheima.web.servlet.base.BaseServlet;
public class StatisServlet extends BaseServlet  {

	private static final long serialVersionUID = 1L;
	
	public String userPortrait(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			UserService us= (UserService)BeanFactory.getBean("UserService");
			AdminService as=(AdminService)BeanFactory.getBean("AdminService");
			OrderService os=(OrderService)BeanFactory.getBean("OrderService");
			CategoryService cs=(CategoryService)BeanFactory.getBean("CategoryService");
			List<Category> clist=cs.findList();
			List<User> ulist=us.findAll();
			List<Score> slist=new ArrayList<Score>();
			for(User u:ulist) {
				double[] score = new double[clist.size()];
				double count=0.0;
				for(Category c:clist) {
					List<Order> olist=os.findMyOrdersWithCid(u.getUid(),c.getCid());
					score[Integer.valueOf(c.getCid())-1]=olist.size();
					count+=olist.size();
				}
				for(int i=0;i<clist.size();i++) {
					if(count>0) {
						score[i]/=count;
					}else score[i]=0.0;
				}
				Score s=new Score();
				s.setUid(u.getUid());
				s.setUsername(u.getUsername());
				s.setList(score);
				slist.add(s);
			}
			request.setAttribute("list", slist);
			request.setAttribute("clist", clist);
			request.setAttribute("width", 1/(clist.size()+1));
			Admin admin=(Admin)request.getSession().getAttribute("admin");
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+admin.getAid()+"] IP 地址["+ip+"] 查询用户画像");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/bigdata/portrait.jsp";
	}
	
	public String userPredict(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			UserService us= (UserService)BeanFactory.getBean("UserService");
			AdminService as=(AdminService)BeanFactory.getBean("AdminService");
			OrderService os=(OrderService)BeanFactory.getBean("OrderService");
			CategoryService cs=(CategoryService)BeanFactory.getBean("CategoryService");
			User user=(User)request.getSession().getAttribute("user");
			ProductService ps = new ProductServiceImpl();
			List<Product> hotList=ps.findHot();
			List<Product> newList=ps.findNew();
			if(user==null) {
				request.setAttribute("lList", hotList);
				request.setAttribute("llList", newList);
			}else {
			List<Category> clist=cs.findList();
			List<User> ulist=us.findAll();
			List<Score> slist=new ArrayList<Score>();
			List<Product> plist1=new ArrayList<Product>();
			List<Product> plist2=new ArrayList<Product>();
			for(User u:ulist) {
				double[] score = new double[clist.size()];
				double count=0.0;
				for(Category c:clist) {
					List<Order> olist=os.findMyOrdersWithCid(u.getUid(),c.getCid());
					score[Integer.valueOf(c.getCid())-1]=olist.size();
					count+=olist.size();
				}
				for(int i=0;i<clist.size();i++) {
					if(count>0) {
						score[i]/=count;
					}else score[i]=0.0;
				}
				Score s=new Score();
				s.setUid(u.getUid());
				s.setUsername(u.getUsername());
				s.setList(score);
				slist.add(s);
			}
			Double s[]=new Double[ulist.size()];
			double s1[]=new double[ulist.size()];
			for(Score score:slist) {
				if(score.getUid().equals(user.getUid())) {
					Score su=new Score();
					su.setUid(score.getUid());
					su.setList(score.getList());
					int i=0;
					double sum = 0;
					
					for(Score iterator:slist) {
						for(int j=0;j<clist.size();j++) {
							sum+=Math.pow((su.getList()[j] - iterator.getList()[j]), 2);
						}
						s1[i]= 1.0 / (1.0 + Math.sqrt(sum));
						s[i] = 1.0 / (1.0 + Math.sqrt(sum));
						i++;
					}
				}
			}
			Arrays.sort(s,Collections.reverseOrder());
			Set<String> set =new LinkedHashSet <String>();
			for(int k=1;k<7;k++) {
				for(int j=0;j<ulist.size();j++) {
					if(s[k]==s1[j]) {
						List<Product> l=os.findMyOrdersProduct(slist.get(j).getUid());
						for(Product p:l) {
							set.add(p.getPid());
						}
					}
				}
				
			}
			for(String ss:set) {
				if(plist1.size()<10) {
					plist1.add(ps.getById(ss));
				}else {
					plist2.add(ps.getById(ss));
				}
			}
			
			if(plist1.size()==0) {
				request.setAttribute("lList", hotList);
			}else {
				request.setAttribute("lList", plist1);
			}
			if(plist2.size()==0) {
				request.setAttribute("llList", newList);
			}else {
				request.setAttribute("llList", plist2);
			}
			
			
			Logger logger = Logger.getLogger("userlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("用户["+user.getUid()+"] IP 地址["+ip+"] 查看推荐商品");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/jsp/like.jsp";
	}
	
	public String salesPredict(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			OrderService os=(OrderService)BeanFactory.getBean("OrderService");
			CategoryService cs=(CategoryService)BeanFactory.getBean("CategoryService");
			ProductService ps=(ProductService)BeanFactory.getBean("ProductService");
			List<Category> clist=cs.findList();
			int score[]=new int[clist.size()];
			for(Category c:clist) {
				List<Order> olist=os.findRecentOrdersWithCid(c.getCid());
				score[Integer.valueOf(c.getCid())-1]=olist.size();
			}
			List<ProductSale> plist=os.findRecentOrdersTop5();
			request.setAttribute("plist", plist);
			request.setAttribute("clist", clist);
			request.setAttribute("score", score);
			request.setAttribute("width", 1/(clist.size()+1));
			Admin admin=(Admin)request.getSession().getAttribute("admin");
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+admin.getAid()+"] IP 地址["+ip+"] 查询销售预测");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/bigdata/salespredict.jsp";
	}
	
	public String salesNotice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			OrderService os=(OrderService)BeanFactory.getBean("OrderService");
			List<Order> olist=os.findExceptionOrders();
			List<ProductSale> plist=os.findRecentOrdersTop5();
			request.setAttribute("list", olist);
			Admin admin=(Admin)request.getSession().getAttribute("admin");
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+admin.getAid()+"] IP 地址["+ip+"] 查询异常订单");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/admin/bigdata/salesexception.jsp";
	}

}
