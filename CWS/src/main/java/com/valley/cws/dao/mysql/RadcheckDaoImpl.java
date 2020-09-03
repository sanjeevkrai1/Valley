package com.valley.cws.dao.mysql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.valley.cws.entity.mysql.Radcheck;

@Repository
public class RadcheckDaoImpl implements IRadcheckDao {

	@Autowired
	@Qualifier(value = "mysqlSessionFactory")
	SessionFactory sessionFactory;

	@Override
	public Radcheck getByUserName(String userName) {
		Session session;
		Radcheck radcheck;
		session = sessionFactory.getCurrentSession();
		radcheck = (Radcheck) session.createQuery("from Radcheck where username = '" + userName + "'").uniqueResult();
		return radcheck;
	}

	@Override
	public void save(Radcheck radcheck) {
		Session session;
		session = sessionFactory.getCurrentSession();
		session.save(radcheck);

	}

	@Override
	public void update(Radcheck radcheck) {
		Session session;
		session = sessionFactory.getCurrentSession();
		session.update(radcheck);

	}

}
