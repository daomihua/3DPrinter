package com.printer.entities;

public class OrderItem {
	
	private String OrderID;
	private int ModelID;
	private String Material;
	private String Size;
	private int Quantity;
	private int AdID;
	private String Designer;
	private double Price;
	private String Image;
	private String Title;
	private String ModelName;
	private String Color;
	
	
	public String getColor() {
		return Color;
	}
	public void setColor(String color) {
		Color = color;
	}
	public String getModelName() {
		return ModelName;
	}
	public void setModelName(String modelName) {
		ModelName = modelName;
	}
	public String getOrderID() {
		return OrderID;
	}
	public void setOrderID(String orderID) {
		OrderID = orderID;
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
	public String getDesigner() {
		return Designer;
	}
	public void setDesigner(String designer) {
		Designer = designer;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double price) {
		Price = price;
	}
	public String getImage() {
		return Image;
	}
	public void setImage(String image) {
		Image = image;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	
	

}
