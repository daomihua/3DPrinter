package com.printer.service;

import java.sql.Timestamp;
import java.util.List;

import com.printer.entities.DOrder;
import com.printer.entities.Order;

public interface OrderService {
	
//------------------------- 生产订单 ----------------------------------//
	
	//生成订单
	public int addOrder(Order order);
	
	//设计师修改订单内容
	public int updateOrder(Order order);
	
	//设计师根据状态查看订单
	public List<Order> queryOrderByAD(int adID,int state);
	
	//修改订单状态
	public int changeOrderSate(String orderID,String sate,Timestamp t);
	
	//修改订单总价（加工商报价）
	public int offerOrder(double priec,String id);
	
	//根据订单状态查询订单（客户订单中心查看）
	public List<Order> queryOrders(String state,String username);
	
	//根据订单状态查询订单(代价工商查看)
	public List<Order> queryAdOrders(String state);
	
	//获取订单详情
	public Order queryOrder(String orderID);
	
	
//-------------------------- 设计订单 --------------------------//

	//添加设计订单
	public int addDOrder(DOrder order);
	
	//代价工商对设计订单报价
	public int quoteDOrder(DOrder oerder);
	
	//设计师返回设计文件
	public int backDesignFile(DOrder order);
	
	//设计师确认设计订单
	public int checkDOrder(DOrder order);
	
	//设计师修改需求
	public int updateDOrder(DOrder order);
	
	//用户取修改订单状态（取消、付款）
	public int updateOrder(DOrder order);
	
	//代价工商、设计师、用户查看设计订单
	public List<DOrder> queryDOrders(DOrder order);
	
	//根据订单编号查看订单
	public DOrder queryDOrder(String id);
	
}
