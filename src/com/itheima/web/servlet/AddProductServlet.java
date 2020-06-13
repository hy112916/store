package com.itheima.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.itheima.constant.Constant;
import com.itheima.domain.Admin;
import com.itheima.domain.Category;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;
import com.itheima.utils.UploadUtils;
import java.net.InetAddress;

/**
 * 保存商品
 */
public class AddProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//0.使用fileuload保存图片和将商品的信息放入map中
			//0.1 创建map 存放商品的信息
			Map<String,Object> map=new HashMap<>();
			
			//0.2 创建磁盘文件项工厂 (设置临时文件的大小和位置)
			DiskFileItemFactory factory = new DiskFileItemFactory();
			
			//0.3 创建核心上传对象
			ServletFileUpload upload = new ServletFileUpload(factory);
			
			//0.4 解析request
			List<FileItem> list = upload.parseRequest(request);
			
			//0.5遍历list 获取每一个文件项
			for (FileItem fi : list) {
				//0.6获取name属性值
				String key = fi.getFieldName();
				
				//0.7判断是否是普通的上传组件
				if(fi.isFormField()){
					//普通
					map.put(key, fi.getString("utf-8"));
				}else{
					//文件
					//a.获取文件的名称  1.jpg
					String name = fi.getName();
					
					//b.获取文件真实名称 1.jpg
					String realName = UploadUtils.getRealName(name);
					
					//c.获取文件的随机名称  12312312434234.jpg
					String uuidName = UploadUtils.getUUIDName(realName);
					
					//d.获取随机目录 /a/3
					String dir = UploadUtils.getDir();
					
					//e.获取文件内容(输入流)
					InputStream is = fi.getInputStream();
					
					//f.创建输出流
					//获取products目录的真实路径
					String productPath = getServletContext().getRealPath("/products");
					
					//创建随机目录
					File dirFile = new File(productPath,dir);
					if(!dirFile.exists()){
						dirFile.mkdirs();
					}
					
					FileOutputStream os = new FileOutputStream(new File(dirFile,uuidName));
					
					//g.对拷流
					IOUtils.copy(is, os);
					
					//h.释放资源
					os.close();
					is.close();
					
					//i.删除临时文件
					fi.delete();
					
					//j.将商品的路径放入map中   prouduct/a/3/12312312434234.jpg
					map.put(key, "products"+dir+"/"+uuidName);
				}
			}
			
			//1.封装product对象
			Product p = new Product();
			
			//1.2.手动设置 pdate
			map.put("pdate", new Date());
			
			//1.3.手动设置 pflag  上架
			map.put("pflag", Constant.PRODUCT_IS_UP);
			
			map.put("sale", 0);
			
			//1.4.使用beanutils封装
			BeanUtils.populate(p, map);
			
			//1.5.手动设置 category
			Category c = new Category();
			c.setCid((String)map.get("cid"));
			p.setCategory(c);
			
			//2.调用service 完成保存
			ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
			ps.save(p);
			Admin a=(Admin)request.getSession().getAttribute("admin");
			Logger logger = Logger.getLogger("adminlog");
			String ip=InetAddress.getLocalHost().getHostAddress();
			logger.info("管理员["+a.getAid()+"] IP 地址["+ip+"] 添加商品["+p.getPid()+"] ");
			//3.重定向
			response.sendRedirect(request.getContextPath()+"/admin/product/list.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("保存商品失败");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
