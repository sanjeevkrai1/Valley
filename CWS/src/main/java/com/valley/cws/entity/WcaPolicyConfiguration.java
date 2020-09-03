package com.valley.cws.entity;

// Generated Aug 9, 2016 10:11:00 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * WcaPolicyConfiguration generated by hbm2java
 */
@Entity
@Table(name = "WCA_POLICY_CONFIGURATION")
public class WcaPolicyConfiguration implements java.io.Serializable {

	private static final long serialVersionUID = 4679814504368506706L;
	private Long id;
	private WcaPolicyPeriod wcaPolicyPeriod;
	private Integer freeMinutesDay;
	private Integer maxVolume;
	private Boolean showAd;
	private Integer year;
	private String location;
	private String accessPoint;
	private String category;
	private String subCategory;
	private String periodType;
	private String periodValue;
	private Date periodFrom;
	private Date periodTo;
	private String fromHour;
	private String toHour;
	private Boolean isActive;
	private Set<WcaPolicyAdConfiguration> wcaPolicyAdConfigurations = new HashSet<WcaPolicyAdConfiguration>(0);

	public WcaPolicyConfiguration() {

	}

	public WcaPolicyConfiguration(Long id, WcaPolicyPeriod wcaPolicyPeriod, Integer freeMinutesDay, Integer maxVolume, Boolean showAd,
			Integer year, String location, String accessPoint, String category, String subCategory, String periodType, String periodValue,
			Date periodFrom, Date periodTo, String fromHour, String toHour, Boolean isActive,
			Set<WcaPolicyAdConfiguration> wcaPolicyAdConfigurations) {
		super();
		this.id = id;
		this.wcaPolicyPeriod = wcaPolicyPeriod;
		this.freeMinutesDay = freeMinutesDay;
		this.maxVolume = maxVolume;
		this.showAd = showAd;
		this.year = year;
		this.location = location;
		this.accessPoint = accessPoint;
		this.category = category;
		this.subCategory = subCategory;
		this.periodType = periodType;
		this.periodValue = periodValue;
		this.periodFrom = periodFrom;
		this.periodTo = periodTo;
		this.fromHour = fromHour;
		this.toHour = toHour;
		this.isActive = isActive;
		this.wcaPolicyAdConfigurations = wcaPolicyAdConfigurations;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@SequenceGenerator(name = "id_Sequence", sequenceName = "WCA_PLCY_CNFGRTN_ID_SEQ", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(name = "period")
	public WcaPolicyPeriod getWcaPolicyPeriod() {
		return this.wcaPolicyPeriod;
	}

	public void setWcaPolicyPeriod(WcaPolicyPeriod wcaPolicyPeriod) {
		this.wcaPolicyPeriod = wcaPolicyPeriod;
	}

	@Column(name = "free_minutes_day")
	public Integer getFreeMinutesDay() {
		return this.freeMinutesDay;
	}

	public void setFreeMinutesDay(Integer freeMinutesDay) {
		this.freeMinutesDay = freeMinutesDay;
	}

	@Column(name = "max_volume")
	public Integer getMaxVolume() {
		return this.maxVolume;
	}

	public void setMaxVolume(Integer maxVolume) {
		this.maxVolume = maxVolume;
	}

	@Column(name = "show_ad")
	public Boolean getShowAd() {
		return this.showAd;
	}

	public void setShowAd(Boolean showAd) {
		this.showAd = showAd;
	}

	@Column(name = "year")
	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	@Column(name = "location", length = 45)
	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "access_point", length = 45)
	public String getAccessPoint() {
		return this.accessPoint;
	}

	public void setAccessPoint(String accessPoint) {
		this.accessPoint = accessPoint;
	}

	@Column(name = "category", length = 45)
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "sub_category", length = 45)
	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	@Column(name = "period_type", length = 45)
	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	@Column(name = "period_value", length = 254)
	public String getPeriodValue() {
		return periodValue;
	}

	public void setPeriodValue(String periodValue) {
		this.periodValue = periodValue;
	}

	@Column(name = "from_hour", length = 10)
	public String getFromHour() {
		return fromHour;
	}

	public void setFromHour(String fromHour) {
		this.fromHour = fromHour;
	}

	@Column(name = "to_hour", length = 10)
	public String getToHour() {
		return toHour;
	}

	public void setToHour(String toHour) {
		this.toHour = toHour;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "period_from", length = 19)
	public Date getPeriodFrom() {
		return periodFrom;
	}

	public void setPeriodFrom(Date periodFrom) {
		this.periodFrom = periodFrom;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "period_to", length = 19)
	public Date getPeriodTo() {
		return periodTo;
	}

	public void setPeriodTo(Date periodTo) {
		this.periodTo = periodTo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "wcaPolicyConfiguration")
	@JsonIgnore
	public Set<WcaPolicyAdConfiguration> getWcaPolicyAdConfigurations() {
		return this.wcaPolicyAdConfigurations;
	}

	public void setWcaPolicyAdConfigurations(Set<WcaPolicyAdConfiguration> wcaPolicyAdConfigurations) {
		this.wcaPolicyAdConfigurations = wcaPolicyAdConfigurations;
	}
}