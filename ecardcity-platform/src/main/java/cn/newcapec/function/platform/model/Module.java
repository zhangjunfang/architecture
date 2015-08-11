/**
 *
 */
package cn.newcapec.function.platform.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.newcapec.framework.core.handler.springDatebind.CustomDateSerializer;
import cn.newcapec.framework.core.model.CommonModel;

/**
 * @author ocean
 * @date : 2014-4-22 上午10:21:35
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
@Entity
@Table(name = "base_app_module")
public class Module extends CommonModel implements Serializable {

	private static final long serialVersionUID = -2346764612568457373L;
	private  String appid;
	private  String moduleid;
	private  String modulecode;
	private  String reditedcode;
	private  String modulename;
	private  Integer limitnum;
	private  Date limitdt;
	private  Date reditdt;
	private  String reditasn;
	private  String description;
	private  Integer sortid;
	private  String apptype;
	private  Date opdt;
	private String regcode=null;
	/**
	 *
	 */
	public Module() {
		super();
	}
	/**
	 * @param appid
	 * @param moduleid
	 * @param modulecode
	 * @param reditedcode
	 * @param modulename
	 * @param limitnum
	 * @param limitdt
	 * @param reditdt
	 * @param reditasn
	 * @param description
	 * @param sortid
	 * @param apptype
	 * @param opdt
	 */
	public Module(String appid, String moduleid, String modulecode,
			String reditedcode, String modulename, Integer limitnum,
			Date limitdt, Date reditdt, String reditasn, String description,
			Integer sortid, String apptype, Date opdt) {
		super();
		this.appid = appid;
		this.moduleid = moduleid;
		this.modulecode = modulecode;
		this.reditedcode = reditedcode;
		this.modulename = modulename;
		this.limitnum = limitnum;
		this.limitdt = limitdt;
		this.reditdt = reditdt;
		this.reditasn = reditasn;
		this.description = description;
		this.sortid = sortid;
		this.apptype = apptype;
		this.opdt = opdt;
	}
	/**
	 * @param appid
	 * @param moduleid
	 * @param modulecode
	 * @param reditedcode
	 * @param modulename
	 * @param limitnum
	 * @param limitdt
	 * @param reditdt
	 * @param reditasn
	 * @param description
	 * @param sortid
	 * @param apptype
	 * @param opdt
	 * @param regcode
	 */
	public Module(String appid, String moduleid, String modulecode,
			String reditedcode, String modulename, Integer limitnum,
			Date limitdt, Date reditdt, String reditasn, String description,
			Integer sortid, String apptype, Date opdt, String regcode) {
		super();
		this.appid = appid;
		this.moduleid = moduleid;
		this.modulecode = modulecode;
		this.reditedcode = reditedcode;
		this.modulename = modulename;
		this.limitnum = limitnum;
		this.limitdt = limitdt;
		this.reditdt = reditdt;
		this.reditasn = reditasn;
		this.description = description;
		this.sortid = sortid;
		this.apptype = apptype;
		this.opdt = opdt;
		this.regcode = regcode;
	}
	@Column(name = "regcode", length = 64)
	public String getRegcode() {
		return regcode;
	}



	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}
	@Column(name = "appid", length = 32)
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
	@Column(name = "modulecode", length = 6)
	public String getModulecode() {
		return modulecode;
	}
	public void setModulecode(String modulecode) {
		this.modulecode = modulecode;
	}
	@Column(name = "reditedcode", length = 1000)
	public String getReditedcode() {
		return reditedcode;
	}
	public void setReditedcode(String reditedcode) {
		this.reditedcode = reditedcode;
	}
	@Column(name = "modulename", length = 50)
	public String getModulename() {
		return modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}
	@Column(name = "limitnum")
	public Integer getLimitnum() {
		return limitnum;
	}

	public void setLimitnum(Integer limitnum) {
		this.limitnum = limitnum;
	}
	@Column(name = "limitdt")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getLimitdt() {
		return limitdt;
	}
	public void setLimitdt(Date limitdt) {
		this.limitdt = limitdt;
	}
	@Column(name = "reditdt")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getReditdt() {
		return reditdt;
	}
	public void setReditdt(Date reditdt) {
		this.reditdt = reditdt;
	}
	@Column(name = "reditasn", length = 50)
	public String getReditasn() {
		return reditasn;
	}
	public void setReditasn(String reditasn) {
		this.reditasn = reditasn;
	}
	@Column(name = "description", length = 100)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name = "sortid")
	public Integer getSortid() {
		return sortid;
	}
	public void setSortid(Integer sortid) {
		this.sortid = sortid;
	}
	@Column(name = "syscode", length = 50)
	public String getApptype() {
		return apptype;
	}
	@Column(name = "apptype", length = 2)
	public void setApptype(String apptype) {
		this.apptype = apptype;
	}
	@Column(name = "opdt")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getOpdt() {
		return opdt;
	}
	public void setOpdt(Date opdt) {
		this.opdt = opdt;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appid == null) ? 0 : appid.hashCode());
		result = prime * result + ((apptype == null) ? 0 : apptype.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((limitdt == null) ? 0 : limitdt.hashCode());
		result = prime * result
				+ ((limitnum == null) ? 0 : limitnum.hashCode());
		result = prime * result
				+ ((modulecode == null) ? 0 : modulecode.hashCode());
		result = prime * result
				+ ((moduleid == null) ? 0 : moduleid.hashCode());
		result = prime * result
				+ ((modulename == null) ? 0 : modulename.hashCode());
		result = prime * result + ((opdt == null) ? 0 : opdt.hashCode());
		result = prime * result
				+ ((reditasn == null) ? 0 : reditasn.hashCode());
		result = prime * result + ((reditdt == null) ? 0 : reditdt.hashCode());
		result = prime * result
				+ ((reditedcode == null) ? 0 : reditedcode.hashCode());
		result = prime * result + ((regcode == null) ? 0 : regcode.hashCode());
		result = prime * result + ((sortid == null) ? 0 : sortid.hashCode());
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
		Module other = (Module) obj;
		if (appid == null) {
			if (other.appid != null)
				return false;
		} else if (!appid.equals(other.appid))
			return false;
		if (apptype == null) {
			if (other.apptype != null)
				return false;
		} else if (!apptype.equals(other.apptype))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (limitdt == null) {
			if (other.limitdt != null)
				return false;
		} else if (!limitdt.equals(other.limitdt))
			return false;
		if (limitnum == null) {
			if (other.limitnum != null)
				return false;
		} else if (!limitnum.equals(other.limitnum))
			return false;
		if (modulecode == null) {
			if (other.modulecode != null)
				return false;
		} else if (!modulecode.equals(other.modulecode))
			return false;
		if (moduleid == null) {
			if (other.moduleid != null)
				return false;
		} else if (!moduleid.equals(other.moduleid))
			return false;
		if (modulename == null) {
			if (other.modulename != null)
				return false;
		} else if (!modulename.equals(other.modulename))
			return false;
		if (opdt == null) {
			if (other.opdt != null)
				return false;
		} else if (!opdt.equals(other.opdt))
			return false;
		if (reditasn == null) {
			if (other.reditasn != null)
				return false;
		} else if (!reditasn.equals(other.reditasn))
			return false;
		if (reditdt == null) {
			if (other.reditdt != null)
				return false;
		} else if (!reditdt.equals(other.reditdt))
			return false;
		if (reditedcode == null) {
			if (other.reditedcode != null)
				return false;
		} else if (!reditedcode.equals(other.reditedcode))
			return false;
		if (regcode == null) {
			if (other.regcode != null)
				return false;
		} else if (!regcode.equals(other.regcode))
			return false;
		if (sortid == null) {
			if (other.sortid != null)
				return false;
		} else if (!sortid.equals(other.sortid))
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(200);
		builder.append("{appid=");
		builder.append(appid);
		builder.append(", moduleid=");
		builder.append(moduleid);
		builder.append(", modulecode=");
		builder.append(modulecode);
		builder.append(", reditedcode=");
		builder.append(reditedcode);
		builder.append(", modulename=");
		builder.append(modulename);
		builder.append(", limitnum=");
		builder.append(limitnum);
		builder.append(", limitdt=");
		builder.append(limitdt);
		builder.append(", reditdt=");
		builder.append(reditdt);
		builder.append(", reditasn=");
		builder.append(reditasn);
		builder.append(", description=");
		builder.append(description);
		builder.append(", sortid=");
		builder.append(sortid);
		builder.append(", apptype=");
		builder.append(apptype);
		builder.append(", opdt=");
		builder.append(opdt);
		builder.append(", regcode=");
		builder.append(regcode);
		builder.append("}");
		return builder.toString();
	}




}
