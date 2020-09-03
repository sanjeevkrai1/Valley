package com.valley.cws.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "WCA_USER_INTEREST")
public class WcaUserInterest {
	private Integer id;
	private WcaUserProfile wcaUserProfile;
	private WcaCategoryDetails wcaCategoryDetails;
	private Date createdOn;
	private Date updatedOn;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@SequenceGenerator(allocationSize = 1, name = "id_Sequence", sequenceName = "user_intrst_ID_SEQ")
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	public WcaUserProfile getWcaUserProfile() {
		return wcaUserProfile;
	}

	public void setWcaUserProfile(WcaUserProfile userProfile) {
		this.wcaUserProfile = userProfile;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	public WcaCategoryDetails getWcaCategoryDetails() {
		return wcaCategoryDetails;
	}

	public void setWcaCategoryDetails(WcaCategoryDetails categoryDetails) {
		this.wcaCategoryDetails = categoryDetails;
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
