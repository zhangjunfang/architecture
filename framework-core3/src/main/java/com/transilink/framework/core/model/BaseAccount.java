package com.transilink.framework.core.model;

import java.io.Serializable;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class BaseAccount implements Serializable {
	private static final long serialVersionUID = 242416230124446179L;
	public String name;
	private String password;
	private String operatorId;
	private Boolean enabled;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}