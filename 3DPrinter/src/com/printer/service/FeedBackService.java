package com.printer.service;

import java.util.List;

import com.printer.entities.FeedBack;

public interface FeedBackService {
	//提交反馈
	public int addFeedBack(FeedBack fb);
	
	//处理反馈
	public int updateFeedBack(FeedBack fb);
	
	//获取未处理反馈列表
	public List<FeedBack> queryFeedBack1(String username);
	
	//获取已处理反馈列表
	public List<FeedBack> queryFeedBack2(String username);
	
	//获取所有反馈列表
	public List<FeedBack> queryAllFeedBack();
	
	//获取单个反馈
	public FeedBack queryFeedBack(int fbID);
		
}
