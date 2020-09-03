package com.valley.cws.dao;

import com.valley.cws.entity.WcaAdData;
import com.valley.cws.entity.WcaAdShownHistory;

public interface ShowAdDao {

	public WcaAdData findAd(int adId);

	public WcaAdShownHistory saveAdHistory(WcaAdShownHistory adShownHistory);

	public WcaAdData update(WcaAdData adData);

	public String getvalue(String key);

}
