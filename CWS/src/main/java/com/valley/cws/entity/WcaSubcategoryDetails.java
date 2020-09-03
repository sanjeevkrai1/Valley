package com.valley.cws.entity;
// Generated Aug 9, 2016 10:11:00 AM by Hibernate Tools 3.4.0.CR1

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

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * WcaSubcategoryDetails generated by hbm2java
 */
@Entity
@Table(name = "WCA_SUBCATEGORY_DETAILS")
public class WcaSubcategoryDetails implements java.io.Serializable {

	private static final long serialVersionUID = 4493406478768226344L;
	private Long id;
	private WcaCategoryDetails wcaCategoryDetails;
	private String name;
	private Set<WcaAdData> wcaAdDatas = new HashSet<WcaAdData>(0);
	private Set<CategoryUrlMapping> categoryUrlMappings = new HashSet<>(0);

	public WcaSubcategoryDetails() {
	}

	public WcaSubcategoryDetails(WcaCategoryDetails wcaCategoryDetails) {
		this.wcaCategoryDetails = wcaCategoryDetails;
	}

	public WcaSubcategoryDetails(Long id, WcaCategoryDetails wcaCategoryDetails, String name,
			Set<WcaAdData> wcaAdDatas, Set<CategoryUrlMapping> categoryUrlMappings) {
		super();
		this.id = id;
		this.wcaCategoryDetails = wcaCategoryDetails;
		this.name = name;
		this.wcaAdDatas = wcaAdDatas;
		this.categoryUrlMappings = categoryUrlMappings;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@SequenceGenerator(name = "id_Sequence", sequenceName = "WCA_SUBCAT_DET_ID_SEQ", allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false)
	@JsonIgnore
	public WcaCategoryDetails getWcaCategoryDetails() {
		return this.wcaCategoryDetails;
	}

	public void setWcaCategoryDetails(WcaCategoryDetails wcaCategoryDetails) {
		this.wcaCategoryDetails = wcaCategoryDetails;
	}

	@Column(name = "name", length = 45)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "wcaSubcategoryDetails")
	public Set<WcaAdData> getWcaAdDatas() {
		return this.wcaAdDatas;
	}

	public void setWcaAdDatas(Set<WcaAdData> wcaAdDatas) {
		this.wcaAdDatas = wcaAdDatas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "wcaSubcategoryDetails")
	public Set<CategoryUrlMapping> getCategoryUrlMappings() {
		return categoryUrlMappings;
	}

	public void setCategoryUrlMappings(Set<CategoryUrlMapping> categoryUrlMappings) {
		this.categoryUrlMappings = categoryUrlMappings;
	}

}