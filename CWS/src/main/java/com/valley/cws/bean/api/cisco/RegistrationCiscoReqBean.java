package com.valley.cws.bean.api.cisco;

public class RegistrationCiscoReqBean {
	private String macAddress;
	private String userName;
	private String emailId;
	private String mobileNumber;
	private String gender;
	private String apMac;
	private String apName;
	private String apTags;
	private String loginUrl;
	private String continueUrl;
	private String dob;

	public RegistrationCiscoReqBean() {
		super();
	}

	public RegistrationCiscoReqBean(String macAddress, String userName, String emailId, String mobileNumber,
			String gender, String apMac, String apName, String apTags, String loginUrl, String continueUrl, String dob) {
		super();
		this.macAddress = macAddress;
		this.userName = userName;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.gender = gender;
		this.apMac = apMac;
		this.apName = apName;
		this.apTags = apTags;
		this.loginUrl = loginUrl;
		this.continueUrl = continueUrl;
		this.dob = dob;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getApMac() {
		return apMac;
	}

	public void setApMac(String apMac) {
		this.apMac = apMac;
	}

	public String getApName() {
		return apName;
	}

	public void setApName(String apName) {
		this.apName = apName;
	}

	public String getApTags() {
		return apTags;
	}

	public void setApTags(String apTags) {
		this.apTags = apTags;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getContinueUrl() {
		return continueUrl;
	}

	public void setContinueUrl(String continueUrl) {
		this.continueUrl = continueUrl;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	@Override
	public String toString() {
		return "[ macAddress : " + macAddress + " ] , [ userName : " + userName + " ] , [ emailId : " + emailId
				+ " ] , [ mobileNumber : " + mobileNumber + " ] , [ gender : " + gender + " ] , [ apMac : " + apMac
				+ " ] , [ apName : " + apName + " ] , [ apTags : " + apTags + " ] , [ loginUrl : " + loginUrl
				+ " ] , [ continueUrl : " + continueUrl + " ] , [ dob : " + dob + " ] ";
	}

}
