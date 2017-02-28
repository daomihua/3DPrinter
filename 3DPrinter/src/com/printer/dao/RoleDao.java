package com.printer.dao;

import java.util.List;

import com.printer.entities.Role;

public interface RoleDao {
	
	//添加角色
	public int addRole(Role role);
	
	//为角色分配权限
	public int distribution(int roleID,int authorityID);
	
	//删除角色
	public int deleteRole(int roleID);
	
	//删除角色权限
	public int deleteAuthorities(int roleID);
	
	//修改角色
	public int updateRole(Role role);
	
	//判断是否存在该角色
	public Role isExistRole(String roleName);
	
	//查询角色列表
	public List<Role> queryRoleList();
	
	//查询角色
	public Role queryRole(int roleID);
	

}
