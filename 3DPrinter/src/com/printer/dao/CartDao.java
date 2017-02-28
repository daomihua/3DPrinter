package com.printer.dao;

import java.util.List;

import com.printer.entities.Cart;

public interface CartDao {
	
	//添加购物车
	public int addCart(Cart cart);
	
	//修改购物车
	public int updateCart(Cart cart);
	
	//删除购物车
	public int deleteCart(Cart cart);
	
	//查询购物车
	public List<Cart> queryCart(int userID);
	
	//查询购物车数量
	public int queryCartNum(int userID);

}
