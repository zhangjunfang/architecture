package com.transilink.framework.core.model.dbmeta;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@SuppressWarnings("all")
public class MetaDataRetriever extends Thread {

	private Container container;

	private DatabaseMetaData metadata;

	private IErrorHandler errorHandler = new DefaultErrorHandler();

	public MetaDataRetriever(Container container, DatabaseMetaData metadata,
			IErrorHandler errorHandler) {
		this.container = container;
		this.metadata = metadata;
		if (errorHandler != null) {
			this.errorHandler = errorHandler;
		}
	}

	public void run() {
		try {
			populateTableData(metadata, null, container);
		} catch (Exception e) {
			errorHandler.onError(null, e);
		} finally {
			try {
				if (null != metadata && null != metadata.getConnection())
					metadata.getConnection().close();
			} catch (SQLException sqle) {
			}
		}
	}

	/**
	 * Return the number of tables that match the schema pattern and table
	 * pattern given
	 *
	 * @param metadata
	 * @param schemaPattern
	 * @param tablePattern
	 * @return the number of tables
	 * @throws java.sql.SQLException
	 */
	public static int getTableCount(DatabaseMetaData metadata,
			String schemaPattern, String tablePattern) throws SQLException {
		String[] names = { "TABLE" };
		String _schema = null;
		String _table = null;
		if (null != schemaPattern && schemaPattern.trim().length() > 0)
			_schema = schemaPattern.trim();
		else
			_schema = metadata.getUserName();// 默认当前数据库链接用户
		if (null != tablePattern && tablePattern.trim().length() > 0)
			_table = tablePattern.trim();
		else
			_table = null;
		ResultSet rs = null;
		try {
			int count = 0;
			rs = metadata.getTables(null, _schema, _table, names);
			// Map tables = new HashMap();
			// String TABLE_NAME = "TABLE_NAME";
			String TABLE_TYPE = "TABLE_TYPE";
			String SYSTEM = "SYSTEM";
			while (rs.next()) {
				String tableType = rs.getString(TABLE_TYPE);
				if (null == tableType
						|| tableType.toUpperCase().indexOf(SYSTEM) < 0) {
					count++;
				}
			}
			return count;
		} finally {
			if (null != rs)
				rs.close();
		}
	}

	/**
	 * Load the container with table objects that only contain the name. Start
	 * the thread or call populateTableData to load the columns and keys.
	 *
	 * @param container
	 * @param metadata
	 * @param schemaPattern
	 * @param tablePattern
	 * @throws java.sql.SQLException
	 */
	public static void getTables(Container container,
			DatabaseMetaData metadata, String schemaPattern, String tablePattern)
			throws SQLException {
		String[] names = { "TABLE" };
		String _schema = null;
		String _table = null;
		if (null != schemaPattern && schemaPattern.trim().length() > 0)
			_schema = schemaPattern.trim();
		else
			_schema = metadata.getUserName();// 默认当前数据库链接用户

		if (null != tablePattern && tablePattern.trim().length() > 0)
			_table = tablePattern.trim();
		else
			_table = null;
		ResultSet rs = null;
		try {
			rs = metadata.getTables(null, _schema, _table, names);
			Map tables = new HashMap();
			String TABLE_NAME = "TABLE_NAME";
			String TABLE_TYPE = "TABLE_TYPE";
			String SYSTEM = "SYSTEM";
			while (rs.next()) {
				String tableName = rs.getString(TABLE_NAME);
				if (tableName.indexOf("$") != -1) {
					continue;
				}
				DBTable table = new DBTable(container, tableName);
				String tableType = rs.getString(TABLE_TYPE);
				if (null == tableType
						|| tableType.toUpperCase().indexOf(SYSTEM) < 0) {
					tables.put(table.getName(), table);
				}
			}
			container.setTables(tables);
		} finally {
			if (null != rs)
				rs.close();
		}
	}

	/**
	 * Populate the column and key information for the tables.
	 *
	 * @param metadata
	 * @param container
	 *            the table container
	 * @throws java.sql.SQLException
	 */
	public static void populateTableData(DatabaseMetaData metadata,
			String schemaPattern, Container container) throws SQLException {
		Map tables = container.getTables();
		for (Iterator i = tables.values().iterator(); i.hasNext();) {
			DBTable table = (DBTable) i.next();
			readTableColumns(metadata, schemaPattern, table);
		}
		for (Iterator i = tables.values().iterator(); i.hasNext();) {
			DBTable table = (DBTable) i.next();
			readTableKeys(metadata, schemaPattern, table, tables);
		}
		for (Iterator i = tables.values().iterator(); i.hasNext();) {
			DBTable table = (DBTable) i.next();
			table.init();
		}
		container.fullyLoaded = true;
	}

