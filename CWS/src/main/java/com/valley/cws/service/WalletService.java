package com.valley.cws.service;

import java.util.List;

import com.valley.cws.entity.WcaWalletPlan;

public interface WalletService {
	
	public List<WcaWalletPlan> findAll();

	public WcaWalletPlan findPlan(Long planId);

}
