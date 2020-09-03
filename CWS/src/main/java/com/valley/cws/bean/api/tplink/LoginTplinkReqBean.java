package com.valley.cws.bean.api.tplink;

public class LoginTplinkReqBean {
	private String macAddress;
	private String mobileNumber;
	private String switchIp;
	private String ssid;

	public LoginTplinkReqBean() {
		super();
	}

	public LoginTplinkReqBean(String macAddress, String mobileNumber, String switchIp, String ssid) {
		super();
		this.macAddress = macAddress;
		this.mobileNumber = mobileNumber;
		this.switchIp = switchIp;
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

	@Override
	public String toString() {
		return "[ macAddress : " + macAddress + " ] , [ mobileNumber : " + mobileNumber + " ] , [ switchIp : "
				+ switchIp + " ] , [ ssid : " + ssid + " ] ";
	}

}
