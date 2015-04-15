package com.transilink.framework.core.utils.pagesUtils;

import java.util.List;

/**
 * Page is not a domain object but is used to store and fetch page information.
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@SuppressWarnings({ "all" })
public class Page<T> {

	public final static int DEFAULT_PAGE_SIZE = 20;

	private int total;
	private List<T> items; // 分页集合

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public Page() {
		// 
	}
}
