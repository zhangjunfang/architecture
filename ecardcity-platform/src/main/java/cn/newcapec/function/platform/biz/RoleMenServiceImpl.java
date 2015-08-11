/**
 *
 */
package cn.newcapec.function.platform.biz;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import cn.newcapec.framework.core.dao.db.SqlDataset;
import cn.newcapec.function.platform.model.RoleMenu;

/**
 * @author ocean
 * date : 2014-4-17 上午11:54:25
 * email : zhangjunfang0505@163.com
 * Copyright : newcapec zhengzhou
 */
@Service()
@Transactional
@SuppressWarnings("all")
public class RoleMenServiceImpl implements RoleMenService {


	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public RoleMenu get(String arg0) {
		return null;
	}

	@Override
	public void removeUnused(String arg0) {

	}

	@Override
	public void saveOrUpdate(RoleMenu arg0) {

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<RoleMenu> findRoleMenuById(String roleId) {
		if (StringUtils.isEmpty(roleId)) {
			return null;
		}
		SqlDataset dataset=new SqlDataset();
		dataset.setSql(" select * from BASE_ROLE_MENU  where   ROLEID in ('1','2')  ");
		String ids[]= roleId.split(",");
		StringBuffer buffer=new StringBuffer(40);
		buffer.append("select * from BASE_ROLE_MENU  where   ROLEID in (");
		for (int i = 0; i < ids.length; i++) {
			buffer.append("'");
			buffer.append(ids[i]);
			buffer.append("'");
			if (i!=ids.length-1) {
				buffer.append(",");
			}
		}
		buffer.append(")");
		dataset.setSql("select 1 from base_role_menu");
		dataset.setClazz(RoleMenu.class);
		dataset.loadData();
		List<RoleMenu> a = dataset.getData();

		 return a;
	}

}
