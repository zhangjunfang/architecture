package com.transilink.framework.core.utils.clazzUtils;

import java.util.List;
import java.util.Set;

import com.transilink.framework.core.biz.db.DbEngineService;
import com.transilink.framework.core.dao.db.PagingResultSet;
import com.transilink.framework.core.exception.BaseException;
import com.transilink.framework.core.model.dbmeta.Container;
import com.transilink.framework.core.model.dbmeta.DBTable;
import com.transilink.framework.core.utils.MathUtils.MathUtil;
import com.transilink.framework.core.utils.pagesUtils.PageContext;
import com.transilink.framework.core.utils.springUtils.SpringConext;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@SuppressWarnings("all")
public class DBUtil {

	public static final int SQL_SELECT = 1;

	public static final int SQL_INSERT = 2;

	public static final int SQL_UPDATE = 3;

	static DbEngineService iDbEngineService = SpringConext
			.getApplicationContext().getBean(DbEngineService.class);

	/**
	 * 将SQL中的值设置到Model中
	 * <p>
	 * 应用实例：
	 * </p>
	 *
	 * @param querySQL
	 * @param modelClass
	 * @param params
	 *            传递参数
	 * @return
	 * @throws Exception
	 */
	public static int execute(String querySQL, Object[] params)
			throws BaseException {
		return iDbEngineService.execute(querySQL, params);
	}

	/**
	 * 将SQL中的值设置到Model中
	 * <p>
	 * 应用实例：
	 * </p>
	 *
	 * @param querySQL
	 * @param modelClass
	 * @param params
	 *            传递参数
	 * @return
	 * @throws Exception
	 */
	public static int execute(String querySQL, Object params)
			throws BaseException {
		return iDbEngineService.execute(querySQL, new Object[] { params });

	}

	/**
	 * 将SQL中的值设置到Model中
	 * <p>
	 * 应用实例：
	 * </p>
	 *
	 * @param querySQL
	 * @param modelClass
	 * @return
	 * @throws Exception
	 */
	public static int execute(String querySQL) throws BaseException {
		return iDbEngineService.execute(querySQL, null);
	}

	/**
	 * 将SQL中的值设置到Model中，放入ArrayList中返回
	 * <p>
	 * 应用实例：
	 * </p>
	 *
	 * @param querySQL
	 * @param modelClass
	 * @param params
	 *            传递参数
	 * @return
	 * @throws Exception
	 */
	public static List query(String querySQL, Object params, Class modelClass,
			int pageSize, int pageIndex) throws BaseException {

		return iDbEngineService.query(querySQL.toString(),
				new Object[] { params }, modelClass, pageSize, pageIndex);
	}

	/**
	 * 将SQL中的值设置到Model中，放入ArrayList中返回
	 * <p>
	 * 应用实例：
	 * </p>
	 *
	 * @param querySQL
	 * @param modelClass
	 * @param params
	 *            传递参数
	 * @return
	 * @throws Exception
	 */
	public static List query(String querySQL, Class modelClass, int pageSize,
			int pageIndex) throws BaseException {

		return iDbEngineService.query(querySQL.toString(), null, modelClass,
				pageSize, pageIndex);
	}

	public static List query(String querySQL, Object params, Class modelClass)
			throws BaseException {
		return iDbEngineService.query(querySQL.toString(),
				new Object[] { params }, modelClass, PageContext.getPageSize(),
				PageContext.getOffset());
	}

	public static List query(String querySQL, Class modelClass)
			throws BaseException {
		return iDbEngineService.query(querySQL.toString(), null, modelClass,
				PageContext.getPageSize(), PageContext.getOffset());
	}

	/**
	 * 数据库查询
	 *
	 * @param querySQL
	 *            查询sql
	 * @param modelClass
	 *            返回对象的的类型
	 * @param params
	 *            查询参数
	 * @param pageSize
	 *            每页页数
	 * @param pageIndex
	 *            第几页
	 * @return
	 * @throws BaseException
	 */
	public static PagingResultSet getPagingResultSet(final String querySQL,
			Object[] params, final Class modelClass, int pageSize, int pageIndex)
			throws BaseException {
		if (pageIndex == 0) {
			pageIndex = 1;
		}
		return iDbEngineService.getPagingResultSet(querySQL, params,
				modelClass, pageSize, pageIndex);

	}

	/**
	 * 数据库查询
	 *
	 * @param querySQL
	 *            查询sql
	 * @param modelClass
	 *            返回对象的的类型
	 * @param params
	 *            查询参数
	 * @param pageSize
	 *            每页页数
	 * @param pageIndex
	 *            第几页
	 * @return
	 * @throws BaseException
	 */
	public static PagingResultSet getPagingResultSet(final String querySQL,
			final Class modelClass, int pageSize, int pageIndex)
			throws BaseException {
		return getPagingResultSet(querySQL, null, modelClass, pageSize,
				pageIndex);

	}

	/**
	 * 数据库查询
	 *
	 * @param querySQL
	 *            查询sql
	 * @param modelClass
	 *            返回对象的的类型
	 * @param params
	 *            查询参数
	 * @param pageSize
	 *            每页页数
	 * @param pageIndex
	 *            第几页
	 * @return
	 * @throws BaseException
	 */
	public static PagingResultSet getPagingResultSet(final String querySQL,
			Object param, final Class modelClass, int pageSize, int pageIndex)
			throws BaseException {

		return getPagingResultSet(querySQL, new Object[] { param }, modelClass,
				pageSize, pageIndex);

	}

