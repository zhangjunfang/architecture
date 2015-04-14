package com.transilink.framework.core.dao.db;

import java.util.Map;

import com.transilink.framework.core.model.variant.VariantSet;
import com.transilink.framework.core.utils.MathUtils.MathUtil;
import com.transilink.framework.core.utils.pagesUtils.PageContext;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@SuppressWarnings("all")
public class SqlDataset extends DbDataset {

	private static final long serialVersionUID = 5599490702037660560L;

	public SqlDataset() {

	}

	/**
	 * 构造方法.
	 *
	 * @param sql
	 *            查询sql
	 * @param params
	 *            查询参数.
	 * @param supportPaging
	 *            是否需要分页，默认为true.
	 * @param start
	 *            请求的开始记录条数,默认为0.
	 * @param pageSize
	 *            每页记录数，默认为20条.
	 */
	public SqlDataset(String sql, ParameterSet params, boolean supportPaging,
			int start, int pageSize) {

		super(sql, params, supportPaging, 1, pageSize);
		this.setStart(start);
	}

	/**
	 * 构造方法.
	 *
	 * @param sql
	 *            查询sql
	 * @param params
	 *            查询参数.
	 * @param supportPaging
	 *            是否需要分页.
	 * @param start
	 *            请求的开始记录条数,默认为0.
	 * @param pageSize
	 *            每页记录数，默认为20.
	 */
	public SqlDataset(String sql, ParameterSet params, boolean supportPaging,
			String start, String pageSize) {
		this(sql, params, supportPaging, MathUtil.getInteger(
				start == null ? "0" : start).intValue(), MathUtil.getInteger(
				pageSize).intValue());
	}

	/**
	 * 构造方法.
	 *
	 * @param sql
	 *            查询sql
	 * @param params
	 *            查询参数.
	 * @param start
	 *            请求的开始记录条数,默认为0.
	 */
	public SqlDataset(String sql, ParameterSet params) {
		this(sql, params, true, PageContext.getOffset(), PageContext
				.getPageSize());
	}

	/**
	 * 构造方法.
	 *
	 * @param sql
	 *            查询sql
	 */
	public SqlDataset(String sql) {
		this(sql, null, true, PageContext.getOffset(), PageContext
				.getPageSize());
	}

	/**
	 * 构造方法.
	 *
	 * @param sql
	 *            查询sql
	 */
	public SqlDataset(StringBuffer sql) {
		this(sql.toString());
	}

	private int start;

	public int getStart() {
		return start;
	}

	/**
	 * 设置用于获取分页数据的开始数据行号
	 *
	 * @param start
	 */
	public SqlDataset setStart(int start) {
		this.start = start;
		return this;
	}

	/**
	 * 设置用于获取分页数据的开始数据行号
	 *
	 * @param start
	 */
	public SqlDataset setStart(String start) {
		if (start == null) {
			this.setStart(0);
			return this;
		}
		this.setStart(MathUtil.getInteger(start).intValue());
		return this;

	}

	/*
	 *
	 * @return
	 */

	@Override
	public SqlDataset loadData() {
		// TODO Auto-generated method stub
		this.setPageSize(PageContext.getPageSize());

		// if (this.getPageSize() == 0) {
		// this.setPageSize(DEFAULT_PAGING_SIZE);
		// }

		this.setPageIndex(PageContext.getOffset());

		return (SqlDataset) super.loadData();
	}

	/*
	 *
	 * @param pageSize
	 *
	 * @return
	 *
	 * @see com.newstar.base.db.DbDataset#setPageSize(int)
	 */

	@Override
	public SqlDataset setPageSize(int pageSize) {
		// TODO Auto-generated method stub
		return (SqlDataset) super.setPageSize(pageSize);
	}

	/*
	 *
	 * @param pageSize
	 *
	 * @return
	 *
	 * @see com.newstar.base.db.DbDataset#setPageSize(java.lang.String)
	 */

	@Override
	public SqlDataset setPageSize(String pageSize) {
		// TODO Auto-generated method stub
		return (SqlDataset) super.setPageSize(pageSize);
	}

