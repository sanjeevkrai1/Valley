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

@Entity
@Table(name = "WCA_IP_RANGES")
public class WcaIpRanges {
	private Integer id;
	private String ipStart;
	private String ipEnd;
	private String location;
	private Boolean isActive;
	private String createdBy;
	private Date createdOn;
	private Date updatedOn;

	public WcaIpRanges() {
		super();
	}

	public WcaIpRanges(Integer id, String ipStart, String ipEnd, String location, Boolean isActive, String createdBy,
			Date createdOn, Date updatedOn) {
		super();
		this.id = id;
		this.ipStart = ipStart;
		this.ipEnd = ipEnd;
		this.location = location;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@SequenceGenerator(allocationSize = 1, name = "id_Sequence", sequenceName = "ip_ranges_ID_SEQ")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ip_start")
	public String getIpStart() {
		return ipStart;
	}

	public void setIpStart(String ipStart) {
		this.ipStart = ipStart;
	}

	@Column(name = "ip_end")
	public String getIpEnd() {
		return ipEnd;
	}

	public void setIpEnd(String ipEnd) {
		this.ipEnd = ipEnd;
	}

	@Column(name = "location")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Column(name = "is_active")
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "created_by", length = 40)
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_on", length = 19)
	public Date getUpdatedOn() {
		return this.updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

}
