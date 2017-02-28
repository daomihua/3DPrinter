package com.printer.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.printer.entities.FeedBack;
import com.printer.service.FeedBackService;

@Controller  
@RequestMapping("/feedbacks")
public class FeedBackController {
	
	@Autowired
	private FeedBackService fbService;
	
	//------------------------ 获取所有反馈 -------------------------------//
	@RequestMapping(value="/user/{username}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getAllFeedBacks(@PathVariable("username") String username){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("state", true);
		List<FeedBack> list1=new ArrayList<>();
		List<FeedBack> list2=new ArrayList<>();
		list1=fbService.queryFeedBack1(username);
		list2=fbService.queryFeedBack2(username);
		if(list1.size()>0)
			modelMap.put("data1", list1);
		if(list2.size()>0)
			modelMap.put("data2", list2);
		return modelMap;
	}
	
	//------------------------ 处理反馈 -------------------------------//
	@RequestMapping(value="/admin/update",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateFeedBack(@RequestBody FeedBack fb){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		
		Date date=new Date();
		fb.setState("已处理");
		fb.setReplyDate(date);
		
		int r=fbService.updateFeedBack(fb);
		if(r>0){
			modelMap.put("state", true);
				modelMap.put("msg", "反馈已成功处理!");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "反馈处理出错!");
		}
		return modelMap;
	}
	
	//------------------------ 提交反馈 -------------------------------//
	@RequestMapping(value="/user/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addFeedBack(@RequestBody FeedBack fb){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		
		Date date=new Date();
		fb.setState("未处理");
		fb.setSubmitDate(date);
		
		int r=fbService.addFeedBack(fb);
		if(r>0){
			modelMap.put("state", true);
				modelMap.put("msg", "反馈已成功提交!");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "反馈提交出错!");
		}
		return modelMap;
	}
	
	//------------------------ 获取反馈列表 -------------------------------//
	@RequestMapping(value="/admin",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getFeedBack(){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("state", true);
		modelMap.put("data", fbService.queryAllFeedBack());
		return modelMap;
	}
	
	//------------------------ 查看单个反馈 -------------------------------//
	@RequestMapping(value="/{fbID}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getOneFB(@PathVariable("fbID") int fbID){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("state", true);
		modelMap.put("data", fbService.queryFeedBack(fbID));
		return modelMap;
	}

}
