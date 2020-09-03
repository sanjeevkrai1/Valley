package com.valley.cws.bean;

public class RateReqBean {
	
	private int adId;
	private String macAddress;
	private Boolean isLike;
	private Boolean isDisLike;
	private Boolean isRating;
	private Integer ratingPoint;
	private Boolean isActivity;
	private Boolean isSchedule;
	private Boolean isCampaign;
	private Boolean isPeriodic;
	private Integer duration;
	
	
	public int getAdId() {
		return adId;
	}
	public void setAdId(int adId) {
		this.adId = adId;
	}
	
	public String getMacAddress() {
		return macAddress;
	}
	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}
	public Boolean getIsLike() {
		return isLike;
	}
	public void setIsLike(Boolean isLike) {
		this.isLike = isLike;
	}
	public Boolean getIsDisLike() {
		return isDisLike;
	}
	public void setIsDisLike(Boolean isDisLike) {
		this.isDisLike = isDisLike;
	}
	public Boolean getIsRating() {
		return isRating;
	}
	public void setIsRating(Boolean isRating) {
		this.isRating = isRating;
	}
	public Integer getRatingPoint() {
		return ratingPoint;
	}
	public void setRatingPoint(Integer ratingPoint) {
		this.ratingPoint = ratingPoint;
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

}
