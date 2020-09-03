package com.valley.cws.bean;

public class PolicyReqBean {
	private String macAddress;
	private String accessPoint;
	private String resolution;

	public PolicyReqBean() {
		super();
	}

	public PolicyReqBean(String macAddress, String accessPoint, String resolution) {
		super();
		this.macAddress = macAddress;
		this.accessPoint = accessPoint;
		this.resolution = resolution;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getAccessPoint() {
		return accessPoint;
	}

	public void setAccessPoint(String accessPoint) {
		this.accessPoint = accessPoint;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

}