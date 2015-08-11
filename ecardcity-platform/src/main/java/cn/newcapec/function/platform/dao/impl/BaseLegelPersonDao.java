package cn.newcapec.function.platform.dao.impl;

import org.springframework.orm.hibernate3.HibernateTemplate;
import cn.newcapec.framework.core.dao.hibernate.HibernateEntityDao;

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
public class BaseLegelPersonDao extends HibernateEntityDao {

	protected Class getReferenceClass() {
		return cn.newcapec.function.platform.model.LegelPerson.class;
	}
}