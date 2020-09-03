package com.valley.cws.bean.api.bng;

import java.util.List;


public class RedirectBngResBean {

	private String status;
	private String reason;
	private String macAddress;
	private String action;
	private Boolean isAd;
	private List<AdBean> adUrls;
	private Integer screenType;

	public RedirectBngResBean() {
		super();
	}

	public RedirectBngResBean(String status, String reason, String macAddress, String action, Boolean isAd, List<AdBean> adUrls,
			Integer screenType) {
		super();
		this.status = status;
		this.reason = reason;
		this.macAddress = macAddress;
		this.action = action;
		this.isAd = isAd;
		this.adUrls = adUrls;
		this.screenType = screenType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Boolean getIsAd() {
		return isAd;
	}

	public void setIsAd(Boolean isAd) {
		this.isAd = isAd;
	}

	public List<AdBean> getAdUrls() {
		return adUrls;
	}

	public void setAdUrls(List<AdBean> adUrls) {
		this.adUrls = adUrls;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getScreenType() {
		return screenType;
	}

	public void setScreenType(Integer screenType) {
		this.screenType = screenType;
	}

	@Override
	public String toString() {
		return "[ status : " + status + " ] , [ reason : " + reason + " ] , [ macAddress : " + macAddress + " ] , [ action : " + action
				+ " ] , [ isAd : " + isAd + " ] , [ adUrl : " + adUrls + " ] , [ screenType : " + screenType + " ] ";
	}

}