	/**
	 * Read the columns from the DatabaseMetaData and notify the given table of
	 * the colums
	 *
	 * @param meta
	 * @param schemaPattern
	 * @param table
	 * @throws java.sql.SQLException
	 */
	private static void readTableColumns(DatabaseMetaData meta,
			String schemaPattern, DBTable table) throws SQLException {
		ResultSet columns = null;
		try {
			if (null != schemaPattern && schemaPattern.trim().length() > 0)
				schemaPattern = schemaPattern.trim();
			else
				schemaPattern = meta.getUserName();// 默认当前数据库链接用户
			columns = meta
					.getColumns(null, schemaPattern, table.getName(), "%");
			while (columns.next()) {
				String columnName = columns.getString("COLUMN_NAME");
				String datatype = columns.getString("TYPE_NAME");
				int datasize = columns.getInt("COLUMN_SIZE");
				int digits = columns.getInt("DECIMAL_DIGITS");
				int nullable = columns.getInt("NULLABLE");
				DBColumn newColumn = new DBColumn(table, columnName, datatype,
						datasize, digits, nullable);
				table.notifyColumn(newColumn);
			}
		} finally {
			if (null != columns)
				columns.close();
		}
	}

	/**
	 * Read the primary and foreign keys from the DatabaseMetaData and notify
	 * the given table of the keys
	 *
	 * @param meta
	 * @param table
	 * @param tables
	 * @throws java.sql.SQLException
	 */
	private static void readTableKeys(DatabaseMetaData meta,
			String schemaPattern, DBTable table, Map tables)
			throws SQLException {
		ResultSet keys = null;
		try {
			if (null != schemaPattern && schemaPattern.trim().length() > 0)
				schemaPattern = schemaPattern.trim();
			else
				schemaPattern = meta.getUserName();// 默认当前数据库链接用户
			// primary keys
			keys = meta.getPrimaryKeys(null, schemaPattern, table.getName());
			while (keys.next()) {
				String tableName = keys.getString("TABLE_NAME");
				String columnName = keys.getString("COLUMN_NAME");
				table = (DBTable) tables.get(checkName(tableName));
				table.notifyPrimaryKey(checkName(columnName));
			}

			// foreign keys
			keys = meta.getImportedKeys(null, schemaPattern, table.getName());
			// List rels = new ArrayList();
			while (keys.next()) {
				String pkTableName = keys.getString("PKTABLE_NAME");
				pkTableName = keys.getString("PKTABLE_NAME");
				String pkColumnName = keys.getString("PKCOLUMN_NAME");
				String fkTableName = keys.getString("FKTABLE_NAME");
				String fkColumnName = keys.getString("FKCOLUMN_NAME");
				DBTable pkTable = (DBTable) tables.get(checkName(pkTableName));
				DBTable fkTable = (DBTable) tables.get(checkName(fkTableName));
				if (null != pkTable && null != fkTable) {
					DBColumn pkColumn = pkTable
							.getColumn(checkName(pkColumnName));
					if (null != pkColumn)
						table.notifyForeignKey(checkName(fkColumnName),
								pkColumn);
				}
			}
		} finally {
			if (null != keys)
				keys.close();
		}
	}

	private static String checkName(String s) {
		if (null == s)
			return null;
		s = stringReplace(s, "`", "");
		return s;
	}

	/**
	 * Replace the contents of the from string with the contents of the to
	 * string in the base string
	 *
	 * @param base
	 *            the string to replace part of
	 * @param from
	 *            the string to be replaced
	 * @param to
	 *            the string to replace
	 */
	public static String stringReplace(String base, String from, String to) {
		StringBuffer sb1 = new StringBuffer(base);
		StringBuffer sb2 = new StringBuffer(base.length() + 50);
		char[] f = from.toCharArray();
		char[] t = to.toCharArray();
		char[] test = new char[f.length];

		for (int x = 0; x < sb1.length(); x++) {

			if (x + test.length > sb1.length()) {
				sb2.append(sb1.charAt(x));
			} else {
				sb1.getChars(x, x + test.length, test, 0);
				if (aEquals(test, f)) {
					sb2.append(t);
					x = x + test.length - 1;
				} else {
					sb2.append(sb1.charAt(x));
				}
			}
		}
		return sb2.toString();
	}

	static private boolean aEquals(char[] a, char[] b) {
		if (a.length != b.length) {
			return false;
		}
		for (int x = 0; x < a.length; x++) {
			if (a[x] != b[x]) {
				return false;
			}
		}
		return true;
	}
}