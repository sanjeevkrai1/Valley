package com.valley.cws.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "WCA_CAMPAIGN_INFO")
public class WcaCampaignInfo {

	private Long id;
	private String campaignName;
	private Long adId;
	private String url;
	private String adName;
	private Long total;
	private Long successCount;
	private Long failureCount;
	private Date startTime;
	private Date endTime;
	private String createdBy;
	private String startTimeString;
	private String endTimeString;
	private Long failurePercentage;
	private Long successPercentage;
	private Date scheduleTime;
	private Boolean isScheduledCampaign;
	
	@Column(name = "schedule_time")
	public Date getScheduleTime() {
		return scheduleTime;
	}

	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@SequenceGenerator(allocationSize = 1, name = "id_Sequence", sequenceName = "WCA_CAMPAIGN_INFO_ID_SE")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "campaign_name")
	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	@Column(name = "ad_id")
	public Long getAdId() {
		return adId;
	}

	public void setAdId(Long adId) {
		this.adId = adId;
	}

	@Column(name = "ad_url")
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "ad_name")
	public String getAdName() {
		return adName;
	}

	public void setAdName(String adName) {
		this.adName = adName;
	}

	@Column(name = "total")
	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	@Column(name = "success_count")
	public Long getSuccessCount() {
		return this.successCount;
	}

	public void setSuccessCount(Long successCount) {
		this.successCount = successCount;
	}

	@Column(name = "failure_count")
	public Long getFailureCount() {
		return this.failureCount;
	}

	public void setFailureCount(Long failureCount) {
		this.failureCount = failureCount;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time", length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time", length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "created_by")
	public String getCreatedBy() {
		return createdBy;
	}
	@Column(name = "is_scheduled_cam")
	public Boolean getIsScheduledCampaign() {
		return isScheduledCampaign;
	}

	public void setIsScheduledCampaign(Boolean isScheduledCampaign) {
		this.isScheduledCampaign = isScheduledCampaign;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Transient
	public String getStartTimeString() {
		return startTimeString;
	}

	public void setStartTimeString(String startTimeString) {
		this.startTimeString = startTimeString;
	}

	@Transient
	public String getEndTimeString() {
		return endTimeString;
	}

	public void setEndTimeString(String endTimeString) {
		this.endTimeString = endTimeString;
	}

	@Transient
	public Long getFailurePercentage() {
		return failurePercentage;
	}

	public void setFailurePercentage(Long failurePercentage) {
		this.failurePercentage = failurePercentage;
	}

	@Transient
	public Long getSuccessPercentage() {
		return successPercentage;
	}

	public void setSuccessPercentage(Long successPercentage) {
		this.successPercentage = successPercentage;
	}

}
