package com.valley.cws.bean.api.cisco;

public class RateCiscoResBean {

	private String status;
	private String reason;
	private String macAddress;

	public RateCiscoResBean() {
		super();
	}

	public RateCiscoResBean(String status, String reason, String macAddress) {
		super();
		this.status = status;
		this.reason = reason;
		this.macAddress = macAddress;
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

	@Override
	public String toString() {
		return "[ status : " + status + " ] , [ reason : " + reason + " ] , [ macAddress : " + macAddress + " ] ";
	}

}
