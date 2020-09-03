package com.valley.cws.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valley.cws.entity.WcaBuyingHistory;

@Repository
public class PaymentDaoImpl implements PaymentDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public WcaBuyingHistory save(WcaBuyingHistory buyingHistory) {
		try {
			sessionFactory.getCurrentSession().save(buyingHistory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buyingHistory;
	}

	
	public WcaBuyingHistory update(WcaBuyingHistory buyingHistory) {
		sessionFactory.getCurrentSession().update(buyingHistory);
		return buyingHistory;
	}


	@Override
	public WcaBuyingHistory findOne(String orderId) {
		WcaBuyingHistory buyingHistory = (WcaBuyingHistory) sessionFactory.getCurrentSession()
				.createQuery("From WcaBuyingHistory WBH where WBH.orderId='" + orderId + "'").uniqueResult();
		return buyingHistory;
	}
}
