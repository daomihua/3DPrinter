package com.printer.service;

import java.util.List;

import com.printer.entities.Cart;

public interface CartService {
	
	//获取购物车列表
	public List<Cart> queryCarts(int userID);
	
	//删除购物车
	public int deleteCart(Cart cart);
	
	//更新购物车
	public int updateCart(Cart cart);
	
	//添加购物车
	public int addCart(Cart cart);
	
	//查询购物车数量
	public int getCartNum(int userID);

}
