package com.valley.cws.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name = "WCA_WALLET_PLANS")
public class WcaWalletPlan {
	
	private Long id;
	private String planName;
	private Integer planPoint;
	private Long planRate;
	private Long grantData;
	private Integer grantMinute;
	private Date createdOn;
	private String createdBy;
	private Date updatedOn;
	private String updatedBy;
	private Set<WcaBuyingHistory> wcaBuyingHistories = new HashSet<WcaBuyingHistory>(0);
	
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "wcaWalletPlan")
	@Cascade(CascadeType.DELETE)
	public Set<WcaBuyingHistory> getWcaBuyingHistories() {
		return this.wcaBuyingHistories;
	}

	public void setWcaBuyingHistories(Set<WcaBuyingHistory> wcaBuyingHistories) {
		this.wcaBuyingHistories = wcaBuyingHistories;
	}

	
	public WcaWalletPlan(){
		
	}
	
	public WcaWalletPlan(Long id, String planName, Integer planPoint, Integer grantMinute, Date createdOn, String createdBy,
			Date updatedOn, String updatedBy) {
		super();
		this.id = id;
		this.planName = planName;
		this.planPoint = planPoint;
		this.grantMinute = grantMinute;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.updatedOn = updatedOn;
		this.updatedBy = updatedBy;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@SequenceGenerator(name = "id_Sequence", sequenceName = "WCA_WALLET_DATA_ID_SEQ", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "plan_name", length = 255)
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	
	@Column(name = "plan_point", length = 19)
	public Integer getPlanPoint() {
		return planPoint;
	}
	public void setPlanPoint(Integer planPoint) {
		this.planPoint = planPoint;
	}
	
	@Column(name = "grant_minute", length = 19)
	public Integer getGrantMinute() {
		return grantMinute;
	}

	public void setGrantMinute(Integer grantMinute) {
		this.grantMinute = grantMinute;
	}
	@Column(name = "plan_rate", length = 19)
	public Long getPlanRate() {
		return planRate;
	}

	public void setPlanRate(Long planRate) {
		this.planRate = planRate;
	}
	@Column(name = "grant_data")
	public Long getGrantData() {
		return grantData;
	}

	public void setGrantData(Long grantData) {
		this.grantData = grantData;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
	@Column(name = "created_by", length = 19)
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	
	@Column(name = "updated_by", length = 19)
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}
