package com.valley.cws.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valley.cws.dao.mysql.IRadcheckDao;
import com.valley.cws.entity.mysql.Radcheck;

@Service
@Transactional(value = "mysqlTransactionManager")
public class AaaAuthenticationServiceImpl implements AaaAuthenticationService {

	@Autowired
	IRadcheckDao radcheckDao;

	@Override
	public String authenticate(String name) {
		Radcheck radcheck;

		radcheck = radcheckDao.getByUserName(name);

		if (radcheck != null)
			return radcheck.getAttribute();
		else
			return null;
	}

}
