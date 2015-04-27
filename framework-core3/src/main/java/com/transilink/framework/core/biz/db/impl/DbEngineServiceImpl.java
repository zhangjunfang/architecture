package com.transilink.framework.core.biz.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Service;

import com.transilink.framework.core.biz.db.DbEngineService;
import com.transilink.framework.core.dao.db.PagingResultSet;
import com.transilink.framework.core.exception.BaseException;
import com.transilink.framework.core.logs.LogEnabled;
import com.transilink.framework.core.model.dbmeta.Container;
import com.transilink.framework.core.model.dbmeta.DBTable;
import com.transilink.framework.core.model.dbmeta.DbContainerHelper;
import com.transilink.framework.core.utils.clazzUtils.BeanUtils;
import com.transilink.framework.core.utils.clazzUtils.DBUtil;
import com.transilink.framework.core.utils.stringUtils.StringUtil;

/**
 * 数据库SQL查询服务
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@Service("dbEngineService")
@SuppressWarnings("all")
public class DbEngineServiceImpl implements DbEngineService, LogEnabled {

	@Resource(name = "sessionFactory")
	SessionFactory sessionFactory;

	HibernateTemplate hibernateTemplate;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public HibernateTemplate getHibernateTemplate() {
		if (this.hibernateTemplate == null) {
			this.hibernateTemplate = new HibernateTemplate(getSessionFactory());
		}
		return this.hibernateTemplate;

	}

	public List query(final String querySQL, final Class modelClass,
			final boolean convertFieldName) throws BaseException {
		List model = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = SessionFactoryUtils.getDataSource(this.getSessionFactory())
					.getConnection();
			// 选择内容
			pstmt = conn.prepareStatement(querySQL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				model.add(BeanUtils.resultSet(rs, modelClass,
						convertFieldName));
			}

		} catch (Exception ex) {

			if (BaseException.class.isInstance(ex))
				throw (BaseException) ex;
			else
				throw new BaseException("查询失败！", ex); // query failed

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex1) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
			}

		}
		return model;

	}

	private int getParameterCount(String querySQL) {
		int count = 0;

		if (querySQL != null) {
			StringBuffer buffer = new StringBuffer(querySQL);
			int pos = -1;
			while ((pos = buffer.indexOf("?")) != -1) {
				buffer.delete(0, pos + 1);
				count++;
			}
		}
		return count;
	}

	/**
	 * 将SQL中的值设置到Model中，放入List中返回
	 *
	 * @param querySQL
	 *            查询sql
	 * @param modelClass
	 *            返回对象类型
	 * @param params
	 *            传递参数
	 * @return
	 * @throws Exception
	 */
	public List query(final String querySQL, final Object[] params,
			final Class modelClass, final boolean convertFieldName) {

		List model = new ArrayList();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = SessionFactoryUtils.getDataSource(this.getSessionFactory())
					.getConnection();

			// 选择内容
			pstmt = conn.prepareStatement(querySQL);
			// 获取参数
			int count = getParameterCount(querySQL);
			if (count > 0) {
				if (params == null || params.length != count) {
					throw new BaseException("SQL 参数传递错误！");
				}

			}
			for (int i = 0; i < count; i++) {

				pstmt.setObject(i + 1, params[i]);
			}

			rs = pstmt.executeQuery();

			while (rs.next()) {
				model.add(BeanUtils.resultSet(rs, modelClass,
						convertFieldName));
			}

		} catch (Exception ex) {

			if (BaseException.class.isInstance(ex))
				throw (BaseException) ex;
			else
				throw new BaseException("query failed！", ex); // query failed

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception ex1) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
			}
		}
		return model;

	}

	/**
	 * 动态执行sql
	 */
	public int execute(final String querySQL, final Object[] params) {
		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session sess)
							throws HibernateException, SQLException {
						PreparedStatement pstmt = null;
						ResultSet rs = null;
						try {
							Connection conn = sess.connection();
							// 选择内容
							pstmt = conn.prepareStatement(querySQL);
							// 获取参数
							int count = getParameterCount(querySQL);
							if (count > 0) {
								if (params == null || params.length != count) {
									throw new BaseException("SQL 参数传递错误！");
								}

							}
							for (int i = 0; i < count; i++) {
								pstmt.setObject(i + 1, params[i]);
							}
							pstmt.execute();
							return 1;

						} catch (Exception ex) {

							if (BaseException.class.isInstance(ex))
								throw (BaseException) ex;
							else
								throw new BaseException("执行SQL失败！", ex); // query
																			// failed

						} finally {
							try {
								if (rs != null)
									rs.close();
							} catch (Exception ex1) {
							}
							try {
								if (pstmt != null)
									pstmt.close();
							} catch (Exception ex) {
							}

						}

					}

				});

	}

	/**
	 *
	 * 获取当前操作的表名称
	 *
	 * @param sql
	 *            动态执行SQL
	 * @param type
	 * @return
	 */
	public String getTableName(String sql, int type) {
		String strOut = "";
		sql = sql.toLowerCase();
		int pos = -1;
		switch (type) {
		case DBUtil.SQL_SELECT:
			pos = sql.indexOf(" from ");
			if (pos >= 0) {
				strOut = sql.substring(pos + 6);
				strOut = strOut.trim();
				pos = strOut.indexOf(" ");
				if (pos > 0) {
					strOut = strOut.substring(0, pos).trim();
				} else {
					// err
				}
			}
			break;
		case DBUtil.SQL_INSERT:
			pos = sql.indexOf(" into ");
			if (pos >= 0) {
				strOut = sql.substring(pos + 6);
				strOut = strOut.trim();

				pos = strOut.indexOf("(");
				if (pos > 0) {
					strOut = strOut.substring(0, pos).trim();
				} else {
					// err
				}
			}
			break;
		case DBUtil.SQL_UPDATE:
			pos = sql.indexOf("update ");
			if (pos >= 0) {
				strOut = sql.substring(pos + 7);
				strOut = strOut.trim();
				pos = strOut.indexOf(" ");
				if (pos > 0) {
					strOut = strOut.substring(0, pos).trim();
				} else {
					// err
				}
			}
			break;

		}
		return strOut;
	}

	/**
	 * 获取页数
	 */

	public List query(final String querySQL, final Object[] params,
			final Class modelClass, final int pageSize, final int pageIndex)
			throws BaseException {

		PagingResultSet pagingResultSet = getPagingResultSet(querySQL, params,
				modelClass, pageSize, pageIndex);
		if (pagingResultSet == null) {
			return null;
		}
		return pagingResultSet.getData();

	}

	/**
	 * 获取分页的结果
	 */
	public PagingResultSet getPagingResultSet(final String querySQL,
			final Object[] params, final Class modelClass, final int pageSize,
			final int pageIndex) throws BaseException {

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		try {
			conn = SessionFactoryUtils.getDataSource(this.getSessionFactory())
					.getConnection();
			// 选择内容
			// 获取参数
			pstmt = conn.prepareStatement(querySQL);
			int count = getParameterCount(querySQL);
			if (count > 0) {
				if (params == null || params.length != count) {
					throw new BaseException("SQL 参数传递错误！");
				}

				for (int i = 0; i < count; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			rs = pstmt.executeQuery();
			PagingResultSet pagingResultSet = new PagingResultSet(rs,
					modelClass);

			pagingResultSet.tranlateData(pageSize, pageIndex);
			return pagingResultSet;

		} catch (Exception ex) {

			log.error(ExceptionUtils.getFullStackTrace(ex));
			if (BaseException.class.isInstance(ex))
				throw (BaseException) ex;
			else
				throw new BaseException("查询失败！", ex); // query failed

		} finally {
			try {
				if (rs != null)
					rs.close();

			} catch (Exception ex1) {
			}
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (Exception ex) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * 校验记录是否存在
	 */
	public boolean checkRecordExist(final String tableName, final String field,
			final Object value, final String extraSql, final Object[] params) {

		return (Boolean) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session sess)
							throws HibernateException, SQLException {
						boolean flag = false;
						PreparedStatement pstmt = null;
						ResultSet rs = null;
						try {
							String sqlWhere, sql;
							sqlWhere = extraSql;
							if (StringUtil.isEmpty(sqlWhere)) {
								sqlWhere = field + " = ? ";
							} else {
								sqlWhere += " and " + field + " = ? ";
							}

							sql = "select distinct 1  from " + tableName
									+ " where  " + sqlWhere;

							Connection conn = sess.connection();
							// 选择内容
							pstmt = conn.prepareStatement(sql);
							// 处理参数
							int count = getParameterCount(sql);
							List paramList = new ArrayList();
							if (params != null) {
								for (int i = 0, len = params.length; i < len; i++) {
									paramList.add(params[i]);
								}
							}

							paramList.add(value);

							if (count > 0) {
								if (paramList == null
										|| paramList.size() != count) {
									throw new BaseException("SQL 参数传递错误！");
								}

							}
							for (int i = 0; i < count; i++) {
								pstmt.setObject(i + 1, paramList.get(i));
							}
							rs = pstmt.executeQuery();
							flag = rs.next();
							return flag;

						} catch (Exception ex) {

							if (BaseException.class.isInstance(ex))
								throw (BaseException) ex;
							else
								throw new BaseException("校验记录是否存在出错！", ex); // query
																			// failed

						} finally {
							try {
								if (rs != null)
									rs.close();
							} catch (Exception ex1) {
							}
							try {
								if (pstmt != null)
									pstmt.close();
							} catch (Exception ex) {
							}
						}

					}

				});
	}

	/**
	 * 校验记录是否存在
	 */
	public boolean checkRecordExist(final Class entityClass,
			final String propertyName, final Object value,
			final String extraHql, final Object[] params) {

		try {

			String hqlWhere, hql;
			hqlWhere = extraHql;
			if (StringUtil.isEmpty(extraHql)) {
				hqlWhere = propertyName + " = ? ";
			} else {
				hqlWhere += " and " + propertyName + " = ? ";
			}

			hql = "select count(*)  from " + entityClass.getName() + " where  "
					+ hqlWhere;
			// 处理参数
			int count = getParameterCount(hql);
			List paramList = new ArrayList();
			if (params != null) {
				for (int i = 0, len = params.length; i < len; i++) {
					paramList.add(params[i]);
				}
			}

			paramList.add(value);

			if (count > 0) {
				if (paramList == null || paramList.size() != count) {
					throw new BaseException("查询 HQL 参数传递错误！");
				}

			}

			List list = getHibernateTemplate().find(hql.toString(),
					paramList.toArray());

			count = 0;

			if (list != null && list.size() > 0) {

				count = Integer.parseInt(list.get(0).toString());
			}

			return (count > 0 ? true : false);

		} catch (Exception ex) {

			if (BaseException.class.isInstance(ex))
				throw (BaseException) ex;
			else
				throw new BaseException("校验记录是否存在出错！", ex); // query failed

		}

	}

	/*
	 *
	 * @param schemaPattern @param tablePattern @return
	 *
	 * @see
	 * com.newstar.sys.service.IDbEngineService#getDBContainer(java.lang.String,
	 * java.lang.String)
	 */

	public Container getDBContainer(final String schemaPattern,
			final String tablePattern) {

		return (Container) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session sess)
							throws HibernateException, SQLException {

						return DbContainerHelper.getDBContainer(schemaPattern,
								tablePattern, sess.connection());
					}
				});
	}

	/*
	 *
	 * @param tableName @return
	 *
	 * @see
	 * com.newstar.sys.service.IDbEngineService#getDBTable(java.lang.String)
	 */

	public DBTable getDBTable(final String tableName) {

		return (DBTable) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session sess)
							throws HibernateException, SQLException {

						return DbContainerHelper.getDBTable(tableName,
								sess.connection());
					}
				});
	}

	/*
	 *
	 * @param schemaPattern @param tablePattern @return
	 *
	 * @see
	 * com.newstar.sys.service.IDbEngineService#getDBTables(java.lang.String,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<DBTable> getDBTables(final String schemaPattern,
			final String tablePattern) {

		return (List<DBTable>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session sess)
							throws HibernateException, SQLException {

						return DbContainerHelper.getDBTables(schemaPattern,
								tablePattern, sess.connection());
					}
				});

	}

	/*
	 *
	 * @param schemaPattern @param tablePattern @return
	 *
	 * @see
	 * com.newstar.sys.service.IDbEngineService#getTableList(java.lang.String,
	 * java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Set<String> getTableList(final String schemaPattern,
			final String tablePattern) {

		return (Set<String>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session sess)
							throws HibernateException, SQLException {

						return DbContainerHelper.getTableList(schemaPattern,
								tablePattern, sess.connection());
					}
				});

	}

}
