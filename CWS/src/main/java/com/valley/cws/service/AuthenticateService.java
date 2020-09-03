package com.valley.cws.service;

import java.util.List;

import com.valley.cws.entity.WcaDefaultAd;
import com.valley.cws.entity.WcaIpRanges;
import com.valley.cws.entity.WcaLoginHistory;
import com.valley.cws.entity.WcaUserProfile;

public interface AuthenticateService {
	
	public WcaUserProfile findOne(String clientMAC);

	public WcaUserProfile save(WcaUserProfile userProfile);

	public WcaUserProfile findbyNumber(String mobileNo , String macAddress);

	public WcaUserProfile update(WcaUserProfile userProfile);

	public WcaUserProfile findbyOtp(String Otp,String macAddress);

	public WcaLoginHistory saveLoginDetail(WcaLoginHistory loginHistory);

	public WcaUserProfile findById(Integer ProfileId);

	public WcaUserProfile updatewithDate(WcaUserProfile userProfile);

	public WcaDefaultAd findAd(String location);

	public List<WcaIpRanges> findAllIp();

}
