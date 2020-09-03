package com.valley.cws.bean.api.cisco;

public class RegistrationCiscoResBean {
	private String status;
	private String reason;
	private String successUrl;
	private String actionUrl;
	private String continueUrl;
	private String macAddress;
	private String userName;

	public RegistrationCiscoResBean() {
		super();
	}

	public RegistrationCiscoResBean(String status, String reason, String successUrl, String actionUrl,
			String continueUrl, String macAddress, String userName) {
		super();
		this.status = status;
		this.reason = reason;
		this.successUrl = successUrl;
		this.actionUrl = actionUrl;
		this.continueUrl = continueUrl;
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

	public String getSuccessUrl() {
		return successUrl;
	}

	public void setSuccessUrl(String successUrl) {
		this.successUrl = successUrl;
	}

	public String getActionUrl() {
		return actionUrl;
	}

	public void setActionUrl(String actionUrl) {
		this.actionUrl = actionUrl;
	}

	public String getContinueUrl() {
		return continueUrl;
	}

	public void setContinueUrl(String continueUrl) {
		this.continueUrl = continueUrl;
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
		return "[ status : " + status + " ] , [ reason : " + reason + " ] , [ successUrl : " + successUrl
				+ " ] , [ actionUrl : " + actionUrl + " ] , [ continueUrl : " + continueUrl + " ] , [ macAddress : "
				+ macAddress + " ] , [ userName : " + userName + " ] ";
	}

}
