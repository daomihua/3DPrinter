package com.printer.service;

import java.util.List;

import com.printer.entities.Case;

public interface CaseService {
	
	//添加成功案例
	public int addCase(Case c);
	
	//删除案例
	public int deleteCase(int caseID);
	
	//查询案例--用户
	public Case queryCaseforUser(int caseID);
	
	//查询案例--管理员
	public Case queryCaseforAdmin(int caseID);
	
	//按条件查询前五案例
	public List<Case> queryTopCases(int num,String category);
	
	//按条件查询案例列表
	public List<Case> queryCases();
	
	//管理员审核案例
	public int auditCase(Case c);

}
