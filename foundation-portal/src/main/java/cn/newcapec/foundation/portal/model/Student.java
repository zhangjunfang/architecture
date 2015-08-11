package cn.newcapec.foundation.portal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.newcapec.framework.core.handler.springDatebind.CustomDateDeserializer;
import cn.newcapec.framework.core.handler.springDatebind.CustomDateSerializer;
import cn.newcapec.framework.core.model.CommonModel;

/**
 * 学生实体类
 * 
 * @author andy
 * 
 */
@Table(name = "t_student")
@Entity
public class Student extends CommonModel {
	private static final long serialVersionUID = 1L;
	
	/* 编号 */
	@Column(name = "no", length = 30)
	private String no;
	/* 班级 */
	@Column(name = "class_name", length = 30)
	private String className;
	/* 性别 */
	@Column(name = "sex", length = 10)
	private String sex;
	/* 名字 */
	@Column(name = "name", length = 25)
	private String name;
	/* 图片 */
	@Column(name = "photo", length = 62)
	private String photo;
	/* 描述 */
	@Column(name = "description", length = 500)
	private String description;
	/**
	 * 生日日期
	 */
	@JsonSerialize(using=CustomDateSerializer.class)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="birthday")
	private Date birthday;
	
	
	
	public String getNo() {
		return no;
	}

	/**
	 * @param no
	 *            the no to set
	 */
	public void setNo(String no) {
		this.no = no;
	}

	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the photo
	 */
	public String getPhoto() {
		return photo;
	}

	/**
	 * @param photo
	 *            the photo to set
	 */
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	/**
	 * @return the birthday
	 */
	public Date getBirthday() {
		return birthday;
	}

	/**
	 * @param birthday the birthday to set
	 */
	@JsonDeserialize(using=CustomDateDeserializer.class)
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	
	
	
}
