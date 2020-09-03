package com.valley.cws.bean.api.bng;

public class ValidateOtpBngResBean {
	private String status;
	private String reason;
	private String macAddress;
	private String userName;
	private String emailId;
	private String mobileNumber;
	private String gender;
	private String ssid;

	public ValidateOtpBngResBean() {
		super();
	}

	public ValidateOtpBngResBean(String status, String reason, String macAddress, String userName, String emailId,
			String mobileNumber, String gender, String ssid) {
		super();
		this.status = status;
		this.reason = reason;
		this.macAddress = macAddress;
		this.userName = userName;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.gender = gender;
		this.ssid = ssid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	@Override
	public String toString() {
		return "[ status : " + status + " ] , [ reason : " + reason + " ] , [ macAddress : " + macAddress
				+ " ] , [ userName : " + userName + " ] , [ emailId : " + emailId + " ] , [ mobileNumber : "
				+ mobileNumber + " ] , [ gender : " + gender + " ] , [ ssid : " + ssid + " ] ";
	}

}
