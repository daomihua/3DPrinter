package com.printer.entities;

import java.sql.Timestamp;
import java.util.List;

public class Order {
	
	private String OrderID;
	private String UserName;
	private String OrderState;
	private double OrderPrice;
	private Timestamp OrderDate;
	private String Receiver;
	private String Phone;
	private String PostCode;
	private String Direction;
	private Timestamp CancelDate;
	private Timestamp CheckDate;
	private Timestamp PayDate;
	private Timestamp DeliverDate;
	private Timestamp ReceiveDate;
	private String Remark;
	private int AdID;
	private List<OrderItem> Items;
	private String Designer;
	
	public List<OrderItem> getItems() {
		return Items;
	}
	public void setItems(List<OrderItem> items) {
		Items = items;
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
	public String getOrderID() {
		return OrderID;
	}
	public void setOrderID(String orderID) {
		OrderID = orderID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getOrderState() {
		return OrderState;
	}
	public void setOrderState(String oerderState) {
		OrderState = oerderState;
	}
	public double getOrderPrice() {
		return OrderPrice;
	}
	public void setOrderPrice(double orderPrice) {
		OrderPrice = orderPrice;
	}
	public Timestamp getOrderDate() {
		return OrderDate;
	}
	public void setOrderDate(Timestamp orderDate) {
		OrderDate = orderDate;
	}
	public String getReceiver() {
		return Receiver;
	}
	public void setReceiver(String receiver) {
		Receiver = receiver;
	}
	public String getPostCode() {
		return PostCode;
	}
	public void setPostCode(String postCode) {
		PostCode = postCode;
	}
	public String getDirection() {
		return Direction;
	}
	public void setDirection(String direction) {
		Direction = direction;
	}
	public Timestamp getCancelDate() {
		return CancelDate;
	}
	public void setCancelDate(Timestamp cancelDate) {
		CancelDate = cancelDate;
	}
	public Timestamp getCheckDate() {
		return CheckDate;
	}
	public void setCheckDate(Timestamp checkDate) {
		CheckDate = checkDate;
	}
	public Timestamp getPayDate() {
		return PayDate;
	}
	public void setPayDate(Timestamp payDate) {
		PayDate = payDate;
	}
	public Timestamp getDeliverDate() {
		return DeliverDate;
	}
	public void setDeliverDate(Timestamp deliverDate) {
		DeliverDate = deliverDate;
	}
	public Timestamp getReceiveDate() {
		return ReceiveDate;
	}
	public void setReceiveDate(Timestamp receiveDate) {
		ReceiveDate = receiveDate;
	}
	public String getRemark() {
		return Remark;
	}
	public void setRemark(String remark) {
		Remark = remark;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	

}
