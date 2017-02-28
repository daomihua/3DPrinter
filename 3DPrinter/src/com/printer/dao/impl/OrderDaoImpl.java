package com.printer.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.printer.dao.OrderDao;
import com.printer.entities.Cart;
import com.printer.entities.Order;

@Repository
public class OrderDaoImpl implements OrderDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	//生成订单
	@Override
	public int addOrder(Order order) {
		String sql="insert into OrderInfo(OrderID,UserName,OrderState,OrderDate,Receiver,Phone,PostCode,Direction,Remark,AdID)"
					+" values(?,?,?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[]{
				order.getOrderID(),
				order.getUserName(),
				order.getOrderState(),
				order.getOrderDate(),
				order.getReceiver(),
				order.getPhone(),
				order.getPostCode(),
				order.getDirection(),
				order.getRemark(),
				order.getAdID()
		});
	}

	//设计师修改订单内容
	@Override
	public int updateOrder(Order order) {
		String sql="update OrderInfo set Receiver=?,Phone=?,PostCode=?,Direction=? where OrderID=?";
		return jdbcTemplate.update(sql, new Object[]{
				order.getReceiver(),
				order.getPhone(),
				order.getPostCode(),
				order.getDirection(),
				order.getOrderID()
		});
	}

	//修改订单状态
	@Override
	public int changeOrderSate(String orderID, String state,Timestamp time) {
		//用户取消订单   (未确认/待付款->已取消)  已取消
		String sql1="update OrderInfo set OrderState=?,CancelDate=? where OrderID=?";
		//设计师确认订单 (待确认->待报价) 
		String sql2="update OrderInfo set OrderState=?,CheckDate=? where OrderID=?";
		//加工商报价订单 (待报价->待付款)
		String sql3="update OrderInfo set OrderState=?,CheckDate=? where OrderID=?";
		//用户付款 (待付款->待发货)
		String sql4="update OrderInfo set OrderState=?,PayDate=? where OrderID=?";
		//设计师生产订单
		String sql5="update OrderInfo set OrderState=?,MakeDate=? where OrderID=?";
		//订单发货 (待发货->待收货)
		String sql6="update OrderInfo set OrderState=?,DeliverDate=? where OrderID=?";
		//用户确认收货 (待收货->已签收)
		String sql7="update OrderInfo set OrderState=?,ReceiveDate=? where OrderID=?";
		
		
		
		int flag=0;
		
		switch(state){
		case "已取消":
			flag = changeState(sql1,state,orderID,time);
			break;
		case "待报价":
			flag = changeState(sql2,state,orderID,time);
			break;
		case "待付款":
			flag = changeState(sql3,state,orderID,time);
			break;
		case "待生产":
			flag = changeState(sql4,state,orderID,time);
			break;
		case "待发货":
			flag = changeState(sql5,state,orderID,time);
			break;
		case "待收货":
			flag = changeState(sql6,state,orderID,time);
			break;
		case "已签收":
			flag = changeState(sql7,state,orderID,time);
			break;
		default:
			return 0;
		}
		return flag;
	}
	
	public int changeState(String sql,String state,String id,Timestamp t){
		return jdbcTemplate.update(sql, new Object[]{ state,t,id});
	}

	//根据订单状态查询订单(客户查询)
	@Override
	public List<Order> queryOrders(String state,String username) {
		String sql="select * from OrderInfo,AdminInfo where OrderInfo.OrderState=? and OrderInfo.AdID=AdminInfo.AdID" +
				" and OrderInfo.UserName=?";
		final List<Order> list = new ArrayList<Order>();
		jdbcTemplate.query(sql, new Object[] { state, username},
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							Order order = new Order();
							order.setOrderID(rs.getString("OrderID"));
							order.setUserName(rs.getString("UserName"));
							order.setOrderState(rs.getString("OrderState"));
							order.setOrderDate(rs.getTimestamp("OrderDate"));
							order.setOrderPrice(rs.getDouble("OrderPrice"));
							order.setReceiver(rs.getString("Receiver"));
							order.setPhone(rs.getString("Phone"));
							order.setPostCode(rs.getString("PostCode"));
							order.setDirection(rs.getString("Direction"));
							order.setCancelDate(rs.getTimestamp("CancelDate"));
							order.setCheckDate(rs.getTimestamp("CheckDate"));
							order.setPayDate(rs.getTimestamp("PayDate"));
							order.setDeliverDate(rs.getTimestamp("DeliverDate"));
							order.setReceiveDate(rs.getTimestamp("ReceiveDate"));
							order.setRemark(rs.getString("Remark"));
							order.setDesigner(rs.getString("AdName"));
							list.add(order);
						}while(rs.next());
					}
		});
		return list;
	}

	//获取订单详情(用户查看)
	@Override
	public Order queryOrder(String orderID) {
		String sql = "select * from OrderInfo,AdminInfo where OrderInfo.OrderID=? and OrderInfo.AdID=AdminInfo.AdID";
		final Order order = new Order();
		jdbcTemplate.query(sql, new Object[] { orderID },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						order.setOrderID(rs.getString("OrderID"));
						order.setUserName(rs.getString("UserName"));
						order.setOrderState(rs.getString("OrderState"));
						order.setOrderDate(rs.getTimestamp("OrderDate"));
						order.setOrderPrice(rs.getDouble("OrderPrice"));
						order.setReceiver(rs.getString("Receiver"));
						order.setPhone(rs.getString("Phone"));
						order.setPostCode(rs.getString("PostCode"));
						order.setDirection(rs.getString("Direction"));
						order.setRemark(rs.getString("Remark"));
						order.setDesigner(rs.getString("AdName"));
						order.setCheckDate(rs.getTimestamp("CheckDate"));
						order.setDeliverDate(rs.getTimestamp("DeliverDate"));
						order.setPayDate(rs.getTimestamp("PayDate"));
						order.setReceiveDate(rs.getTimestamp("ReceiveDate"));
					}
		});
		return order;
	}

	//加工商报价
	@Override
	public int offerOrder(double price, String id) {
		String sql="update OrderInfo set OrderPrice=?,orderState='待付款' where OrderID=?";
		return jdbcTemplate.update(sql, new Object[] { price,id });
	}

	//获取每种订单的状态
	@Override
	public int[] queryOrderNum() {
		//用户取消订单   (待付款->已取消)  已取消
		String sql1="select count(*) from OrderInfo OrderState='待确认'";
		//加工商确认订单 (待付款)  待付款
		String sql2="select count(*) from OrderInfo OrderState='待付款'";
		//用户付款 (待付款->待发货)
		String sql3="select count(*) from OrderInfo OrderState='待发货'";
		//订单发货 (待发货->待收货)
		String sql4="select count(*) from OrderInfo OrderState='待收货'";
		
		int num1=jdbcTemplate.queryForInt(sql1);
		int num2=jdbcTemplate.queryForInt(sql2);
		int num3=jdbcTemplate.queryForInt(sql3);
		int num4=jdbcTemplate.queryForInt(sql4);
		int[] stateNum;
		stateNum = new int[4];
		stateNum[0]=num1;
		stateNum[1]=num2;
		stateNum[2]=num3;
		stateNum[3]=num4;
		return stateNum;
	}

	//查询设计师对应的状态的订单
	@Override
	public List<Order> queryOrderByAD(int adID, int state) {

		String sql1="select * from OrderInfo where AdID=? or OrderState='' order by OrderDate desc";		
		String sql2="select * from OrderInfo where AdID=? and OrderState='待确认' order by OrderDate desc";
		String sql3="select * from OrderInfo where AdID=? and OrderState='待生产' order by CheckDate desc";
		String sql4="select * from OrderInfo where AdID=? and OrderState='待发货' order by MakeDate desc";	
		String sql5="select * from OrderInfo where AdID=? and OrderState='待收货' order by DeliverDate desc";
		String sql="";
		switch(state){
		case 0:
			sql=sql1;
			break;
		case 1:
			sql=sql2;
			break;
		case 2:
			sql=sql3;
			break;
		case 3:
			sql=sql4;
			break;
		case 4:
			sql=sql5;
			break;
		default:
			sql=sql1;
			break;
		}
		final List<Order> list = new ArrayList<Order>();
		jdbcTemplate.query(sql, new Object[] { adID },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							Order order = new Order();
							order.setOrderID(rs.getString("OrderID"));
							order.setUserName(rs.getString("UserName"));
							order.setOrderState(rs.getString("OrderState"));
							order.setOrderDate(rs.getTimestamp("OrderDate"));
							list.add(order);
						}while(rs.next());
					}
		});
		return list;
	}

	//代价工商根据状态查询订单
	@Override
	public List<Order> queryOrderByJia(String state) {
		String sql1="select * from OrderInfo,AdminInfo where AdminInfo.AdID=OrderInfo.AdID and OrderState='"+state+"' order by OrderDate desc";
		String sql2="select * from OrderInfo,AdminInfo where AdminInfo.AdID=OrderInfo.AdID order by OrderDate desc";
		String sql="";
		if(state=="全部")
			sql=sql2;
		else
			sql=sql1;
		final List<Order> list = new ArrayList<Order>();
		jdbcTemplate.query(sql, new Object[] { },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							Order order = new Order();
							order.setOrderID(rs.getString("OrderID"));
							order.setUserName(rs.getString("UserName"));
							order.setOrderState(rs.getString("OrderState"));
							order.setOrderDate(rs.getTimestamp("OrderDate"));
							order.setOrderPrice(rs.getDouble("orderPrice"));
							order.setDesigner(rs.getString("AdName"));
							list.add(order);
						}while(rs.next());
					}
		});
		return list;
	}
	
	

}
