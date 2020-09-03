package com.valley.cws.bean.api.bng;

public class LoginBngResBean {
	private String status;
	private String reason;
	private String macAddress;
	private String mobileNumber;

	public LoginBngResBean() {
		super();
	}

	public LoginBngResBean(String status, String reason, String macAddress, String mobileNumber) {
		super();
		this.status = status;
		this.reason = reason;
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
		return "[ status : " + status + " ] , [ reason : " + reason + " ] , [ macAddress : " + macAddress
				+ " ] , [ mobileNumber : " + mobileNumber + " ] ";
	}

}
