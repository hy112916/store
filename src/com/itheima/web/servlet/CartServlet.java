package com.itheima.web.servlet;

import java.io.IOException;
import java.net.InetAddress;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Product;
import com.itheima.domain.User;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.web.servlet.base.BaseServlet;

/**
 * 购物车模块
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取购物车 执行清空操作
		getCart(request).clearCart();
		User user=(User)request.getSession().getAttribute("user");
		Logger logger = Logger.getLogger("userlog");
		String ip=InetAddress.getLocalHost().getHostAddress();
		logger.info("用户["+user.getUid()+"] IP 地址["+ip+"] 清空购物车");
		//2.重定向
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		
		return null;
	}
	
	/**
	 * 从购物车移除商品
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取商品的pid
		String pid = request.getParameter("pid");
		
		//2.获取购物车 执行移除
		getCart(request).removeFromCart(pid);
		
		//3.重定向
		User user=(User)request.getSession().getAttribute("user");
		Logger logger = Logger.getLogger("userlog");
		String ip=InetAddress.getLocalHost().getHostAddress();
		logger.info("用户["+user.getUid()+"] IP 地址["+ip+"] 从购物车移除商品["+pid+"]");
		response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		return null;
	}
	
	/**
	 * 加入购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String add2cart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取pid count
			User user=(User)request.getSession().getAttribute("user");
			if(user==null) {
				request.setAttribute("msg", "请先登陆");
				return "/jsp/msg.jsp";
			}else {
			String pid = request.getParameter("pid");
			int count = Integer.parseInt(request.getParameter("count"));
			
			//2.封装cartitem
			//调用service获取product
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			Product product = ps.getById(pid);
			
			//创建cartitem
			CartItem cartItem = new CartItem(product, count);
			
			//3.将cartitem加入购物车
			//获取购物车
			Cart cart=getCart(request);
			
			cart.add2cart(cartItem);

			
				Logger logger = Logger.getLogger("userlog");
				String ip=InetAddress.getLocalHost().getHostAddress();
				logger.info("用户["+user.getUid()+"] IP 地址["+ip+"] 将商品["+pid+"] 加入购物车");
			}
			//4.重定向
			response.sendRedirect(request.getContextPath()+"/jsp/cart.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "加入购物车失败");
			return "/jsp/msg.jsp";
		}
		
		return null;
	}

	/**
	 * 获取购物车
	 * @param request
	 * @return
	 */
	private Cart getCart(HttpServletRequest request) {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if(cart == null){
			cart = new Cart();
			
			//将cart放入session中
			request.getSession().setAttribute("cart", cart);
		}
		return cart;
	}
}
