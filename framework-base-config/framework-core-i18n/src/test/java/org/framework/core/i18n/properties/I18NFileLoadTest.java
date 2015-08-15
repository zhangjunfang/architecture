/**
 * 
 */
package org.framework.core.i18n.properties;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 描述：
 * 
 * @author ocean 2015年8月15日 email：zhangjunfang0505@163.com
 */
public class I18NFileLoadTest {

	public static void main(String[] args) {
		//new LoadProperties();
		// loadI18NFile("message_zh_cn.properties");
		String value = LoadProperties.getLocaleMessage("name", new Locale("zh",
				"cn"), new String[] { "张伯雨", "李燕菲" });
		System.err.println(value);
		value = LoadProperties.getLocaleMessage("name", new Locale("en", "us"),
				new String[] { "zhangboyu", "liyanfei" });
		System.err.println(value);
		value = LoadProperties.getLocaleMessage("name1",
				new Locale("zh", "cn"), new String[] { "张伯雨", "李燕菲" });
		System.err.println(value);
		value = LoadProperties.getLocaleMessage("name1",
				new Locale("en", "us"),
				new String[] { "zhangboyu", "liyanfei" });
		System.err.println(value);
		
		
		System.err.println(LoadProperties.CONCURRENT_HASH_MAP.size());
		System.err.println(LoadProperties.CONCURRENT_HASH_MAP.get(
				new Locale("zh", "cn")).size());
		System.err.println(LoadProperties.CONCURRENT_HASH_MAP.get(
				new Locale("en", "us")).size());
		
		
		
		value = LoadProperties.getLocaleMessage("date",
				new Locale("en", "us"),
				new String[] { DateFormat.getDateInstance().format(new Date())  });
		System.err.println(value);
		
		System.out.println(new String().trim());
		System.out.println("".trim());
		System.out.println("fsdfsdfsdfds #sdfsdfsdfsdf###".substring(0,
				"fsdfsdfsdfds #sdfsdfsdfsdf###".indexOf("#")));

	}

}
