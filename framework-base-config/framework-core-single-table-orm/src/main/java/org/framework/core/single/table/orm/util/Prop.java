/**
 * Copyright (c) 2011-2015, @author ocean(zhangjufang0505@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.framework.core.single.table.orm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author ocean(zhangjufang0505@163.com)
 *
 */
/**
 * Prop loads properties from files.
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class Prop {

	private Properties prop = null;
	
	/**
	 * Load Properties from class path.
	 */
	public Prop(String file) {
		this(file,"UTF-8");
	}
	
	/**
	 * Load Properties from class path.
	 */
	public Prop(String file,String encoding) {
		InputStream input = null;
		try {
			input = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
			if(input==null)
				throw new RuntimeException("fail to load file '"+file+"' from class path.");
			prop = new Properties();
			prop.load(new InputStreamReader(input, encoding));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if(input!=null){try {input.close();input = null;} catch (IOException e) {e.printStackTrace();}}
		}
	}
	
	public Prop(File file) {
		this(file, "UTF-8");
	}
	
	public Prop(File file,String encoding) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			prop = new Properties();
			prop.load(new InputStreamReader(fis, encoding));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if(fis!=null){try {fis.close();fis = null;} catch (IOException e) {e.printStackTrace();}}
		}
	}
	
	public Properties getProperties() {
		return prop;
	}
}
