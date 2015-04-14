package com.transilink.foundation.cppt.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.transilink.framework.core.model.CommonModel;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@Table(name = "s_user")
@Entity
public class User extends CommonModel {

	private static final long serialVersionUID = 1968235354973491611L;

	private String user_name;
	private String password;

	private String real_name;
	private Integer sex;

	private Date create_time;

	@Column(name = "CREATE_TIME")
	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	@Column(name = "REAL_NAME", length = 32)
	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	@Column(name = "SEX", length = 2)
	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	@Column(name = "USER_NAME", length = 32)
	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	@Column(name = "PASSWORD", length = 32)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
