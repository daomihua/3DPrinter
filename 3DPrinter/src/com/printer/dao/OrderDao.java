package com.printer.dao;

import java.sql.Timestamp;
import java.util.List;

import com.printer.entities.Order;

public interface OrderDao {
	
	//生成订单
	public int addOrder(Order order);
	
	//设计师修改订单内容
	public int updateOrder(Order order);
	
	//查询设计师对应的状态订单
	public List<Order> queryOrderByAD(int adID,int state);
	
	//修改订单状态
	public int changeOrderSate(String orderID,String sate,Timestamp t);
	
	//修改订单总价（加工商报价）
	public int offerOrder(double priec,String id);
	
	//根据订单状态查询订单
	public List<Order> queryOrders(String state,String username);
	
	//获取订单详情
	public Order queryOrder(String orderID);
	
	//获取每种状态的订单数目
	public int[] queryOrderNum();
	
	//代加工商根据状态查询订单
	public List<Order> queryOrderByJia(String state);
	
	

}
