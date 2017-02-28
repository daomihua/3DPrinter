package com.printer.dao;

import java.util.List;

import com.printer.entities.User;

public interface UserDao {
	
	//添加用户
	public int addUser(User user);
	
	//通过名字查询用户
	public User queryUser(int userID);
	
	//查询用户列表
	public List<User> queryUserList();
	
	//修改用户信息
	public int updateUser(User user);
	
	//删除用户
	public int deleteUser(int userID);
	
	//查询用户是否存在
	public User isExistUser(String UserName);
	
	//查询用户是否存在
	public int checkName(String UserName);


}
