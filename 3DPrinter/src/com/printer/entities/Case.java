package com.printer.entities;

import java.util.Date;

public class Case {
	private int CaseID;
	private String CaseTitle;
	private String CaseContent;
	private String CaseImg;
	private String Publisher;
	private Date PublishTime;
	private String Category;
	private String State;
	private String Auditor;
	
	public String getAuditor() {
		return Auditor;
	}
	public void setAuditor(String auditor) {
		Auditor = auditor;
	}
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
	}
	public int getCaseID() {
		return CaseID;
	}
	public void setCaseID(int caseID) {
		CaseID = caseID;
	}
	public String getCaseTitle() {
		return CaseTitle;
	}
	public void setCaseTitle(String caseTitle) {
		CaseTitle = caseTitle;
	}
	public String getCaseContent() {
		return CaseContent;
	}
	public void setCaseContent(String caseContent) {
		CaseContent = caseContent;
	}
	public String getCaseImg() {
		return CaseImg;
	}
	public void setCaseImg(String caseImg) {
		CaseImg = caseImg;
	}
	public String getPublisher() {
		return Publisher;
	}
	public void setPublisher(String publisher) {
		Publisher = publisher;
	}
	public Date getPublishTime() {
		return PublishTime;
	}
	public void setPublishTime(Date publishTime) {
		PublishTime = publishTime;
	}
	public String getCategory() {
		return Category;
	}
	public void setCategory(String category) {
		Category = category;
	}

	
}
