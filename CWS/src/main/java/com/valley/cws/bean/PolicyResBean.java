package com.valley.cws.bean;

import java.util.List;

public class PolicyResBean {
	private String macAddress;
	private String status;
	private String reason;
	private Boolean isStatic;
	private List<StaticAdBean> staticAds;
	private Integer staticScreenType;
	private Integer staticFreeMinute;
	private Integer staticTriggerTime;
	private Boolean isStaticRating;
	private Boolean isStaticLike;
	private Integer staticShowTime;
	private Integer staticAdPoint;
	private Boolean isVideo;
	private String videoAdUrl;
	private Integer videoFreeMinute;
	private Integer videoTriggerTime;
	private Boolean isVideoRating;
	private Boolean isVideoLike;
	private Integer videoRatingPoints;
	private Integer videoTotalRatingCount;
	private Double videoRateAvg;
	private Integer videoLikePoints;
	private Integer videoDislikePoints;
	private Integer videoAdPoint;
	private Integer videoAdId;
	private Boolean isAudio;
	private String audioAdUrl;
	private Integer audioFreeMinute;
	private Integer audioTriggerTime;
	private Boolean isAudioRating;
	private Boolean isAudioLike;
	private Integer audioRatingPoints;
	private Integer audioTotalRatingCount;
	private Double audioRateAvg;
	private Integer audioLikePoints;
	private Integer audioDislikePoints;
	private Integer audioAdPoint;
	private Integer audioAdId;
	private Boolean isPgUrl;
	private String pgUrl;
	private Boolean isRedirection;
	private String redirectionUrl;

	public PolicyResBean() {
		super();
	}

