package com.transilink.foundation.cppt.biz;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.transilink.foundation.cppt.model.Menu;
import com.transilink.framework.core.biz.BaseService;
import com.transilink.framework.core.utils.pagesUtils.Page;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public interface MenuService extends BaseService<Menu> {

	/**
	 *
	 * @param paramMap
	 * @param orderby
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page findList(Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderby);

	@SuppressWarnings("rawtypes")
	public List findChildren(Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderby);
}
