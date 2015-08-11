package cn.newcapec.foundation.privilege.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.newcapec.framework.core.model.AppBaseModel;

/**
 * 资源实体类
 * 
 * @author andy.li
 * 
 */
@Entity
@Table(name = "t_resource")
public class Resource extends AppBaseModel implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4185219592133607499L;

	/* 资源所属类型编号 */
	private String resourcetypeid;
	/* 菜单编号 */
	private String menuid;
	/* 父节点 */
	private String parentid;
	/* 资源的链接地址 */
	private String url;
	/* 名称 */
	private String resourceName;
	/* 值 */
	private String resourceValue;
	/* 编码 */
	private String code;
	/* 状态 0停用 1启用 */
	private String status;
	/* 排序字段 */
	private String orderby;

	public Resource() {
	}

	public Resource(String url, String resourceName, String status,String menuid) {
		this.url = url;
		this.resourceName = resourceName;
		this.status = status;
		this.menuid=menuid;
	}

	@Column(name = "resourcetype_id", length = 32)
	public String getResourcetypeid() {
		return resourcetypeid;
	}

	public void setResourcetypeid(String resourcetypeid) {
		this.resourcetypeid = resourcetypeid;
	}

	@Column(name = "menu_id", length = 32)
	public String getMenuid() {
		return menuid;
	}

	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}

	@Column(name = "name", length = 32)
	public String getResourceName() {
		return this.resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Column(name = "resourcevalue", length = 512)
	public String getResourceValue() {
		return this.resourceValue;
	}

	public void setResourceValue(String resourceValue) {
		this.resourceValue = resourceValue;
	}

	@Column(name = "url", length = 150)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "code", length = 64)
	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "parent_id", length = 32)
	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Column(name = "status", length = 8)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "order_by", length = 64)
	public String getOrderby() {
		return orderby;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

}