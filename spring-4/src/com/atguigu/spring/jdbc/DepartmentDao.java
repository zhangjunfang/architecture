package com.atguigu.spring.jdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * ���Ƽ�ʹ�� JdbcDaoSupport, ���Ƽ�ֱ��ʹ�� JdbcTempate ��Ϊ Dao ��ĳ�Ա����
 */
@Repository
public class DepartmentDao extends JdbcDaoSupport{

	@Autowired
	public void setDataSource2(DataSource dataSource){
		setDataSource(dataSource);
	}

	public Department get(Integer id){
		String sql = "SELECT id, dept_name name FROM departments WHERE id = ?";
		RowMapper<Department> rowMapper = new BeanPropertyRowMapper<>(Department.class);
		return getJdbcTemplate().queryForObject(sql, rowMapper, id);
	}
	
}
