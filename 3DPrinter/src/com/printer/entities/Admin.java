package com.printer.entities;

import java.io.Serializable;

public class Admin implements Serializable{
	
	private int AdID;
	private String AdName;
	private String AdPassword;
	private String AdSex;
	private String AdPhone;
	private String AdEmail;
	private String Portrait;
	private String AdQQ;
	private int RoleID;
	private String RoleName;
	
	
	public String getAdQQ() {
		return AdQQ;
	}
	public void setAdQQ(String adQQ) {
		AdQQ = adQQ;
	}
	public String getPortrait() {
		return Portrait;
	}
	public void setPortrait(String portrait) {
		Portrait = portrait;
	}
	public int getAdID() {
		return AdID;
	}
	public void setAdID(int adID) {
		AdID = adID;
	}
	public String getAdName() {
		return AdName;
	}
	public void setAdName(String adName) {
		AdName = adName;
	}
	public String getAdPassword() {
		return AdPassword;
	}
	public void setAdPassword(String adPassword) {
		AdPassword = adPassword;
	}
	public String getAdSex() {
		return AdSex;
	}
	public void setAdSex(String adSex) {
		AdSex = adSex;
	}
	public String getAdPhone() {
		return AdPhone;
	}
	public void setAdPhone(String adPhone) {
		AdPhone = adPhone;
	}
	public String getAdEmail() {
		return AdEmail;
	}
	public void setAdEmail(String adEmail) {
		AdEmail = adEmail;
	}
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
	
	
	
	
	
	

}
