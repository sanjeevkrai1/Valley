package com.valley.cws.bean.api.bng;

public class GrantBngReqBean {
	private String macAddress;
	private String userName;
	private String password;

	public GrantBngReqBean() {
		super();
	}

	public GrantBngReqBean(String macAddress, String userName, String password) {
		super();
		this.macAddress = macAddress;
		this.userName = userName;
		this.password = password;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "[ macAddress : " + macAddress + " ] , [ userName : " + userName + " ] , [ password : " + password
				+ " ] ";
	}

}
