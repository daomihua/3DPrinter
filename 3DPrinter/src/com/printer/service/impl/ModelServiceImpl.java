package com.printer.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.printer.dao.ModelDao;
import com.printer.entities.Material;
import com.printer.entities.Model;
import com.printer.service.ModelService;

@Service
public class ModelServiceImpl implements ModelService {

	@Autowired
	public ModelDao modeldao;
	
	//添加商品模型
	@Override
	public int addModel(Model model) {
		return modeldao.addModel(model);
	}

	//用户分享模型
	@Override
	public int updateModel(Model model) {
		return modeldao.updateModel(model);
	}

	//删除商品模型
	@Override
	public int deleteModel(int modelid) {
		return modeldao.deleteModel(modelid);
	}

	//查询工艺文件
	@Override
	public Model queryModel(String modelNmae) {
		return modeldao.queryModel(modelNmae);
	}

	//查询工艺文件列表
	@Override
	public List<Model> queryModelList() {
		return modeldao.queryModelList();
	}

	//获取材料列表
	@Override
	public List<Material> queryMaterial() {
		return modeldao.queryMaterial();
	}

	//获取模型库
	@Override
	public List<Model> queryLibraryModels() {
		return modeldao.queryLibModels();
	}

	//用户下载模型文件，添加下载量
	@Override
	public int downloadModel(Model model) {
		return modeldao.downlaodModel(model);
	}

}
