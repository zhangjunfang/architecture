/**
 * 
 */
package org.framework.core.spi.test.impl;

import org.framework.core.spi.common.Adaptive;
import org.framework.core.spi.test.Serializer;

/**
 * 描述：
 * 
 * @author ocean
 * 2015年8月17日
 *  email：zhangjunfang0505@163.com
 */
@Adaptive("json")
public class Json implements Serializer {

	
	@Override
	public String serialize() {
		
		return "serialize--json";
	}

	
	@Override
	public String deserialize() {
		
		return "deserialize--json";
	}
 
}
