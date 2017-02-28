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

import com.printer.dao.DOrderDao;
import com.printer.entities.DOrder;
import com.printer.entities.Order;

@Repository
public class DOrderDaoImpl implements DOrderDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	//添加设计订单
	@Override
	public int addDOrder(DOrder order) {
		String sql="insert DOrderInfo(DOrderID,Demand,Designer,UserName,State,DemandFile,DOrderDate,UPrice,Phone)"
				+" values(?,?,?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[]{
				order.getDOrderID(),
				order.getDemand(),
				order.getDesigner(),
				order.getUserName(),
				order.getState(),
				order.getDemandFile(),
				order.getDOrderDate(),
				order.getUserPrice(),
				order.getPhone()
		});
	}

	//代价工商对设计订单报价
	@Override
	public int quoteDOrder(DOrder order) {
		String sql="update DOrderInfo set APrice=?,State=?,Process=?,QuoteDate=? where DOrderID=?";
		return jdbcTemplate.update(sql, new Object[]{
				order.getProPrice(),
				order.getState(),
				order.getProcess(),
				order.getQuoteDate(),
				order.getDOrderID()
		});
	}

	//设计师返回设计文件
	@Override
	public int backDesignFile(DOrder order) {
		String sql="update DOrderInfo set DesignFile=?,State=?,DeliverDate=? where DOrderID=?";
		return jdbcTemplate.update(sql, new Object[]{
				order.getDesignFile(),
				order.getState(),
				order.getDeliverDate(),
				order.getDOrderID()
		});
	}
	
	//设计师确认设计订单
	@Override
	public int checkDOrder(DOrder order) {
		String sql="update DOrderInfo set State=?,CheckDate=? where DOrderID=?";
		return jdbcTemplate.update(sql, new Object[]{
				order.getState(),
				order.getCheckDate(),
				order.getDOrderID()
		});
	}

	//用户取修改订单状态（取消、付款）
	@Override
	public int updateOrder(DOrder order) {
		String sql="";
		Timestamp date = null;
		if(order.getState().equals("已取消")){
			sql="update DOrderInfo set State=?,CancelDate=? where DOrderID=?";
			date=order.getCancelDate();
		}
		else if(order.getState().equals("待设计")){
			sql="update DOrderInfo set State=?,PayDate=? where DOrderID=?";
			date=order.getPayDate();
		}
		return jdbcTemplate.update(sql,new Object[]{
				order.getState(),date,order.getDOrderID()
		});
	}

	//用户、代价工商、设计师根据条件查看设计订单
	@Override
	public List<DOrder> queryDOrders(DOrder order) {
		String sql="";
		String state=order.getState();
		String designer=order.getDesigner();
		String username=order.getUserName();
		
		//根据状态查询用户订单
		if(username!=null && state!=null)
			sql="select * from DOrderInfo where State like '%"+state+"%' and UserName='"+username+"' order by DOrderDate desc";
		else if(username!=null && state==null)
			sql="select * from DOrderInfo where  UserName='"+username+"' order by DOrderDate desc";
		
		//根据状态查询设计师接收的设计订单
		if(designer!=null && state!=null)
			sql="select * from DOrderInfo where State like '%"+state+"%' and Designer='"+designer+"' order by DOrderDate desc";
		else if(designer!=null && state==null)
			sql="select * from DOrderInfo where Designer='"+designer+"' order by DOrderDate desc";
		
		//代价工商查看
		if(designer==null && username==null && state==null)
			sql="select * from DOrderInfo";
		else if(designer==null && username==null && state!=null)
			sql="select * from DOrderInfo where State like '%"+state+"%' order by DOrderDate desc";
		
		return query(sql);
	}
	
	public List<DOrder> query(String sql){
		final List<DOrder> list = new ArrayList<DOrder>();
		jdbcTemplate.query(sql, new Object[] {},
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							DOrder o= new DOrder();
							o.setUserName(rs.getString("UserName"));
							o.setDemand(rs.getString("Demand"));
							o.setDemandFile(rs.getString("DemandFile"));
							o.setDesigner(rs.getString("Designer"));
							o.setDesignFile(rs.getString("DesignFile"));
							o.setDOrderDate(rs.getTimestamp("DOrderDate"));
							o.setPayDate(rs.getTimestamp("PayDate"));
							o.setUserPrice(rs.getDouble("UPrice"));
							o.setProPrice(rs.getDouble("APrice"));
							o.setProcess(rs.getString("Process"));
							o.setQuoteDate(rs.getTimestamp("QuoteDate"));
							o.setState(rs.getString("State"));
							o.setDOrderID(rs.getString("DOrderID"));
							o.setDeliverDate(rs.getTimestamp("DeliverDate"));
							o.setCheckDate(rs.getTimestamp("CheckDate"));
							list.add(o);
						}while(rs.next());
					}
		});
		return list;
	}

	//根据订单编号查看订单
	@Override
	public DOrder queryDOrder(String id) {
		final DOrder o=new DOrder();
		String sql="select * from DOrderInfo,UserInfo where UserInfo.UserName=DOrderInfo.UserName and DOrderID=?";
		jdbcTemplate.query(sql, new Object[] { id },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
							o.setUserName(rs.getString("UserName"));
							o.setDemand(rs.getString("Demand"));
							o.setDemandFile(rs.getString("DemandFile"));
							o.setDesigner(rs.getString("Designer"));
							o.setDesignFile(rs.getString("DesignFile"));
							o.setDOrderDate(rs.getTimestamp("DOrderDate"));
							o.setPayDate(rs.getTimestamp("PayDate"));
							o.setUserPrice(rs.getDouble("UPrice"));
							o.setProPrice(rs.getDouble("APrice"));
							o.setProcess(rs.getString("Process"));
							o.setQuoteDate(rs.getTimestamp("QuoteDate"));
							o.setState(rs.getString("State"));
							o.setDOrderID(rs.getString("DOrderID"));
							o.setDeliverDate(rs.getTimestamp("DeliverDate"));
							o.setCheckDate(rs.getTimestamp("CheckDate"));
							o.setPhone(rs.getString("Phone"));
					}
		});
		return o;
	}

	//设计师修改需求
	@Override
	public int updateDOrder(DOrder order) {
		String sql="update DOrderInfo set DemandFile=?,Demand=? where DOrderID=?";
		return jdbcTemplate.update(sql, new Object[]{
				order.getDemandFile(),
				order.getDemand(),
				order.getDOrderID()
		});
	}

}
