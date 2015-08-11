package cn.newcapec.foundation.privilege.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.newcapec.framework.core.model.AppBaseModel;

/**
 * 菜单实体类
 * 
 * @author: andy.li
 */
@Entity
@Table(name = "t_menu")
public class Menu extends AppBaseModel implements Serializable {

	private static final long serialVersionUID = 8101378185861361450L;
	/* 菜单名称 */
	private String name;
	/* 菜单URL */
	private String url;
	/* 父菜单 */
	private String parentId;
	/* 排序 */
	private String sortby;
	/* 是否是父节点 */
	private String isParent;
	/* 图标 */
	private String icon;
	/*是否可见*/
	private String visible;

	public Menu() {
	}

	public Menu(String name, String url, String parentId,String visible) {
		this.name = name;
		this.url = url;
		this.parentId = parentId;
		this.visible=visible;
	}
	

	public Menu(String name, String url, String parentId, String icon,String visible) {
		this.name = name;
		this.url = url;
		this.parentId = parentId;
		this.icon = icon;
		this.visible=visible;
	}
	
	


	@Column(name = "isParent", length = 32)
	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	@Column(name = "name", length = 82)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "url", length = 82)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "parent_id", length = 32)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "sortby", length = 8)
	public String getSortby() {
		return sortby;
	}

	public void setSortby(String sortby) {
		this.sortby = sortby;
	}

	@Column(name = "icon", length = 300)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Column(name = "visible", length = 20)
	public String getVisible() {
		return visible;
	}

	public void setVisible(String visible) {
		this.visible = visible;
	}
	
	

}
