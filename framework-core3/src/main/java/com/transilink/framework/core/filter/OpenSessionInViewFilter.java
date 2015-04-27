package com.transilink.framework.core.filter;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.SessionFactoryUtils;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class OpenSessionInViewFilter extends
		org.springframework.orm.hibernate3.support.OpenSessionInViewFilter {
	protected void closeSession(Session session, SessionFactory sessionFactory) {
		session.flush();
		session.getTransaction().commit();
		super.closeSession(session, sessionFactory);
	}

	protected Session getSession(SessionFactory sessionFactory)
			throws DataAccessResourceFailureException {
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		session.beginTransaction();
		FlushMode flushMode = getFlushMode();
		if (flushMode != null) {
			setFlushMode(FlushMode.AUTO);
		}
		return session;
	}
}