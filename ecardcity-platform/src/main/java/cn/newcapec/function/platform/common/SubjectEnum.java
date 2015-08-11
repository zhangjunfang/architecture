/**
 *
 */
package cn.newcapec.function.platform.common;

import java.io.Serializable;

/**
 * @author ocean date : 2014-4-16 下午05:45:57 email : zhangjunfang0505@163.com
 *         Copyright : newcapec zhengzhou
 */
public enum SubjectEnum implements Serializable {

	USER("user"), LEGALPERSON("legalPerson"),AUTHORITY("authority");

	private SubjectEnum(String name) {
		this.alias = name;
	}

	private String alias = "";

	public String getAlias() {
		return alias;
	}

}
