package com.valley.cws.bean.api.bng;

import java.util.List;

public class RateBngReqBean {

	private List<RateBngAdBean> adList;
	private String macAddress;
	private Boolean isActivity;
	private Boolean isSchedule;
	private Boolean isCampaign;
	private Boolean isPeriodic;
	private Integer duration;
	private String mobileNumber;

	public RateBngReqBean() {
		super();
	}

	public RateBngReqBean(List<RateBngAdBean> adList, String macAddress, Boolean isActivity, Boolean isSchedule,
			Boolean isCampaign, Boolean isPeriodic, Integer duration, String mobileNumber) {
		super();
		this.adList = adList;
		this.macAddress = macAddress;
		this.isActivity = isActivity;
		this.isSchedule = isSchedule;
		this.isCampaign = isCampaign;
		this.isPeriodic = isPeriodic;
		this.duration = duration;
		this.mobileNumber = mobileNumber;
	}

	public List<RateBngAdBean> getAdList() {
		return adList;
	}

	public void setAdList(List<RateBngAdBean> adList) {
		this.adList = adList;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public Boolean getIsActivity() {
		return isActivity;
	}

	public void setIsActivity(Boolean isActivity) {
		this.isActivity = isActivity;
	}

	public Boolean getIsSchedule() {
		return isSchedule;
	}

	public void setIsSchedule(Boolean isSchedule) {
		this.isSchedule = isSchedule;
	}

	public Boolean getIsCampaign() {
		return isCampaign;
	}

	public void setIsCampaign(Boolean isCampaign) {
		this.isCampaign = isCampaign;
	}

	public Boolean getIsPeriodic() {
		return isPeriodic;
	}

	public void setIsPeriodic(Boolean isPeriodic) {
		this.isPeriodic = isPeriodic;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Override
	public String toString() {
		return "[ adBeans : " + adList + " ] , [ macAddress : " + macAddress + " ] , [ isActivity : " + isActivity
				+ " ] , [ isSchedule : " + isSchedule + " ] , [ isCampaign : " + isCampaign + " ] , [ isPeriodic : "
				+ isPeriodic + " ] , [ duration : " + duration + " ] , [ mobileNumber : " + mobileNumber + " ] ";
	}

}
