package cn.newcapec.function.platform.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.newcapec.function.platform.dao.impl.BaseLegelPersonDao;
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
@SuppressWarnings("all")
@Repository("legelPersonDao")
public class LegelPersonDao extends BaseLegelPersonDao {

	public Page query(Map<String, Object> params, LinkedHashMap<String, String> orderby) {
		List<Object> param = new ArrayList<Object>();

		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append("a.id, a.CustomerUnitCode, a.CustomerName, a.CustomerNameJP, a.LinkMan, a.Telephone, a.Address, a.Email, a.BankCode, a.BankCardNo, a.DataBaseStr, a.WebUrl, a.OpDt, a.OpenDt, a.Remark, a.Status, a.IsDelete, a.IsBalance");
		sql.append(", b.BankName");
		sql.append(" from BASE_LEGAL_PERSON a");
		sql.append(" Left JOIN BASE_BANK b on a.BANKCODE=b.ID");

		return this.sqlQueryForPage(sql.toString(), null, 0, 100, null);
	}
}