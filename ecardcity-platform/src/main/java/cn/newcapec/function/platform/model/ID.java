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
 * @author ocean date : 2014-4-4 下午04:02:54
 * @email : zhangjunfang0505@163.com
 * @company : newcapec zhengzhou
 */
@Entity
@Table(name = "base_id")
public class ID extends AppBaseModel implements Serializable {

	private static final long serialVersionUID = -9141918011015822029L;

	private String cusid = null;


	/**
	 *
	 */
	public ID() {
		super();
	}

	@Column(name = "cusid", length = 6)
	public String getCusid() {
		return cusid;
	}


	public void setCusid(String cusid) {
		this.cusid = cusid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cusid == null) ? 0 : cusid.hashCode());
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
		ID other = (ID) obj;
		if (cusid == null) {
			if (other.cusid != null)
				return false;
		} else if (!cusid.equals(other.cusid))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(10);
		builder.append("{cusid=");
		builder.append(cusid);
		builder.append("}");
		return builder.toString();
	}






}
