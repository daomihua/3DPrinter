package com.printer.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.printer.entities.Cart;
import com.printer.entities.User;
import com.printer.service.CartService;

@Controller  
@RequestMapping("/carts")
public class CartController {
	
	@Autowired
	private CartService cartService;
	
	//-----------------------------获取购物车列表-------------------------//
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getCarts(HttpSession session){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		User u = (User)session.getAttribute("User");
		modelMap.put("state", true);
		modelMap.put("data",cartService.queryCarts(u.getUserID()));
		return modelMap;
	}
	
	//-----------------------------获取购物车数量-------------------------//
	@RequestMapping(value="/count",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getCartNum(HttpSession session){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		User u = (User)session.getAttribute("User");
		if(u!=null){
			modelMap.put("state", true);
			modelMap.put("data",cartService.getCartNum(u.getUserID()));
		}else
		{
			modelMap.put("state", false);
			modelMap.put("data",null);
		}
		return modelMap;
	}
	
	//-----------------------------删除购物车-------------------------//
	@RequestMapping(value="/delete/{modelID}",method=RequestMethod.DELETE)
	@ResponseBody
	public Map<String,Object> deleteCart(@PathVariable("modelID") int modelID,HttpSession session){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		User u = (User)session.getAttribute("User");
		Cart cart = new Cart();
		cart.setUserID(u.getUserID());
		cart.setModelID(modelID);
		int flag = cartService.deleteCart(cart);
		if(flag>0){
			modelMap.put("state", true);
			modelMap.put("msg","购物车删除成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg","购物车删除失败");
		}
		return modelMap;
	}
	
	//-----------------------------修改购物车-------------------------//
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateCart(@RequestBody Cart cart,HttpSession session){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		User u = (User)session.getAttribute("User");
		cart.setUserID(u.getUserID());
		int flag = cartService.updateCart(cart);
		if(flag>0){
			modelMap.put("state", true);
			modelMap.put("msg","购物车修改成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg","购物车修改失败");
		}
		return modelMap;
	}
	
	//-----------------------------添加购物车-------------------------//
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addCart(@RequestBody Cart cart,HttpSession session){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		User u = (User)session.getAttribute("User");
		cart.setUserID(u.getUserID());
		int flag = cartService.addCart(cart);
		if(flag>0){
			modelMap.put("state", true);
			modelMap.put("msg","购物车添加成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg","购物车添加失败");
		}
		return modelMap;
	}

}
