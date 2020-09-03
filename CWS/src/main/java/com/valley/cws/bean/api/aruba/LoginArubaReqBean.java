package com.valley.cws.bean.api.aruba;

public class LoginArubaReqBean {
	private String macAddress;
	private String mobileNumber;
	private String clientIp;
	private String ssid;
	private String cmd;
	private String apName;
	private String vcName;
	private String switchIp;
	private String url;

	public LoginArubaReqBean() {
		super();
	}

	public LoginArubaReqBean(String macAddress, String userName, String emailId, String mobileNumber, String gender,
			String clientIp, String ssid, String cmd, String apName, String vcName, String switchIp, String url) {
		super();
		this.macAddress = macAddress;
		this.mobileNumber = mobileNumber;
		this.clientIp = clientIp;
		this.ssid = ssid;
		this.cmd = cmd;
		this.apName = apName;
		this.vcName = vcName;
		this.switchIp = switchIp;
		this.url = url;
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

	@Override
	public String toString() {
		return "[ macAddress : " + macAddress + " ] , [ mobileNumber : " + mobileNumber + " ] , [ clientIp : "
				+ clientIp + " ] , [ ssid : " + ssid + " ] , [ cmd : " + cmd + " ] , [ apName : " + apName
				+ " ] , [ vcName : " + vcName + " ] , [ switchIp : " + switchIp + " ] , [ url : " + url + " ] ";
	}

}
