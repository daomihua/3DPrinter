package com.printer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.printer.dao.OrderItemDao;
import com.printer.entities.OrderItem;
import com.printer.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {
	
	@Autowired
	public OrderItemDao itemdao;

	//添加订单详情（用户下单）
	@Override
	public int addOrderItem(OrderItem item) {
		// TODO Auto-generated method stub
		return itemdao.addOrderItem(item);
	}

	//获取订单详情
	@Override
	public List<OrderItem> queryOrderItem(String orderID) {
		// TODO Auto-generated method stub
		return itemdao.queryOrderItem(orderID);
	}

	//加工商报价(即修改订单详情价格)
	@Override
	public int updateOrderItem(double price, int mid, String oid) {
		// TODO Auto-generated method stub
		return itemdao.updateOrderItem(price, mid, oid);
	}

	//设计师上传模型图片和描述(修改image和title)
	@Override
	public int uploadOrderItem( OrderItem item) {
		// TODO Auto-generated method stub
		return itemdao.uploadOrderItem(item);
	}

	//查询设计师的订单(成品)
	@Override
	public List<OrderItem> queryItems(int adID) {
		// TODO Auto-generated method stub
		return itemdao.queryItems(adID);
	}

	@Override
	public int updateItem(OrderItem item) {
		// TODO Auto-generated method stub
		return itemdao.updateItem(item);
	}

}
