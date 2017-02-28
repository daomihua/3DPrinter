package com.printer.dao;

import java.util.List;
import java.util.Map;

import com.printer.entities.Admin;
import com.printer.entities.Authority;

public interface AdminDao {
	
	//添加管理员
	public int addAdmin(Admin admin);
	
	//修改管理员信息
	public int updateAdmin(Admin admin);
	
	//删除管理员
	public int deleteAdmin(int adID);
	
	//查询是否存在该管理员
	public Admin isExistAdmin(String adName);
	
	//查询管理员
	public Admin queryAdmin(int adID);
	
	//查询管理员列表
	public List<Admin> queryAdminList();
	
	//查询设计师列表
	public List<Admin> queryDesignerList();
	
	//获取管理员的权限
	public List<Authority> queryAuthorityList(int roleID);
	
	//查询统计数据
	public Map<String,Object> TongJi();

}
