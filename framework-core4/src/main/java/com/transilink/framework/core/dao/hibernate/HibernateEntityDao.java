package com.transilink.framework.core.dao.hibernate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.BasicTransformerAdapter;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.transilink.framework.core.logs.LogEnabled;
import com.transilink.framework.core.utils.pagesUtils.Page;
import com.transilink.framework.core.utils.pagesUtils.PageContext;

/**
 * 操作的Hibernate DAO基类
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@SuppressWarnings({ "all" })
public abstract class HibernateEntityDao extends HibernateTemplate implements
		LogEnabled {

	/**
	 * 为父类HibernateDaoSupport注入sessionFactory的值
	 *
	 * @param sessionFactory
	 */
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public HibernateEntityDao() {
		setCacheQueries(true);
	}

	/**
	 * 实体类定义
	 *
	 * @return
	 */
	protected abstract Class getReferenceClass();

	/**
	 * Return a Criteria object that relates to the DAO's table
	 */
	public Criteria createCriteria() throws HibernateException {
		Session s = getSessionFactory().openSession();
		return s.createCriteria(getReferenceClass());
	}

	/**
	 * 根据 HQL 批更新记录
	 * <p>
	 * 注：传递insert update,delete等操作语句
	 * </p>
	 *
	 * @param hql
	 *            HQL 语言
	 * @param args
	 *            传递参数
	 * @return 返回更新记录数
	 */
	public int update(String hql, Object[] args) {
		return (Integer) execute(new ExecuteCallback(hql, args));
	}

	/**
	 *
	 * 根据 HQL 批删除记录
	 *
	 * @param hql
	 *            HQL 语言
	 * @param args
	 *            传递参数
	 * @return 返回批删除记录数
	 */
	public int delete(String hql, Object[] args) {
		return update(hql, args);
	}

	/**
	 * 返回单实体类hql
	 *
	 * @param select
	 *            查询语句
	 * @param values
	 *            参数
	 * @return
	 */
	public Object findForObject(final String select, final Object[] values) {
		HibernateCallback selectCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery(select);
				if (null != values && 0 < values.length) {
					for (int i = 0, j = values.length; i < j; i++)
						query.setParameter(i, values[i]);
				}
				return query.uniqueResult();
			}
		};
		return execute(selectCallback);
	}

	/**
	 * 获取分页查询
	 *
	 * @param hql
	 *            查询 语句
	 * @return
	 */
	public Page queryForPage(String hql) {
		return queryForPage(hql, null, null);
	}

	/**
	 * 获取分页查询
	 *
	 * @param hql
	 *            查询 语句
	 * @param orderby
	 *            排序
	 * @return
	 */
	public Page queryForPage(String hql, LinkedHashMap<String, String> orderby) {
		return queryForPage(hql, null, orderby);
	}

	/**
	 * 获取分页查询
	 *
	 * @param hql
	 *            查询 语句
	 * @param params
	 *            参数
	 * @param orderby
	 *            排序
	 * @return
	 */
	public Page queryForPage(String hql, Object[] params,
			LinkedHashMap<String, String> orderby) {
		return queryForPage(hql, params, PageContext.getOffset(),
				PageContext.getPageSize(), orderby);
	}

	/**
	 * 获取分页查询
	 *
	 * @param hql
	 *            查询 语句
	 * @param offset
	 *            页码
	 * @param pageSize
	 *            条数
	 * @param orderby
	 *            排序
	 * @return
	 */
	public Page queryForPage(String hql, int offset, int pageSize,
			LinkedHashMap<String, String> orderby) {
		return queryForPage(hql, null, offset, pageSize, orderby);
	}

	/**
	 * 获取分页查询
	 *
	 * @param hql
	 *            查询 语句
	 * @param values
	 *            参数
	 * @param offset
	 *            页码
	 * @param pageSize
	 *            条数
	 * @param orderby
	 *            排序
	 * @return
	 */
	public Page queryForPage(String hql, Object values, int offset,
			int pageSize, LinkedHashMap<String, String> orderby) {
		return queryForPage(hql, new Object[] { values }, offset, pageSize,
				orderby);
	}

	/**
	 * 获取分页查询
	 *
	 * @param select
	 *            查询语句
	 * @param values
	 *            参数
	 * @param pageStart
	 *            页码
	 * @param pageSize
	 *            条数
	 * @param orderby
	 *            排序
	 * @return
	 */
	public Page queryForPage(String select, Object[] values, int pageStart,
			int pageSize, LinkedHashMap<String, String> orderby) {
		Page page = new Page();
		String countHql = getHibernateCountQuery(select);
		Long totalCount = (Long) findForObject(countHql, values);
		page.setTotal(totalCount.intValue());
		page.setItems(queryForList(select, values, pageStart, pageSize, orderby));
		return page;
	}

	/**
	 * 获取查询列表
	 *
	 * @param select
	 *            查询列表语句
	 * @param values
	 *            参数
	 * @param pageStart
	 *            页码
	 * @param pageSize
	 *            条数
	 * @param orderby
	 *            排序
	 * @return
	 */
	public List queryForList(final String select, final Object[] values,
			final int pageStart, final int pageSize,
			final LinkedHashMap<String, String> orderby) {
		HibernateCallback selectCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createQuery(select
						+ buildOrderby(orderby));
				if (null != values) {
					for (int i = 0, j = values.length; i < j; i++)
						query.setParameter(i, values[i]);
				}

				return query
						.setFirstResult(
								0 < pageStart ? (pageStart - 1) * pageSize
										: pageStart).setMaxResults(pageSize)
						.list();
			}
		};
		return (List) execute(selectCallback);
	}

	/**
	 * 获取分页列表hql
	 *
	 * @param selectCount
	 *            查询总页码
	 * @param select
	 *            查询列表
	 * @param values
	 *            参数
	 * @param pageStart
	 *            页码
	 * @param pageSize
	 *            条数
	 * @param orderby
	 *            排序
	 * @return
	 */
	public Page queryForPage(final String selectCount, final String select,
			final Object[] values, int pageStart, final int pageSize,
			final LinkedHashMap<String, String> orderby) {
		Page page = new Page();
		Long totalCount = (Long) findForObject(selectCount, values);
		page.setTotal(totalCount.intValue());
		page.setItems(queryForList(select, values, pageStart, pageSize, orderby));
		return page;
	}

	/**
	 * 获取分页查询sql
	 *
	 * @param sql
	 *            查询 语句
	 * @param args
	 *            参数
	 * @param orderby
	 *            排序
	 * @return
	 */
	public Page sqlQueryForPage(String sql, Map args,
			LinkedHashMap<String, String> orderby) {
		SQLQuery query = getSessionFactory().getCurrentSession().createSQLQuery(sql);
		setParames(args, query);
		Page page = new Page();
		List list = query
				.setFirstResult(
						0 < PageContext.getOffset() ? (PageContext.getOffset() - 1)
								* PageContext.getPageSize()
								: PageContext.getOffset())
				.setMaxResults(PageContext.getPageSize())
				.setResultTransformer(
						(ResultTransformer) new UpperCasedAliasToEntityMapResultTransformer())
				.list();
		long count = countBySql(sql, args);
		page.setTotal((int) count);
		page.setItems(list);
		return page;
	}

	/**
	 * 通过sql查询分页列表
	 *
	 * @param listSql
	 * @param countSql
	 * @param rowMapper
	 * @param orderby
	 * @return
	 */
	public Page sqlQueryForPage(String sql, Object[] params,
			LinkedHashMap<String, String> orderby) {
		return sqlQueryForPage(sql, params, PageContext.getOffset(),
				PageContext.getPageSize(), orderby);
	}

	/**
	 * 通过sql查询分页列表
	 *
	 * @param listSql
	 *            获取列表的SQL
	 * @param countSql
	 *            查询总数的SQL
	 * @param rowMapper
	 *            结果数组转化成对象
	 * @param pagestart
	 * @param pageSize
	 * @param orderby
	 * @return
	 */
	public Page sqlQueryForPage(String sql, Object[] params, int offSet,
			int pageSize, LinkedHashMap<String, String> orderby) {
		// 查询总数
		SQLQuery countQuery = getSessionFactory().getCurrentSession().createSQLQuery(
				getHibernateCountQuery(sql));
		if (null != params && 0 < params.length) {
			for (int i = 0, j = params.length; i < j; i++) {
				countQuery.setParameter(i, params[i]);
			}
		}
		Integer totalCount = 0;
		Object count = countQuery.uniqueResult();
		if (count instanceof BigDecimal) {
			totalCount = null == count ? 0 : ((BigDecimal) count).intValue();
		} else if (count instanceof BigInteger) {
			totalCount = null == count ? 0 : ((BigInteger) count).intValue();
		}
		Page page = new Page();
		page.setTotal(totalCount);
		page.setItems(sqlQueryForList(sql, params, offSet, pageSize, orderby));
		return page;
	}

	/**
	 * 返回实体类sql
	 *
	 * @param select
	 *            查询语句
	 * @param values
	 *            参数
	 * @return
	 */
	public Object sqlFindObject(final String select, final Object[] values) {
		HibernateCallback selectCallback = new HibernateCallback() {
			public Object doInHibernate(Session session) {
				Query query = session.createSQLQuery(select);
				if (null != values) {
					for (int i = 0, j = values.length; i < j; i++)
						query.setParameter(i, values[i]);
				}
				return query.uniqueResult();
			}
		};
		return execute(selectCallback);
	}

	/**
	 * 获取查询列表sql
	 *
	 * @param sql
	 *            查询语句
	 * @param params
	 *            参数
	 * @param orderby
	 *            排序
	 * @return
	 */
	public List<Map<String, Object>> sqlQueryForList(String sql,
			Object[] params, LinkedHashMap<String, String> orderby) {
		return sqlQueryForList(sql, params, PageContext.getOffset(),
				PageContext.getPageSize(), orderby);

	}

	/**
	 * 获取查询列表sql
	 *
	 * @param sql
	 *            查询语句
	 * @param params
	 *            参数
	 * @param offSet
	 *            页码
	 * @param pageSize
	 *            显示条数
	 * @param orderby
	 *            排序
	 * @return
	 */
	public List<Map<String, Object>> sqlQueryForList(String sql,
			Object[] params, Integer offSet, Integer pageSize,
			LinkedHashMap<String, String> orderby) {
		// sql的拼装
		String querySql = null;
		sql += buildOrderby(orderby);
		int firstResult = 0;
		if (0 < offSet) {
			firstResult = (offSet - 1) * pageSize;
		}
		SQLQuery listQuery = getSessionFactory().getCurrentSession().createSQLQuery(sql);
		if (params != null) {
			for (int i = 0, j = params.length; i < j; i++) {
				listQuery.setParameter(i, params[i]);
			}
		}
		List list = listQuery
				.setFirstResult(firstResult)
				.setMaxResults(pageSize)
				.setResultTransformer(
						(ResultTransformer) new UpperCasedAliasToEntityMapResultTransformer())
				.list();
		return list;
	}

	/**
	 * 获取查询列表sql
	 *
	 * @param sql
	 *            查询语句
	 * @param params
	 *            参数数组
	 * @param offSet
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @param orderby
	 *            排序
	 * @param clazz
	 *            返回实体类
	 * @return
	 */
	public List sqlQueryForList(String sql, Object[] params, Integer offSet,
			Integer pageSize, LinkedHashMap<String, String> orderby, Class clazz) {
		// sql的拼装
		String querySql = null;
		sql += buildOrderby(orderby);
		int firstResult = 0;
		if (0 < offSet) {
			firstResult = (offSet - 1) * pageSize;
		}

		SQLQuery listQuery = getSessionFactory().getCurrentSession().createSQLQuery(querySql);
		if (null != params) {
			for (int i = 0, j = params.length; i < j; i++) {
				listQuery.setParameter(i, params[i]);
			}
		}
		if (null == clazz) {
			listQuery
					.setResultTransformer((ResultTransformer) new UpperCasedAliasToEntityMapResultTransformer());
		} else {
			listQuery.setResultTransformer(Transformers.aliasToBean(clazz));
		}
		return listQuery.list();
	}

	/**
	 * 获取查询列表sql
	 *
	 * @param sql
	 *            查询语句
	 * @param params
	 *            参数Map
	 * @param offset
	 *            页码
	 * @param pageSize
	 *            每页条数
	 * @param orderby
	 *            排序
	 * @param clazz
	 *            返回实体类
	 * @return
	 */
	public List sqlQueryForList(String sql, Map params, Integer offset,
			Integer pageSize, LinkedHashMap<String, String> orderby, Class clazz) {
		// sql的拼装
		String querySql = null;
		sql += buildOrderby(orderby);
		int firstResult = 0;
		if (0 < offset) {
			firstResult = (offset - 1) * pageSize;
		}

		SQLQuery listQuery = getSessionFactory().getCurrentSession().createSQLQuery(querySql);
		setParames(params, listQuery);
		if (null == clazz) {
			listQuery
					.setResultTransformer((ResultTransformer) new UpperCasedAliasToEntityMapResultTransformer());
		} else {
			listQuery.setResultTransformer(Transformers.aliasToBean(clazz));
		}
		return listQuery.list();
	}

	/**
	 * 获取总页数
	 *
	 * @param strSql
	 *            查询 语句
	 * @param params
	 *            参数
	 * @return
	 */
	private long countBySql(String strSql, Map params) {
		strSql = "select count(1) as num from (" + strSql + ") t";
		SQLQuery query = getSessionFactory().getCurrentSession().createSQLQuery(strSql);
		setParames(params, query);
		Integer totalCount = 0;
		Object count = query.uniqueResult();
		if (count instanceof BigDecimal) {
			totalCount = null == count ? 0 : ((BigDecimal) count).intValue();
		} else if (count instanceof BigInteger) {
			totalCount = null == count ? 0 : ((BigInteger) count).intValue();
		}
		return totalCount;
	}

	/**
	 * 组装order by语句
	 *
	 * @param orderby
	 * @return
	 */
	protected static String buildOrderby(LinkedHashMap<String, String> orderby) {
		StringBuffer orderbySql = new StringBuffer(" ");
		if (null != orderby && 0 < orderby.size()) {
			orderbySql.append(" order by ");
			for (String key : orderby.keySet()) {
				orderbySql.append(key).append(" ").append(orderby.get(key))
						.append(",");
			}
			orderbySql.deleteCharAt(orderbySql.length() - 1);
		}
		return orderbySql.toString();
	}

	/**
	 * 参数赋值
	 *
	 * @param args
	 * @param query
	 */
	private void setParames(Map args, Query query) {
		if (null != args && 0 < args.size()) {
			Set keySet = args.keySet();
			Iterator it = keySet.iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = (String) args.get(key);
				query.setParameter(key, value);
			}
		}
	}

	/**
	 * 返回对象定义
	 *
	 * @author huangxin
	 *
	 */
	private static class UpperCasedAliasToEntityMapResultTransformer extends
			BasicTransformerAdapter implements Serializable {
		@SuppressWarnings("unchecked")
		public Object transformTuple(Object[] tuple, String[] aliases) {
			Map result = new HashMap(tuple.length);
			for (int i = 0, j = tuple.length; i < j; i++) {
				String alias = aliases[i];
				if (null != alias) {
					result.put(alias.toUpperCase(), tuple[i]);
				}
			}
			return result;
		}
	}

	/**
	 * 截取总条数语句
	 *
	 * @param hql
	 * @return
	 */
	private String getHibernateCountQuery(String hql) {
		hql = StringUtils.defaultIfEmpty(hql, "");
		// hql = hql.toLowerCase();
		int index = hql.indexOf("from");
		if (-1 != index) {
			return "select count(1) " + hql.substring(index);
		}
		throw new RuntimeErrorException(null, "无效的SQL语句");
	}

	/**
	 * 回调内部类定义
	 *
	 * @author huangxin
	 *
	 */
	private class ExecuteCallback implements HibernateCallback {
		ExecuteCallback(String hql, Object[] args) {
			this.hql = hql;
			this.args = args;
		}

		private String hql;
		private Object[] args;

		public Object[] getArgs() {
			return args;
		}

		public void setArgs(Object[] args) {
			this.args = args;
		}

		public String getHql() {
			return hql;
		}

		public void setHql(String hql) {
			this.hql = hql;
		}

		private int getParameterCount(String hql) {
			int count = 0;
			if (null != hql) {
				StringBuffer buffer = new StringBuffer(hql);
				int pos = -1;
				while (-1 != (pos = buffer.indexOf("?"))) {
					buffer.delete(0, pos + 1);
					count++;
				}
			}
			return count;
		}
        @Override
		public Object doInHibernate(Session session) throws HibernateException {
			if (null == hql)
				throw new RuntimeException("nullable statement");

			int paramCount = 0;
			if (0 < (paramCount = getParameterCount(hql))) {
				if (null == args || args.length != paramCount)
					throw new RuntimeException("nullable arguments");
			}
			Query query = session.createQuery(hql);

			if (null != args) {
				for (int i = 0, j = args.length; i < j; i++) {
					query.setParameter(i, args[i]);
				}
			}
			return query.executeUpdate();
		}
	}
}