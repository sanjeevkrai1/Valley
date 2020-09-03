package com.valley.cws.dao.mysql;

import com.valley.cws.entity.mysql.Radreply;

public interface IRadreplyDao {
	Radreply getByUserName(String userName);

	void save(Radreply radreply);

	void update(Radreply radreply);
}