	public PolicyResBean(String macAddress, String status, String reason, Boolean isStatic,
			List<StaticAdBean> staticAds, Integer staticScreenType, Integer staticFreeMinute,
			Integer staticTriggerTime, Boolean isStaticRating, Boolean isStaticLike, Integer staticShowTime,
			Integer staticAdPoint, Boolean isVideo, String videoAdUrl, Integer videoFreeMinute,
			Integer videoTriggerTime, Boolean isVideoRating, Boolean isVideoLike, Integer videoRatingPoints,
			Integer videoTotalRatingCount, Double videoRateAvg, Integer videoLikePoints, Integer videoDislikePoints,
			Integer videoAdPoint, Integer videoAdId, Boolean isAudio, String audioAdUrl, Integer audioFreeMinute,
			Integer audioTriggerTime, Boolean isAudioRating, Boolean isAudioLike, Integer audioRatingPoints,
			Integer audioTotalRatingCount, Double audioRateAvg, Integer audioLikePoints, Integer audioDislikePoints,
			Integer audioAdPoint, Integer audioAdId, Boolean isPgUrl, String pgUrl, Boolean isRedirection,
			String redirectionUrl) {
		super();
		this.macAddress = macAddress;
		this.status = status;
		this.reason = reason;
		this.isStatic = isStatic;
		this.staticAds = staticAds;
		this.staticScreenType = staticScreenType;
		this.staticFreeMinute = staticFreeMinute;
		this.staticTriggerTime = staticTriggerTime;
		this.isStaticRating = isStaticRating;
		this.isStaticLike = isStaticLike;
		this.staticShowTime = staticShowTime;
		this.staticAdPoint = staticAdPoint;
		this.isVideo = isVideo;
		this.videoAdUrl = videoAdUrl;
		this.videoFreeMinute = videoFreeMinute;
		this.videoTriggerTime = videoTriggerTime;
		this.isVideoRating = isVideoRating;
		this.isVideoLike = isVideoLike;
		this.videoRatingPoints = videoRatingPoints;
		this.videoTotalRatingCount = videoTotalRatingCount;
		this.videoRateAvg = videoRateAvg;
		this.videoLikePoints = videoLikePoints;
		this.videoDislikePoints = videoDislikePoints;
		this.videoAdPoint = videoAdPoint;
		this.videoAdId = videoAdId;
		this.isAudio = isAudio;
		this.audioAdUrl = audioAdUrl;
		this.audioFreeMinute = audioFreeMinute;
		this.audioTriggerTime = audioTriggerTime;
		this.isAudioRating = isAudioRating;
		this.isAudioLike = isAudioLike;
		this.audioRatingPoints = audioRatingPoints;
		this.audioTotalRatingCount = audioTotalRatingCount;
		this.audioRateAvg = audioRateAvg;
		this.audioLikePoints = audioLikePoints;
		this.audioDislikePoints = audioDislikePoints;
		this.audioAdPoint = audioAdPoint;
		this.audioAdId = audioAdId;
		this.isPgUrl = isPgUrl;
		this.pgUrl = pgUrl;
		this.isRedirection = isRedirection;
		this.redirectionUrl = redirectionUrl;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Boolean getIsStatic() {
		return isStatic;
	}

	public void setIsStatic(Boolean isStatic) {
		this.isStatic = isStatic;
	}

	public List<StaticAdBean> getStaticAds() {
		return staticAds;
	}

	public void setStaticAds(List<StaticAdBean> staticAds) {
		this.staticAds = staticAds;
	}

	public Integer getStaticScreenType() {
		return staticScreenType;
	}

	public void setStaticScreenType(Integer staticScreenType) {
		this.staticScreenType = staticScreenType;
	}

	public Integer getStaticFreeMinute() {
		return staticFreeMinute;
	}

	public void setStaticFreeMinute(Integer staticFreeMinute) {
		this.staticFreeMinute = staticFreeMinute;
	}

	public Integer getStaticTriggerTime() {
		return staticTriggerTime;
	}

	public void setStaticTriggerTime(Integer staticTriggerTime) {
		this.staticTriggerTime = staticTriggerTime;
	}

	public Boolean getIsStaticRating() {
		return isStaticRating;
	}

	public void setIsStaticRating(Boolean isStaticRating) {
		this.isStaticRating = isStaticRating;
	}

	public Boolean getIsStaticLike() {
		return isStaticLike;
	}

	public void setIsStaticLike(Boolean isStaticLike) {
		this.isStaticLike = isStaticLike;
	}

	public Integer getStaticShowTime() {
		return staticShowTime;
	}

	public void setStaticShowTime(Integer staticShowTime) {
		this.staticShowTime = staticShowTime;
	}

	public Integer getStaticAdPoint() {
		return staticAdPoint;
	}

	public void setStaticAdPoint(Integer staticAdPoint) {
		this.staticAdPoint = staticAdPoint;
	}

	public Boolean getIsVideo() {
		return isVideo;
	}

	public void setIsVideo(Boolean isVideo) {
		this.isVideo = isVideo;
	}

	public String getVideoAdUrl() {
		return videoAdUrl;
	}

	public void setVideoAdUrl(String videoAdUrl) {
		this.videoAdUrl = videoAdUrl;
	}

	public Integer getVideoFreeMinute() {
		return videoFreeMinute;
	}

	public void setVideoFreeMinute(Integer videoFreeMinute) {
		this.videoFreeMinute = videoFreeMinute;
	}

	public Integer getVideoTriggerTime() {
		return videoTriggerTime;
	}

	public void setVideoTriggerTime(Integer videoTriggerTime) {
		this.videoTriggerTime = videoTriggerTime;
	}

	public Boolean getIsVideoRating() {
		return isVideoRating;
	}

	public void setIsVideoRating(Boolean isVideoRating) {
		this.isVideoRating = isVideoRating;
	}

	public Boolean getIsVideoLike() {
		return isVideoLike;
	}

	public void setIsVideoLike(Boolean isVideoLike) {
		this.isVideoLike = isVideoLike;
	}

	public Integer getVideoRatingPoints() {
		return videoRatingPoints;
	}

	public void setVideoRatingPoints(Integer videoRatingPoints) {
		this.videoRatingPoints = videoRatingPoints;
	}

	public Integer getVideoTotalRatingCount() {
		return videoTotalRatingCount;
	}

	public void setVideoTotalRatingCount(Integer videoTotalRatingCount) {
		this.videoTotalRatingCount = videoTotalRatingCount;
	}

	public Integer getVideoLikePoints() {
		return videoLikePoints;
	}

	public void setVideoLikePoints(Integer videoLikePoints) {
		this.videoLikePoints = videoLikePoints;
	}

	public Integer getVideoDislikePoints() {
		return videoDislikePoints;
	}

	public void setVideoDislikePoints(Integer videoDislikePoints) {
		this.videoDislikePoints = videoDislikePoints;
	}

	public Integer getVideoAdPoint() {
		return videoAdPoint;
	}

	public void setVideoAdPoint(Integer videoAdPoint) {
		this.videoAdPoint = videoAdPoint;
	}

	public Integer getVideoAdId() {
		return videoAdId;
	}

	public void setVideoAdId(Integer videoAdId) {
		this.videoAdId = videoAdId;
	}

	public Boolean getIsAudio() {
		return isAudio;
	}

	public void setIsAudio(Boolean isAudio) {
		this.isAudio = isAudio;
	}

	public String getAudioAdUrl() {
		return audioAdUrl;
	}

	public void setAudioAdUrl(String audioAdUrl) {
		this.audioAdUrl = audioAdUrl;
	}

	public Integer getAudioFreeMinute() {
		return audioFreeMinute;
	}

	public void setAudioFreeMinute(Integer audioFreeMinute) {
		this.audioFreeMinute = audioFreeMinute;
	}

	public Integer getAudioTriggerTime() {
		return audioTriggerTime;
	}

	public void setAudioTriggerTime(Integer audioTriggerTime) {
		this.audioTriggerTime = audioTriggerTime;
	}

	public Boolean getIsAudioRating() {
		return isAudioRating;
	}

	public void setIsAudioRating(Boolean isAudioRating) {
		this.isAudioRating = isAudioRating;
	}

	public Boolean getIsAudioLike() {
		return isAudioLike;
	}

	public void setIsAudioLike(Boolean isAudioLike) {
		this.isAudioLike = isAudioLike;
	}

	public Integer getAudioRatingPoints() {
		return audioRatingPoints;
	}

	public void setAudioRatingPoints(Integer audioRatingPoints) {
		this.audioRatingPoints = audioRatingPoints;
	}

	public Integer getAudioTotalRatingCount() {
		return audioTotalRatingCount;
	}

	public void setAudioTotalRatingCount(Integer audioTotalRatingCount) {
		this.audioTotalRatingCount = audioTotalRatingCount;
	}

	public Integer getAudioLikePoints() {
		return audioLikePoints;
	}

	public void setAudioLikePoints(Integer audioLikePoints) {
		this.audioLikePoints = audioLikePoints;
	}

	public Integer getAudioDislikePoints() {
		return audioDislikePoints;
	}

	public void setAudioDislikePoints(Integer audioDislikePoints) {
		this.audioDislikePoints = audioDislikePoints;
	}

	public Integer getAudioAdPoint() {
		return audioAdPoint;
	}

	public void setAudioAdPoint(Integer audioAdPoint) {
		this.audioAdPoint = audioAdPoint;
	}

	public Integer getAudioAdId() {
		return audioAdId;
	}

	public void setAudioAdId(Integer audioAdId) {
		this.audioAdId = audioAdId;
	}

	public Boolean getIsPgUrl() {
		return isPgUrl;
	}

	public void setIsPgUrl(Boolean isPgUrl) {
		this.isPgUrl = isPgUrl;
	}

	public String getPgUrl() {
		return pgUrl;
	}

	public void setPgUrl(String pgUrl) {
		this.pgUrl = pgUrl;
	}

	public Double getVideoRateAvg() {
		return videoRateAvg;
	}

	public void setVideoRateAvg(Double videoRateAvg) {
		this.videoRateAvg = videoRateAvg;
	}

	public Double getAudioRateAvg() {
		return audioRateAvg;
	}

	public void setAudioRateAvg(Double audioRateAvg) {
		this.audioRateAvg = audioRateAvg;
	}

	public Boolean getIsRedirection() {
		return isRedirection;
	}

	public void setIsRedirection(Boolean isRedirection) {
		this.isRedirection = isRedirection;
	}

	public String getRedirectionUrl() {
		return redirectionUrl;
	}

	public void setRedirectionUrl(String redirectionUrl) {
		this.redirectionUrl = redirectionUrl;
	}

	@Override
	public String toString() {
		return "[ macAddress : " + macAddress + " ] , [ status : " + status + " ] , [ reason : " + reason
				+ " ] , [ isStatic : " + isStatic + " ] , [ staticAds : " + staticAds + " ] , [ staticScreenType : "
				+ staticScreenType + " ] , [ staticFreeMinute : " + staticFreeMinute + " ] , [ staticTriggerTime : "
				+ staticTriggerTime + " ] , [ isStaticRating : " + isStaticRating + " ] , [ isStaticLike : "
				+ isStaticLike + " ] , [ staticShowTime : " + staticShowTime + " ] , [ staticAdPoint : "
				+ staticAdPoint + " ] , [ isVideo : " + isVideo + " ] , [ videoAdUrl : " + videoAdUrl
				+ " ] , [ videoFreeMinute : " + videoFreeMinute + " ] , [ videoTriggerTime : " + videoTriggerTime
				+ " ] , [ isVideoRating : " + isVideoRating + " ] , [ isVideoLike : " + isVideoLike
				+ " ] , [ videoRatingPoints : " + videoRatingPoints + " ] , [ videoTotalRatingCount : "
				+ videoTotalRatingCount + " ] , [ videoRateAvg : " + videoRateAvg + " ] , [ videoLikePoints : "
				+ videoLikePoints + " ] , [ videoDislikePoints : " + videoDislikePoints + " ] , [ videoAdPoint : "
				+ videoAdPoint + " ] , [ videoAdId : " + videoAdId + " ] , [ isAudio : " + isAudio
				+ " ] , [ audioAdUrl : " + audioAdUrl + " ] , [ audioFreeMinute : " + audioFreeMinute
				+ " ] , [ audioTriggerTime : " + audioTriggerTime + " ] , [ isAudioRating : " + isAudioRating
				+ " ] , [ audioRateAvg : " + audioRateAvg + " ] , [ isAudioLike : " + isAudioLike
				+ " ] , [ audioRatingPoints : " + audioRatingPoints + " ] , [ audioTotalRatingCount : "
				+ audioTotalRatingCount + " ] , [ audioLikePoints : " + audioLikePoints
				+ " ] , [ audioDislikePoints : " + audioDislikePoints + " ] , [ audioAdPoint : " + audioAdPoint
				+ " ] , [ audioAdId : " + audioAdId + " ] , [ isPgUrl : " + isPgUrl + " ] , [ pgUrl : " + pgUrl
				+ " ] , [ isRedirection : " + isRedirection + " ] , [ redirectionUrl : " + redirectionUrl + " ] ";
	}

}
