package com.valley.cws.entity;
// Generated Aug 9, 2016 10:11:00 AM by Hibernate Tools 3.4.0.CR1

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

/**
 * WcaBrowsingHistory generated by hbm2java
 */
@Entity
@Table(name = "WCA_BROWSING_HISTORY")
public class WcaBrowsingHistory implements java.io.Serializable {

	private static final long serialVersionUID = 2163111508868604327L;
	private Integer id;
	private WcaUserProfile wcaUserProfile;
	private String title;
	private String url;
	private String category;
	private Integer counter;
	private Date createdOn;

	public WcaBrowsingHistory() {
	}

	public WcaBrowsingHistory(Integer id, WcaUserProfile wcaUserProfile, String title, String url, String category,
			Integer counter, Date createdOn) {
		super();
		this.id = id;
		this.wcaUserProfile = wcaUserProfile;
		this.title = title;
		this.url = url;
		this.category = category;
		this.counter = counter;
		this.createdOn = createdOn;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@SequenceGenerator(name = "id_Sequence", sequenceName = "WCA_BRWSNG_HSTRY_ID_SEQ", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	public WcaUserProfile getWcaUserProfile() {
		return this.wcaUserProfile;
	}

	public void setWcaUserProfile(WcaUserProfile wcaUserProfile) {
		this.wcaUserProfile = wcaUserProfile;
	}

	@Column(name = "title")
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "url", nullable = false)
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "category", length = 45)
	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Column(name = "counter")
	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}