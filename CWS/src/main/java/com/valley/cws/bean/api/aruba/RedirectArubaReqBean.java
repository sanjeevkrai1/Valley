package com.valley.cws.bean.api.aruba;

public class RedirectArubaReqBean {
	private String macAddress;

	public RedirectArubaReqBean() {
		super();
	}

	public RedirectArubaReqBean(String macAddress) {
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
