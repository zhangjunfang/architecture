package cn.newcapec.function.platform.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.newcapec.framework.core.model.CommonModel;

/**
 * 菜单目录表
 *
 * @author chongfeigao
 *
 */
@Entity
@Table(name = "base_menu")
public class Menu extends CommonModel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1399875599793424185L;
	/* 菜单标识 */
	@Column(name = "menuid", length = 8)
	private String menuid;
	/* 菜单名称 */
	@Column(name = "menuname", length = 30)
	private String menuname;
	/* 小图标文件名(URL) */
	@Column(name = "smallicon", length = 80)
	private String smallicon;
	/* 导航链接(URL)) */
	@Column(name = "navlink", length = 80)
	private String navlink;
	/* 描述 */
	@Column(name = "description", length = 80)
	private String description;
	/* 大图标文件名(URL) */
	@Column(name = "largeicon", length = 80)
	private String largeicon;
	/* 上级菜单标识(空表示顶级菜单，顶级菜单为自系统列表) */
	@Column(name = "parentmenuid", length = 6)
	private String parentmenuid;
	/* 排序ID */
	@Column(name = "sortid")
	private String sortid;
	/* 是否显示（0不显示。1显示） */
	@Column(name = "isvisible")
	private String isvisible;
	/* 所属应用模块 */
	@Column(name = "subsystemid", length = 30)
	private String subsystemid;
	/* 所属应用包 */
	@Column(name = "appid", length = 6)
	private String appid;

	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	public String getMenuname() {
		return menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	public String getSmallicon() {
		return smallicon;
	}

	public void setSmallicon(String smallicon) {
		this.smallicon = smallicon;
	}

	public String getNavlink() {
		return navlink;
	}

	public void setNavlink(String navlink) {
		this.navlink = navlink;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLargeicon() {
		return largeicon;
	}

	public void setLargeicon(String largeicon) {
		this.largeicon = largeicon;
	}

	public String getParentmenuid() {
		return parentmenuid;
	}

	public void setParentmenuid(String parentmenuid) {
		this.parentmenuid = parentmenuid;
	}

	public String getSortid() {
		return sortid;
	}

	public void setSortid(String sortid) {
		this.sortid = sortid;
	}

	public String getIsvisible() {
		return isvisible;
	}

	public void setIsvisible(String isvisible) {
		this.isvisible = isvisible;
	}

	public String getSubsystemid() {
		return subsystemid;
	}

	public void setSubsystemid(String subsystemid) {
		this.subsystemid = subsystemid;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{menuid=");
		builder.append(menuid);
		builder.append(", menuname=");
		builder.append(menuname);
		builder.append(", smallicon=");
		builder.append(smallicon);
		builder.append(", navlink=");
		builder.append(navlink);
		builder.append(", description=");
		builder.append(description);
		builder.append(", largeicon=");
		builder.append(largeicon);
		builder.append(", parentmenuid=");
		builder.append(parentmenuid);
		builder.append(", sortid=");
		builder.append(sortid);
		builder.append(", isvisible=");
		builder.append(isvisible);
		builder.append(", subsystemid=");
		builder.append(subsystemid);
		builder.append(", appid=");
		builder.append(appid);
		builder.append("}");
		return builder.toString();
	}

}