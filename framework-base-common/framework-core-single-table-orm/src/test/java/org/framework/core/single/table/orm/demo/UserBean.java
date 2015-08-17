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
package org.framework.core.single.table.orm.demo;

import org.framework.core.single.table.orm.core.ColumnMapping;
import org.framework.core.single.table.orm.core.TableMapping;
import org.framework.core.single.table.orm.util.Js;

/**
 * This is just a demo class.
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@TableMapping("user")
public class UserBean {

	@ColumnMapping
	private int id;
	@ColumnMapping
	private String username;
	@ColumnMapping
	private String password;
	
	public UserBean() {}

	public UserBean(int id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return Js.toJson(this);
	}
}
