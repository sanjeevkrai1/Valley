package com.valley.cws.bean.api.cisco;

public class RateCiscoAdBean {

	private int adId;
	private Boolean isLike;
	private Boolean isDisLike;
	private Boolean isRating;
	private Integer ratingPoint;

	public RateCiscoAdBean() {
		super();
	}

	public RateCiscoAdBean(int adId, Boolean isLike, Boolean isDisLike, Boolean isRating, Integer ratingPoint) {
		super();
		this.adId = adId;
		this.isLike = isLike;
		this.isDisLike = isDisLike;
		this.isRating = isRating;
		this.ratingPoint = ratingPoint;
	}

	public int getAdId() {
		return adId;
	}

	public void setAdId(int adId) {
		this.adId = adId;
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

	@Override
	public String toString() {
		return "[ adId : " + adId + " ] , [ isLike : " + isLike + " ] , [ isDisLike : " + isDisLike
				+ " ] , [ isRating : " + isRating + " ] , [ ratingPoint : " + ratingPoint + " ] ";
	}

}
