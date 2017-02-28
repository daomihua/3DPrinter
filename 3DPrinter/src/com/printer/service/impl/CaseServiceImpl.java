package com.printer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.printer.dao.CaseDao;
import com.printer.entities.Case;
import com.printer.service.CaseService;

@Service
public class CaseServiceImpl implements CaseService {
	
	@Autowired
	public CaseDao casedao;

	@Override
	public int addCase(Case c) {
		// TODO Auto-generated method stub
		return casedao.addCase(c);
	}

	@Override
	public int deleteCase(int caseID) {
		// TODO Auto-generated method stub
		return casedao.deleteCase(caseID);
	}

	@Override
	public Case queryCaseforUser(int caseID) {
		// TODO Auto-generated method stub
		return casedao.queryCaseforUser(caseID);
	}

	@Override
	public Case queryCaseforAdmin(int caseID) {
		// TODO Auto-generated method stub
		return casedao.queryCaseforAdmin(caseID);
	}

	@Override
	public List<Case> queryTopCases(int num,String category) {
		// TODO Auto-generated method stub
		return casedao.queryTopCases(num,category);
	}

	@Override
	public List<Case> queryCases() {
		// TODO Auto-generated method stub
		return casedao.queryCases();
	}

	@Override
	public int auditCase(Case c) {
		// TODO Auto-generated method stub
		return casedao.updateCase(c);
	}

}
