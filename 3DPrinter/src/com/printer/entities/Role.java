package com.printer.entities;

import java.io.Serializable;

public class Role{
	
	private int RoleID;
	private String RoleName;
	private String RoleIntro;
	public int getRoleID() {
		return RoleID;
	}
	public void setRoleID(int roleID) {
		RoleID = roleID;
	}
	public String getRoleName() {
		return RoleName;
	}
	public void setRoleName(String roleName) {
		RoleName = roleName;
	}
	public String getRoleIntro() {
		return RoleIntro;
	}
	public void setRoleIntro(String roleIntro) {
		RoleIntro = roleIntro;
	}
	
}
