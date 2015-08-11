package cn.newcapec.function.platform.biz.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.framework.core.dao.db.SqlDataset;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.function.platform.biz.AppPackageService;
import cn.newcapec.function.platform.model.LegelPerson;

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
@Service("appPackageService")
@Transactional
@SuppressWarnings("all")
public class AppPackageServiceImpl implements AppPackageService {

	@Override
	public LegelPerson get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeUnused(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveOrUpdate(Object arg0) {
		// TODO Auto-generated method stub

	}

	/**
	 * 查询所有的应用包（包括标准和自定义）
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Page findAll(Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderby) {
		SqlDataset sqldateset = new SqlDataset();
		String sql = "SELECT a.* FROM BASE_APP_REDIT a order by a.id,a.apptype";
		sqldateset.setSql(sql);
		sqldateset.loadData();
		return sqldateset.toPage();
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Page queryAll(Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderby) {
		SqlDataset sqldateset = new SqlDataset();
		String sql = "SELECT a.Id, a.AppName, (select count(1) from BASE_APPMODULE b where b.APPID=a.APPID) moduleCount FROM BASE_APP_REDIT a where a.apptype=0 order by a.id";
		sqldateset.setSql(sql);
		sqldateset.loadData();
		return sqldateset.toPage();
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Page queryCustomAppPackage(Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderby) {
		SqlDataset sqldateset = new SqlDataset();
		StringBuilder sb = new StringBuilder();
		sb.append("select c.*,REDIT.APPNAME,person.customername from ");
		sb.append("(SELECT DISTINCT APPID,CUSTOMERUNITCODE FROM BASE_LEGAL_APPMODULE) c ");
		sb.append("LEFT JOIN BASE_APP_REDIT redit on c.appid=redit.appid ");
		sb.append("LEFT JOIN BASE_LEGAL_PERSON person on c.customerunitcode=PERSON.CUSTOMERUNITCODE");
		sqldateset.setSql(sb.toString());
		sqldateset.loadData();
		return sqldateset.toPage();
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public String queryAppMenuByAppId(String AppId) {
		SqlDataset sqldateset = new SqlDataset();
		StringBuilder sb = new StringBuilder();
		sb.append("select nt.* from (");
		sb.append("select 'm'||a.MODULEID id,'0' pid,b.MODULEID nodeid,b.MODULENAME nodename,a.appid,a.SORTID from BASE_APPMODULE a  LEFT JOIN BASE_APP_MODULE b on a.MODULEID=b.moduleid WHERE a.APPID='");
		sb.append(AppId);
		sb.append("' union all ");
		sb.append("select 'n'||menu.menuid, case menu.parentmenuid when '0' then 'm'||MENU.SUBSYSTEMID ");
		sb.append("else 'n'||MENU.PARENTMENUID end as pid,menu.menuid nodeid,menu.menuname nodename,'");
		sb.append(AppId);
		sb.append("' appid,menu.sortid from BASE_MENU menu where ");
		sb.append("menu.SUBSYSTEMID in (SELECT moduleid from BASE_APPMODULE where APPID='");
		sb.append(AppId);
		sb.append("')) nt ");
		sb.append("order by nt.sortid");
		sqldateset.setSql(sb.toString());
		sqldateset.loadData();
		JSONArray s = sqldateset.toJSONArray();
		return s.toString();
	}

	/**
	 * 获取客户的模块菜单
	 *
	 * @param AppId
	 * @return
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public String queryAppMenuByCustId(String CustId) {
		SqlDataset sqldateset = new SqlDataset();
		StringBuilder sb = new StringBuilder();
		sb.append("select nt.* from (");
		sb.append("select 'm'||a.MODULEID id,'0' pid,b.MODULEID nodeid,b.MODULENAME nodename,a.appid,a.SORTID from ");
		sb.append("(select c.*,1 sortid from BASE_LEGAL_APPMODULE c where c.CUSTOMERUNITCODE='");
		sb.append(CustId);
		sb.append("') a LEFT JOIN BASE_APP_MODULE b on a.MODULEID=b.moduleid ");
		sb.append(" union all ");
		sb.append("select 'n'||menu.menuid, case menu.parentmenuid when '0' then 'm'||MENU.SUBSYSTEMID ");
		sb.append("else 'n'||MENU.PARENTMENUID end as pid,menu.menuid nodeid,menu.menuname nodename,'");
		sb.append("' appid,menu.sortid from BASE_MENU menu where ");
		sb.append("MENU.SUBSYSTEMID in (select MODULEID from BASE_LEGAL_APPMODULE where CUSTOMERUNITCODE='");
		sb.append(CustId);
		sb.append("')) nt ");
		sb.append("order by nt.sortid");
		sqldateset.setSql(sb.toString());
		sqldateset.loadData();
		JSONArray s = sqldateset.toJSONArray();
		return s.toString();
	}

}
