package com.valley.cws.bean.api.bng;

public class RegistrationBngReqBean {
	private String macAddress;
	private String userName;
	private String emailId;
	private String mobileNumber;
	private String gender;
	private String ssid;
	private String ip;
	private String dob;

	public RegistrationBngReqBean() {
		super();
	}

	public RegistrationBngReqBean(String macAddress, String userName, String emailId, String mobileNumber,
			String gender, String ssid, String ip, String dob) {
		super();
		this.macAddress = macAddress;
		this.userName = userName;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.gender = gender;
		this.ssid = ssid;
		this.ip = ip;
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

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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
				+ " ] , [ mobileNumber : " + mobileNumber + " ] , [ gender : " + gender + " ] , [ ip : " + ip
				+ " ] , [ ssid : " + ssid + " ] , [ dob : " + dob + " ] ";

	}

}
