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
package org.framework.core.single.table.orm.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

/**
 * DbUtil.
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class DbUtil {
	
	private static final Object[] NULL_PARAMS = new Object[0];
	
	private static PreparedStatement prepareStatement(Connection con,String sql,Object...params){
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
			for(int i=0;i<params.length;i++){
				ps.setObject(i+1, params[i]);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ps;
	}
	
	private static CachedRowSet query(PreparedStatement ps){
		CachedRowSet crs = null;
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			crs = new CachedRowSetImpl();
			crs.populate(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, null, null);
		}
		return crs;
	}
	
	public static CachedRowSet query(Connection con,String sql,Object...params){
		return query(prepareStatement(con,sql,params));
	}
	
	public static CachedRowSet query(Connection con,String sql,List<Object> params){
		return query(con, sql, params.toArray());
	}
	
	public static CachedRowSet query(Connection con,String sql){
		return query(con, sql, NULL_PARAMS);
	}
	
	public static boolean update(Connection con,String sql,Object...params){
		boolean flag = true;
		PreparedStatement ps = prepareStatement(con,sql,params);
		try {
			ps.execute();
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		} finally {
			close(null, ps, null);
		}
		return flag;
	}
	
	public static boolean update(Connection con,String sql,List<Object> params){
		return update(con,sql,params.toArray());
	}
	
	public static boolean update(Connection con,String sql){
		return update(con, sql, NULL_PARAMS);
	}
	
	private static Object getGeneratedKey(PreparedStatement ps) {
		Object key = null;
		ResultSet rs = null;
		try {
			rs = ps.getGeneratedKeys();
			if(rs.next()){
				key = rs.getObject(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, null, null);
		}
		return key;
	}
	
	public static boolean updateAndRetrieveKey(Object keyContainer,Connection con,String sql,Object...params){
		boolean flag = true;
		PreparedStatement ps = prepareStatement(con, sql, params);
		try {
			ps.execute();
			keyContainer = getGeneratedKey(ps);
		} catch (SQLException e) {
			flag = false;
			e.printStackTrace();
		} finally {
			close(null, ps, null);
		}
		return flag;
	}
	
	public static boolean updateAndRetrieveKey(Object keyContainer,Connection con,String sql,List<Object> params){
		return updateAndRetrieveKey(keyContainer,con,sql,params.toArray());
	}
	
	public static boolean updateAndRetrieveKey(Object keyContainer,Connection con,String sql){
		return updateAndRetrieveKey(keyContainer,con, sql, NULL_PARAMS);
	}
	
	public static boolean validate(Connection con,String sql,Object...params) {
		CachedRowSet crs = query(con, sql, params);
		try {
			if(crs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(crs, null, null);
		}
		return false;
	}
	
	public static boolean validate(Connection con,String sql,List<Object> params) {
		return validate(con, sql, params.toArray());
	}
	
	public static boolean validate(Connection con,String sql){
		return validate(con, sql, NULL_PARAMS);
	}
	
	public static long count(Connection con,String sql,Object...params) {
		CachedRowSet crs = query(con, sql, params);
		long count = -1;
		try {
			if(crs.next())
				count = crs.getLong(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(crs, null, null);
		}
		return count;
	}
	
	public static long count(Connection con,String sql,List<Object> params) {
		return count(con, sql, params.toArray());
	}
	
	public static long count(Connection con,String sql){
		return count(con, sql, NULL_PARAMS);
	}
	
	public static void close(Connection con) {
		close(null, null, con);
	}
	
	public static void close(ResultSet rs,Statement sm,Connection con) {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (sm != null) {
				sm.close();
				sm = null;
			}
			if (con != null) {
				con.close();
				con = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
