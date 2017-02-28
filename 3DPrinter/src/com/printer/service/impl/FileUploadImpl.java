package com.printer.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.printer.service.FileUpload;

@Service
public class FileUploadImpl implements FileUpload {

	//上传用户文件
	@Override
	public Map<String, Object> uploadFile(CommonsMultipartFile file,
			String path, String folder) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String filename= new Date().getTime() + file.getOriginalFilename();
		try{
			SaveFileFromInputStream(file.getInputStream(),path,filename);
			modelMap.put("state", true);
            modelMap.put("msg", "文件上传成功");
            modelMap.put("url", "files/"+folder+filename);
            modelMap.put("filename", filename);
		} catch (Exception e) {  
            e.printStackTrace();  
            System.out.println("上传出错"); 
            modelMap.put("state", false);
            modelMap.put("msg", "文件上传出错");
        } 
		return modelMap;
	}

	//上传重命名文件
	@Override
	public Map<String, Object> uploadRenameFile(CommonsMultipartFile file,
			String path, String folder,String name) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		String[] n=file.getOriginalFilename().split("\\.");
		String filename= name+"."+n[1];
		try{
			SaveFileFromInputStream(file.getInputStream(),path,filename);
			modelMap.put("state", true);
            modelMap.put("msg", "文件上传成功");
            modelMap.put("url", "files/"+folder+filename);
            modelMap.put("filename", filename);
           
		} catch (Exception e) {  
            e.printStackTrace();  
            System.out.println("上传出错"); 
            modelMap.put("state", false);
            modelMap.put("msg", "文件上传出错");
        } 
		return modelMap;
	}
	
	//实现上传
	public void SaveFileFromInputStream(InputStream stream,String path,String filename) throws IOException   
    {         
        FileOutputStream fs=new FileOutputStream( path + filename);   
        byte[] buffer =new byte[1024*1024];   
        int bytesum = 0;   
        int byteread = 0;    
        while ((byteread=stream.read(buffer))!=-1)   
        {   
           bytesum+=byteread;   
           fs.write(buffer,0,byteread);   
           fs.flush();   
        }    
        fs.close();   
        stream.close();         
    }
 
	private static String zipName(String name) {  
        String prefix = "";  
        if (name.indexOf(".") != -1) {  
            prefix = name.substring(0, name.lastIndexOf("."));  
        } else {  
            prefix = name;  
        }  
        return prefix + ".zip";  
    }  
	
	//上传模型文件并压缩
	@Override
	public Map<String, Object> uploadZipFile(CommonsMultipartFile file,
			String path, String folder) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		
		String fileName=file.getOriginalFilename();
//		String storeName = rename(fileName); 
		String storeName= new Date().getTime() + fileName;
        String noZipName = path + storeName;  
        String zipName = zipName(noZipName);  
        String newname = zipName(storeName);
        
        try{
        	
        	// 上传成为压缩文件  
            ZipOutputStream outputStream = new ZipOutputStream(  
                    new BufferedOutputStream(new FileOutputStream(zipName)));  
            outputStream.putNextEntry(new ZipEntry(fileName));  
            outputStream.setEncoding("GBK");
            
            InputStream stream=file.getInputStream();  
            byte[] buffer =new byte[1024*1024];   
            int bytesum = 0;   
            int byteread = 0;    
            while ((byteread=stream.read(buffer))!=-1)   
            {   
               bytesum+=byteread;   
               outputStream.write(buffer,0,byteread);   
               outputStream.flush();   
            }    
            outputStream.close();   
            stream.close();
            
            modelMap.put("state", true);
            modelMap.put("msg", "文件上传成功");
            modelMap.put("url", "files/"+folder+newname);
            modelMap.put("filename", newname);
            System.out.println(modelMap.get("url"));
            
        }catch(Exception e){
        	e.printStackTrace();  
            System.out.println("上传出错"); 
            modelMap.put("state", false);
            modelMap.put("msg", "文件上传出错");
        }
        
		return modelMap;
	}


}
