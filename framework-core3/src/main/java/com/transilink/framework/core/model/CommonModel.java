package com.transilink.framework.core.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import com.transilink.framework.core.model.datacontainer.DataContainer;
import com.transilink.framework.core.model.datacontainer.DataObject;
import com.transilink.framework.core.model.datacontainer.Property;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@MappedSuperclass
public class CommonModel extends DataContainer implements DataObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5954303371203855137L;

	public CommonModel() {
		init(new Property[][] { Base2ModelProperty.values() });
	}

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "uuid")
	@GeneratedValue(generator = "idGenerator")
	@Column(name = "id", nullable = false, length = 32)
	public String getId() {
		return (String) super.getValue(Base2ModelProperty.id);
	}

	public void setId(String id) {
		super.setValue(Base2ModelProperty.id, id);
	}

	public boolean equals(Object obj) {
		if ((obj != null) && ((obj instanceof CommonModel))) {
			String tempId = ((CommonModel) obj).getId();
			if (tempId == null) {
				return false;
			}
			return tempId.equals(getId());
		}
		return false;
	}

	public static enum Base2ModelProperty implements Property {
		id(String.class);

		Class<?> type;

		private Base2ModelProperty(Class<?> type) {
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