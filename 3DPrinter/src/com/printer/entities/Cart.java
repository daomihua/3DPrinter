package com.printer.entities;

public class Cart {
	private int UserID;
	private int ModelID;
	private String Material;
	private String Size;
	private int Quantity;
	private int AdID;
	private String ModelName;
	private String Designer;
	private String Color;
	
	public String getColor() {
		return Color;
	}
	public void setColor(String color) {
		Color = color;
	}
	public String getDesigner() {
		return Designer;
	}
	public void setDesigner(String designer) {
		Designer = designer;
	}
	public String getModelName() {
		return ModelName;
	}
	public void setModelName(String modelName) {
		ModelName = modelName;
	}
	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public int getModelID() {
		return ModelID;
	}
	public void setModelID(int modelID) {
		ModelID = modelID;
	}
	public String getMaterial() {
		return Material;
	}
	public void setMaterial(String material) {
		Material = material;
	}
	public String getSize() {
		return Size;
	}
	public void setSize(String size) {
		Size = size;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public int getAdID() {
		return AdID;
	}
	public void setAdID(int adID) {
		AdID = adID;
	}
	
}
