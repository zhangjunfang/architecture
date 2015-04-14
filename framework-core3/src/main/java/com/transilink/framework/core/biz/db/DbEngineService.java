package com.transilink.framework.core.biz.db;

import java.util.List;
import java.util.Set;

import com.transilink.framework.core.dao.db.PagingResultSet;
import com.transilink.framework.core.exception.BaseException;
import com.transilink.framework.core.logs.LogEnabled;
import com.transilink.framework.core.model.dbmeta.Container;
import com.transilink.framework.core.model.dbmeta.DBTable;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@SuppressWarnings("rawtypes")
public abstract interface DbEngineService extends LogEnabled {
	
	public abstract List query(String paramString, Class paramClass,
			boolean paramBoolean) throws BaseException;

	public abstract PagingResultSet getPagingResultSet(String paramString,
			Object[] paramArrayOfObject, Class paramClass, int paramInt1,
			int paramInt2) throws BaseException;

	public abstract List query(String paramString, Object[] paramArrayOfObject,
			Class paramClass, int paramInt1, int paramInt2)
			throws BaseException;

	public abstract List query(String paramString, Object[] paramArrayOfObject,
			Class paramClass, boolean paramBoolean);

	public abstract int execute(String paramString, Object[] paramArrayOfObject);

	public abstract boolean checkRecordExist(String paramString1,
			String paramString2, Object paramObject, String paramString3,
			Object[] paramArrayOfObject);

	public abstract boolean checkRecordExist(Class paramClass,
			String paramString1, Object paramObject, String paramString2,
			Object[] paramArrayOfObject);

	public abstract Container getDBContainer(String paramString1,
			String paramString2);

	public abstract DBTable getDBTable(String paramString);

	public abstract Set<String> getTableList(String paramString1,
			String paramString2);

	public abstract List<DBTable> getDBTables(String paramString1,
			String paramString2);
}