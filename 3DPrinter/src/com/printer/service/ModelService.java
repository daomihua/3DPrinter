package com.printer.service;

import java.util.List;

import com.printer.entities.Material;
import com.printer.entities.Model;

public interface ModelService {
	
	//添加一个商品模型
	public int addModel(Model model);
	
	//修改一个商品模型
	public int updateModel(Model model);
	
	//删除一个商品模型
	public int deleteModel(int modelid);
	
	//查询一个商品模型
	public Model queryModel(String modelName);
	
	//查询商品模型列表
	public List<Model> queryModelList();
	
	//获取材料列表
	public List<Material> queryMaterial();
	
	//获取模型库
	public List<Model> queryLibraryModels();
	
	//用户下载模型文件，添加下载量
	public int downloadModel(Model model);

}
