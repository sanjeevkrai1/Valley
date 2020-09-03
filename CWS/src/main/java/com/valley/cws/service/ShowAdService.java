package com.valley.cws.service;

import com.valley.cws.entity.WcaAdData;
import com.valley.cws.entity.WcaAdShownHistory;

public interface ShowAdService {

	public WcaAdData findAd(int adId);

	public WcaAdShownHistory saveAdHistory(WcaAdShownHistory adShownHistory);
	
	public WcaAdData update(WcaAdData adData);
	
	public String getvalue(String key);

}
