package com.valley.cws.dao;

import com.valley.cws.entity.WcaBuyingHistory;

public interface PaymentDao {

	public WcaBuyingHistory save(WcaBuyingHistory buyingHistory);

	public WcaBuyingHistory update(WcaBuyingHistory buyingHistory);

	public WcaBuyingHistory findOne(String orderId);

}
