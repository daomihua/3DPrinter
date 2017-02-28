package com.printer.controller;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.printer.entities.Admin;
import com.printer.entities.Authority;
import com.printer.entities.Role;
import com.printer.service.AdminService;
import com.printer.service.FileUpload;

@Controller  
@RequestMapping("/admins")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	@Autowired
	private FileUpload fileAction;
	
	//---------------------------管理员登录---------------------------//
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> AdminLogin(@RequestBody Admin admin,HttpSession httpSession){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Admin a = new Admin();
		String adName = admin.getAdName();
		String adPassword = admin.getAdPassword();
		a = adminService.isExistAdmin(adName);
		System.out.println(a.getAdQQ());
		if(a.getAdName()==null){
			modelMap.put("state", false);
			modelMap.put("msg", "用户不存在！");
			modelMap.put("data", null);
		}else if( !adPassword.equals(a.getAdPassword()) ){
			modelMap.put("state", false);
			modelMap.put("msg", "密码错误！");
			modelMap.put("data", null);
		}else{
			modelMap.put("state", true);
			modelMap.put("msg", "登录成功！");
			modelMap.put("data", a);
			httpSession.setAttribute("admin", a);
		}
		return modelMap;
	}
	
	//----------------------------- 获取管理员权限-----------------------//
	@RequestMapping(value="/authorities/{roleID}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getAuthorities(@PathVariable("roleID") int roleID){
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		List<Authority> list = new ArrayList<Authority>();
		list = adminService.queryAuthorityList(roleID);
		if(!list.isEmpty()){
			modelMap.put("state", true);
			modelMap.put("data", list);
			modelMap.put("msg", "已经取得权限！");
		}else
		{
			modelMap.put("state", false);
			modelMap.put("data", null);
			modelMap.put("msg", "没有任何权限！");
		}
		return modelMap;
	}
	
	//----------------------------- 添加管理员-----------------------//
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addAdmin(@RequestBody Admin admin){
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		//默认密码
		admin.setAdPassword("1234");
		admin.setPortrait("files/images/portraits/a0.jpg");
		Admin a = new Admin();
		a = adminService.isExistAdmin(admin.getAdName());
		if( a.getAdName()!=null ){
			modelMap.put("state", false);
			modelMap.put("msg", "该用户名已经存在");
		}else if( adminService.addAdmin(admin)!=0 ){
			modelMap.put("state", true);
			modelMap.put("msg", "管理员添加成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "管理员添加出错");
		}
		return modelMap;
	}
	
	//---------------------------删除管理员----------------------------//
	@RequestMapping(value="/delete/{adID}",method=RequestMethod.DELETE)
	@ResponseBody
	public Map<String,Object> deleteAdmin(@PathVariable("adID") int adID){
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		int rs = adminService.deleteAdmin(adID);
		if(rs!=0){
			modelMap.put("state", true);
			modelMap.put("msg", "管理员删除成功！");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "管理员删除失败！");
		}
		return modelMap;
	}
	
	//--------------------------- 修改管理员（个人中心） -------------------------------//
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateAdmin(@RequestBody Admin admin){
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		if( adminService.updateAdmin(admin)!=0 ){
			modelMap.put("state", true);
			modelMap.put("msg", "管理员修改成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "管理员修改出错");
		}
		return modelMap;
	}
	
	//----------------------- 上传头像 -----------------------------------//
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCaseImg(@RequestParam("portrait") CommonsMultipartFile file,
			HttpSession session){  
		Admin a = new Admin();
		a = (Admin)session.getAttribute("admin");
		String folder="images/portraits/";
		String path=session.getServletContext().getRealPath("/")+"\\files"+"\\images"+"\\portraits\\";
		return fileAction.uploadRenameFile(file, path,folder, a.getAdID()+"");

    } 
	
	//---------------------------- 获取管理员列表 --------------------------//
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getAdmins(){
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		List<Admin> list = new ArrayList<Admin>();
		list = adminService.queryAdmins();
		modelMap.put("state", true);
		modelMap.put("data", list);
		return modelMap;
	}
	
	//---------------------------- 获取设计师列表 --------------------------//
	@RequestMapping(value="/designer",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getDesigner(){
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		List<Admin> list = new ArrayList<Admin>();
		list = adminService.queryDesignerList();
		modelMap.put("state", true);
		modelMap.put("data", list);
		return modelMap;
	}
	
	//------------------------------ 查询管理员 ----------------------------//
	@RequestMapping(value="/admin/{adID}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getAdmin(@PathVariable("adID")int adID){
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		Admin admin = new Admin();
		admin = adminService.queryAdmin(adID);
		modelMap.put("state", true);
		modelMap.put("data", admin);
		modelMap.put("msg", "读取管理员成功");
		return modelMap;
	}
	  
	//------------------------------ 管理员登出 ----------------------------//
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> logoutAdmin(HttpSession session){
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		session.removeAttribute("admin");
		modelMap.put("state", true);
		modelMap.put("msg", "管理员登出成功");
		return modelMap;
	}
	
	//------------------------------ 查询统计信息 ----------------------------//
	@RequestMapping(value="/tongji",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> tongji(){
		return adminService.getTongJi();
	}
	
	//----------------------打印报表--------------------------------//
	@RequestMapping(value="/print/word",method=RequestMethod.POST)
	public ModelAndView  downloadModel(@RequestBody String html,HttpServletRequest request, 
			HttpServletResponse response)throws Exception{
		String content="<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title></title></head><body>" +
				html+"</body></html>";
		System.out.println(content);
		byte b[] = content.getBytes();    
		ByteArrayInputStream bais = new ByteArrayInputStream(b);    
		POIFSFileSystem poifs = new POIFSFileSystem();    
		DirectoryEntry directory = poifs.getRoot();    
		DocumentEntry documentEntry = directory.createDocument("WordDocument", bais);    
		//输出文件  
		String name="报表";  
		response.reset();  
		response.setHeader("Content-Disposition",  
		                     "attachment;filename=" +  
		                     new String( (name + ".doc").getBytes(),  
		                                "iso-8859-1"));  
		response.setContentType("application/msword");  
		OutputStream ostream = response.getOutputStream();   
		//输出文件的话，new一个文件流  
		//FileOutputStream ostream = new FileOutputStream(path+ fileName);    
		poifs.writeFilesystem(ostream);    
		ostream.flush();  
		ostream.close();   
		bais.close();   
		return null; 
	}

}
