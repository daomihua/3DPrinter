package com.printer.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.printer.dao.FeedBackDao;
import com.printer.entities.FeedBack;

@Repository
public class FeedBackDaoImpl implements FeedBackDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int addFeedBack(FeedBack fb) {
		String sql="insert FeedBack(UserName,SubmitDate,FeedBack,State) "
				+"values(?,?,?,?)";
		return jdbcTemplate.update(sql,new Object[]{
				fb.getUserName(),
				fb.getSubmitDate(),
				fb.getFeedBack(),
				fb.getState()
		});
	}

	@Override
	public int updateFeedBack(FeedBack fb) {
		String sql="update FeedBack set ReplyDate=?,Reply=?,Replyer=?,State=? where FbID=?";
		return jdbcTemplate.update(sql,new Object[]{
				fb.getReplyDate(),
				fb.getReply(),
				fb.getReplyer(),
				fb.getState(),
				fb.getFbID()
		});
	}

	@Override
	public List<FeedBack> queryFeedBack1(String username) {
		String sql="select * from FeedBack where  State='未处理' and UserName=? order by SubmitDate desc";
		final List<FeedBack> list= new ArrayList<>();
		jdbcTemplate.query(sql, new Object[] {username},
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							FeedBack fb= new FeedBack();
							fb.setFbID(rs.getInt("FbID"));
							fb.setFeedBack(rs.getString("FeedBack"));
							fb.setSubmitDate(rs.getDate("SubmitDate"));
							fb.setState(rs.getString("State"));
							fb.setUserName(rs.getString("UserName"));
							list.add(fb);
						}while(rs.next());
					}
		});
		return list;
	}
	
	@Override
	public List<FeedBack> queryFeedBack2(String username) {
		String sql="select * from FeedBack where FeedBack.State='已处理' and FeedBack.UserName=? order by ReplyDate desc";
		final List<FeedBack> list= new ArrayList<>();
		jdbcTemplate.query(sql, new Object[] {username},
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							FeedBack fb= new FeedBack();
							fb.setFbID(rs.getInt("FbID"));
							fb.setFeedBack(rs.getString("FeedBack"));
							fb.setSubmitDate(rs.getDate("SubmitDate"));
							fb.setReply(rs.getString("Reply"));
							fb.setReplyDate(rs.getDate("ReplyDate"));
							fb.setState(rs.getString("State"));
							fb.setReplyer(rs.getString("Replyer"));
							fb.setUserName(rs.getString("UserName"));
							list.add(fb);
						}while(rs.next());
					}
		});
		return list;
	}

	@Override
	public List<FeedBack> queryAllFeedBack() {
		String sql="select * from FeedBack order by SubmitDate desc";
		final List<FeedBack> list= new ArrayList<>();
		jdbcTemplate.query(sql, new Object[] {},
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							FeedBack fb= new FeedBack();
							fb.setFbID(rs.getInt("FbID"));
							fb.setFeedBack(rs.getString("FeedBack"));
							fb.setSubmitDate(rs.getDate("SubmitDate"));
							fb.setReply(rs.getString("Reply"));
							fb.setReplyDate(rs.getDate("ReplyDate"));
							fb.setState(rs.getString("State"));
							fb.setUserName(rs.getString("UserName"));
							fb.setReplyer(rs.getString("Replyer"));
							list.add(fb);
						}while(rs.next());
					}
		});
		return list;
	}

	@Override
	public FeedBack queryFeedBack(int fbID) {
		String sql="select * from FeedBack where FbID="+fbID;
		final FeedBack fb= new FeedBack();
		jdbcTemplate.query(sql, new Object[] {},
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							fb.setFbID(rs.getInt("FbID"));
							fb.setFeedBack(rs.getString("FeedBack"));
							fb.setSubmitDate(rs.getDate("SubmitDate"));
							fb.setReply(rs.getString("Reply"));
							fb.setReplyDate(rs.getDate("ReplyDate"));
							fb.setState(rs.getString("State"));
							fb.setReplyer(rs.getString("Replyer"));
							fb.setUserName(rs.getString("UserName"));							
						}while(rs.next());
					}
		});
		return fb;
	}

}
