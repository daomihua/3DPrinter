package com.printer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.printer.dao.FeedBackDao;
import com.printer.entities.FeedBack;
import com.printer.service.FeedBackService;

@Service
public class FeedBackServiceImpl implements FeedBackService {

	@Autowired
	public FeedBackDao fbDao;
	
	@Override
	public int addFeedBack(FeedBack fb) {
		// TODO Auto-generated method stub
		return fbDao.addFeedBack(fb);
	}

	@Override
	public int updateFeedBack(FeedBack fb) {
		// TODO Auto-generated method stub
		return fbDao.updateFeedBack(fb);
	}

	@Override
	public List<FeedBack> queryFeedBack1(String username) {
		// TODO Auto-generated method stub
		return fbDao.queryFeedBack1(username);
	}

	@Override
	public List<FeedBack> queryFeedBack2(String username) {
		// TODO Auto-generated method stub
		return fbDao.queryFeedBack2(username);
	}

	//获取所有反馈列表
	@Override
	public List<FeedBack> queryAllFeedBack() {
		// TODO Auto-generated method stub
		return fbDao.queryAllFeedBack();
	}

	@Override
	public FeedBack queryFeedBack(int fbID) {
		// TODO Auto-generated method stub
		return fbDao.queryFeedBack(fbID);
	}

}
