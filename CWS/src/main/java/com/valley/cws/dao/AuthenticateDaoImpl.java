package com.valley.cws.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.valley.cws.entity.WcaDefaultAd;
import com.valley.cws.entity.WcaIpRanges;
import com.valley.cws.entity.WcaLoginHistory;
import com.valley.cws.entity.WcaUserProfile;

@Repository
public class AuthenticateDaoImpl implements AuthenticateDao {

	@Autowired
	@Qualifier(value = "sessionFactory")
	private SessionFactory sessionFactory;

	public WcaUserProfile findOne(String clientMAC) {
		WcaUserProfile userDetails = (WcaUserProfile) sessionFactory.getCurrentSession()
				.createQuery("From WcaUserProfile UP where UP.macAddress='" + clientMAC + "'").uniqueResult();
		return userDetails;
	}

	public WcaUserProfile save(WcaUserProfile userProfile) {
		try {
			userProfile.setCreatedOn(new Date());
			sessionFactory.getCurrentSession().save(userProfile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userProfile;
	}

	public WcaUserProfile findbyNumber(String mobileNo, String macAddress) {
		WcaUserProfile userDetails = (WcaUserProfile) sessionFactory
				.getCurrentSession()
				.createQuery(
						"From WcaUserProfile UP where UP.mobileNo='" + mobileNo + "' AND UP.macAddress='" + macAddress
								+ "'").uniqueResult();
		return userDetails;
	}

	public WcaUserProfile update(WcaUserProfile userProfile) {
		try {
			sessionFactory.getCurrentSession().update(userProfile);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return userProfile;
	}

	public WcaUserProfile findbyOtp(String otp, String macAddress) {
		WcaUserProfile userDetails = (WcaUserProfile) sessionFactory
				.getCurrentSession()
				.createQuery(
						"From WcaUserProfile UP where UP.lastOtp='" + otp + "' AND UP.macAddress='" + macAddress + "'")
				.uniqueResult();
		return userDetails;
	}

	public WcaLoginHistory saveLoginDetail(WcaLoginHistory loginHistory) {
		try {
			loginHistory.setCreatedOn(new Date());
			sessionFactory.getCurrentSession().save(loginHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginHistory;
	}

	public WcaUserProfile findById(Integer profileId) {
		WcaUserProfile userDetails = (WcaUserProfile) sessionFactory.getCurrentSession()
				.createQuery("From WcaUserProfile UP where UP.id=" + profileId).uniqueResult();
		return userDetails;
	}

	public WcaUserProfile updatewithDate(WcaUserProfile userProfile) {
		try {
			userProfile.setUpdatedOn(new Date());
			sessionFactory.getCurrentSession().update(userProfile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userProfile;
	}

	@Override
	public WcaDefaultAd findAd(String location) {
		WcaDefaultAd defaultAd = (WcaDefaultAd) sessionFactory.getCurrentSession()
				.createQuery("From WcaDefaultAd DA where DA.location='" + location + "'").uniqueResult();
		return defaultAd;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WcaIpRanges> findAllIp() {
		List<WcaIpRanges> ipRanges = (List<WcaIpRanges>) sessionFactory.getCurrentSession().createQuery("From WcaIpRanges").list();
		return ipRanges;
	}

}
