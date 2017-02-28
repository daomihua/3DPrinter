package com.printer.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.printer.dao.OrderItemDao;
import com.printer.entities.Cart;
import com.printer.entities.OrderItem;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	//添加订单详情（用户下单）
	@Override
	public int addOrderItem(OrderItem item) {
		String sql="insert OrderDetail(OrderID,ModelID,Material,Size,Quantity,Color)"
				+" values(?,?,?,?,?,?)";
		return jdbcTemplate.update(sql,new Object[]{
				item.getOrderID(),
				item.getModelID(),
				item.getMaterial(),
				item.getSize(),
				item.getQuantity(),
				item.getColor()
		});
	}

	//获取订单详情(设计师和用户查看)
	@Override
	public List<OrderItem> queryOrderItem(String orderID) {
		String sql="select * from( "
				+"select t1.Title,t1.Image,t1.OrderID,t1.ModelID,t1.Material,t1.Size,t1.Quantity,t1.Price,t1.Color,t2.ModelName "
				+"from OrderDetail t1,ModelInfo t2 where t1.ModelID=t2.ModelID"
				+") as t where t.OrderID=?";
		final List<OrderItem> list = new ArrayList<OrderItem>();
		jdbcTemplate.query(sql, new Object[] { orderID },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							OrderItem item = new OrderItem();
							item.setOrderID(rs.getString("OrderID"));
							item.setMaterial(rs.getString("Material"));
							item.setQuantity(rs.getInt("Quantity"));
							item.setModelName(rs.getString("ModelName"));
							item.setSize(rs.getString("Size"));
							item.setModelID(rs.getInt("ModelID"));
							item.setPrice(rs.getDouble("Price"));
							item.setColor(rs.getString("Color"));
							item.setImage(rs.getString("Image"));
							item.setTitle(rs.getString("Title"));
							list.add(item);
						}while(rs.next());
					}
		});
		return list;
	}

	//加工商报价(即修改订单详情价格)
	@Override
	public int updateOrderItem(double price, int mid, String oid) {
		String sql = "update OrderDetail set Price=? where OrderID=? and ModelID=?";
		return jdbcTemplate.update(sql, new Object[]{
				price, oid, mid
		});
	}

	//设计师上传模型图片和描述(修改image和title)
	@Override
	public int uploadOrderItem(OrderItem item) {
		String sql = "update OrderDetail set Image=?,Title=? where OrderID=? and ModelID=?";
		return jdbcTemplate.update(sql, new Object[]{
				item.getImage(),
				item.getTitle(),
				item.getOrderID(),
				item.getModelID()
		});
	}

	//查询设计师的订单(成品)
	@Override
	public List<OrderItem> queryItems(int adID) {
		String sql="select top 9 t1.Image,t1.Title from OrderDetail t1,OrderInfo t2 where "
					+"t1.OrderID=t2.OrderID and t2.AdID=? and Image is not null and "
					+"Title is not null order by t1.OrderID desc";
		final List<OrderItem> list = new ArrayList<OrderItem>();
		jdbcTemplate.query(sql, new Object[] { adID },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							OrderItem item = new OrderItem();
							item.setImage(rs.getString("Image"));
							item.setTitle(rs.getString("Title"));
							list.add(item);
						}while(rs.next());
					}
		});
		return list;
	}

	//设计师根据用户要求修改订单详情
	@Override
	public int updateItem(OrderItem item) {
		String sql = "update OrderDetail set Size=?,Material=?,Color=?,Quantity=? where OrderID=? and ModelID=?";
		return jdbcTemplate.update(sql, new Object[]{
				item.getSize(),
				item.getMaterial(),
				item.getColor(),
				item.getQuantity(),
				item.getOrderID(),
				item.getModelID()
		});
	}

}
