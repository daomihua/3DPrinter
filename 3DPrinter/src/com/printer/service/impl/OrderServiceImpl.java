package com.printer.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.printer.dao.CaseDao;
import com.printer.dao.DOrderDao;
import com.printer.dao.OrderDao;
import com.printer.dao.OrderItemDao;
import com.printer.entities.DOrder;
import com.printer.entities.Order;
import com.printer.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	public OrderDao orderado;
	@Autowired
	public OrderItemDao itemdao;
	@Autowired
	public DOrderDao dorderdao;

	//------------------------- 生产订单 ----------------------------------//
	
	//生成订单
	@Override
	public int addOrder(Order order) {
		// TODO Auto-generated method stub
		return orderado.addOrder(order);
	}

	//设计师修改订单内容
	@Override
	public int updateOrder(Order order) {
		// TODO Auto-generated method stub
		return orderado.updateOrder(order);
	}

	//修改订单状态
	@Override
	public int changeOrderSate(String orderID, String sate, Timestamp t) {
		// TODO Auto-generated method stub
		return orderado.changeOrderSate(orderID, sate, t);
	}

	//修改订单总价和状态（加工商报价）
	@Override
	public int offerOrder(double priec, String id) {
		// TODO Auto-generated method stub
		return orderado.offerOrder(priec, id);
	}

	//根据订单状态查询订单（客户订单中心）
	@Override
	public List<Order> queryOrders(String state,String username) {
		List<Order> list =new ArrayList<Order>();
		List<Order> list2 =new ArrayList<Order>();
		list = orderado.queryOrders(state,username);
		Order o = new Order();
		for(int i = 0; i < list.size(); i++)  
        {  
            o=list.get(i); 
            o.setItems(itemdao.queryOrderItem(o.getOrderID()));
            list2.add(o);
        }
		return list2;
	}

	//获取订单列表详情
	@Override
	public Order queryOrder(String orderID) {
		// TODO Auto-generated method stub
		Order order =new Order();
		order = orderado.queryOrder(orderID);
		order.setItems(itemdao.queryOrderItem(orderID));
		return order;
	}

	@Override
	public List<Order> queryAdOrders(String state) {
		// TODO Auto-generated method stub
		return orderado.queryOrderByJia(state);
	}
	

	//查询设计师对应的状态订单
	@Override
	public List<Order> queryOrderByAD(int adID, int state) {
		// TODO Auto-generated method stub
		return orderado.queryOrderByAD(adID, state);
	}

	
	//-------------------------- 设计订单 --------------------------//
	
	//添加设计订单
	@Override
	public int addDOrder(DOrder order) {
		// TODO Auto-generated method stub
		return dorderdao.addDOrder(order);
	}

	//代价工商对设计订单报价
	@Override
	public int quoteDOrder(DOrder oerder) {
		// TODO Auto-generated method stub
		return dorderdao.quoteDOrder(oerder);
	}

	//设计师返回设计文件
	@Override
	public int backDesignFile(DOrder order) {
		// TODO Auto-generated method stub
		return dorderdao.backDesignFile(order);
	}

	//设计师确认设计订单
	@Override
	public int checkDOrder(DOrder order) {
		// TODO Auto-generated method stub
		return dorderdao.checkDOrder(order);
	}

	//用户取修改订单状态（取消、付款）
	@Override
	public int updateOrder(DOrder order) {
		// TODO Auto-generated method stub
		return dorderdao.updateOrder(order);
	}

	//代价工商、设计师、用户查看设计订单
	@Override
	public List<DOrder> queryDOrders(DOrder order) {
		// TODO Auto-generated method stub
		return dorderdao.queryDOrders(order);
	}

	//根据订单编号查看订单
	@Override
	public DOrder queryDOrder(String id) {
		// TODO Auto-generated method stub
		return dorderdao.queryDOrder(id);
	}

	//设计师修改需求
	@Override
	public int updateDOrder(DOrder order) {
		// TODO Auto-generated method stub
		return dorderdao.updateDOrder(order);
	}


}
