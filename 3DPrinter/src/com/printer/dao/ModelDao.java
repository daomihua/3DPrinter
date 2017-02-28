package com.printer.dao;

import java.util.List;

import com.printer.entities.Material;
import com.printer.entities.Model;

public interface ModelDao {
	
	//添加商品模型
	public int addModel(Model model);
	
	//用户分享模型
	public int updateModel(Model model);
	
	//用户下载模型文件
	public int downlaodModel(Model model);
	
	//删除商品模型
	public int deleteModel(int modelid);
	
	//查询一个模型商品
	public Model queryModel(String modelName);
	
	//查询商品模型列表
	public List<Model> queryModelList();
	
	//根据条件查询商品 condition:条件类型   param:条件  num:查询数目
	public List<Model> queryModelBy(String condition,String param,int num);
	
	//查询材料信息
	public List<Material> queryMaterial();
	
	//查询3D模型库的内容
	public List<Model> queryLibModels();
	
	//分类获取模型
//	public List<Model> queryModelsByCategory(String catagory);
	
	//

}
