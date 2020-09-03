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
@Table(name = "CATEGORY_URL_MAPPING")
public class CategoryUrlMapping {
	private Long id;
	private String url;
	private WcaCategoryDetails wcaCategoryDetails;
	private WcaSubcategoryDetails wcaSubcategoryDetails;
	private Date createdOn;
	private Date updatedOn;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@SequenceGenerator(name = "id_Sequence", sequenceName = "CAT_URL_MAP_ID_SEQ", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "url", unique = true)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false)
	public WcaCategoryDetails getWcaCategoryDetails() {
		return this.wcaCategoryDetails;
	}

	public void setWcaCategoryDetails(WcaCategoryDetails wcaCategoryDetails) {
		this.wcaCategoryDetails = wcaCategoryDetails;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subcategory_id")
	public WcaSubcategoryDetails getWcaSubcategoryDetails() {
		return this.wcaSubcategoryDetails;
	}

	public void setWcaSubcategoryDetails(WcaSubcategoryDetails wcaSubcategoryDetails) {
		this.wcaSubcategoryDetails = wcaSubcategoryDetails;
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
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

}
