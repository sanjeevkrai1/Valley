package com.valley.cws.bean.api.aruba;

public class RegistrationArubaReqBean {
	private String macAddress;
	private String userName;
	private String emailId;
	private String mobileNumber;
	private String gender;
	private String clientIp;
	private String ssid;
	private String cmd;
	private String apName;
	private String vcName;
	private String switchIp;
	private String url;
	private String dob;

	public RegistrationArubaReqBean() {
		super();
	}

	public RegistrationArubaReqBean(String macAddress, String userName, String emailId, String mobileNumber,
			String gender, String clientIp, String ssid, String cmd, String apName, String vcName, String switchIp,
			String url, String dob) {
		super();
		this.macAddress = macAddress;
		this.userName = userName;
		this.emailId = emailId;
		this.mobileNumber = mobileNumber;
		this.gender = gender;
		this.clientIp = clientIp;
		this.ssid = ssid;
		this.cmd = cmd;
		this.apName = apName;
		this.vcName = vcName;
		this.switchIp = switchIp;
		this.url = url;
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

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getApName() {
		return apName;
	}

	public void setApName(String apName) {
		this.apName = apName;
	}

	public String getVcName() {
		return vcName;
	}

	public void setVcName(String vcName) {
		this.vcName = vcName;
	}

	public String getSwitchIp() {
		return switchIp;
	}

	public void setSwitchIp(String switchIp) {
		this.switchIp = switchIp;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
				+ " ] , [ mobileNumber : " + mobileNumber + " ] , [ gender : " + gender + " ] , [ clientIp : "
				+ clientIp + " ] , [ ssid : " + ssid + " ] , [ cmd : " + cmd + " ] , [ apName : " + apName
				+ " ] , [ vcName : " + vcName + " ] , [ switchIp : " + switchIp + " ] , [ url : " + url
				+ " ] , [ dob : " + dob + " ] ";

	}

}
