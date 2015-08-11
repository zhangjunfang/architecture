/**
 *
 */
package cn.newcapec.function.platform.biz.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.framework.core.dao.db.Record;
import cn.newcapec.framework.core.dao.db.SqlDataset;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.function.platform.biz.BusinessAppreditService;
import cn.newcapec.function.platform.common.Constant;
import cn.newcapec.function.platform.dao.impl.BusinessAppreditDAO;
import cn.newcapec.function.platform.dao.impl.IDDAO;
import cn.newcapec.function.platform.dao.impl.ModuleAndRelationDAO;
import cn.newcapec.function.platform.model.BusinessAppredit;
import cn.newcapec.function.platform.model.ID;
import cn.newcapec.function.platform.model.Module;
import cn.newcapec.function.platform.model.ModuleAndRelation;

/**
 * @author ocean
 * @date : 2014-4-24 下午03:16:05
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
@Service()
@Transactional
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BusinessAppreditServiceImpl implements BusinessAppreditService {

	private static final long serialVersionUID = -4588580116982414464L;
	@Autowired
	private BusinessAppreditDAO dao;

	@Autowired
	private ModuleAndRelationDAO moduleAndRelationDAO;

	@Autowired
	private IDDAO iddao;

	/**
	 * 获取3
	 *
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Page<Record> getBusinessAppredit3() {
		SqlDataset sqldateset = new SqlDataset();

		String sql = " select app.id ids,app.appid,app.appdescription,app.appver,app.applimitdate,app.appname,app.applimitnum,m.id, m.modulename,m.limitnum,m.moduleid from  base_app_redit app left join base_appmodule rel on  app.appid=rel.appid left join  base_app_module m on rel.moduleid=m.moduleid where  app.sign='1' ";
		sqldateset.setSql(sql);
		sqldateset.setSupportPaging(true);
		sqldateset.loadData();
		sqldateset.getData();
		Page<Record> page = sqldateset.toPage();
		return page;
	}

	/**
	 * 获取2
	 *
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<Record> getBusinessAppredit2() {
		SqlDataset sqldateset = new SqlDataset();
		String sql = " select * from  base_app_redit ";
		sqldateset.setSql(sql);
		sqldateset.setSupportPaging(true);
		sqldateset.loadData();
		List<Record> list = sqldateset.getData();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<BusinessAppredit> getBusinessAppredit() {
		SqlDataset sqldateset = new SqlDataset();
		StringBuffer buffer = new StringBuffer(60);
		buffer.append("  select * from  base_app_redit  where apptype=");
		buffer.append("'");
		buffer.append(Constant.APPTYPE_APP);
		buffer.append("'");
		sqldateset.setSql(buffer.toString());
		sqldateset.setClazz(BusinessAppredit.class);
		sqldateset.setSupportPaging(true);
		sqldateset.loadData();
		return (List<BusinessAppredit>) sqldateset.getData();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public BusinessAppredit queryCustomApp(String id) {
		return dao.get(BusinessAppredit.class, id);
	}

	@Override
	public void saveOrUpdate(BusinessAppredit entity) {
		dao.saveOrUpdate(entity);
	}

	@Override
	public void delete(String id) {
		BusinessAppredit appredit = dao.load(BusinessAppredit.class, id);
		appredit.setSign(Constant.APPTYPE_DELETE_SIGN);
		dao.update(dao.load(BusinessAppredit.class, id));
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public BusinessAppredit get(String id) {
		return dao.get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<BusinessAppredit> getCusAppredit() {

		StringBuffer buffer = new StringBuffer(80);
		buffer.append(" select * from  base_app_redit ");
		buffer.append(" where apptype=");
		buffer.append("'");
		buffer.append(Constant.APPTYPE_CUS);
		buffer.append("'");
		buffer.append("and sign !='");
		buffer.append(Constant.APPTYPE_DELETE_SIGN);
		buffer.append("'");
		SqlDataset sqldateset = new SqlDataset();
		sqldateset.setSql(buffer.toString());
		sqldateset.setClazz(BusinessAppredit.class);
		sqldateset.setSupportPaging(true);
		sqldateset.loadData();
		return (List<BusinessAppredit>) sqldateset.getData();
	}

	@Override
	public boolean SaveOrUpdate(JSONObject jsonObject) {
		String ids = jsonObject.getString("ids");
		String appname = jsonObject.getString("appname");
		String desc = jsonObject.getString("desc");
		String id = jsonObject.getString("id");
		String[] temps = ids.split(",");
		if (StringUtils.isEmpty(id)) {// 保存数据
			return saveData(desc, appname, temps);
		} else {// 更新数据
			return updateData(id, desc, appname, temps);
		}
	}

	/**
	 * @param id
	 * @param desc
	 * @param appname
	 * @param temps
	 * @return
	 */
	private boolean saveData(String desc, String appname, String[] temps) {
		boolean bool = false;
		BusinessAppredit appredit = getConstantBusinessAppredit();
		Module temp = getModuleMinDateAndLimitnum(temps);
		appredit.setAppid(getCustID().getCusid());// 是由cus+三位数字组成，初始值为cus000;
		appredit.setAppname(appname);
		appredit.setAppdescription(desc);
		appredit.setApptype(Constant.APPTYPE_CUS);
		appredit.setSign(Constant.APPTYPE_NORMAL_SIGN);
		appredit.setApplimitdate(temp.getLimitdt());
		appredit.setApplimitnum(temp.getLimitnum());
		dao.save(appredit);
		// 记录关系表数据
		if (null != temps && temps.length > 0 && temps[0].length()>0) {
			for (int i = 0; i < temps.length; i++) {
				ModuleAndRelation rel = new ModuleAndRelation();
				rel.setAppid(appredit.getAppid());// 模拟数据
				rel.setModuleid(temps[i]);// 模块id
				rel.setSortid(0);
				moduleAndRelationDAO.save(rel);
			}
		}
		bool = true;
		return bool;
	}

	/**
	 * @param id
	 * @param desc
	 * @param appname
	 */
	private boolean updateData(String id, String desc, String appname,
			String... temps) {
		boolean bool = false;
		BusinessAppredit appredit = dao.get(id);
		appredit.setAppname(appname);
		appredit.setAppdescription(desc);
		dao.saveOrUpdate(appredit);
		// 更新中间关系表
		List<ModuleAndRelation> oldRelations = getModuleByBusinessAppredit(appredit
				.getAppid());
		CopyOnWriteArrayList<ModuleAndRelation> copyOnWriteArrayList = new CopyOnWriteArrayList<ModuleAndRelation>(
				oldRelations);
		if (null != temps && 0 < temps.length && !"".equals(temps[0])) {
			ModuleAndRelation relation = null;
			for (int i = 0; i < temps.length; i++) {
				relation = new ModuleAndRelation();
				relation.setAppid(appredit.getAppid());
				relation.setModuleid(temps[i]);
				relation.setSortid(0);
				boolean sign = false;
				if (null == oldRelations || oldRelations.size() == 0) {
					moduleAndRelationDAO.save(relation);
				} else {

					for (ModuleAndRelation moduleAndRelation : oldRelations) {
						// 通过值判断对象是否相等
						if (moduleAndRelation.getAppid().equals(
								relation.getAppid())
								&& moduleAndRelation.getModuleid().equals(
										relation.getModuleid())) {
							sign = true;
							copyOnWriteArrayList.remove(moduleAndRelation);
						}
						if (!sign) {
							moduleAndRelationDAO.save(relation);
						}
					}
				}
			}
		} else {
			// 编辑后 没有模块数据 执行删除操作
			for (ModuleAndRelation moduleAndRelation : oldRelations) {
				moduleAndRelationDAO.delete(moduleAndRelation);
			}
		}
		// 执行编辑后 不存在 数据 删除
		for (ModuleAndRelation moduleAndRelation : copyOnWriteArrayList) {
			moduleAndRelationDAO.delete(moduleAndRelation);
		}
		bool = true;
		return bool;

	}

	/**
	 * 获取最小授权到期时间，最小接入数
	 *
	 * @param ids
	 *            模块id集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Module getModuleMinDateAndLimitnum(String... ids) {
		if (null == ids || ids.length == 0) {
			return null;
		}
		StringBuffer buffer = new StringBuffer(50);
		buffer.append(" select min(a.limitdt) limitdt ,min(a.limitnum) limitnum from base_app_module  a  where a.MODULEID in (");
		for (int i = 0; i < ids.length; i++) {
			buffer.append("'");
			buffer.append(ids[i]);
			buffer.append("'");
			if (i != ids.length - 1) {
				buffer.append(" , ");
			}
		}
		buffer.append(")");
		SqlDataset sqldateset = new SqlDataset();
		sqldateset.setSql(buffer.toString());
		sqldateset.setClazz(Module.class);
		sqldateset.loadData();
		List<Module> modules = (List<Module>) sqldateset.getData();
		if (null == modules || modules.size() == 0) {
			return null;
		} else {
			return modules.get(0);
		}

	}

	/**
	 * 获取应用包对应的模块集合
	 *
	 * @param ids
	 *            模块id集合
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<ModuleAndRelation> getModuleByBusinessAppredit(String... ids) {
		if (null == ids || ids.length == 0 || "".equals(ids[0])) {
			return null;
		}
		SqlDataset sqldateset = new SqlDataset();
		StringBuffer buffer = new StringBuffer(50);
		buffer.append(" select * from  base_appmodule  where appid in( ");
		for (int i = 0; i < ids.length; i++) {
			buffer.append("'");
			buffer.append(ids[i]);
			buffer.append("'");
			if (i != ids.length - 1) {
				buffer.append("',");
			}
		}
		buffer.append(")");
		sqldateset.setSql(buffer.toString());
		sqldateset.setClazz(ModuleAndRelation.class);
		sqldateset.loadData();
		return (List<ModuleAndRelation>) sqldateset.getData();

	}

	/**
	 * 第一步： 自定义应用包 添加需要的辅助信息 首先 需要保证050100的appid的唯一性
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public BusinessAppredit getConstantBusinessAppredit() {
		SqlDataset sqldateset = new SqlDataset();
		StringBuffer buffer = new StringBuffer(60);
		buffer.append("  select * from  base_app_redit  where appid='050100'  and  apptype=");
		buffer.append("'");
		buffer.append(Constant.APPTYPE_APP);
		buffer.append("'");
		sqldateset.setSql(buffer.toString());
		sqldateset.setClazz(BusinessAppredit.class);
		sqldateset.setSupportPaging(true);
		sqldateset.loadData();
		List<BusinessAppredit> appredits = sqldateset.getData();
		if (null == appredits || appredits.size() == 0) {
			return null;
		} else {
			return (BusinessAppredit) appredits.get(0);
		}

	}

	/**
	 * 第二步： 自定义应用包 添加需要的辅助信息
	 */
	@SuppressWarnings("unchecked")
	public ID getCustID() {
		List<ID> ids = iddao.findAll();
		if (null == ids || ids.size() == 0) {
			ID id = new ID();
			id.setCusid("cus000");
			iddao.saveOrUpdate(id);
			return id;
		} else {
			ID id = ids.get(0);
			id.setCusid(getappid(id));
			iddao.saveOrUpdate(id);
			return id;
		}
	}

	private String getappid(ID id) {
		int ints = Integer.parseInt(id.getCusid().substring(3)) + 1;
		int length = (ints + "").length();
		StringBuffer buffer = new StringBuffer(10);
		buffer.append("cus");
		switch (length) {
		case 0:
			buffer.append("00");
			buffer.append("0");
			break;
		case 1:
			buffer.append("00");
			buffer.append(ints);
			break;
		case 2:
			buffer.append("0");
			buffer.append(ints);
			break;
		case 3:
			buffer.append(ints);
			break;
		default:
			// 潜在隐患 暂时 不想处理
			break;
		}

		return buffer.toString();

	}
}
