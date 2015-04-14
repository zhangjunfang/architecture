package com.transilink.framework.core.dao.db;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.transilink.framework.core.logs.LogEnabled;
import com.transilink.framework.core.utils.clazzUtils.BeanUtils;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class PagingResultSet implements Serializable, LogEnabled {
	private int currRow;
	private long rowCount;
	private int pageCount;
	private int pageIndex;
	private int pageSize;
	private ResultSet resultSet;
	private Class modelClass;
	private List list;

	private PagingResultSet() {
	}

	public long getRowCount() {
		return this.rowCount;
	}

	public void setRowCount(long rowCount) {
		this.rowCount = rowCount;
	}

	public PagingResultSet(ResultSet rst, Class modelClass) {
		this.currRow = 0;
		this.rowCount = 0L;
		this.resultSet = rst;
		this.modelClass = modelClass;
		this.list = new ArrayList();
	}

	private void calcEndRow() throws SQLException {
		if (this.resultSet.getType() != 1003) {
			this.resultSet.last();
			this.currRow = this.resultSet.getRow();
		} else {
			while (isLast())
				;
		}
		if (this.currRow < 0)
			this.currRow = 0;
		this.rowCount = this.currRow;
	}

	private void calPageCount() throws SQLException {
		if (this.pageSize > 0) {
			this.pageCount = (this.currRow % this.pageSize != 0 ? this.currRow
					/ this.pageSize + 1 : this.currRow / this.pageSize);
		} else
			this.pageCount = 1;
	}

	public int getPageCount() {
		return this.pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getPageIndex() {
		return this.pageIndex;
	}

	private void setPageIndex(int pageIndex) {
		if (pageIndex < 1)
			this.pageIndex = 1;
		else
			this.pageIndex = pageIndex;
	}

	private void setPageSize(int pageSize) {
		if (pageSize < 1)
			this.pageSize = 0;
		else
			this.pageSize = pageSize;
	}

	private void updateList() throws Exception {
		try {
			caculFirstRow();
			for (int i = 0; (i < this.pageSize) && (isLast()); i++)
				this.list.add(BeanUtils.resultSetToDO(this.resultSet,
						this.modelClass, true));
		} finally {
		}
	}

	public List getData() {
		return this.list;
	}

	private void caculFirstRow() throws SQLException {
		log.info("resultSet.getType:" + this.resultSet.getType());
		if (this.resultSet.getType() != 1003) {
			int startRow = 1 * (this.pageIndex - 1);
			if (startRow == 0)
				this.resultSet.beforeFirst();
			else {
				this.resultSet.absolute(startRow);
			}
			this.currRow = this.resultSet.getRow();
			if (this.currRow < 0)
				this.currRow = 0;
		} else {
			int startRow = this.pageSize * (this.pageIndex - 1);
			for (int i = 0; (i < startRow) && (isLast()); i++)
				;
		}
	}

	private boolean isLast() throws SQLException {
		if (this.resultSet.next()) {
			this.currRow += 1;
			return true;
		}
		return false;
	}

	public int tranlateData(int pageSize, int pageIndex) throws Exception {
		setPageSize(pageSize);
		setPageIndex(pageIndex);
		if (this.list != null)
			updateList();
		calcEndRow();
		calPageCount();
		return this.pageCount;
	}
}