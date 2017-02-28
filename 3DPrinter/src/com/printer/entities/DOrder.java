package com.printer.entities;

import java.sql.Timestamp;

public class DOrder {
	
	private String DOrderID;
	private String Demand;
	private String Designer;
	private String UserName;
	private String State;
	private String DemandFile;
	private String DesignFile;
	private String Process;
	private Timestamp DOrderDate;
	private Timestamp QuoteDate;
	private Timestamp PayDate;
	private double UserPrice;
	private double ProPrice;
	private Timestamp CancelDate;
	private Timestamp CheckDate;
	private Timestamp DeliverDate;
	private String Phone;
	
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public Timestamp getCheckDate() {
		return CheckDate;
	}
	public void setCheckDate(Timestamp checkDate) {
		CheckDate = checkDate;
	}
	public Timestamp getDeliverDate() {
		return DeliverDate;
	}
	public void setDeliverDate(Timestamp deliverDate) {
		DeliverDate = deliverDate;
	}
	public String getDOrderID() {
		return DOrderID;
	}
	public void setDOrderID(String dOrderID) {
		DOrderID = dOrderID;
	}
	public String getDemand() {
		return Demand;
	}
	public void setDemand(String demand) {
		Demand = demand;
	}
	public String getDesigner() {
		return Designer;
	}
	public void setDesigner(String designer) {
		Designer = designer;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getDemandFile() {
		return DemandFile;
	}
	public void setDemandFile(String demandFile) {
		DemandFile = demandFile;
	}
	public String getDesignFile() {
		return DesignFile;
	}
	public void setDesignFile(String designFile) {
		DesignFile = designFile;
	}
	public String getProcess() {
		return Process;
	}
	public void setProcess(String process) {
		Process = process;
	}
	public Timestamp getDOrderDate() {
		return DOrderDate;
	}
	public void setDOrderDate(Timestamp dOrderDate) {
		DOrderDate = dOrderDate;
	}
	public Timestamp getQuoteDate() {
		return QuoteDate;
	}
	public void setQuoteDate(Timestamp quoteDate) {
		QuoteDate = quoteDate;
	}
	public Timestamp getPayDate() {
		return PayDate;
	}
	public void setPayDate(Timestamp payDate) {
		PayDate = payDate;
	}
	
	public double getUserPrice() {
		return UserPrice;
	}
	public void setUserPrice(double userPrice) {
		UserPrice = userPrice;
	}
	public double getProPrice() {
		return ProPrice;
	}
	public void setProPrice(double proPrice) {
		ProPrice = proPrice;
	}
	public Timestamp getCancelDate() {
		return CancelDate;
	}
	public void setCancelDate(Timestamp cancelDate) {
		CancelDate = cancelDate;
	}
	
	
	

}
