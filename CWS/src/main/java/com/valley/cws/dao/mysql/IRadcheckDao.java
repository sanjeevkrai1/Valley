package com.valley.cws.dao.mysql;

import com.valley.cws.entity.mysql.Radcheck;

public interface IRadcheckDao {
	Radcheck getByUserName(String userName);

	void save(Radcheck radcheck);

	void update(Radcheck radcheck);
}
