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


@Entity
@Table(name = "wca_group_master")
public class GroupMaster implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	private Long id ;
	private String groupName;
	private Character isActive;
	private String g_privilege;
	private String createdBy;
	private Date createdOn;
	private Date updatedOn;
	private Set<UserLogin> userDetails = new HashSet<UserLogin>(0);

	public GroupMaster() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@SequenceGenerator(allocationSize=1,name = "id_Sequence", sequenceName = "group_master_ID_SEQ")
	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "group_name", length = 40)
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "isActive", length = 1)
	public Character getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Character isActive) {
		this.isActive = isActive;
	}

	@Column(name = "g_privilege" , length = 60)
	public String getG_privilege() {
		return this.g_privilege;
	}

	public void setG_privilege(String g_privilege) {
		this.g_privilege = g_privilege;
	}

	@Column(name = "created_by", length = 40)
	public String getCreatedBy() {
		return this.createdBy;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "groupMaster")
	public Set<UserLogin> getUserDetails() {
		return this.userDetails;
	}

	public void setUserDetails(Set<UserLogin> userDetails) {
		this.userDetails = userDetails;
	}

}
