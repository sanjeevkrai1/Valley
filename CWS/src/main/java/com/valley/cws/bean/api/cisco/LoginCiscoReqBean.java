package com.valley.cws.bean.api.cisco;

public class LoginCiscoReqBean {
	private String macAddress;
	private String mobileNumber;
	private String apMac;
	private String apName;
	private String apTags;
	private String loginUrl;
	private String continueUrl;

	public LoginCiscoReqBean() {
		super();
	}

	public LoginCiscoReqBean(String macAddress, String mobileNumber, String apMac, String apName, String apTags,
			String loginUrl, String continueUrl) {
		super();
		this.macAddress = macAddress;
		this.mobileNumber = mobileNumber;
		this.apMac = apMac;
		this.apName = apName;
		this.apTags = apTags;
		this.loginUrl = loginUrl;
		this.continueUrl = continueUrl;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
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

	@Override
	public String toString() {
		return "[ macAddress : " + macAddress + " ] , [ mobileNumber : " + mobileNumber + " ] , [ apMac : " + apMac
				+ " ] , [ apName : " + apName + " ] , [ apTags : " + apTags + " ] , [ loginUrl : " + loginUrl
				+ " ] , [ continueUrl : " + continueUrl + " ] ";
	}

}
