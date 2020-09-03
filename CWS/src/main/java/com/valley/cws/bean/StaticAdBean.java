package com.valley.cws.bean;

public class StaticAdBean {
	private String staticAdUrl;
	private Integer staticAdId;
	private Integer staticRatingPoints;
	private Integer staticTotalRatingCount;
	private Integer staticLikePoints;
	private Integer staticDislikePoints;
	private Double staticRateAvg;

	public StaticAdBean() {
		super();
	}

	public StaticAdBean(String staticAdUrl, Integer staticAdId, Integer staticRatingPoints,
			Integer staticTotalRatingCount, Integer staticLikePoints, Integer staticDislikePoints, Double staticRateAvg) {
		super();
		this.staticAdUrl = staticAdUrl;
		this.staticAdId = staticAdId;
		this.staticRatingPoints = staticRatingPoints;
		this.staticTotalRatingCount = staticTotalRatingCount;
		this.staticLikePoints = staticLikePoints;
		this.staticDislikePoints = staticDislikePoints;
		this.staticRateAvg = staticRateAvg;
	}

	public String getStaticAdUrl() {
		return staticAdUrl;
	}

	public void setStaticAdUrl(String staticAdUrl) {
		this.staticAdUrl = staticAdUrl;
	}

	public Integer getStaticAdId() {
		return staticAdId;
	}

	public void setStaticAdId(Integer staticAdId) {
		this.staticAdId = staticAdId;
	}

	public Integer getStaticRatingPoints() {
		return staticRatingPoints;
	}

	public void setStaticRatingPoints(Integer staticRatingPoints) {
		this.staticRatingPoints = staticRatingPoints;
	}

	public Integer getStaticTotalRatingCount() {
		return staticTotalRatingCount;
	}

	public void setStaticTotalRatingCount(Integer staticTotalRatingCount) {
		this.staticTotalRatingCount = staticTotalRatingCount;
	}

	public Integer getStaticLikePoints() {
		return staticLikePoints;
	}

	public void setStaticLikePoints(Integer staticLikePoints) {
		this.staticLikePoints = staticLikePoints;
	}

	public Integer getStaticDislikePoints() {
		return staticDislikePoints;
	}

	public void setStaticDislikePoints(Integer staticDislikePoints) {
		this.staticDislikePoints = staticDislikePoints;
	}

	public Double getStaticRateAvg() {
		return staticRateAvg;
	}

	public void setStaticRateAvg(Double staticRateAvg) {
		this.staticRateAvg = staticRateAvg;
	}

	@Override
	public String toString() {
		return "[ staticAdUrl : " + staticAdUrl + " ] , [ staticAdId : " + staticAdId + " ] , [ staticRatingPoints : "
				+ staticRatingPoints + " ] , [ staticTotalRatingCount : " + staticTotalRatingCount
				+ " ] , [ staticLikePoints : " + staticLikePoints + " ] , [ staticDislikePoints : "
				+ staticDislikePoints + " ] , [ staticRateAvg : " + staticRateAvg + " ] ";
	}
}
