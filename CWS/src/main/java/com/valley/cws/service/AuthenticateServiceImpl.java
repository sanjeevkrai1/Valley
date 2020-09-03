package com.valley.cws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valley.cws.dao.AuthenticateDao;
import com.valley.cws.entity.WcaDefaultAd;
import com.valley.cws.entity.WcaIpRanges;
import com.valley.cws.entity.WcaLoginHistory;
import com.valley.cws.entity.WcaUserProfile;

@Service
public class AuthenticateServiceImpl implements AuthenticateService {

	@Autowired
	AuthenticateDao dao;
	@Transactional(value="transactionManager") 
	public WcaUserProfile findOne(String clientMAC) {
		if (clientMAC!=null && !clientMAC.equalsIgnoreCase("")) {
			return dao.findOne(clientMAC.toUpperCase());
		}
		return null;
	}
	@Transactional(value="transactionManager")
	public WcaUserProfile save(WcaUserProfile userProfile) {
		return dao.save(userProfile);
	}
	@Transactional(value="transactionManager")
	public WcaUserProfile findbyNumber(String mobileNo , String macAddress) {
		if (macAddress!=null && !macAddress.equalsIgnoreCase("")) {
			return dao.findbyNumber(mobileNo , macAddress.toUpperCase());
		}
		return null;
	}
	@Transactional(value="transactionManager")
	public WcaUserProfile update(WcaUserProfile userProfile) {
	
		return dao.update(userProfile);
	}
	@Transactional(value="transactionManager")
	public WcaUserProfile findbyOtp(String Otp , String macAddress) {
		if (macAddress!=null && !macAddress.equalsIgnoreCase("")) {
			return dao.findbyOtp(Otp , macAddress.toUpperCase());
		}
		return null;
		
	}
	@Transactional(value="transactionManager")
	public WcaLoginHistory saveLoginDetail(WcaLoginHistory loginHistory) {
		return dao.saveLoginDetail(loginHistory);
	}
	@Transactional(value="transactionManager")
	public WcaUserProfile findById(Integer ProfileId) {
		return dao.findById(ProfileId);
	}
	@Transactional(value="transactionManager")
	public WcaUserProfile updatewithDate(WcaUserProfile userProfile) {
		return dao.updatewithDate(userProfile);
	}

	@Override
	@Transactional(value="transactionManager")
	public WcaDefaultAd findAd(String location) {
		return dao.findAd(location);
	}
	@Override
	@Transactional(value="transactionManager")
	public List<WcaIpRanges> findAllIp() {
		return dao.findAllIp();
	}
	
	
}
