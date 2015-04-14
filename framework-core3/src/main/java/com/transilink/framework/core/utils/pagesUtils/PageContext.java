package com.transilink.framework.core.utils.pagesUtils;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@SuppressWarnings({ "all" })
public class PageContext {
	private static ThreadLocal offset = new ThreadLocal();
	private static ThreadLocal pageSize = new ThreadLocal();

	@SuppressWarnings("unchecked")
	public static void setOffset(Integer _offset) {
		offset.set(_offset);
	}

	public static Integer getOffset() {
		Integer os = (Integer) offset.get();
		return null == os ? 0 : os;
	}

	public static void removeOffset() {
		offset.remove();   
	}

	@SuppressWarnings("unchecked")
	public static void setPageSize(Integer _pageSize) {
		pageSize.set(_pageSize);
	}

	public static Integer getPageSize() {
		Integer ps = (Integer) pageSize.get();
		return null == ps ? Integer.MAX_VALUE : ps;
	}

	public static void removePageSize() {
		pageSize.remove();
	}
}
