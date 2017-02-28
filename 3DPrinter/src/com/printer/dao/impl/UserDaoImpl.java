package com.printer.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.printer.dao.UserDao;
import com.printer.entities.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	//添加用户
	@Override
	public int addUser(User user) {
		String sql = "insert into UserInfo(UserName,RealName,Sex,Password,Address,Phone,PostCode,Email)"
				+ "values(?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[] { 
						user.getUserName(),
						user.getRealName(),
						user.getSex(),
						user.getPassword(),
						user.getAddress(),
						user.getPhone(),
						user.getPostCode(),
						user.getEmail()}
				);
	}

	//按用户名查询一个用户
	@Override
	public User queryUser(int userID) {
		String sql="select * from UserInfo where UserID=?";
		final User user = new User();
		jdbcTemplate.query(sql, new Object[] { userID },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						user.setUserID(rs.getInt("UserID"));
						user.setUserName(rs.getString("UserName"));
						user.setRealName(rs.getString("RealName"));
						user.setSex(rs.getString("Sex"));
						user.setAddress(rs.getString("Address"));
						user.setEmail(rs.getString("Email"));
						user.setPassword(rs.getString("Password"));
						user.setPostCode(rs.getString("PostCode"));
						user.setPhone(rs.getString("Phone"));
					}
				});
		return user;
	}

	//查询用户列表
	@Override
	public List<User> queryUserList() {
		final List<User> userlist = new ArrayList<User>();
		String sql = "select * from UserInfo";
		jdbcTemplate.query(sql, new Object[] { },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							User user = new User();
							user.setUserID(rs.getInt("UserID"));
							user.setUserName(rs.getString("UserName"));
							user.setRealName(rs.getString("RealName"));
							user.setSex(rs.getString("Sex"));
							user.setAddress(rs.getString("Address"));
							user.setEmail(rs.getString("Email"));
							user.setPassword(rs.getString("Password"));
							user.setPostCode(rs.getString("PostCode"));
							user.setPhone(rs.getString("Phone"));
							userlist.add(user);
						}while(rs.next());
					}
				});
		return userlist;
	}

	//修改用户信息
	@Override
	public int updateUser(User user) {
		String sql="update UserInfo set Sex=?,Address=?,Email=?,"
				+"Password=?,PostCode=?,Phone=?,RealName=? where UserID=?";
		return jdbcTemplate.update(sql, new Object[] { 
				user.getSex(),
				user.getAddress(),
				user.getEmail(),
				user.getPassword(),
				user.getPostCode(),
				user.getPhone(),
				user.getRealName(),
				user.getUserID()
		});

	}

	//删除用户
	@Override
	public int deleteUser(int userID) {
		String sql="delete from UserInfo where UserID=?";
		return jdbcTemplate.update(sql, new Object[] { userID });

	}

	//用于查询用户是否存在
	@Override
	public User isExistUser(String userName) {
		String sql = "select * from UserInfo where UserName=?";
		final User user = new User();
		jdbcTemplate.query(sql, new Object[] { userName },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						user.setUserID(rs.getInt("UserID"));
						user.setUserName(rs.getString("UserName"));
						user.setRealName(rs.getString("RealName"));
						user.setSex(rs.getString("Sex"));
						user.setAddress(rs.getString("Address"));
						user.setEmail(rs.getString("Email"));
						user.setPassword(rs.getString("Password"));
						user.setPostCode(rs.getString("PostCode"));
						user.setPhone(rs.getString("Phone"));
					}
				});
		return user;	
	}

	//检查是否重名
	@Override
	public int checkName(String userName) {
		String sql = "select count(*) from UserInfo where UserName=?";
		return jdbcTemplate.queryForInt(sql,new Object[] { userName });
	}



}
