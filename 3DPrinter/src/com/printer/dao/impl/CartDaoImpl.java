package com.printer.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.printer.dao.CartDao;
import com.printer.entities.Cart;

@Repository
public class CartDaoImpl implements CartDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	//添加购物车
	@Override
	public int addCart(Cart cart) {
		String sql = "insert into CartInfo(UserID,ModelID,Material,Size,Quantity,Color)"
					+" values(?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[]{
				cart.getUserID(),
				cart.getModelID(),
				cart.getMaterial(),
				cart.getSize(),
				cart.getQuantity(),
				cart.getColor()
		});
	}

	//修改
	@Override
	public int updateCart(Cart cart) {
		String sql = "update CartInfo set Quantity=? where UserID=? and ModelID=?";
		return jdbcTemplate.update(sql, new Object[]{
				cart.getQuantity(),
				cart.getUserID(),
				cart.getModelID()
		});
	}

	//删除购物车
	@Override
	public int deleteCart(Cart cart) {
		String sql = "delete CartInfo where UserID=? and ModelID=?";
		return jdbcTemplate.update(sql, new Object[]{
				cart.getUserID(),
				cart.getModelID()
		});
	}

	//查询购物车列表
	@Override
	public List<Cart> queryCart(int userID) {
		String sql ="select * from( "
					+"select t1.UserID,t1.ModelID,t1.Material,t1.Size,t1.Quantity,t1.Color,t2.ModelName "
					+"from CartInfo t1,ModelInfo t2 where t1.ModelID=t2.ModelID "
					+") as t where t.UserID=?";
		final List<Cart> list = new ArrayList<Cart>();
		jdbcTemplate.query(sql, new Object[] { userID },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							Cart cart = new Cart();
							cart.setModelID(rs.getInt("ModelID"));
							cart.setMaterial(rs.getString("Material"));
							cart.setQuantity(rs.getInt("Quantity"));
							cart.setSize(rs.getString("Size"));
							cart.setModelName(rs.getString("ModelName"));
							cart.setColor(rs.getString("Color"));
							list.add(cart);
						}while(rs.next());
					}
		});
		return list;
	}

	//查询购物车数量
	@Override
	public int queryCartNum(int userID) {
		String sql="select count(*) from CartInfo where UserID=?";
		return jdbcTemplate.queryForInt(sql, new Object[]{ userID });
	}

}
