package com.printer.dao;

import java.util.List;

import com.printer.entities.DOrder;

public interface DOrderDao {

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
