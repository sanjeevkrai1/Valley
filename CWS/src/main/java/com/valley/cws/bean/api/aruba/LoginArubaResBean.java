package com.valley.cws.bean.api.aruba;

public class LoginArubaResBean {
	private String status;
	private String reason;
	private String url;
	private String login;
	private String cmd;
	private String actionUrl;
	private String macAddress;
	private String mobileNumber;

	public LoginArubaResBean() {
		super();
	}

	public LoginArubaResBean(String status, String reason, String url, String login, String cmd,
			String actionUrl, String macAddress, String mobileNumber) {
		super();
		this.status = status;
		this.reason = reason;
		this.url = url;
		this.login = login;
		this.cmd = cmd;
		this.actionUrl = actionUrl;
		this.macAddress = macAddress;
		this.mobileNumber = mobileNumber;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
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

	@Override
	public String toString() {
		return "[ status : " + status + " ] , [ reason : " + reason + " ] , [ url : " + url + " ] , [ login : " + login
				+ " ] , [ cmd : " + cmd + " ] , [ actionUrl : " + actionUrl + " ] , [ macAddress : " + macAddress
				+ " ] , [ mobileNumber : " + mobileNumber + " ] ";
	}

}
