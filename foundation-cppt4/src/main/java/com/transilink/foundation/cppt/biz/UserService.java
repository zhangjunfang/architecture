package com.transilink.foundation.cppt.biz;

import java.util.Map;

import com.transilink.foundation.cppt.model.User;
import com.transilink.framework.core.biz.BaseService;
import com.transilink.framework.core.rest.Msg;
import com.transilink.framework.core.utils.pagesUtils.Page;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public interface UserService extends BaseService<User> {

	/**
	 * 用户登录验证
	 *
	 * @param userName
	 * @param password
	 * @return
	 */
	public Msg loginValidate(String userName, String password);

	/**
	 *
	 * @param paramMap
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page findList(Map<String, Object> paramMap);
}
