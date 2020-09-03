package com.valley.cws.bean;

public class PlanInfo {
	
	private Long id; 
	private String planName;
	private Integer planPoint;
	private Long planRate;
	private Long grantData;
	private Integer grantMinute;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public Integer getPlanPoint() {
		return planPoint;
	}
	public void setPlanPoint(Integer planPoint) {
		this.planPoint = planPoint;
	}
	public Long getPlanRate() {
		return planRate;
	}
	public void setPlanRate(Long planRate) {
		this.planRate = planRate;
	}
	public Long getGrantData() {
		return grantData;
	}
	public void setGrantData(Long grantData) {
		this.grantData = grantData;
	}
	public Integer getGrantMinute() {
		return grantMinute;
	}
	public void setGrantMinute(Integer grantMinute) {
		this.grantMinute = grantMinute;
	}
	
	

}
