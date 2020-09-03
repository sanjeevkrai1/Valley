package com.valley.cws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valley.cws.dao.PaymentDao;
import com.valley.cws.entity.WcaBuyingHistory;

@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	PaymentDao dao;
	@Transactional(value="transactionManager")
	public WcaBuyingHistory save(WcaBuyingHistory buyingHistory) {
		return dao.save(buyingHistory);
	}
	@Transactional(value="transactionManager")
	public WcaBuyingHistory update(WcaBuyingHistory buyingHistory) {
		return dao.update(buyingHistory);
	}

	@Override
	@Transactional(value="transactionManager")
	public WcaBuyingHistory findOne(String orderId) {
		return dao.findOne(orderId);
	}

}
