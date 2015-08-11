/**
 *
 */
package cn.newcapec.function.platform.biz.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.framework.core.dao.db.SqlDataset;
import cn.newcapec.function.platform.biz.ModuleService;
import cn.newcapec.function.platform.model.BusinessAppredit;
import cn.newcapec.function.platform.model.Module;

/**
 * @Description :
 * @author : ocean
 * @date : 2014-4-28 上午10:10:43
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
@Service()
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional()
public class ModuleServiceImpl implements ModuleService, Serializable {

	private static final long serialVersionUID = 5392739020835834448L;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<Module> findByBusinessAppredit(
			List<BusinessAppredit> businessAppredits) {
		StringBuffer buffer = new StringBuffer(200);
		if (null != businessAppredits && 0 < businessAppredits.size()) {
			buffer.append(" select mm.id,mm.modulecode,mm.reditedcode,mm.modulename,mm.limitnum,mm.limitdt,mm.reditdt,mm.reditasn,mm.description,mm.apptype,mm.opdt,mm.syscode,mm.sortid,rel.appid,rel.moduleid from base_appmodule rel  right join  base_app_module mm on rel.moduleid=mm.moduleid ");
			buffer.append(" where  rel.appid  in (  ");
			int i = 0;
			for (BusinessAppredit businessAppredit : businessAppredits) {
				buffer.append("'");
				buffer.append(businessAppredit.getAppid());
				buffer.append("'");
				if (i != businessAppredits.size() - 1) {
					buffer.append(",");
				}
				i++;
			}
			buffer.append(" ) ");
		}
		if (buffer.length() > 0) {
			SqlDataset dataset = new SqlDataset();
			dataset.setSql(buffer.toString());
			dataset.setClazz(Module.class);
			dataset.loadData();
			return dataset.getData();
		}

		return null;

	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	@Override
	public List<Module> findByBusinessAppredit(String id) {

		StringBuffer buffer = new StringBuffer(200);
		if (null != id && 0 < id.length()) {
			buffer.append("  select m.* from base_appmodule rel  left join  base_app_module m on rel.moduleid=m.moduleid    ");
			buffer.append(" where  rel.appid  in (  '");
			buffer.append(id);
			buffer.append("' ) ");
		}
		SqlDataset dataset = new SqlDataset();
		dataset.setSql(buffer.toString());
		dataset.setClazz(Module.class);
		dataset.loadData();
		return dataset.getData();
	}

}
