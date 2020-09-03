package com.valley.cws.bean;

public class RadiusBean {
	private String radcheckUsername;
	private String radcheckValue;
	private String radcheckAttribute;
	private String radcheckOp;
	private String radreplyUsername;
	private String radreplyValue;
	private String radreplyAttribute;
	private String radreplyOp;

	public RadiusBean() {
		super();
	}

	public RadiusBean(String radcheckUsername, String radcheckValue, String radcheckAttribute, String radcheckOp,
			String radreplyUsername, String radreplyValue, String radreplyAttribute, String radreplyOp) {
		super();
		this.radcheckUsername = radcheckUsername;
		this.radcheckValue = radcheckValue;
		this.radcheckAttribute = radcheckAttribute;
		this.radcheckOp = radcheckOp;
		this.radreplyUsername = radreplyUsername;
		this.radreplyValue = radreplyValue;
		this.radreplyAttribute = radreplyAttribute;
		this.radreplyOp = radreplyOp;
	}

	public String getRadcheckUsername() {
		return radcheckUsername;
	}

	public void setRadcheckUsername(String radcheckUsername) {
		this.radcheckUsername = radcheckUsername;
	}

	public String getRadcheckValue() {
		return radcheckValue;
	}

	public void setRadcheckValue(String radcheckValue) {
		this.radcheckValue = radcheckValue;
	}

	public String getRadcheckAttribute() {
		return radcheckAttribute;
	}

	public void setRadcheckAttribute(String radcheckAttribute) {
		this.radcheckAttribute = radcheckAttribute;
	}

	public String getRadcheckOp() {
		return radcheckOp;
	}

	public void setRadcheckOp(String radcheckOp) {
		this.radcheckOp = radcheckOp;
	}

	public String getRadreplyUsername() {
		return radreplyUsername;
	}

	public void setRadreplyUsername(String radreplyUsername) {
		this.radreplyUsername = radreplyUsername;
	}

	public String getRadreplyValue() {
		return radreplyValue;
	}

	public void setRadreplyValue(String radreplyValue) {
		this.radreplyValue = radreplyValue;
	}

	public String getRadreplyAttribute() {
		return radreplyAttribute;
	}

	public void setRadreplyAttribute(String radreplyAttribute) {
		this.radreplyAttribute = radreplyAttribute;
	}

	public String getRadreplyOp() {
		return radreplyOp;
	}

	public void setRadreplyOp(String radreplyOp) {
		this.radreplyOp = radreplyOp;
	}

	@Override
	public String toString() {
		return "[ radcheckUsername : " + radcheckUsername + " ] , [ radcheckValue : " + radcheckValue
				+ " ] , [ radcheckAttribute : " + radcheckAttribute + " ] , [ radcheckOp : " + radcheckOp
				+ " ] , [ radreplyUsername : " + radreplyUsername + " ] , [ radreplyValue : " + radreplyValue
				+ " ] , [ radreplyAttribute : " + radreplyAttribute + " ] , [ radreplyOp : " + radreplyOp + " ]";
	}

}
