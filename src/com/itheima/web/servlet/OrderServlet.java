package com.itheima.web.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.constant.Constant;
import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.PageBean;
import com.itheima.domain.User;
import com.itheima.service.OrderService;
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
		//1.获取收获信息 获取oid 获取银行
		
		//2.调用service获取订单 修改收获人信息  更新订单
		
		//3.拼接给第三方的url
		
		//4.重定向
		
		
		try {
			//接受参数
			String address=request.getParameter("address");
			String name=request.getParameter("name");
			String telephone=request.getParameter("telephone");
			String oid=request.getParameter("oid");
			
			
			//通过id获取order
			OrderService s=(OrderService) BeanFactory.getBean("OrderService");
			Order order = s.getById(oid);
			
			order.setAddress(address);
			order.setName(name);
			order.setTelephone(telephone);
			
			//更新order
			s.update(order);
			

			// 组织发送支付公司需要哪些数据
			String pd_FrpId = request.getParameter("pd_FrpId");
			String p0_Cmd = "Buy";
			String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
			String p2_Order = oid;
			String p3_Amt = "0.01";
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
			// 第三方支付可以访问网址
			String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
			String p9_SAF = "";
			String pa_MP = "";
			String pr_NeedResponse = "1";
			// 加密hmac 需要密钥
			String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
					p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
					pd_FrpId, pr_NeedResponse, keyValue);

			
			//发送给第三方
			StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
			sb.append("p0_Cmd=").append(p0_Cmd).append("&");
			sb.append("p1_MerId=").append(p1_MerId).append("&");
			sb.append("p2_Order=").append(p2_Order).append("&");
			sb.append("p3_Amt=").append(p3_Amt).append("&");
			sb.append("p4_Cur=").append(p4_Cur).append("&");
			sb.append("p5_Pid=").append(p5_Pid).append("&");
			sb.append("p6_Pcat=").append(p6_Pcat).append("&");
			sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
			sb.append("p8_Url=").append(p8_Url).append("&");
			sb.append("p9_SAF=").append(p9_SAF).append("&");
			sb.append("pa_MP=").append(pa_MP).append("&");
			sb.append("pd_FrpId=").append(pd_FrpId).append("&");
			sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
			sb.append("hmac=").append(hmac);
			
			response.sendRedirect(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "支付失败");
			return "/jsp/msg.jsp";
		}
		
		return null;
	}
	
	/**
	 * 支付成功之后的回调
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String callback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.获取第三方发送过来的数据
		
		//2.获取订单 修改订单状态
		
		//3.更新订单
		
		try {
			String p1_MerId = request.getParameter("p1_MerId");
			String r0_Cmd = request.getParameter("r0_Cmd");
			String r1_Code = request.getParameter("r1_Code");
			String r2_TrxId = request.getParameter("r2_TrxId");
			String r3_Amt = request.getParameter("r3_Amt");
			String r4_Cur = request.getParameter("r4_Cur");
			String r5_Pid = request.getParameter("r5_Pid");
			String r6_Order = request.getParameter("r6_Order");
			String r7_Uid = request.getParameter("r7_Uid");
			String r8_MP = request.getParameter("r8_MP");
			String r9_BType = request.getParameter("r9_BType");
			String rb_BankId = request.getParameter("rb_BankId");
			String ro_BankOrderId = request.getParameter("ro_BankOrderId");
			String rp_PayDate = request.getParameter("rp_PayDate");
			String rq_CardNo = request.getParameter("rq_CardNo");
			String ru_Trxtime = request.getParameter("ru_Trxtime");
			// 身份校验 --- 判断是不是支付公司通知你
			String hmac = request.getParameter("hmac");
			String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
					"keyValue");

			// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
			boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
					r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
					r8_MP, r9_BType, keyValue);
			if (isValid) {
				// 响应数据有效
				if (r9_BType.equals("1")) {
					// 浏览器重定向
					System.out.println("111");
					request.setAttribute("msg", "您的订单号为:"+r6_Order+",金额为:"+r3_Amt+"已经支付成功,等待发货~~");
					
				} else if (r9_BType.equals("2")) {
					// 服务器点对点 --- 支付公司通知你
					System.out.println("付款成功！222");
					// 修改订单状态 为已付款
					// 回复支付公司
					response.getWriter().print("success");
				}
				
				//修改订单状态
				OrderService s=(OrderService) BeanFactory.getBean("OrderService");
				Order order = s.getById(r6_Order);
				order.setState(Constant.ORDER_YIFUKUAN);
				
				s.update(order);
				
			} else {
				// 数据无效
				System.out.println("数据被篡改！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("msg", "支付失败");
		}
		
		
		return "/jsp/msg.jsp";
	}
}
