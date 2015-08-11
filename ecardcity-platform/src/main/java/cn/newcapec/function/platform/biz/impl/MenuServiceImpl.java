/**
 *
 */
package cn.newcapec.function.platform.biz.impl;

import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.framework.core.dao.db.SqlDataset;
import cn.newcapec.function.platform.biz.MenuService;
import cn.newcapec.function.platform.model.Menu;
import cn.newcapec.function.platform.model.RoleMenu;

/**
 * @author ocean
 * date : 2014-4-17 上午11:05:01
 * email : zhangjunfang0505@163.com
 * Copyright : newcapec zhengzhou
 */
@Service()
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional()
public class MenuServiceImpl implements MenuService {


	@Override
	public Menu get(String arg0) {

		return null;
	}


	@Override
	public void removeUnused(String arg0) {


	}


	@Override
	public void saveOrUpdate(Menu arg0) {


	}


	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<Menu> findMenubyRole(List<RoleMenu>  roleMenus) {

		if (null==roleMenus||roleMenus.size()==0) {
			return null;
		}

		SqlDataset dataset=new SqlDataset();
        StringBuffer buffer=new StringBuffer(200);

		for (int i=0;i<roleMenus.size();i++) {
			RoleMenu roleMenu=roleMenus.get(i);
			buffer.append("'");
			buffer.append(roleMenu.getRoleid());
			buffer.append("'");
			if (i!=roleMenus.size()-1) {
				buffer.append(",");
			}
		}
		dataset.setSql("  SELECT * FROM BASE_MENU   WHERE  ROLEID  in(" +buffer.toString()+")" );
		dataset.setClazz(Menu.class);
		dataset.loadData();
		return dataset.getData();
	}

}
