package cn.newcapec.framework.log.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import cn.newcapec.framework.core.model.BaseModel;

/**
 * 操作日志
 * 
 */ 
@Entity
@Table(name = "t_operateLog")
public class OperateLog extends BaseModel implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	/* 操作员*/
	private String username;
	/*等级*/
	private int level;
	/* 标题*/
	private String title;
	private String ip;
	/* 描述*/
	private String description;

	@Column(name = "title", length = 80)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "description", length = 800)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Column(name = "username", length = 60)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "levels", length = 80)
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Column(name = "ips", length = 80)
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}


	@Override
	public String toString() {
		return new StringBuffer().append("name=").append(this.username).append(",title=").append(this.title).toString();
	}
}
