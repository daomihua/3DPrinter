package com.printer.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.printer.entities.Model;
import com.printer.entities.User;
import com.printer.service.FileUpload;
import com.printer.service.ModelService;

@Controller  
@RequestMapping("/models")
public class ModelController {
	
	@Autowired
	private ModelService modelService;
	@Autowired
	private FileUpload fileAction;
	
	//-----------------------------获取工艺文件列表-------------------------//
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getAllModel(){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("state", true);
		modelMap.put("data",modelService.queryModelList());
		return modelMap;
	}
	
	//-----------------------------获取材料列表-------------------------//
	@RequestMapping(value="/material",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> getAllMaterial(){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("state", true);
		modelMap.put("data",modelService.queryMaterial());
		return modelMap;
	}
	
	//--------------------------- 上传工艺文件 ---------------------------//
	@RequestMapping(value="/add",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addModelFile(@RequestParam("fileupload") CommonsMultipartFile file,
			HttpSession session){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		User u=(User)session.getAttribute("User");
		String folder="modelfile/";
		String path=session.getServletContext().getRealPath("/")+"\\files"+"\\modelfile"+"\\";
		Date d=new Date();
		
		Map<String, Object> map= fileAction.uploadZipFile(file, path, folder);
//		Map<String, Object> map= fileAction.uploadRenameFile(file, path, folder, d.getTime()+"");
		String p=map.get("url").toString();
		String[] modelname=p.split("/");
		
		Model model = new Model();
		model.setModelFile(p);
		model.setModelName(modelname[2]);
		model.setUploadDate(d);
		model.setUserID(u.getUserID());
		model.setShare(false);
		
		int f = modelService.addModel(model);
		
		if(f>0 && map.get("state").toString().equals("true")){
			modelMap.put("state", true);
			modelMap.put("msg", "工艺文件上传成功");
			modelMap.put("modelid", modelService.queryModel(modelname[2]).getModelID());
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "工艺文件上传出错");
		}
		return modelMap;
	}
	
	//------------------------ 删除工艺文件 -----------------------------//
	@RequestMapping(value="/delete/{modelID}",method=RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Object> deleteModel(@PathVariable("modelID")int modelID){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		int flag=modelService.deleteModel(modelID);
		if(flag>0){
			modelMap.put("state", true);
			modelMap.put("msg", "工艺文件删除成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "工艺文件删除失败");
		}
		return modelMap;
	}
	
	//----------------------------修改工艺文件----------------------------- //
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteModel(@RequestBody Model model){
		Map<String,Object> modelMap = new HashMap<String,Object>();
		model.setShare(true);
		int flag=modelService.updateModel(model);
		if(flag>0){
			modelMap.put("state", true);
			modelMap.put("msg", "工艺文件修改成功");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "工艺文件修改失败");
		}
		return modelMap;
	}
	
	//----------------------- 上传工艺文件图片 -----------------------------------//
	@RequestMapping(value="/upload",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addModelImg(@RequestParam("modelImg") CommonsMultipartFile file,
			HttpSession session){  
		String folder="images/models/";
		String path=session.getServletContext().getRealPath("/")+"\\files"+"\\images"+"\\models\\";
		return fileAction.uploadFile(file, path,folder);
	}
	
	//----------------------- 分享工艺文件 -----------------------------------//
	@RequestMapping(value="/share",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> shareModel(@RequestBody Model model,
			HttpSession session){  
		Map<String,Object> modelMap = new HashMap<String,Object>();
		System.out.println(model.getTitle());
		int f=modelService.updateModel(model);
		if(f>0){
			modelMap.put("state", true);
			modelMap.put("msg", "工艺文件已分享至模型库~");
		}else{
			modelMap.put("state", false);
			modelMap.put("msg", "工艺文件分享失败");
		}
		return modelMap;
	}
	
	//----------------------下载工艺文件--------------------------------//
	@RequestMapping(value="/download",method=RequestMethod.POST)
	public ModelAndView  downloadModel(@RequestBody Model model,
			HttpServletRequest request, HttpServletResponse response) throws Exception{  
		String storeName = model.getModelName();
        String realName = model.getModelName();  
		 
		request.setCharacterEncoding("UTF-8"); 
		
        BufferedInputStream bis = null;  
        BufferedOutputStream bos = null;  
        String ctxPath = request.getSession().getServletContext()  
                .getRealPath("/")  
                + "\\files"+"\\modelfile"+"\\";  
        String downLoadPath = ctxPath + storeName;  
  
         File file= new File(downLoadPath);  
         long fileLength=file.length();

        response.setContentType("multipart/form-data"); 
        response.setHeader("Content-disposition", "attachment; filename="  
                + new String(realName.getBytes("utf-8"), "ISO8859-1"));  
        response.setHeader("Content-Length", String.valueOf(fileLength));  
  
        bis = new BufferedInputStream(new FileInputStream(downLoadPath));  
        bos = new BufferedOutputStream(response.getOutputStream());  
        byte[] buff = new byte[2048];  
        int bytesRead;  
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {  
            bos.write(buff, 0, bytesRead);  
        }  
        bis.close();
        bos.flush();
        bos.close(); 
        System.out.println(model.getModelID());
        modelService.downloadModel(model);
        return null;
	}
	
	//----------------------- 获取模型库 -----------------------------------//
	@RequestMapping(value="/library",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getLibrary(){  
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("state", true);
		modelMap.put("data", modelService.queryLibraryModels());
		return modelMap;
	}
	
	//----------------------- 分类获取模型 -----------------------------------//
	@RequestMapping(value="/category/{category}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getModelsByCategory(@PathVariable("category")String category){  
		Map<String,Object> modelMap = new HashMap<String,Object>();
		modelMap.put("state", true);
		modelMap.put("data", modelService.queryLibraryModels());
		return modelMap;
	}
	
}
