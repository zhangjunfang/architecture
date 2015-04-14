package com.transilink.foundation.cppt.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.transilink.framework.core.model.CommonModel;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@Table(name = "s_menu")
@Entity
public class Menu extends CommonModel {

	private static final long serialVersionUID = -8340012625413254838L;

	private String pid;
	private String menu_name;
	private String menu_url;
	private Integer sort;
	private Integer isParent;

	private String path;

	@Column(name = "PATH", length = 1000)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column(name = "ISPARENT")
	public Integer getIsParent() {
		return isParent;
	}

	public void setIsParent(Integer isParent) {
		this.isParent = isParent;
	}

	@Column(name = "PID", length = 32)
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Column(name = "MENU_NAME", length = 32)
	public String getMenu_name() {
		return menu_name;
	}

	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}

	@Column(name = "MENU_URL", length = 128)
	public String getMenu_url() {
		return menu_url;
	}

	@Column(name = "SORT")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}

}
