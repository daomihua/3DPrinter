package com.printer.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.web.bind.annotation.ResponseBody;

import com.printer.entities.User;
import com.printer.service.UserService;

@Controller  
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//---------------------------用户登录------------------------------
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> checkUser(@RequestBody User user,HttpSession httpSession){
		String username = user.getUserName();
		String password = user.getPassword();
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		//验证是否存在该用户
		User u=new User();
		u=userService.isExistUser(username);
		if( u.getUserID()!=0 ){
			if(userService.checkUser(u, password)){
				modelMap.put("state", true);
				modelMap.put("msg", "登录成功！");
				modelMap.put("data", u);
				httpSession.setAttribute("User", u);
			}else{
				modelMap.put("state", false);
				modelMap.put("msg", "密码错误！");
			}
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "用户不存在！");
		}
		return modelMap;
	}
	
	
	//--------------------用户注册 或者 添加用户------------------------
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> registerUser(@RequestBody User user,HttpSession httpSession){
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		String username = user.getUserName();
		User u = new User();
		u=userService.isExistUser(username);
		if(u.getUserID()!=0){
			modelMap.put("state", false);
			modelMap.put("msg", "该用户名已存在");
		}else{
			int flag = userService.addUser(user);
			if(flag!=0){
				u=userService.isExistUser(username);
				modelMap.put("state", true);
				modelMap.put("msg", "注册成功！");
				modelMap.put("data", u);
				httpSession.setAttribute("User", u);
			}else{
				modelMap.put("state", false);
				modelMap.put("msg", "注册失败！");
			}
		}
		return modelMap;
	}
	
	//--------------------------------删除用户---------------------------------//
	
	@RequestMapping(value="/delete/{userID}",method=RequestMethod.DELETE)
	@ResponseBody
	public Map<String,Object> deleteUser(@PathVariable("userID")int userID){
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		int flag = userService.deleteUser(userID);
		if(flag!=0){
			modelMap.put("state", true);
			modelMap.put("msg", "用户删除成功！");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "用户删除失败！");
		}
		return modelMap;
	}
	
	
	//--------------------获取一个用户的信息-------------------------
	
	@RequestMapping(value="/user/{userID}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> grtUser(@PathVariable("userID") int userID){
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("data", userService.queryUser(userID));
		return modelMap;
	}
	
	//--------------------- 用户修改信息 -----------------------------
	@RequestMapping(value="/update",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateUser(@RequestBody User user){
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		int flag=userService.updateUser(user);
		if(flag>0){
			modelMap.put("state", true);
			modelMap.put("msg", "用户修改成功！");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "用户修改失败！");
		}
		return modelMap;
	}

}
