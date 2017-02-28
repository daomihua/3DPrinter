package com.printer.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.printer.dao.UserDao;
import com.printer.entities.User;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
@Transactional

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class UserTest {
	
	@Autowired
	UserDao userdao;
	
	//��ѯһ���û�
//	@Test
//	public void getUser1(){
//		User u = new User();
//		u=userdao.queryUser("cc");
//		System.out.println(u.getEmail());
//		System.out.println(u.getPhone());
//		
//	}
	
	//��ѯһ���û��б�
//	@Test
//	public void getUse2(){
//		List<User> list = new ArrayList<User>();
//		list=userdao.queryUserList();
//		System.out.println(list.get(2).getUserName());
//		System.out.println(list.size());
//			
//	}
	
	//�޸��û���Ϣ
//	@Test
//	public void updateUser(){
//		User u = new User();
//		String name="minor";
//		u.setSex("Ů");
//		u.setAddress("���ֵ��ӿƼ���ѧ");
//		u.setEmail("daomihua@163.com");
//		u.setPassword("1234");
//		u.setPhone("15677360950");
//		u.setPostCode("530001");
//		u.setRealName("huang");
//		int r=userdao.updateUser(u, name);
//		System.out.println(r);
//	}
	
	//����û�
//	@Test
//	public void addUser(){
//		User u = new User();
//		u.setUserName("daomi");
//		u.setSex("Ů");
//		u.setAddress("���ֵ��ӿƼ���ѧ");
//		u.setEmail("daomihua@163.com");
//		u.setPassword("1234");
//		u.setPhone("15677360950");
//		u.setPostCode("530001");
//		u.setRealName("ting");
//		int r=userdao.addUser(u);
//		System.out.println(r);
//	}
	
	//ɾ���û�
//	@Test
//	public void deleteUser(){
//		userdao.deleteUser("daomi");
//	}
	
	//��ѯ�Ƿ�����û�
//	@Test
//	public void testUser(){
//		int c=userdao.isExistUser("minor");
//		System.out.println(c);
//	}

}
