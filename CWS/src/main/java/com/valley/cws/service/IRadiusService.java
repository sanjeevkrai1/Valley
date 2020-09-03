package com.valley.cws.service;

import com.valley.cws.bean.RadiusBean;

public interface IRadiusService {
	void saveRadiusDetails(RadiusBean radiusBean);

	Integer getDuration(String mobileNumber);
}
