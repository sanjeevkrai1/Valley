package com.valley.cws.bean.api.tplink;

public class RegistrationTplinkResBean {
	private String status;
	private String reason;
	private String actionUrl;
	private String macAddress;
	private String userName;

	public RegistrationTplinkResBean() {
		super();
	}

	public RegistrationTplinkResBean(String status, String reason, String actionUrl, String macAddress, String userName) {
		super();
		this.status = status;
		this.reason = reason;
		this.actionUrl = actionUrl;
		this.macAddress = macAddress;
		this.userName = userName;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "[ status : " + status + " ] , [ reason : " + reason + " ] , [ actionUrl : " + actionUrl
				+ " ] , [ macAddress : " + macAddress + " ] , [ userName : " + userName + " ] ";
	}

}
