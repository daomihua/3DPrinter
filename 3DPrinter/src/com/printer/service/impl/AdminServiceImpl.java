package com.printer.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.printer.dao.AdminDao;
import com.printer.entities.Admin;
import com.printer.entities.Authority;
import com.printer.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	public AdminDao admindao;

	//检验管理员
	@Override
	public Admin queryAdmin(int adID) {
		return admindao.queryAdmin(adID);
	}

	//获取管理员权限
	@Override
	public List<Authority> queryAuthorityList(int roleID) {
		return admindao.queryAuthorityList(roleID);
	}

	//添加管理员
	@Override
	public int addAdmin(Admin admin) {
		return admindao.addAdmin(admin);
	}

	//修改管理员
	@Override
	public int updateAdmin(Admin admin) {
		return admindao.updateAdmin(admin);
	}

	//删除管理员
	@Override
	public int deleteAdmin(int adID) {
		return admindao.deleteAdmin(adID);
	}

	
	//获取管理员列表
	@Override
	public List<Admin> queryAdmins() {
		return admindao.queryAdminList();
	}

	//查询是否已经存在管理员
	@Override
	public Admin isExistAdmin(String adName) {
		return admindao.isExistAdmin(adName);
	}

	@Override
	public List<Admin> queryDesignerList() {
		// TODO Auto-generated method stub
		return admindao.queryDesignerList();
	}

	//获取统计数据
	@Override
	public Map<String, Object> getTongJi() {
		// TODO Auto-generated method stub
		return admindao.TongJi();
	}
	

}
