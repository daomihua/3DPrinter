package com.printer.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.printer.entities.Admin;
import com.printer.entities.Case;
import com.printer.service.CaseService;
import com.printer.service.FileUpload;

@Controller  
@RequestMapping("/cases")
public class CaseController {
	
	@Autowired
	private CaseService caseService;
	@Autowired
	private FileUpload fileAction;

	//------------------------ 获取所有案例 -------------------------------//
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getAllCases(){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("state", true);
		modelMap.put("category1", caseService.queryTopCases(5,"工业设计"));
		modelMap.put("category2", caseService.queryTopCases(5,"雕塑"));
		modelMap.put("category3", caseService.queryTopCases(5,"建筑"));
		modelMap.put("category4", caseService.queryTopCases(5,"工艺品"));
		modelMap.put("category5", caseService.queryTopCases(5,"医疗"));
		modelMap.put("category6", caseService.queryTopCases(5,"人像"));
		modelMap.put("category7", caseService.queryTopCases(5,"珠宝"));
		modelMap.put("category8", caseService.queryTopCases(5,"个性定制"));
		modelMap.put("msg", "成功获取案例列表");
		return modelMap;
	}
	
	//------------------------ 获取所有案例 -------------------------------//
	@RequestMapping(value="/admin",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getAdminCases(){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("state", true);
		modelMap.put("data", caseService.queryCases());
		modelMap.put("msg", "成功获取案例列表");
		return modelMap;
	}
	
	//------------------------ 分类获取案例 -------------------------------//
	@RequestMapping(value="/category/{category}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getCases(@PathVariable("category")String category){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		System.out.println(category);
		modelMap.put("state", true);
		modelMap.put("data", caseService.queryTopCases(0,category));
		modelMap.put("msg", "成功获取案例列表");
		return modelMap;
	}
	
	//------------------------ 获取一个案例(用户) -------------------------------//
	@RequestMapping(value="/case/{caseID}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getCase(@PathVariable("caseID")int caseID){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("state", true);
		modelMap.put("data", caseService.queryCaseforUser(caseID));
		modelMap.put("msg", "成功获取案例列表");
		return modelMap;
	}
	
	//------------------------ 获取一个案例(管理员) -------------------------------//
	@RequestMapping(value="/admin/case/{caseID}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getCaseByAdmin(@PathVariable("caseID")int caseID){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("state", true);
		modelMap.put("data", caseService.queryCaseforAdmin(caseID));
		modelMap.put("msg", "成功获取案例列表");
		return modelMap;
	}
	
	//------------------------ 审核案例(管理员) -------------------------------//
	@RequestMapping(value="/audit/{caseID}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> auditCase(@PathVariable("caseID")int caseID,HttpSession session){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		Case c=new Case();
		Admin a=(Admin)session.getAttribute("admin");
		c.setCaseID(caseID);
		c.setState("已审核");
		c.setAuditor(a.getAdName());
		int r=caseService.auditCase(c);
		if(r>0){
			modelMap.put("state", true);
			modelMap.put("msg", "案例审核完毕");
		}else{
			modelMap.put("state", true);
			modelMap.put("msg", "案例审核失败");
		}
		return modelMap;
	}
	
	//----------------------- 上传案例图片 -----------------------------------//
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCaseImg(@RequestParam("caseImg") CommonsMultipartFile file,
			HttpSession session){  
        String folder="images/cases/";
		String path=session.getServletContext().getRealPath("/")+"\\files"+"\\images"+"\\cases\\";
		return fileAction.uploadFile(file, path,folder); 
    }  
	
	//--------------------------------- 添加成功案例 -----------------------------//
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCase(@RequestBody Case c){  
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		c.setState("未审核");
		c.setPublishTime(new Date());// new Date()为获取当前系统时间
		int a = caseService.addCase(c);
		if(a>0){
			modelMap.put("state",true);
			modelMap.put("msg", "案例添加成功！");
		}
		else{
			modelMap.put("state",false);
			modelMap.put("msg", "案例添加出错！");
		}
		return modelMap;
	}
	
	//--------------------------------- 删除成功案例 -----------------------------//
	@RequestMapping(value="/delete/{caseID}",method=RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteCase(@PathVariable("caseID")int caseID){  
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		int a = caseService.deleteCase(caseID);
		if(a!=0){
			modelMap.put("state",true);
			modelMap.put("msg", "案例删除成功！");
		}
		else{
			modelMap.put("state",false);
			modelMap.put("msg", "案例删除出错！");
		}
		return modelMap;
	}

	

}
