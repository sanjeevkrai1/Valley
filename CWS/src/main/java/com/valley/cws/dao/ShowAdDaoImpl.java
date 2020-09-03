package com.valley.cws.dao;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valley.cws.entity.WcaAdData;
import com.valley.cws.entity.WcaAdShownHistory;

@Repository
public class ShowAdDaoImpl implements ShowAdDao{

	@Autowired
	private SessionFactory sessionFactory;
	
	public WcaAdData findAd(int adId) {
		WcaAdData adData = (WcaAdData) sessionFactory.getCurrentSession()
				.createQuery("From WcaAdData AD where AD.id='" + adId + "'").uniqueResult();
		return adData;
	}

	public WcaAdShownHistory saveAdHistory(WcaAdShownHistory adShownHistory) {
		try {
			adShownHistory.setCreatedOn(new Date());
			sessionFactory.getCurrentSession().save(adShownHistory);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return adShownHistory;
	}

	public WcaAdData update(WcaAdData adData) {
		sessionFactory.getCurrentSession().update(adData);
		return adData;
	}
	
	public String getvalue(String key) {
	String value;
	//value =  (String) sessionFactory.getCurrentSession().createQuery("select value from WcaConfiguration where key_ = '" + key + "'").uniqueResult();
	value =  (String) sessionFactory.getCurrentSession().createQuery("select value from WcaConfiguration where key_ = '" + key + "'").list().get(0);
	return value;
	}


}
