package com.printer.dao;

import java.util.List;

import com.printer.entities.Case;

public interface CaseDao {
	
	//添加成功案例
	public int addCase(Case c);
	
	//删除案例
	public int deleteCase(int caseID);
	
	//查询案例(用户查看)
	public Case queryCaseforUser(int caseID);
	
	
	//按条件查询前五案例(用户)
	public List<Case> queryTopCases(int num,String category);
	
	//按类别查询案例(用户)
	public List<Case> queryCases(String category);
	
	//查询案例(管理员查看)
	public List<Case> queryCases();
	
	//查询案例--管理员
	public Case queryCaseforAdmin(int caseID);
	
	//管理员审核案例
	public int updateCase(Case c);

}
