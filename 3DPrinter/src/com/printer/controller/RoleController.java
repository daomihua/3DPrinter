package com.printer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.printer.entities.RA;
import com.printer.entities.Role;
import com.printer.service.RoleService;

@Controller  
@RequestMapping("/roles")
public class RoleController {
	
	@Autowired
	private RoleService roleservice;
	
	//---------------------------获取角色列表----------------------------//
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getRoles(){
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		List<Role> list = new ArrayList<Role>();
		list = roleservice.queryRoleList();
		modelMap.put("state", true);
		modelMap.put("data", list);
		return modelMap;
	}
	
	
	//---------------------------获取单个角色----------------------------//
	@RequestMapping(value="/role/{roleID}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getRole(@PathVariable("roleID") int roleID){
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		Role role = new Role();
		role = roleservice.queryRole(roleID);
		modelMap.put("state", true);
		modelMap.put("data", role);
		return modelMap;
	}
	
	
	//--------------------------- 添加角色 -------------------------------//
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addRole(@RequestBody Role role){
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		Role r = new Role();
		r = roleservice.isExistRole(role.getRoleName());
		if( r.getRoleName()!=null ){
			modelMap.put("state", false);
			modelMap.put("msg", "该角色已经存在");
			modelMap.put("data", null);
		}else if( roleservice.addRole(role)!=0 ){
			r = roleservice.isExistRole(role.getRoleName());
			modelMap.put("state", true);
			modelMap.put("msg", "角色添加成功");
			modelMap.put("data", r);
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "角色添加出错");
			modelMap.put("data", null);
		}
		return modelMap;
	}

	
	//---------------------------删除角色----------------------------//
	@RequestMapping(value="/delete/{roleID}",method=RequestMethod.DELETE)
	@ResponseBody
	public Map<String,Object> deleteRole(@PathVariable("roleID")int roleID){
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		boolean rs = roleservice.deleteRole(roleID);
		if(rs){
			modelMap.put("state", true);
			modelMap.put("msg", "角色删除成功！");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "角色删除失败！");
		}
		return modelMap;
	}
	
	
	//--------------------------- 修改角色 -------------------------------//
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateRole(@RequestBody Role role){
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		if( roleservice.updateRole(role)!=0 ){
			modelMap.put("state", true);
			modelMap.put("msg", "角色修改成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "角色修改出错");
		}
		return modelMap;
	}
	
	
	//--------------------------- 删除所有权限 -------------------------------//
	@RequestMapping(value="/delete/authority/{roleID}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> deleteAuthoritise(@PathVariable("roleID")int roleID){
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		int rs = roleservice.deleteAuthoritise(roleID);
		if( rs>0 ){
			modelMap.put("state", true);
			modelMap.put("rs", rs);
		}else{
			modelMap.put("state", false);
			modelMap.put("rs", 0);
		}
		return modelMap;
	}
	
	//--------------------------- 分配权限 -------------------------------//
	@RequestMapping(value="/distribute",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> distribute(@RequestBody RA ra){
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		int rs = roleservice.distribution(ra.getRoleID(), ra.getAuthorityID());
		if( rs!=0 ){
			modelMap.put("rs", rs);
			modelMap.put("msg", "权限分配成功");
		}else{
			modelMap.put("rs", 0);
			modelMap.put("msg", "权限分配错误");
		}
		return modelMap;
	}

}