	/*
	 *
	 * @param name
	 *
	 * @param value
	 *
	 * @return
	 *
	 * @see com.newstar.base.db.DbDataset#setParameter(java.lang.String,
	 * java.lang.Object)
	 */

	@Override
	public SqlDataset setParameter(String name, Object value) {
		// TODO Auto-generated method stub
		return (SqlDataset) super.setParameter(name, value);
	}

	/*
	 *
	 * @param sql
	 *
	 * @return
	 *
	 * @see com.newstar.base.db.DbDataset#setSql(java.lang.String)
	 */

	@Override
	public SqlDataset setSql(String sql) {
		// TODO Auto-generated method stub
		return (SqlDataset) super.setSql(sql);
	}

	/*
	 *
	 * @param sql
	 *
	 * @return
	 *
	 * @see com.newstar.base.db.DbDataset#setSql(java.lang.StringBuffer)
	 */

	@Override
	public SqlDataset setSql(StringBuffer sql) {
		// TODO Auto-generated method stub
		return (SqlDataset) super.setSql(sql);
	}

	/*
	 *
	 * @param map
	 *
	 * @param names
	 *
	 * @return
	 *
	 * @see com.newstar.base.db.DbDataset#setParameters(java.util.Map,
	 * java.lang.String[])
	 */

	@Override
	public SqlDataset setParameters(Map map, String[] names) {
		// TODO Auto-generated method stub
		return (SqlDataset) super.setParameters(map, names);
	}

	/*
	 *
	 * @param map
	 *
	 * @return
	 *
	 * @see com.newstar.base.db.DbDataset#setParameters(java.util.Map)
	 */

	@Override
	public SqlDataset setParameters(Map map) {
		// TODO Auto-generated method stub
		return (SqlDataset) super.setParameters(map);
	}

	/*
	 *
	 * @param obj
	 *
	 * @param names
	 *
	 * @return
	 *
	 * @see com.newstar.base.db.DbDataset#setParameters(java.lang.Object,
	 * java.lang.String[])
	 */

	@Override
	public SqlDataset setParameters(Object obj, String[] names) {
		// TODO Auto-generated method stub
		return (SqlDataset) super.setParameters(obj, names);
	}

	/*
	 *
	 * @param obj
	 *
	 * @return
	 *
	 * @see com.newstar.base.db.DbDataset#setParameters(java.lang.Object)
	 */

	@Override
	public SqlDataset setParameters(Object obj) {
		// TODO Auto-generated method stub
		return (SqlDataset) super.setParameters(obj);
	}

	/*
	 *
	 * @param variantSet
	 *
	 * @param names
	 *
	 * @return
	 *
	 * @see
	 * com.newstar.base.db.DbDataset#setParameters(com.newstar.util.variant.
	 * VariantSet, java.lang.String[])
	 */

	@Override
	public SqlDataset setParameters(VariantSet variantSet, String[] names) {
		// TODO Auto-generated method stub
		return (SqlDataset) super.setParameters(variantSet, names);
	}

	/*
	 *
	 * @param variantSet
	 *
	 * @return
	 *
	 * @see
	 * com.newstar.base.db.DbDataset#setParameters(com.newstar.util.variant.
	 * VariantSet)
	 */

	@Override
	public SqlDataset setParameters(VariantSet variantSet) {
		// TODO Auto-generated method stub
		return (SqlDataset) super.setParameters(variantSet);
	}

	/*
	 *
	 * @param clazz
	 *
	 * @return
	 *
	 * @see com.newstar.base.db.DbDataset#setClazz(java.lang.Class)
	 */

	@Override
	public SqlDataset setClazz(Class clazz) {
		// TODO Auto-generated method stub
		return (SqlDataset) super.setClazz(clazz);
	}

	/*
	 *
	 * @param supportPaging
	 *
	 * @return
	 *
	 * @see com.newstar.base.db.DbDataset#setSupportPaging(boolean)
	 */

	@Override
	public SqlDataset setSupportPaging(boolean supportPaging) {
		// TODO Auto-generated method stub
		return (SqlDataset) super.setSupportPaging(supportPaging);
	}

}
