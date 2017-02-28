package com.printer.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.printer.dao.ModelDao;
import com.printer.entities.Material;
import com.printer.entities.Model;
import com.printer.entities.User;

@Repository
public class ModelDaoImpl implements ModelDao {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	//添加商品模型
	@Override
	public int addModel(Model model) {
		String sql = "insert into ModelInfo(ModelName,ModelFile,UploadDate,UserID,ModelPic,isShare)"
				+ " values(?,?,?,?,?,?)";
		return jdbcTemplate.update(sql, new Object[] { 
				model.getModelName(),
				model.getModelFile(),
				model.getUploadDate(),
				model.getUserID(),
				model.getModelPic(),
				model.isShare()
			});
	}

	//删除商品模型
	@Override
	public int deleteModel(int modelid) {
		String sql="delete from ModelInfo where ModelID=?";
		return jdbcTemplate.update(sql, new Object[] { modelid });
	}

	//查询单个商品
	@Override
	public Model queryModel(String modelName) {
		String sql="select * from ModelInfo where ModelName=?";
		final Model model = new Model();
		jdbcTemplate.query(sql, new Object[] { modelName },
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						model.setModelID(rs.getInt("ModelID"));
						model.setModelName(rs.getString("ModelName"));
						model.setModelFile(rs.getString("ModelFile"));
						model.setUploadDate(rs.getDate("UploadDate"));
						model.setUserID(rs.getInt("UserID"));
						model.setModelPic(rs.getString("ModelPic"));
						model.setShare(rs.getBoolean("isShare"));					
					}
				});
		return model;

	}
	

	//查询商品列表
	@Override
	public List<Model> queryModelList() {
		String sql="select * from ModelInfo order by UploadDate desc";
		final List<Model> modellist = new ArrayList<Model>();
		jdbcTemplate.query(sql, new Object[] {},
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							Model model= new Model();
							model.setModelID(rs.getInt("ModelID"));
							model.setModelName(rs.getString("ModelName"));
							model.setModelFile(rs.getString("ModelFile"));
							model.setUploadDate(rs.getDate("UploadDate"));
							model.setUserID(rs.getInt("UserID"));
							model.setModelPic(rs.getString("ModelPic"));
							model.setShare(rs.getBoolean("isShare"));
							modellist.add(model);
						}while(rs.next());
					}
				});
		
		return modellist;
	}

	//用户分享模型
	@Override
	public int updateModel(Model model) {
		String sql = "update ModelInfo set ModelPic=?,isShare=?,Title=?,Category=? where ModelID=?";
		
		return jdbcTemplate.update(sql, new Object[] {
					model.getModelPic(),
					model.isShare(),
					model.getTitle(),
					model.getCategory(),
					model.getModelID()
				});
	}

	//根据条件查询商品
	@Override
	public List<Model> queryModelBy(String condition, String param, int num) {
		String sql = "";
		Object[] args = null;
		if(num==0){
			switch(condition){
			case "ClassID":
				sql="select * from ModelInfo where ClassID=?";
				args = new Object[] { param };
				break;
			case "ModelName":
				sql="select * from ModelInfo where ModelName like '%?%'";
				args = new Object[] { param };
				break;
			default:
				sql="select * from ModelInfo";
				args = new Object[] {};
			}
			
		}
		else{
			switch(condition){
			case "ClassID":
				sql="select top "+num+" * from ModelInfo where ClassID=? order by Date desc";
				args = new Object[] { param };
				break;
			case "ModelName":
				sql="select top "+num+" * from ModelInfo where ModelName like '%?%' order by Date desc";
				args = new Object[] { param };
				break;
			default:
				sql="select top "+num+" * from ModelInfo";
				args = new Object[] {};
			}
			
		}
		
		final List<Model> modellist = new ArrayList<Model>();
		jdbcTemplate.query(sql, args,
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							Model model= new Model();
							model.setModelID(rs.getInt("ModelID"));
							model.setModelName(rs.getString("ModelName"));
							model.setModelFile(rs.getString("ModelFile"));
							model.setUploadDate(rs.getDate("UploadDate"));
							model.setUserID(rs.getInt("UserID"));
							model.setShare(rs.getBoolean("isShare"));
							modellist.add(model);
						}while(rs.next());
					}
				});
		
		return modellist;
		
	}

	@Override
	public List<Material> queryMaterial() {
		String sql="select * from MaterialInfo";
		final List<Material> list = new ArrayList<Material>();
		jdbcTemplate.query(sql, new Object[] {},
				new RowCallbackHandler() {
					public void processRow(ResultSet rs) throws SQLException {
						do{
							Material m= new Material();
							m.setMaterialID(rs.getInt("MaterialID"));
							m.setMaterialName(rs.getString("MaterialName"));
							m.setMaterialPrice(rs.getDouble("MaterialPrice"));
							list.add(m);
						}while(rs.next());
					}
				});
		return list;
	}

	//查询3D模型库的内容
	@Override
	public List<Model> queryLibModels() {
		String sql="select t1.Title,t1.ModelID,t1.ModelFile,t1.UploadDate,t1.ModelPic,t1.Download,t2.UserName,t1.Category,t1.ModelName from ModelInfo t1,UserInfo t2 where t1.UserID=t2.UserID and t1.isShare='true'";
		final List<Model> modellist = new ArrayList<Model>();
		jdbcTemplate.query(sql, new Object[] {},
				new RowCallbackHandler() { 
					public void processRow(ResultSet rs) throws SQLException {
						do{
							Model model= new Model();
							model.setModelID(rs.getInt("ModelID"));
							model.setTitle(rs.getString("Title"));
							model.setModelFile(rs.getString("ModelFile"));
							model.setModelPic(rs.getString("ModelPic"));
							model.setModelName(rs.getString("ModelName"));
							model.setDownload(rs.getInt("Download"));
							model.setUserName(rs.getString("UserName"));
							model.setUploadDate(rs.getDate("UploadDate"));
							model.setCategory(rs.getString("Category"));
							modellist.add(model);
						}while(rs.next());
					}
				});
		return modellist;
	}

	//用户下载模型文件
	@Override
	public int downlaodModel(Model model) {
		String sql="update ModelInfo set Download=Download+1 where ModelID=?";
		return jdbcTemplate.update(sql, new Object[] { model.getModelID() });
	}
	
	

}
