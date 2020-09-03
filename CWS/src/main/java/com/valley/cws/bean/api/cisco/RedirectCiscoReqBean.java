package com.valley.cws.bean.api.cisco;

public class RedirectCiscoReqBean {
	private String macAddress;

	public RedirectCiscoReqBean() {
		super();
	}

	public RedirectCiscoReqBean(String macAddress) {
		super();
		this.macAddress = macAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

}
