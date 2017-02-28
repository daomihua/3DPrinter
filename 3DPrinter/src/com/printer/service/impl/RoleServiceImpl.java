package com.printer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.printer.dao.RoleDao;
import com.printer.entities.Role;
import com.printer.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	public RoleDao roledao;

	//检查是否存在角色
	@Override
	public Role isExistRole(String roleName) {
		return roledao.isExistRole(roleName);
	}

	//获取角色列表
	@Override
	public List<Role> queryRoleList() {		
		return roledao.queryRoleList();
	}

	//获取单个角色
	@Override
	public Role queryRole(int roleID) {
		return roledao.queryRole(roleID);
	}

	//添加角色
	@Override
	public int addRole(Role role) {
		return roledao.addRole(role);
	}	

	//删除角色
	@Override
	public boolean deleteRole(int roleID) {
		int flag1 = roledao.deleteRole(roleID);
		int flag2 = roledao.deleteAuthorities(roleID);
		if( flag1!=0 && flag2!=0 )
			return true;
		else 
			return false;
	}

	//为角色分配权限
	@Override
	public int distribution(int roleID, int authorityID) {
		return roledao.distribution(roleID, authorityID);
	}

	//修改角色
	@Override
	public int updateRole(Role role) {
		return roledao.updateRole(role);
	}

	//删除角色权限
	@Override
	public int deleteAuthoritise(int roleID) {
		// TODO Auto-generated method stub
		return roledao.deleteAuthorities(roleID);
	}

}
