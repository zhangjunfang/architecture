/**
 *
 */
package cn.newcapec.function.platform.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.newcapec.framework.core.model.AppBaseModel;

/**
 *
 * @Description : 暂时没有使用
 * @author : ocean
 * @date : 2014-4-28 下午02:11:06
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
@Entity
@Table(name = "base_appmodule")
public class ModuleAndRelation extends AppBaseModel implements Serializable {

	private static final long serialVersionUID = 7477750517542014354L;


	private  String appid;
	private  String moduleid;
	private  Integer sortid;


	public ModuleAndRelation() {
		super();

	}

	/**
	 * @param appid
	 * @param moduleid
	 * @param sortid
	 */
	public ModuleAndRelation(String appid, String moduleid, Integer sortid) {
		super();
		this.appid = appid;
		this.moduleid = moduleid;
		this.sortid = sortid;
	}


	@Column(name = "appid", length = 6)
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	@Column(name = "moduleid", length = 6)
	public String getModuleid() {
		return moduleid;
	}
	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}
	@Column(name = "sortid")
	public Integer getSortid() {
		return sortid;
	}
	public void setSortid(Integer sortid) {
		this.sortid = sortid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appid == null) ? 0 : appid.hashCode());
		result = prime * result
				+ ((moduleid == null) ? 0 : moduleid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ModuleAndRelation other = (ModuleAndRelation) obj;
		if (appid == null) {
			if (other.appid != null)
				return false;
		} else if (!appid.equals(other.appid))
			return false;
		if (moduleid == null) {
			if (other.moduleid != null)
				return false;
		} else if (!moduleid.equals(other.moduleid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{appid=");
		builder.append(appid);
		builder.append(", moduleid=");
		builder.append(moduleid);
		builder.append(", sortid=");
		builder.append(sortid);
		builder.append(", id=");
		builder.append(getId());
		builder.append("}");
		return builder.toString();
	}


}
