package com.valley.cws.bean;

public class ValidateResBean {

	private String userName;
	private String emailId;
	private String mobileNo;
	private String gender;
	private String SSID;
	private String baseGrantUrl;
	private String continueUrl;
	private String duration;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSSID() {
		return SSID;
	}

	public void setSSID(String sSID) {
		SSID = sSID;
	}
	
	public String getBaseGrantUrl() {
		return baseGrantUrl;
	}

	public void setBaseGrantUrl(String baseGrantUrl) {
		this.baseGrantUrl = baseGrantUrl;
	}

	public String getContinueUrl() {
		return continueUrl;
	}

	public void setContinueUrl(String continueUrl) {
		this.continueUrl = continueUrl;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

}
