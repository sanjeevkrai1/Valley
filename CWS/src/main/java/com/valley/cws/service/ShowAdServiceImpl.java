package com.valley.cws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valley.cws.dao.ShowAdDao;
import com.valley.cws.entity.WcaAdData;
import com.valley.cws.entity.WcaAdShownHistory;

@Service
public class ShowAdServiceImpl implements ShowAdService{
	
	@Autowired
	ShowAdDao dao;
	@Transactional(value="transactionManager")
	public WcaAdData findAd(int adId) {
		return dao.findAd(adId);
	}
	@Transactional(value="transactionManager")
	public WcaAdShownHistory saveAdHistory(WcaAdShownHistory adShownHistory) {
		return dao.saveAdHistory(adShownHistory);
	}
	@Transactional(value="transactionManager")
	public WcaAdData update(WcaAdData adData) {
		return dao.update(adData);
	}
	@Transactional(value="transactionManager")
	public String getvalue(String key) {
		return dao.getvalue(key);
	}

}
