package com.valley.cws.bean.api.aruba;

public class AdBean {
	private String adUrl;

	public AdBean() {
		super();
	}

	public AdBean(String adUrl) {
		super();
		this.adUrl = adUrl;
	}

	public String getAdUrl() {
		return adUrl;
	}

	public void setAdUrl(String adUrl) {
		this.adUrl = adUrl;
	}

	@Override
	public String toString() {
		return "[ adUrl : " + adUrl + " ] ";
	}
}
