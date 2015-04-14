package com.transilink.framework.core.dao.db;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class PagingHelper {
	private int pageSize = 0;

	private int pageIndex = 1;

	private int totalCount = -1;

	private int fromIndex = 0;

	private int toIndex = 0;

	private int pageCount = 0;

	private static int DEFAULT_MAX_PAGING_LINK = 10;

	public int[] getPaginLinks() {
		if (this.pageCount == 0) {
			return new int[] { 1 };
		}

		if (this.pageIndex > this.pageCount) {
			this.pageIndex = this.pageCount;
		}

		int firstNo = this.pageIndex - DEFAULT_MAX_PAGING_LINK / 2;
		int offset = firstNo - 1;

		int endNo = this.pageIndex + DEFAULT_MAX_PAGING_LINK / 2 - 1;
		int endoffset = endNo - this.pageCount;
		if (offset < 0) {
			if (endoffset > 0) {
				firstNo = 1;
			} else {
				endNo -= offset;
				endoffset = endNo - this.pageCount;
			}
		}
		if (endoffset > 0) {
			if (offset < 0)
				endNo = this.pageCount;
			else {
				firstNo -= endoffset;
			}
		}
		if (firstNo < 1) {
			firstNo = 1;
		}
		if (endNo > this.pageCount) {
			endNo = this.pageCount;
		}
		if (endNo == 0) {
			endNo = 1;
		}

		int[] nums = new int[endNo - firstNo + 1];
		int i = 0;
		while (firstNo <= endNo) {
			nums[(i++)] = firstNo;
			firstNo++;
		}
		return nums;
	}

	public PagingHelper(int pageSize, int pageIndex) {
		doSetPageSize(pageSize);
		doSetPageIndex(pageIndex);
		calc();
	}

	public PagingHelper(int pageSize, int pageIndex, int totalCount) {
		doSetPageSize(pageSize);
		doSetPageIndex(pageIndex);
		this.totalCount = totalCount;
		calc();
	}

	private void calc() {
		if ((this.totalCount > -1)
				&& (this.pageSize * (this.pageIndex - 1) > this.totalCount)) {
			this.pageIndex = ((this.totalCount - 1) / this.pageSize + 1);
		}
		this.fromIndex = (this.pageSize * (this.pageIndex - 1));
		this.toIndex = (this.fromIndex + this.pageSize);
		if ((this.totalCount > -1) && (this.toIndex > this.totalCount))
			this.toIndex = this.totalCount;
		if (this.totalCount > -1) {
			if (this.totalCount > 0) {
				this.pageCount = ((this.totalCount - 1) / this.pageSize + 1);
				return;
			}
			this.pageCount = 0;
			return;
		}
		this.pageCount = this.pageIndex;
	}

	private void doSetPageIndex(int pageIndex) {
		if (pageIndex < 1)
			pageIndex = 1;
		this.pageIndex = pageIndex;
	}

	private void doSetPageSize(int pageSize) {
		if (pageSize < 0)
			pageSize = 0;
		this.pageSize = pageSize;
	}

	public void setPageIndex(int pageIndex) {
		doSetPageIndex(pageIndex);
		calc();
	}

	public void setPageSize(int pageSize) {
		doSetPageSize(pageSize);
		calc();
	}

	public void setTotalRow(int totalCount) {
		this.totalCount = totalCount;
		calc();
	}

	public int getPageIndex() {
		return this.pageIndex;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public int getTotalRow() {
		return this.totalCount;
	}

	public int getFromIndex() {
		return this.fromIndex;
	}

	public int getToIndex() {
		return this.toIndex;
	}

	public int getPageCount() {
		return this.pageCount;
	}
}