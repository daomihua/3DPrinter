package com.printer.entities;

import java.sql.Timestamp;
import java.util.Date;

public class Model {
	
	private int ModelID;
	private String ModelFile;
	private Date UploadDate;
	private int UserID;
	private String UserName;
	private String ModelName;
	private String ModelPic;
	private boolean Share;
	private int Download;
	private String Title;
	private String Category;
	
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public int getDownload() {
		return Download;
	}
	public void setDownload(int download) {
		Download = download;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public boolean isShare() {
		return Share;
	}
	public void setShare(boolean share) {
		Share = share;
	}
	public int getModelID() {
		return ModelID;
	}
	public void setModelID(int modelID) {
		ModelID = modelID;
	}
	public String getModelFile() {
		return ModelFile;
	}
	public void setModelFile(String modelFile) {
		ModelFile = modelFile;
	}
	public Date getUploadDate() {
		return UploadDate;
	}
	public void setUploadDate(Date uploadDate) {
		UploadDate = uploadDate;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getModelName() {
		return ModelName;
	}
	public void setModelName(String modelName) {
		ModelName = modelName;
	}
	public String getModelPic() {
		return ModelPic;
	}
	public void setModelPic(String modelPic) {
		ModelPic = modelPic;
	}

	
	

}
