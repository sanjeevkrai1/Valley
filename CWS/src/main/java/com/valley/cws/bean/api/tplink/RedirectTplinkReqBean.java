package com.valley.cws.bean.api.tplink;

public class RedirectTplinkReqBean {
	private String macAddress;

	public RedirectTplinkReqBean() {
		super();
	}

	public RedirectTplinkReqBean(String macAddress) {
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
