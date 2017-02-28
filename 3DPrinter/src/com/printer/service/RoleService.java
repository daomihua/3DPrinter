package com.printer.service;

import java.util.List;

import com.printer.entities.Role;

public interface RoleService {

	//检查是否存在角色
	public Role isExistRole(String roleName);
	
	//获取角色列表
	public List<Role> queryRoleList();
	
	//获取角色
	public Role queryRole(int roleID);
	
	//添加角色
	public int addRole(Role role);
	
	//修改角色
	public int updateRole(Role role);
	
	//为角色分配权限
	public int distribution(int roleID,int authorityID);
	
	//删除角色所有权限
	public int deleteAuthoritise(int roleID);
	
	//删除角色
	public boolean deleteRole(int roleID);
	
}
