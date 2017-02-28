package com.printer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.printer.dao.CartDao;
import com.printer.entities.Cart;
import com.printer.service.CartService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartDao cartdao;
	
	@Override
	public List<Cart> queryCarts(int userID) {
		// TODO Auto-generated method stub
		return cartdao.queryCart(userID);
	}

	@Override
	public int deleteCart(Cart cart) {
		// TODO Auto-generated method stub
		return cartdao.deleteCart(cart);
	}

	@Override
	public int updateCart(Cart cart) {
		// TODO Auto-generated method stub
		return cartdao.updateCart(cart);
	}

	@Override
	public int addCart(Cart cart) {
		// TODO Auto-generated method stub
		return cartdao.addCart(cart);
	}

	@Override
	public int getCartNum(int userID) {
		// TODO Auto-generated method stub
		return cartdao.queryCartNum(userID);
	}

}
