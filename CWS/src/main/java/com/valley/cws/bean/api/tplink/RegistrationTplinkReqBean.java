package com.valley.cws.bean.api.tplink;

public class RegistrationTplinkReqBean {
	private String macAddress;
	private String userName;
	private String emailId;
	private String mobileNumber;
	private String gender;
	private String switchIp;
	private String ssid;
	private String dob;

	public RegistrationTplinkReqBean() {
		super();
	}

	public RegistrationTplinkReqBean(String macAddress, String userName, String emailId, String mobileNumber,
			String gender, String switchIp, String ssid, String dob) {
		super();
		this.macAddress = macAddress;
		this.userName = userName;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.gender = gender;
		this.switchIp = switchIp;
		this.ssid = ssid;
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

	public String getSwitchIp() {
		return switchIp;
	}

	public void setSwitchIp(String switchIp) {
		this.switchIp = switchIp;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
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
				+ " ] , [ mobileNumber : " + mobileNumber + " ] , gender=" + gender + " ] , [ ssid : " + ssid
				+ " ] , [ switchIp : " + switchIp + " ] , [ dob : " + dob + "]";
	}

}
