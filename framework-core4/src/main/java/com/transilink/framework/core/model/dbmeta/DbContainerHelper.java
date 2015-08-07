package com.transilink.framework.core.model.dbmeta;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.transilink.framework.core.exception.BaseException;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@SuppressWarnings("all")
public final class DbContainerHelper {
	/**
	 * prevent initialization
	 *
	 */
	private DbContainerHelper() {

	}

	/**
	 * 读取数据库完整定义
	 *
	 * @param schemaPattern
	 *            schema
	 * @param tablePattern
	 * @param conn
	 * @return
	 */
	public static Container getDBContainer(String schemaPattern,
			String tablePattern, Connection conn) {
		try {
			Container container = new Container();
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			MetaDataRetriever.getTables(container, databaseMetaData,
					schemaPattern, tablePattern);
			MetaDataRetriever.populateTableData(databaseMetaData,
					schemaPattern, container);
			return container;

		} catch (Exception ex) {

			throw new BaseException("读取数据库配置信息失败！", ex);
		}
	}

	/**
	 * 获取表集合定义
	 *
	 * @param schemaPattern
	 *            schema
	 * @param tablePattern
	 * @param conn
	 * @return
	 */
	public static List<DBTable> getDBTables(String schemaPattern,
			String tablePattern, Connection conn) {
		try {

			Container container = new Container();
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			MetaDataRetriever.getTables(container, databaseMetaData,
					schemaPattern, tablePattern);
			if (container.getTables() == null
					|| container.getTables().isEmpty()) {
				return null;
			}
			MetaDataRetriever.populateTableData(databaseMetaData,
					schemaPattern, container);
			List<DBTable> tables = new ArrayList<DBTable>();
			for (Iterator<Map.Entry> iterator = container.getTables()
					.entrySet().iterator(); iterator.hasNext();) {
				tables.add((DBTable) iterator.next().getValue());
			}
			return tables;
		} catch (Exception ex) {

			throw new BaseException("读取数据库配置信息失败！", ex);
		}
	}

	/**
	 * 获取表定义
	 *
	 * @param schemaPattern
	 *            schema
	 * @param tablePattern
	 * @param conn
	 * @return
	 */
	public static DBTable getDBTable(String tableName, Connection conn) {
		try {
			Container container = new Container();
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			MetaDataRetriever.getTables(container, databaseMetaData, null,
					tableName);
			MetaDataRetriever.populateTableData(databaseMetaData, null,
					container);
			if (container.getTables() == null
					|| container.getTables().isEmpty()) {
				return null;
			}
			return (DBTable) container.getTables().get(tableName);

		} catch (Exception ex) {

			throw new BaseException("读取数据库配置信息失败！", ex);
		}
	}

	/**
	 * 获取表名称列表
	 *
	 * @param schemaPattern
	 *            schema
	 * @param tablePattern
	 * @param conn
	 * @return
	 */
	public static Set<String> getTableList(String schemaPattern,
			String tablePattern, Connection conn) {
		try {
			Container container = new Container();
			DatabaseMetaData databaseMetaData = conn.getMetaData();
			MetaDataRetriever.getTables(container, databaseMetaData,
					schemaPattern, tablePattern);
			if (container.getTables() == null
					|| container.getTables().isEmpty()) {
				return null;
			}

			return container.getTables().keySet();

		} catch (Exception ex) {

			throw new BaseException("读取数据库配置信息失败！", ex);
		}
	}

}
