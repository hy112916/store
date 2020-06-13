package com.itheima.service.impl;

import java.util.List;

import com.itheima.constant.Constant;
import com.itheima.dao.OrderDao;
import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.Order;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.MailUtils;

public class UserServiceImpl implements UserService {

	@Override
	/**
	 * 用户注册
	 */
	public void regist(User user) throws Exception {
		//1.调用dao完成注册
		//UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		ud.save(user);
		
		//2.发送激活邮件
		String emailMsg="恭喜"+user.getName()+":成为我们商城的一员,<a href='http://101.132.147.119:8080/store/user?method=active&code="+user.getCode()+"'>点此激活</a>";
		MailUtils.sendMail(user.getEmail(), emailMsg);
	}

	@Override
	/**
	 * 用户激活
	 */
	public User active(String code) throws Exception {
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		//1.通过code获取用户
		User user=ud.getByCode(code);
		//1.1 通过激活码没有找到 用户
		if(user == null){
			return null;
		}
		
		//2.若获取到了 修改用户
		user.setState(Constant.USER_IS_ACTIVE);
		user.setCode("NULL");
		
		ud.update(user);
		return user;
	}

	@Override
	/**
	 * 用户登录
	 */
	public User login(String username, String password) throws Exception {
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		
		return ud.getByUsernameAndPwd(username,password);
	}
	public void update(User user) throws Exception {
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		ud.update(user);
	}
	public void updateMoney(String uid, Double money) throws Exception {
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		ud.updateMoney(uid,money);
	}
	public User getById(String uid) throws Exception {
		UserDao ud = (UserDao) BeanFactory.getBean("UserDao");
		return ud.getById(uid);
	}

	@Override
	public List<User> findAll() throws Exception {
		// TODO Auto-generated method stub
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		return ud.findAll();
	}

	@Override
	public List<User> findAllExUid(String uid) throws Exception {
		// TODO Auto-generated method stub
		UserDao ud=(UserDao) BeanFactory.getBean("UserDao");
		return ud.findAllExUid(uid);
	}

}
