package com.valley.cws.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.valley.cws.bean.RadiusBean;
import com.valley.cws.dao.mysql.IRadcheckDao;
import com.valley.cws.dao.mysql.IRadreplyDao;
import com.valley.cws.entity.mysql.Radcheck;
import com.valley.cws.entity.mysql.Radreply;
import com.valley.cws.utils.CommonUtils;

@Service
@Transactional(value = "mysqlTransactionManager")
public class RadiusServiceImpl implements IRadiusService {
	private static final Logger LOGGER = Logger.getLogger(RadiusServiceImpl.class);

	@Autowired
	IRadcheckDao radcheckDao;

	@Autowired
	IRadreplyDao radreplyDao;

	@Override
	public void saveRadiusDetails(RadiusBean radiusBean) {
		LOGGER.info("START saveRadiusDetails - " + radiusBean);
		Radcheck radcheck;
		Radreply radreply;
		String radcheckUsername;
		String radreplyUsername;

		// save Radcheck
		radcheckUsername = CommonUtils.checkString(radiusBean.getRadcheckUsername());
		radcheck = radcheckDao.getByUserName(radcheckUsername);
		if (radcheck != null) {
			LOGGER.info("saveRadiusDetails - update radcheck - [ username : " + radcheckUsername + " ] ");
			if (!CommonUtils.checkInteger(radiusBean.getRadcheckValue()).equals(0)) {
				radcheck.setValue(CommonUtils.checkString(radiusBean.getRadcheckValue()));
				radcheckDao.update(radcheck);
			}
		} else {
			LOGGER.info("saveRadiusDetails - save radcheck - [ username : " + radcheckUsername + " ] ");
			radcheck = new Radcheck();

			radcheck.setUsername(radcheckUsername);
			radcheck.setValue(CommonUtils.checkString(radiusBean.getRadcheckValue()));
			radcheck.setAttribute(CommonUtils.checkString(radiusBean.getRadcheckAttribute()));
			radcheck.setOp(CommonUtils.checkString(radiusBean.getRadcheckOp()));

			radcheckDao.save(radcheck);
		}

		// save Radreply
		radreplyUsername = CommonUtils.checkString(radiusBean.getRadreplyUsername());
		radreply = radreplyDao.getByUserName(radreplyUsername);
		if (radreply != null) {
			LOGGER.info("saveRadiusDetails - update radreply - [ username : " + radreplyUsername + " ] ");
			if (!CommonUtils.checkInteger(radiusBean.getRadreplyValue()).equals(0)) {
				radreply.setValue(CommonUtils.checkString(radiusBean.getRadreplyValue()));
				radreplyDao.update(radreply);
			}
		} else {
			LOGGER.info("saveRadiusDetails - save radreply - [ username : " + radreplyUsername + " ] ");
			radreply = new Radreply();

			radreply.setUsername(radreplyUsername);
			radreply.setValue(CommonUtils.checkString(radiusBean.getRadreplyValue()));
			radreply.setAttribute(CommonUtils.checkString(radiusBean.getRadreplyAttribute()));
			radreply.setOp(CommonUtils.checkString(radiusBean.getRadreplyOp()));

			radreplyDao.save(radreply);
		}

		LOGGER.info("END saveRadiusDetails - ");
	}

	@Override
	public Integer getDuration(String mobileNumber) {
		Integer duration = 0;
		Radreply radreply;

		radreply = radreplyDao.getByUserName(mobileNumber);
		if (radreply != null) {
			duration = CommonUtils.checkInteger(radreply.getValue());
		}
		return CommonUtils.secToMin(duration);
	}

}
