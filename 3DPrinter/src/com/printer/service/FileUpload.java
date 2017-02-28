package com.printer.service;

import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;


public interface FileUpload{
	//上传图片文件
	public Map<String, Object> uploadFile(CommonsMultipartFile file,String path,String folder);
	
	//上传头像
	public Map<String, Object> uploadRenameFile(CommonsMultipartFile file,String path,String folder,String name);
	
	//上传模型文件并压缩
	public Map<String, Object> uploadZipFile(CommonsMultipartFile file,String path,String folder);

}
