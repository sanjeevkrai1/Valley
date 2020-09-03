package com.valley.cws.dao;

import java.util.List;

import com.valley.cws.entity.WcaWalletPlan;

public interface WalletDao {

	public List<WcaWalletPlan> findAll();

	public WcaWalletPlan findPlan(Long planId);

}
