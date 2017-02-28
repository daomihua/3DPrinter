package com.printer.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller  
@RequestMapping("/files")
public class FileController {
	
//	@RequestMapping(method = RequestMethod.POST, value = "/upload")
//    public Map<String, Object> handleFileUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) {
//		Map<String, Object> modelMap = new HashMap<String, Object>(3);
//		modelMap.put("name", file.toString());
//		System.out.println(file.toString());
//		return modelMap;
//        // todo what you want
//    }
	
	@RequestMapping(value="/upload",headers = "'Content-Type': 'multipart/form-data'",method=RequestMethod.POST)
	@ResponseBody
	public int addUser(@RequestParam("sdd") MultipartFile file){  
		Map<String, Object> modelMap = new HashMap<String, Object>(1);
//        for(int i = 0;i<files.length;i++){  
            System.out.println("fileName---------->" + file.getOriginalFilename());  
          int a = 0;
            if(!file.isEmpty()){  
                int pre = (int) System.currentTimeMillis();  
                try {  
                    //拿到输出流，同时重命名上传的文件  
                    FileOutputStream os = new FileOutputStream("D:/" + new Date().getTime() + file.getOriginalFilename());  
                    //拿到上传文件的输入流  
                    FileInputStream in = (FileInputStream) file.getInputStream();  
                      
                    //以写字节的方式写文件  
                    int b = 0;  
                    while((b=in.read()) != -1){  
                        os.write(b);  
                    }  
                    os.flush();  
                    os.close();  
                    in.close();  
                    int finaltime = (int) System.currentTimeMillis();  
                    System.out.println(finaltime - pre);  
                    modelMap.put("state", true);
                    a=1;
                      
                } catch (Exception e) {  
                    e.printStackTrace();  
                    System.out.println("上传出错"); 
                    modelMap.put("state", false);
                }  
        }  
//        } 
        return a;  
    }  

}
