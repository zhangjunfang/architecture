package cn.newcapec.function.platform.biz.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.function.platform.biz.CodeBankService;
import cn.newcapec.function.platform.dao.CodeBankDao;
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
@Service("codeBankService")
@Transactional
@SuppressWarnings("all")
public class CodeBankServiceImpl implements CodeBankService {

	@Autowired
	private CodeBankDao codeBankDao;

	@Override
	public CodeBank get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeUnused(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveOrUpdate(CodeBank arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page query(Map<String, Object> paramMap, LinkedHashMap<String, String> orderby) {
		Page page = codeBankDao.query(paramMap, orderby);
		return page;
	}
}