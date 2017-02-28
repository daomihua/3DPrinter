package com.printer.service;

import java.util.List;
import java.util.Map;

import com.printer.entities.Admin;
import com.printer.entities.Authority;

public interface AdminService {
	
	//查询管理员
	public Admin queryAdmin(int adID);
	
	//查询是否存在该管理员
	public Admin isExistAdmin(String adName);
	
	//获取管理员权限
	public List<Authority> queryAuthorityList(int roleID);
	
	//添加管理员
	public int addAdmin(Admin admin);
	
	//修改管理员
	public int updateAdmin(Admin admin);
	
	//删除管理员
	public int deleteAdmin(int adID);
	
	//获取管理员列表
	public List<Admin> queryAdmins();
	
	//查询设计师列表
	public List<Admin> queryDesignerList();
	
	//获取统计
	public Map<String,Object> getTongJi();

}
