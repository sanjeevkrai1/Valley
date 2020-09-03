package com.valley.cws.bean.api.bng;

public class RedirectBngReqBean {
	private String macAddress;

	public RedirectBngReqBean() {
		super();
	}

	public RedirectBngReqBean(String macAddress) {
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
