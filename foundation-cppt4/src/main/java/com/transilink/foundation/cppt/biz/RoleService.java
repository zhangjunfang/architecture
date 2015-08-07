package com.transilink.foundation.cppt.biz;

import java.util.Map;

import com.transilink.foundation.cppt.model.Role;
import com.transilink.framework.core.biz.BaseService;
import com.transilink.framework.core.utils.pagesUtils.Page;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public interface RoleService extends BaseService<Role> {

	/**
	 *
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page findList(Map<String, Object> paramMap);
}
