package com.valley.cws.entity.mysql;

// Generated Oct 5, 2016 1:44:42 PM by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Radreply generated by hbm2java
 */
@Entity
@Table(name = "radreply")
public class Radreply implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String username;
	private String attribute;
	private String op;
	private String value;

	public Radreply() {
	}

	public Radreply(String username, String attribute, String op, String value) {
		this.username = username;
		this.attribute = attribute;
		this.op = op;
		this.value = value;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "username", nullable = false, length = 64)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "attribute", nullable = false, length = 64)
	public String getAttribute() {
		return this.attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	@Column(name = "op", nullable = false, length = 2)
	public String getOp() {
		return this.op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	@Column(name = "value", nullable = false, length = 253)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
