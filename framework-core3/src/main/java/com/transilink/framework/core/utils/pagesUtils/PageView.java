package com.transilink.framework.core.utils.pagesUtils;

import java.util.List;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 * @param <T>
 */
@SuppressWarnings({ "all" })
public class PageView<T> {
	/* 分页数据 */
	private List<T> records;
	/* 页码开始索引和结束索引 */
	private PageIndex pageIndex;
	/* 总页数 */
	private long totalPage = 1;
	/* 每页显示记录数 */
	private int maxResult = 20;
	/* 当前页 */
	private int currentPage = 1;
	/* 总记录数 */
	private long totalRecord;
	/* 页码数量 */
	private int pageCode = 10;

	private int begin = 0;

	private int end = 0;
	/* 分页时调用的js方法名 */
	private String jsMethod;

	/**
	 * 要获取记录的开始索引
	 *
	 * @return
	 */
	public int getFirstResult() {
		return (this.currentPage - 1) * this.maxResult;
	}

	public int getPageCode() {
		return pageCode;
	}

	public void setPageCode(int pageCode) {
		this.pageCode = pageCode;
	}

	public PageView(int maxResult, int currentPage) {
		this.maxResult = maxResult;
		if (-1 > currentPage) {
			currentPage = 1;
		}
		this.currentPage = currentPage;
	}

	@SuppressWarnings("rawtypes")
	public void setQueryResult(Page page) {
		setTotalRecord(page.getTotal());
		setRecords(page.getItems());
	}

	public long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(long totalRecord) {
		this.totalRecord = totalRecord;
		setTotalPage(this.totalRecord % this.maxResult == 0 ? this.totalRecord
				/ this.maxResult : this.totalRecord / this.maxResult + 1);
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public PageIndex getPageindex() {
		return pageIndex;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
		this.pageIndex = PageIndex.getPageIndex(pageCode, currentPage,
				totalPage);
	}

	public int getMaxResult() {
		return maxResult;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getBegin() {
		begin = (currentPage - 1) * maxResult;
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		end = (currentPage) * maxResult - 1;
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	/**
	 * @return the jsMethod
	 */
	public String getJsMethod() {
		return jsMethod;
	}

	/**
	 * @param jsMethod
	 *            the jsMethod to set
	 */
	public void setJsMethod(String jsMethod) {
		this.jsMethod = jsMethod;
	}
}
