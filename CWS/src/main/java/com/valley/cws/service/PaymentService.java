package com.valley.cws.service;

import com.valley.cws.entity.WcaBuyingHistory;

public interface PaymentService {
	
	public WcaBuyingHistory save(WcaBuyingHistory buyingHistory);

	public WcaBuyingHistory update(WcaBuyingHistory buyingHistory);

	public WcaBuyingHistory findOne(String orderId);

}
