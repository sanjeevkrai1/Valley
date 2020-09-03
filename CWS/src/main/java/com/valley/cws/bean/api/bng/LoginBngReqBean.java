package com.valley.cws.bean.api.bng;

public class LoginBngReqBean {
	private String macAddress;
	private String mobileNumber;
	private String ip;
	private String ssid;

	public LoginBngReqBean() {
		super();
	}

	public LoginBngReqBean(String macAddress, String mobileNumber, String ip, String ssid) {
		super();
		this.macAddress = macAddress;
		this.mobileNumber = mobileNumber;
		this.ip = ip;
		this.ssid = ssid;
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

	@Override
	public String toString() {
		return "[ macAddress : " + macAddress + " ] , [ mobileNumber : " + mobileNumber + " ] , [ ssid : " + ssid
				+ " ] , [ ip : " + ip + " ] ";
	}

}
