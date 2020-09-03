package com.valley.cws.bean;


public class ShowAdRequestBean {

	private String macAddress;
	private int adId;
	private int freeMinutes;
	private int policyId;
	private Integer likeCount;
	private Integer disLikeCount;
	private Integer rateCount;
	private Integer totalRatePoint;
	private Integer staticFreeMinute;
	private Integer videoFreeMinute;
	private Integer audioFreeMinute;

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getDisLikeCount() {
		return disLikeCount;
	}

	public void setDisLikeCount(Integer disLikeCount) {
		this.disLikeCount = disLikeCount;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public int getAdId() {
		return adId;
	}

	public void setAdId(int adId) {
		this.adId = adId;
	}

	public int getFreeMinutes() {
		return freeMinutes;
	}

	public void setFreeMinutes(int freeMinutes) {
		this.freeMinutes = freeMinutes;
	}

	public int getPolicyId() {
		return policyId;
	}

	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}

	public Integer getRateCount() {
		return rateCount;
	}

	public void setRateCount(Integer rateCount) {
		this.rateCount = rateCount;
	}

	public Integer getTotalRatePoint() {
		return totalRatePoint;
	}

	public void setTotalRatePoint(Integer totalRatePoint) {
		this.totalRatePoint = totalRatePoint;
	}

	public Integer getStaticFreeMinute() {
		return staticFreeMinute;
	}

	public void setStaticFreeMinute(Integer staticFreeMinute) {
		this.staticFreeMinute = staticFreeMinute;
	}

	public Integer getVideoFreeMinute() {
		return videoFreeMinute;
	}

	public void setVideoFreeMinute(Integer videoFreeMinute) {
		this.videoFreeMinute = videoFreeMinute;
	}

	public Integer getAudioFreeMinute() {
		return audioFreeMinute;
	}

	public void setAudioFreeMinute(Integer audioFreeMinute) {
		this.audioFreeMinute = audioFreeMinute;
	}

}
