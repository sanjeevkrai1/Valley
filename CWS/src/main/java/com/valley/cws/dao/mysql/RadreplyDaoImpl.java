package com.valley.cws.dao.mysql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.valley.cws.entity.mysql.Radreply;

@Repository
public class RadreplyDaoImpl implements IRadreplyDao {

	@Autowired
	@Qualifier(value = "mysqlSessionFactory")
	SessionFactory sessionFactory;

	@Override
	public Radreply getByUserName(String userName) {
		Session session;
		Radreply radreply;

		session = sessionFactory.getCurrentSession();
		radreply = (Radreply) session.createQuery("from Radreply where username = '" + userName + "'").uniqueResult();

		return radreply;
	}

	@Override
	public void save(Radreply radreply) {
		Session session;
		session = sessionFactory.getCurrentSession();
		session.save(radreply);

	}

	@Override
	public void update(Radreply radreply) {
		Session session;
		session = sessionFactory.getCurrentSession();
		session.update(radreply);

	}

}
