package com.printer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.printer.dao.UserDao;
import com.printer.entities.User;
import com.printer.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	public UserDao userdao;

	//根据用户名查询用户信息
	@Override
	public User queryUser(int userID) {
		User u= new User();
		u= userdao.queryUser(userID);
		return u;
	}

	//检验用户密码是否正确
	@Override
	public boolean checkUser(User u,String password) {
		if(u.getPassword().equals(password))
			return true;
		else
			return false;
	}

	//查询用户是否存在
	@Override
	public User isExistUser(String userName) {
		return userdao.isExistUser(userName);
	}

	//添加用户
	@Override
	public int addUser(User user) {
		return userdao.addUser(user);
	}

	//删除用户
	@Override
	public int deleteUser(int userID) {
		return userdao.deleteUser(userID);
	}

	//检查是否重名
	@Override
	public int checkName(String userName) {
		// TODO Auto-generated method stub
		return userdao.checkName(userName);
	}

	//修改用户
	@Override
	public int updateUser(User user) {
		// TODO Auto-generated method stub
		return userdao.updateUser(user);
	}

}
