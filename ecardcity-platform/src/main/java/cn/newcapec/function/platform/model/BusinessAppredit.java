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
import cn.newcapec.framework.core.model.AppBaseModel;

/**
 * @author ocean date : 2014-4-4 下午04:02:54
 * @email : zhangjunfang0505@163.com
 * @company : newcapec zhengzhou
 */
@Entity
@Table(name = "base_app_redit")
public class BusinessAppredit extends AppBaseModel implements Serializable {

	private static final long serialVersionUID = -9141918011015822029L;

	private String appid = null;
	private String syscode = null;
	private String accreditedcode = null;
	private String appname = null;
	private String appver = null;
	private String appdescription = null;
	private Integer applimitnum = null;
	private Date applimitdate = null;
	private Date opdt = null;
	private String reditasn = null;
	private Date reditdt = null;
	private String apptype = null;
	private String sign = null;
	private String regcode=null;

	/**
	 *
	 */
	public BusinessAppredit() {
		super();
	}


	/**
	 * @param appid
	 * @param syscode
	 * @param accreditedcode
	 * @param appname
	 * @param appver
	 * @param appdescription
	 * @param applimitnum
	 * @param applimitdate
	 * @param opdt
	 * @param reditasn
	 * @param reditdt
	 * @param apptype
	 * @param sign
	 * @param regcode
	 */
	public BusinessAppredit(String appid, String syscode,
			String accreditedcode, String appname, String appver,
			String appdescription, Integer applimitnum, Date applimitdate,
			Date opdt, String reditasn, Date reditdt, String apptype,
			String sign, String regcode) {
		super();
		this.appid = appid;
		this.syscode = syscode;
		this.accreditedcode = accreditedcode;
		this.appname = appname;
		this.appver = appver;
		this.appdescription = appdescription;
		this.applimitnum = applimitnum;
		this.applimitdate = applimitdate;
		this.opdt = opdt;
		this.reditasn = reditasn;
		this.reditdt = reditdt;
		this.apptype = apptype;
		this.sign = sign;
		this.regcode = regcode;
	}


	@Column(name = "regcode", length = 128)
	public String getRegcode() {
		return regcode;
	}



	public void setRegcode(String regcode) {
		this.regcode = regcode;
	}



	@Column(name = "sign", length = 1)
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Column(name = "apptype", length = 1)
	public String getApptype() {
		return apptype;
	}

	public void setApptype(String apptype) {
		this.apptype = apptype;
	}

	@Column(name = "appid", length = 6)
	public String getAppid() {
		return appid;
	}

	public void setAppid(String appId) {
		this.appid = appId;
	}

