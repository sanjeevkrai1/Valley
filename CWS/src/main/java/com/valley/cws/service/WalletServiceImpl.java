package com.valley.cws.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valley.cws.dao.WalletDao;
import com.valley.cws.entity.WcaWalletPlan;

@Service
@Transactional 
public class WalletServiceImpl implements WalletService {


	@Autowired
	WalletDao dao;
	@Transactional(value="transactionManager")
	public List<WcaWalletPlan> findAll() {
		return dao.findAll();
	}

	@Override
	@Transactional(value="transactionManager")
	public WcaWalletPlan findPlan(Long planId) {
		return dao.findPlan(planId);
	}
	
	

}