	/**
	 * 数据库查询
	 *
	 * @param querySQL
	 *            查询sql
	 * @param modelClass
	 *            返回对象的的类型
	 * @param params
	 *            查询参数
	 * @param pageSize
	 *            每页页数
	 * @param pageIndex
	 *            第几页
	 * @return
	 * @throws BaseException
	 */
	public static PagingResultSet getPagingResultSet(final String querySQL,
			Object[] params, final Class modelClass, int pageSize,
			String pageIndex) throws BaseException {
		int pageIndexNumber = MathUtil.getInteger(pageIndex);
		return getPagingResultSet(querySQL, params, modelClass, pageSize,
				pageIndexNumber);

	}

	/**
	 * 数据库查询
	 *
	 * @param querySQL
	 *            查询sql
	 * @param modelClass
	 *            返回对象的的类型
	 * @param pageSize
	 *            每页页数
	 * @param pageIndex
	 *            第几页
	 * @return
	 * @throws BaseException
	 */
	public static PagingResultSet getPagingResultSet(final String querySQL,
			final Class modelClass, int pageSize, String pageIndex)
			throws BaseException {
		return getPagingResultSet(querySQL, null, modelClass, pageSize,
				pageIndex);

	}

	/**
	 * 数据库查询
	 *
	 * @param querySQL
	 *            查询sql
	 * @param modelClass
	 *            返回对象的的类型
	 * @param param
	 *            参数
	 * @param pageSize
	 *            每页页数
	 * @param pageIndex
	 *            第几页
	 * @return
	 * @throws BaseException
	 */
	public static PagingResultSet getPagingResultSet(final String querySQL,
			Object param, final Class modelClass, int pageSize, String pageIndex)
			throws BaseException {
		return getPagingResultSet(querySQL, new Object[] { param }, modelClass,
				pageSize, pageIndex);

	}

	public static PagingResultSet getPagingResultSet(String querySQL,
			Object param, Class modelClass) throws BaseException {
		return getPagingResultSet(querySQL, new Object[] { param }, modelClass,
				PageContext.getPageSize(), PageContext.getOffset());

	}

	public static PagingResultSet getPagingResultSet(String querySQL,
			Class modelClass) throws BaseException {
		return getPagingResultSet(querySQL, null, modelClass);

	}

	/**
	 * 校验记录是否存在
	 *
	 * @param tableName
	 * @param field
	 * @param value
	 * @param extraSql
	 * @return
	 */
	public static boolean checkRecordExist(String tableName, String field,
			Object value, String extraSql) {
		return iDbEngineService.checkRecordExist(tableName, field, value,
				extraSql, null);
	}

	/**
	 * 校验记录是否存在
	 *
	 * @param tableName
	 * @param field
	 * @param value
	 * @param extraSql
	 *            扩展sql
	 * @param extraParams
	 *            扩展查询sql的查询参数
	 * @return
	 */
	public static boolean checkRecordExist(String tableName, String field,
			Object value, String extraSql, Object[] extraParams) {
		return iDbEngineService.checkRecordExist(tableName, field, value,
				extraSql, extraParams);
	}

	/**
	 * 校验记录是否存在
	 *
	 * @param tableName
	 * @param field
	 * @param value
	 * @param extraParam
	 * @return
	 */
	public static boolean checkRecordExist(String tableName, String field,
			Object value) {
		return iDbEngineService.checkRecordExist(tableName, field, value, null,
				null);
	}

	/**
	 * 利用hibernate hql 校验记录是否存在
	 *
	 * @param entityClass
	 *            实体类型
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @param extraSql
	 *            扩展查询hql
	 * @return
	 */
	public static boolean checkRecordExist(Class entityClass,
			String propertyName, Object value, String extraHql,
			Object[] extraParams) {
		return iDbEngineService.checkRecordExist(entityClass, propertyName,
				value, extraHql, extraParams);
	}

	/**
	 * 利用hibernate hql 校验记录是否存在
	 *
	 * @param entityClass
	 *            实体类型
	 * @param propertyName
	 *            属性名称
	 * @param value
	 *            属性值
	 * @param extraSql
	 * @return
	 */
	public static boolean checkRecordExist(Class entityClass,
			String propertyName, Object value, String extraHql) {
		return iDbEngineService.checkRecordExist(entityClass, propertyName,
				value, extraHql, null);
	}

	/**
	 * 利用hibernate hql 校验记录是否存在
	 *
	 * @param entityClass
	 *            实体类型
	 * @param property
	 *            属性名称
	 * @param value
	 *            属性值
	 * @param extraParam
	 * @return
	 */
	public static boolean checkRecordExist(Class entityClass, String property,
			Object value) {
		return iDbEngineService.checkRecordExist(entityClass, property, value,
				null, null);
	}

	/**
	 * 读取数据库完整配置
	 *
	 * @param schemaPattern
	 *            schema
	 * @param tablePattern
	 * @return
	 */
	public static Container getDBContainer(String schemaPattern,
			String tablePattern) {

		return iDbEngineService.getDBContainer(schemaPattern, tablePattern);
	}

	/**
	 * 获取表定义
	 *
	 * @param schemaPattern
	 *            schema
	 * @param tablePattern
	 * @return
	 */
	public static List<DBTable> getDBTables(String schemaPattern,
			String tablePattern) {
		return iDbEngineService.getDBTables(schemaPattern, tablePattern);
	}

	/**
	 * 获取表定义
	 *
	 * @param tableName
	 *            表名
	 * @return
	 */
	public static DBTable getDBTable(String tableName) {
		return iDbEngineService.getDBTable(tableName);
	}

	/**
	 * 获取表列表
	 *
	 * @param schemaPattern
	 * @param tablePattern
	 * @return
	 */
	public static Set<String> getTableList(String schemaPattern,
			String tablePattern) {
		return iDbEngineService.getTableList(schemaPattern, tablePattern);
	}
}