	@Column(name = "syscode", length = 50)
	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String sysCode) {
		this.syscode = sysCode;
	}

	@Column(name = "accreditedcode", length = 1000)
	public String getAccreditedcode() {
		return accreditedcode;
	}

	public void setAccreditedcode(String accreditedcode) {
		this.accreditedcode = accreditedcode;
	}

	@Column(name = "appname", length = 1000)
	public String getAppname() {
		return appname;
	}

	public void setAppname(String appName) {
		this.appname = appName;
	}

	@Column(name = "appver", length = 100)
	public String getAppver() {
		return appver;
	}

	public void setAppver(String appver) {
		this.appver = appver;
	}

	@Column(name = "appdescription", length = 200)
	public String getAppdescription() {
		return appdescription;
	}

	public void setAppdescription(String appDescription) {
		this.appdescription = appDescription;
	}

	@Column(name = "applimitnum")
	public Integer getApplimitnum() {
		return applimitnum;
	}

	public void setApplimitnum(Integer appLimitNum) {
		this.applimitnum = appLimitNum;
	}

	@Column(name = "applimitdate")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getApplimitdate() {
		return applimitdate;
	}

	public void setApplimitdate(Date appLimitDate) {
		this.applimitdate = appLimitDate;
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

	@Column(name = "reditasn", length = 50)
	public String getReditasn() {
		return reditasn;
	}

	public void setReditasn(String reditasn) {
		this.reditasn = reditasn;
	}

	@Column(name = "reditdt")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getReditdt() {
		return reditdt;
	}

	public void setReditdt(Date reditdt) {
		this.reditdt = reditdt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accreditedcode == null) ? 0 : accreditedcode.hashCode());
		result = prime * result
				+ ((appdescription == null) ? 0 : appdescription.hashCode());
		result = prime * result + ((appid == null) ? 0 : appid.hashCode());
		result = prime * result
				+ ((applimitdate == null) ? 0 : applimitdate.hashCode());
		result = prime * result
				+ ((applimitnum == null) ? 0 : applimitnum.hashCode());
		result = prime * result + ((appname == null) ? 0 : appname.hashCode());
		result = prime * result + ((apptype == null) ? 0 : apptype.hashCode());
		result = prime * result + ((appver == null) ? 0 : appver.hashCode());
		result = prime * result + ((opdt == null) ? 0 : opdt.hashCode());
		result = prime * result
				+ ((reditasn == null) ? 0 : reditasn.hashCode());
		result = prime * result + ((reditdt == null) ? 0 : reditdt.hashCode());
		result = prime * result + ((regcode == null) ? 0 : regcode.hashCode());
		result = prime * result + ((sign == null) ? 0 : sign.hashCode());
		result = prime * result + ((syscode == null) ? 0 : syscode.hashCode());
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
		BusinessAppredit other = (BusinessAppredit) obj;
		if (accreditedcode == null) {
			if (other.accreditedcode != null)
				return false;
		} else if (!accreditedcode.equals(other.accreditedcode))
			return false;
		if (appdescription == null) {
			if (other.appdescription != null)
				return false;
		} else if (!appdescription.equals(other.appdescription))
			return false;
		if (appid == null) {
			if (other.appid != null)
				return false;
		} else if (!appid.equals(other.appid))
			return false;
		if (applimitdate == null) {
			if (other.applimitdate != null)
				return false;
		} else if (!applimitdate.equals(other.applimitdate))
			return false;
		if (applimitnum == null) {
			if (other.applimitnum != null)
				return false;
		} else if (!applimitnum.equals(other.applimitnum))
			return false;
		if (appname == null) {
			if (other.appname != null)
				return false;
		} else if (!appname.equals(other.appname))
			return false;
		if (apptype == null) {
			if (other.apptype != null)
				return false;
		} else if (!apptype.equals(other.apptype))
			return false;
		if (appver == null) {
			if (other.appver != null)
				return false;
		} else if (!appver.equals(other.appver))
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
		if (regcode == null) {
			if (other.regcode != null)
				return false;
		} else if (!regcode.equals(other.regcode))
			return false;
		if (sign == null) {
			if (other.sign != null)
				return false;
		} else if (!sign.equals(other.sign))
			return false;
		if (syscode == null) {
			if (other.syscode != null)
				return false;
		} else if (!syscode.equals(other.syscode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(200);
		builder.append("{appid=");
		builder.append(appid);
		builder.append(", syscode=");
		builder.append(syscode);
		builder.append(", accreditedcode=");
		builder.append(accreditedcode);
		builder.append(", appname=");
		builder.append(appname);
		builder.append(", appver=");
		builder.append(appver);
		builder.append(", appdescription=");
		builder.append(appdescription);
		builder.append(", applimitnum=");
		builder.append(applimitnum);
		builder.append(", applimitdate=");
		builder.append(applimitdate);
		builder.append(", opdt=");
		builder.append(opdt);
		builder.append(", reditasn=");
		builder.append(reditasn);
		builder.append(", reditdt=");
		builder.append(reditdt);
		builder.append(", apptype=");
		builder.append(apptype);
		builder.append(", sign=");
		builder.append(sign);
		builder.append(", regcode=");
		builder.append(regcode);
		builder.append("}");
		return builder.toString();
	}

}
