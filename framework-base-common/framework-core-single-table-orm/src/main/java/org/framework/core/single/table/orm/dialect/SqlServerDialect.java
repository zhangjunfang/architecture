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
package org.framework.core.single.table.orm.dialect;

import java.util.List;
import java.util.Map;

/**
 * SqlServerDialect for Microsoft SqlServer.
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class SqlServerDialect extends DefaultDialect {

	@Override
	public String paginate(String table, Map<String, Object> map,
			int pageNumber, int pageSize, List<Object> params) {
		int end = pageNumber * pageSize;
		if (end <= 0)
			end = pageSize;
		int begin = (pageNumber - 1) * pageSize;
		if (begin < 0)
			begin = 0;
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ( select row_number() over (order by tempcolumn) temprownumber, * from ( select top ")
		   .append(end)
		   .append(" tempcolumn=0,")
		   .append(select(table, map, params))
		   .append(")vip)mvp where temprownumber>")
		   .append(begin);
		return sql.toString();
	}
}
