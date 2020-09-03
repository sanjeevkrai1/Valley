package com.valley.cws.bean.api.aruba;

public class ValidateOtpArubaReqBean {
	private String macAddress;
	private String mobileNumber;
	private String otp;

	public ValidateOtpArubaReqBean() {
		super();
	}

	public ValidateOtpArubaReqBean(String macAddress, String mobileNumber, String otp) {
		super();
		this.macAddress = macAddress;
		this.mobileNumber = mobileNumber;
		this.otp = otp;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	@Override
	public String toString() {
		return "[ macAddress : " + macAddress + " ] , [ mobileNumber : " + mobileNumber + " ] , [ otp : " + otp + " ] ";
	}

}
