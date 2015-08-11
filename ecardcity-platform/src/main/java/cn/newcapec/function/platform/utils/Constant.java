/**
 * 系统名称 ：城市一卡通综合管理平台
 * 开发组织 ：城市一卡通事业部
 * 版权所属 ：新开普电子股份有限公司
 * (C) Copyright  Corporation 2014  All Rights Reserved.
 * 本内容仅限于郑州新开普电子股份有限公司内部使用，版权保护，未经过授权拷贝使用将追究法律责任
 */
package cn.newcapec.function.platform.utils;

import java.util.List;

import cn.newcapec.framework.core.dao.db.SqlDataset;
import cn.newcapec.function.platform.model.LoginlimitType;

/**
 * @author chongfeigao
 * @category 常量类
 * @version 1.0
 * @date 2014年3月24日 下午4:57:23
 */
public class Constant {

	public static final String SESSIONCUSTOMER = "sessionCustomer"; // 登陆时放入session中的名称
	public static final String SESSIONUSER = "sessionUser"; // 登陆时放入session中的名称
	public static final String EMPID = "EMPID"; // 索引信息表的对应 CODE 代码类型

	public static final String PERCENT_SIGN = "%"; // %,like操作用到

	/**
	 *
	 * @Title: getloginlimitType
	 * @Description: 获取职员登录限制(BASE_LOGINLIMIT_TYPE登录限制类型表)
	 * @param @return 设定文件
	 * @return List 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public static List<LoginlimitType> getloginlimitType() {
		SqlDataset sqldateset = new SqlDataset();
		String sql = "select typeid,typename from base_loginlimit_type";
		sqldateset.setSql(sql);
		sqldateset.setClazz(LoginlimitType.class);
		List<LoginlimitType> loginlimitlist = (List<LoginlimitType>)sqldateset.loadData().getData();
		return loginlimitlist;
	}

}