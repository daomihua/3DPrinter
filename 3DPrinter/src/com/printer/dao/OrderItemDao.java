package com.printer.dao;

import java.util.List;

import com.printer.entities.OrderItem;

public interface OrderItemDao {
	
	//添加订单详情（用户下单）
	public int addOrderItem(OrderItem item);
	
	//获取订单详情
	public List<OrderItem> queryOrderItem(String orderID);
	
	//加工商报价(即修改订单详情价格)
	public int updateOrderItem(double price,int mid,String oid);
	
	//设计师上传模型图片和描述(修改image和title)
	public int uploadOrderItem(OrderItem item);
	
	//设计师根据用户要求修改订单详情
	public int updateItem(OrderItem item);
	
	//查询设计师的订单
	public List<OrderItem> queryItems(int adID);

}
