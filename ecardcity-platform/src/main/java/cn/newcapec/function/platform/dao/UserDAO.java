package cn.newcapec.function.platform.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.function.platform.dao.impl.BaseUserDAO;
import cn.newcapec.function.platform.model.User;
import cn.newcapec.function.platform.utils.Constant;

/**
 *
 * @Description: TODO
 * @author gaochongfei
 * @date 2014-4-4 下午04:02:18
 * @version V1.0
 */
@SuppressWarnings("all")
@Repository("userDAO")
public class UserDAO extends BaseUserDAO {

	/**
	 * 查询用户
	 *
	 * @Description: TODO
	 * @param @param params
	 * @param @param orderby
	 * @param @return
	 * @return Page
	 * @throws
	 */
	public Page queryUsers(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		/* 参数集合类 */
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer();
		sb.append("select u.* from base_user u where 1=1 ");
		if (params.containsKey("roleid")
				&& StringUtils.isNotBlank(params.get("roleid").toString())
				&& !"-1".equals(params.get("roleid").toString())) {
			sb.append(" and u.roleid = ? ");
			param.add(params.get("roleid"));
		}
		if (params.containsKey("name")
				&& StringUtils.isNotBlank(params.get("name").toString())) {
			sb.append(" and u.name like ?");
			param.add(Constant.PERCENT_SIGN + params.get("name")
					+ Constant.PERCENT_SIGN);
		}
		if (params.containsKey("empstate")
				&& StringUtils.isNotBlank(params.get("empstate").toString())) {
			sb.append(" and u.empstate=? ");
			param.add(params.get("empstate"));
		}
//		if (params.containsKey("roletype")
//				&& StringUtils.isNotBlank(params.get("roletype").toString())) {
//			sb.append(" and u.roletype=? ");
//			param.add(params.get("roletype"));
//		}
		if (params.containsKey("customerunitcode")
				&& StringUtils.isNotBlank(params.get("customerunitcode")
						.toString())) {
			sb.append(" and u.customerunitcode=? ");
			param.add(params.get("customerunitcode"));
		}
		sb.append(" and u.isdelete=0");
		return this.sqlQueryForPage(sb.toString(), param.toArray(), orderby);
	}

	/**
	 *
	 * @Title: findUserByName
	 * @Description: 根据用户名返回用户
	 * @param @param name
	 * @param @return 设定文件
	 * @return User 返回类型
	 * @throws
	 */
	public User findUserByName(String name) {

		String hql = "select u from User u where  u.accountName =?";
		User user = (User) this.findForObject(hql.toString(),
				new Object[] { name });
		return user;
	}

	/**
	 *
	 * @Title: deleteUser --
	 * @Description: 根据ids删除
	 * @param @param userids 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void deleteUser(String uuid) {
		StringBuffer sb = new StringBuffer(
				"update User set isdelete = 1 where id =? ");
		this.update(sb.toString(), new Object[] { uuid });
	}

	/**
	 *
	 * @Title: findUser
	 * @Description: 根据用户名，密码查询用户
	 * @param @param accountName
	 * @param @param password
	 * @param @return 设定文件
	 * @return User 返回类型
	 * @throws
	 */
	public User findUser(String accountName, String password) {
		String sql = "select u from User u  where  u.empCode=? and u.empPwd=? and u.empstate='1'  and u.isdelete=0 ";
		User user = (User) this.findForObject(sql, new Object[] { accountName,
				password });
		return user;
	}

	/**
	 * 获取的角色信息 --
	 *
	 * @param userid
	 * @return
	 */
	public Page queryUserByRoleId(String id,
			LinkedHashMap<String, String> orderby) {
		String hql = "select u from User u where u.roleid =?";
		Page users = this.queryForpage(hql, new Object[] { id }, orderby);
		return users;
	}

	/**
	 * 获取的角色组信息 --
	 *
	 * @param userid
	 * @return
	 */
	public Page queryUserByRoleGroupId(String id,
			LinkedHashMap<String, String> orderby) {
		String hql = "select u from User u where u.roleid =? ";
		Page users = this.queryForpage(hql, new Object[] { id }, orderby);
		return users;
	}

	/**
	 * @Title: updateUser
	 * @Description: 更新用户信息---
	 * @param @param user 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void updateUser(User user) {
		String sb = "update User set certificateid=?, email=?, empcode=?,"
				+ " empstate=?, idcardno=?, loglimit=?, name=?, nation=?, opdt=?, sex=?, telephone=?, roleid=?"
				+ " where id = ?";
		this.update(
				sb,
				new Object[] { user.getCertificateid(), user.getEmail(),
						user.getEmpCode(), user.getEmpstate(),
						user.getIdcardno(), user.getLoglimit(), user.getName(),
						user.getNation(), user.getOpdt(), user.getSex(),
						user.getTelephone(), user.getRoleid(), user.getId() });
	}

	/**
	 *
	 * @Title: sequenceNo
	 * @Description: 获取（索引信息表）最大id
	 * @param @return 设定文件
	 * @return List 返回类型
	 * @throws
	 */
	public String sequenceNo(String code, String customerunitcode) {
		String sql = "select max(max_no)+1 maxnum from base_sequence_no where code = ? and customerunitcode = ?";
		String maxNo = "";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, code);
		query.setParameter(1, customerunitcode);
		maxNo = query.uniqueResult().toString();
		return maxNo;
	}

	/**
	 *
	 * @Title: updateSeqNo
	 * @Description: 更新（索引信息表）最大id
	 * @param @param code
	 * @param @param customerunitcode 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void updateSeqNo(String code, String customerunitcode) {
		String sql = "update base_sequence_no set max_no =(select max(max_no)+1 from base_sequence_no where code = ? and customerunitcode = ?)"
				+ " where code = ? and customerunitcode = ? ";
		// String sql =
		// "update base_sequence_no set max_no = where code = ? and customerunitcode = ?)"
		// +
		// " where code = ? and customerunitcode = ? ";
		Query query = this.getSession().createSQLQuery(sql);
		query.setParameter(0, code);
		query.setParameter(1, customerunitcode);
		query.setParameter(2, code);
		query.setParameter(3, customerunitcode);
		query.executeUpdate();
	}
}
