package com.printer.entities;

import java.util.Date;

public class FeedBack {
	
	private int fbID;
	private String UserName;
	private Date SubmitDate; 
	private String FeedBack;
	private Date ReplyDate;
	private String Reply;
	private int AdID;
	private String State;
	private String Replyer;
	private String Uportrait;
	private String Aportrait;
	
	public String getUportrait() {
		return Uportrait;
	}
	public void setUportrait(String uportrait) {
		Uportrait = uportrait;
	}
	public String getAportrait() {
		return Aportrait;
	}
	public void setAportrait(String aportrait) {
		Aportrait = aportrait;
	}
	public int getFbID() {
		return fbID;
	}
	public void setFbID(int fbID) {
		this.fbID = fbID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public Date getSubmitDate() {
		return SubmitDate;
	}
	public void setSubmitDate(Date submitDate) {
		SubmitDate = submitDate;
	}
	public String getFeedBack() {
		return FeedBack;
	}
	public void setFeedBack(String feedBack) {
		FeedBack = feedBack;
	}
	public Date getReplyDate() {
		return ReplyDate;
	}
	public void setReplyDate(Date replyDate) {
		ReplyDate = replyDate;
	}
	public String getReply() {
		return Reply;
	}
	public void setReply(String reply) {
		Reply = reply;
	}
	public int getAdID() {
		return AdID;
	}
	public void setAdID(int adID) {
		AdID = adID;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public String getReplyer() {
		return Replyer;
	}
	public void setReplyer(String replyer) {
		Replyer = replyer;
	}
	
	

}
