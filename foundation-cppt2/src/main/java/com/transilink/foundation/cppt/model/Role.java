package com.transilink.foundation.cppt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.transilink.framework.core.model.CommonModel;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@Table(name = "s_role")
@Entity
public class Role extends CommonModel {

	private static final long serialVersionUID = -503653781273049231L;

	private String role_name;
	private String role_desc;
	private Date start_time;
	private Date end_time;

	@Column(name = "ROLE_NAME", length = 32)
	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	@Column(name = "ROLE_DESC", length = 128)
	public String getRole_desc() {
		return role_desc;
	}

	public void setRole_desc(String role_desc) {
		this.role_desc = role_desc;
	}

	@Column(name = "START_TIME")
	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	@Column(name = "END_TIME")
	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

}
