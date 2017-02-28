package com.printer.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.printer.dao.CaseDao;
import com.printer.entities.Case;

@Repository
public class CaseDaoImpl implements CaseDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	//添加成功案例
	@Override
	public int addCase(Case c) {
		String sql = "insert into CaseInfo(CaseTitle,CaseContent,CaseImg,Publisher,PublishTime,Category,State)"
				+" values(?,?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[]{
				c.getCaseTitle(),
				c.getCaseContent(),
				c.getCaseImg(),
				c.getPublisher(),
				c.getPublishTime(),
				c.getCategory(),
				c.getState()
		});
	}

	//删除案例
	@Override
	public int deleteCase(int caseID) {
		String sql = "delete from CaseInfo where CaseID=?";
		return jdbcTemplate.update(sql, new Object[]{caseID});
	}

	//查询案例(用户查看)
	@Override
	public Case queryCaseforUser(int caseID) {
		String sql = "select * from CaseInfo where CaseID=? and State='已审核' order by PublishTime desc";
		final Case c = new Case();
		jdbcTemplate.query(sql, new Object[] { caseID },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						c.setCaseID(rs.getInt("CaseID"));
						c.setCaseTitle(rs.getString("CaseTitle"));
						c.setCaseContent(rs.getString("CaseContent"));
						c.setPublisher(rs.getString("Publisher"));
						c.setPublishTime(rs.getDate("PublishTime"));
					}
		});
		return c;
	}
	

	//按类型获取前五个案例
	@Override
	public List<Case> queryTopCases(int num,String category) {
		String sql1 = "select top "+num+" * from CaseInfo where Category=? and State='已审核' order by PublishTime desc";
		String sql2 = "select * from CaseInfo where Category=?";
		String sql="";
		if(num==0)
			sql=sql2;
		else 
			sql=sql1;
		final List<Case> list = new ArrayList<Case>();
		jdbcTemplate.query(sql, new Object[] { category },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							Case c =new Case();
							c.setCaseID(rs.getInt("CaseID"));
							c.setCaseTitle(rs.getString("CaseTitle"));
							c.setCaseImg(rs.getString("CaseImg"));
							list.add(c);
						}while(rs.next());
					}
		});
		return list;
	}

	//查询案例(管理员查看)
	@Override
	public List<Case> queryCases() {
		String sql = "select * from CaseInfo order by PublishTime desc";
		final List<Case> list = new ArrayList<Case>();
		jdbcTemplate.query(sql, new Object[] {},
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							Case c =new Case();
							c.setCaseID(rs.getInt("CaseID"));
							c.setCaseTitle(rs.getString("CaseTitle"));
							c.setPublisher(rs.getString("Publisher"));
							c.setPublishTime(rs.getDate("PublishTime"));
							c.setCategory(rs.getString("Category"));
							c.setState(rs.getString("State"));
							list.add(c);
						}while(rs.next());
					}
		});
		return list;
	}


	@Override
	public List<Case> queryCases(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	//管理员审核案例
	@Override
	public int updateCase(Case c) {
		String sql="update CaseInfo set Auditor=?,State='已审核' where CaseID=?";
		return jdbcTemplate.update(sql, new Object[]{
				c.getAuditor(),
				c.getCaseID()
		});
	}

	//管理员查看单个案例
	@Override
	public Case queryCaseforAdmin(int caseID) {
		String sql = "select * from CaseInfo where CaseID=?";
		final Case c = new Case();
		jdbcTemplate.query(sql, new Object[] { caseID },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						c.setCaseID(rs.getInt("CaseID"));
						c.setCaseTitle(rs.getString("CaseTitle"));
						c.setCaseContent(rs.getString("CaseContent"));
						c.setPublisher(rs.getString("Publisher"));
						c.setPublishTime(rs.getDate("PublishTime"));
						c.setAuditor(rs.getString("Auditor"));
					}
		});
		return c;
	}

}
