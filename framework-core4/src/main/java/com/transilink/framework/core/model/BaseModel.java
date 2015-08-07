package com.transilink.framework.core.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.transilink.framework.core.model.datacontainer.DataContainer;
import com.transilink.framework.core.model.datacontainer.DataObject;
import com.transilink.framework.core.model.datacontainer.Property;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class BaseModel extends DataContainer implements DataObject {
	public BaseModel() {
		init(new Property[][] { BaseModelProperty.values() });
	}

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id", nullable = false, length = 32)
	public String getId() {
		return (String) super.getValue(BaseModelProperty.id);
	}

	public void setId(String id) {
		super.setValue(BaseModelProperty.id, id);
	}

	@Column(name = "create_date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateDate() {
		return (Date) super.getValue(BaseModelProperty.createDate);
	}

	public void setCreateDate(Date createDate) {
		super.setValue(BaseModelProperty.createDate, createDate);
	}

	@Column(name = "update_date")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdateDate() {
		return (Date) super.getValue(BaseModelProperty.updateDate);
	}

	public void setUpdateDate(Date updateDate) {
		super.setValue(BaseModelProperty.updateDate, updateDate);
	}

	@Column(name = "create_by", length = 32)
	public String getCreateBy() {
		return (String) super.getValue(BaseModelProperty.createBy);
	}

	public void setCreateBy(String createBy) {
		super.setValue(BaseModelProperty.createBy, createBy);
	}

	@Column(name = "update_by", length = 32)
	public String getUpdateBy() {
		return (String) super.getValue(BaseModelProperty.updateBy);
	}

	public void setUpdateBy(String updateBy) {
		super.setValue(BaseModelProperty.updateBy, updateBy);
	}

	@Column(name = "version")
	public Integer getVersion() {
		return (Integer) super.getValue(BaseModelProperty.version);
	}

	public void setVersion(Integer version) {
		super.setValue(BaseModelProperty.version, version);
	}

	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof BaseModel))) {
			String tempId = ((BaseModel) obj).getId();
			if (tempId == null) {
				return false;
			}
			return tempId.equals(getId());
		}
		return false;
	}

	public static enum BaseModelProperty implements Property {
		id(String.class), createDate(Date.class), updateDate(Date.class), createBy(
				String.class), updateBy(String.class), version(Integer.class);

		Class<?> type;

		private BaseModelProperty(Class<?> type) {
			this.type = type;
		}

		public String getName() {
			return name();
		}

		public Class<?> getType() {
			return this.type;
		}
	}
}