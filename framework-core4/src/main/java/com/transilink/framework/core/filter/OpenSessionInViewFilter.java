package com.transilink.framework.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.dao.DataAccessResourceFailureException;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class OpenSessionInViewFilter extends org.springframework.orm.hibernate4.support.OpenSessionInViewFilter {
//	@Override
//	protected void closeSession(Session session, SessionFactory sessionFactory) {
//		session.flush();
//		session.getTransaction().commit();
//		//super.closeSession(session, sessionFactory);
//		session.close();
//	}
	@Override
	public Session openSession(SessionFactory sessionFactory) throws DataAccessResourceFailureException{
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		FlushMode flushMode = session.getFlushMode();
		if (flushMode != null) {
			session.setFlushMode(FlushMode.AUTO);
		}
		return session;
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		super.doFilterInternal(request, response, filterChain);
	}
}