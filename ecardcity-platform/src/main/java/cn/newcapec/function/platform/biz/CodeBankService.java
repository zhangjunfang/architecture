package cn.newcapec.function.platform.biz;

import java.util.LinkedHashMap;
import java.util.Map;

import cn.newcapec.framework.core.biz.BaseService;
import cn.newcapec.function.platform.biz.CodeBankService;
import cn.newcapec.function.platform.model.CodeBank;
import cn.newcapec.framework.core.utils.pagesUtils.Page;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright(c) 2011 郑州新开普电子股份有限公司
 * </p>
 *
 * @author 黄鑫 (huangxin)
 * @version
 * @data 创建日期：2011-11-11 修改日期： 修改人： 复审人：
 * @generated
 */
public interface CodeBankService extends BaseService<CodeBank> {

	@SuppressWarnings("rawtypes")
	public Page query(Map<String, Object> paramMap, LinkedHashMap<String, String> orderby);
}