package com.valley.cws.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.valley.cws.entity.WcaWalletPlan;

@Repository
public class WalletDaoImpl implements WalletDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public List<WcaWalletPlan> findAll() {
		
		List<WcaWalletPlan> Walletplans = sessionFactory.getCurrentSession().createQuery("From WcaWalletPlan").list();
		
		return Walletplans;
	}

	@Override
	public WcaWalletPlan findPlan(Long planId) {
		WcaWalletPlan walletPlan = (WcaWalletPlan) sessionFactory.getCurrentSession()
				.createQuery("From WcaWalletPlan WP where WP.id=" + planId)
				.uniqueResult();
		return walletPlan;
	}

}
