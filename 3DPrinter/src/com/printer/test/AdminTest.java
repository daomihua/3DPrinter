package com.printer.test;

import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.printer.dao.AdminDao;
import com.printer.dao.RoleDao;
import com.printer.entities.Admin;
import com.printer.entities.Authority;
import com.printer.entities.Case;
import com.printer.entities.RA;
import com.printer.entities.Role;
import com.printer.service.AdminService;
import com.printer.service.CartService;
import com.printer.service.CaseService;
import com.printer.service.RoleService;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class AdminTest {
	
	@Autowired
	private AdminDao admindao;
	
	@Autowired
	private RoleDao roledao;
	
	@Autowired
	private RoleService roleservice;
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private CaseService caseservice;
	@Autowired
	private CartService cartService;
	
//	@Test
//	public void getAuthorities(){
//		List<Authority> list = new ArrayList<Authority>();
//		list=admindao.queryAuthorityList(1);
//		System.out.println(list.get(2).getAuthorityName());
//		System.out.println(list.size());
//	}
	
//	@Test
//	public void updateRole(){
//		Map<String, Object> modelMap = new HashMap<String, Object>(3);
//		Role role= new Role();
//		role.setRoleIntro("1212");
//		role.setRoleName("1212");
//		Role r = new Role();
//		r = roleservice.isExistRole(role.getRoleName());
//		if( r.getRoleName()!=null ){
//			modelMap.put("state", false);
//			modelMap.put("msg", "该角色已经存在");
//			modelMap.put("data", null);
//		}else if( roleservice.addRole(role)!=0 ){
//			r = roleservice.isExistRole(role.getRoleName());
//			modelMap.put("state", true);
//			modelMap.put("msg", "角色添加成功");
//			modelMap.put("data", r);
//		}else{
//			modelMap.put("state", false);
//			modelMap.put("msg", "角色添加出错");
//			modelMap.put("data", null);
//		}
//		System.out.println(r.getRoleID());
//		System.out.println(modelMap.toString());
//	}
	
	@Test
	public void deleteRole(){
//		Timestamp t = new Timestamp(System.currentTimeMillis());
//		Date d= new Date();
//		DateFormat df=new SimpleDateFormat("yyyyMMddHHmmss");
//		String s=df.format(t);
//		System.out.println(t);
//		System.out.println(new Date().getTime());
//		System.out.println(s);
//		String file="aaa.jpg";
//		String[] filetype=file.split(".");
//    	String rename="ss"+filetype[filetype.length];
//    	System.out.println(rename);
		
		Admin a=adminService.isExistAdmin("邓艳萍");
		System.out.println(a.getAdQQ());;
	}

}
