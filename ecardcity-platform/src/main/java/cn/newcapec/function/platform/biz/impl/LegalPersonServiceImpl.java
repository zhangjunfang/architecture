/**  
 * @Title: LegalPersonServiceImpl.java
 * @Package cn.newcapec.function.platform.biz.impl
 * @Description: TODO(用一句话描述该文件做什么)
 * @author gaochongfei 
 * @date 2014-3-31 上午10:25:38
 * @version V1.0  
 */
package cn.newcapec.function.platform.biz.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.function.platform.biz.LegalPersonService;
import cn.newcapec.function.platform.dao.LegalPersonDAO;
import cn.newcapec.function.platform.model.LegalPerson;

/**
 * @author chongfeigao
 * 
 */
@Service("legalPersonService")
@Transactional
@SuppressWarnings("all")
public class LegalPersonServiceImpl implements LegalPersonService {
	/* 用户接口类 */
	@Autowired
	private LegalPersonDAO legalPersonDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.newcapec.framework.core.biz.BaseService#get(java.lang.String)
	 */
	@Override
	public LegalPerson get(String arg0) {
		return legalPersonDAO.get(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.newcapec.framework.core.biz.BaseService#removeUnused(java.lang.String)
	 */
	@Override
	public void removeUnused(String arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.newcapec.framework.core.biz.BaseService#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(LegalPerson arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc) --
	 * 
	 * @see
	 * cn.newcapec.function.platform.biz.LegalPersonService#queryUsers(java.
	 * util.Map, java.util.LinkedHashMap)
	 */
	@Override
	public LegalPerson queryByCustomerunitcode(String code) {
		return legalPersonDAO.queryByCustomerunitcode(code);
	}

}
