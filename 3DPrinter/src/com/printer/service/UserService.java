package com.printer.service;

import com.printer.entities.User;

public interface UserService {

	//查询用户是否存在
	public User isExistUser(String userName);
	
	//检验密码是否正确
	public boolean checkUser(User u,String password);
	
	//根据用户名查询用户信息
	public User queryUser(int userID);
	
	//添加用户（用户注册）
	public int addUser(User user);
	
	//删除用户
	public int deleteUser(int userID);
	
	//修改用户
	public int updateUser(User user);
	
	//检查是否重名
	public int checkName(String userName);
	
}
