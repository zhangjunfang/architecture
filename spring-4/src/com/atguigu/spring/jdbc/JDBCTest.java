package com.atguigu.spring.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public class JDBCTest {
	
	private ApplicationContext ctx = null;
	private JdbcTemplate jdbcTemplate;
	private EmployeeDao employeeDao;
	private DepartmentDao departmentDao;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	{
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
		employeeDao = ctx.getBean(EmployeeDao.class);
		departmentDao = ctx.getBean(DepartmentDao.class);
		namedParameterJdbcTemplate = ctx.getBean(NamedParameterJdbcTemplate.class);
	}
	
	/**
	 * ʹ�þ�������ʱ, ����ʹ�� update(String sql, SqlParameterSource paramSource) �������и��²���
	 * 1. SQL ����еĲ��������������һ��!
	 * 2. ʹ�� SqlParameterSource �� BeanPropertySqlParameterSource ʵ������Ϊ����. 
	 */
	@Test
	public void testNamedParameterJdbcTemplate2(){
		String sql = "INSERT INTO employees(last_name, email, dept_id) "
				+ "VALUES(:lastName,:email,:dpetId)";
		
		Employee employee = new Employee();
		employee.setLastName("XYZ");
		employee.setEmail("xyz@sina.com");
		employee.setDpetId(3);
		
		SqlParameterSource paramSource = new BeanPropertySqlParameterSource(employee);
		namedParameterJdbcTemplate.update(sql, paramSource);
	}
	
	/**
	 * ����Ϊ����������. 
	 * 1. �ô�: ���ж������, ������ȥ��Ӧλ��, ֱ�Ӷ�Ӧ������, ����ά��
	 * 2. ȱ��: ��Ϊ�鷳. 
	 */
	@Test
	public void testNamedParameterJdbcTemplate(){
		String sql = "INSERT INTO employees(last_name, email, dept_id) VALUES(:ln,:email,:deptid)";
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("ln", "FF");
		paramMap.put("email", "ff@atguigu.com");
		paramMap.put("deptid", 2);
		
		namedParameterJdbcTemplate.update(sql, paramMap);
	}
	
	@Test
	public void testDepartmentDao(){
		System.out.println(departmentDao.get(1));
	}
	
	@Test
	public void testEmployeeDao(){
		System.out.println(employeeDao.get(1));
	}
	
	/**
	 * ��ȡ�����е�ֵ, ����ͳ�Ʋ�ѯ
	 * ʹ�� queryForObject(String sql, Class<Long> requiredType) 
	 */
	@Test
	public void testQueryForObject2(){
		String sql = "SELECT count(id) FROM employees";
		long count = jdbcTemplate.queryForObject(sql, Long.class);
		
		System.out.println(count);
	}
	
	/**
	 * �鵽ʵ����ļ���
	 * ע����õĲ��� queryForList ����
	 */
	@Test
	public void testQueryForList(){
		String sql = "SELECT id, last_name lastName, email FROM employees WHERE id > ?";
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		List<Employee> employees = jdbcTemplate.query(sql, rowMapper,5);
		
		System.out.println(employees);
	}
	
	/**
	 * �����ݿ��л�ȡһ����¼, ʵ�ʵõ���Ӧ��һ������
	 * ע�ⲻ�ǵ��� queryForObject(String sql, Class<Employee> requiredType, Object... args) ����!
	 * ����Ҫ���� queryForObject(String sql, RowMapper<Employee> rowMapper, Object... args)
	 * 1. ���е� RowMapper ָ�����ȥӳ����������, ���õ�ʵ����Ϊ BeanPropertyRowMapper
	 * 2. ʹ�� SQL ���еı�����������������������ӳ��. ���� last_name lastName
	 * 3. ��֧�ּ�������. JdbcTemplate ������һ�� JDBC ��С����, ������ ORM ���
	 */
	@Test
	public void testQueryForObject(){
		String sql = "SELECT id, last_name lastName, email, dept_id as \"department.id\" FROM employees WHERE id = ?";
		RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
		Employee employee = jdbcTemplate.queryForObject(sql, rowMapper, 1);
		
		System.out.println(employee);
	}
	
	/**
	 * ִ����������: ������ INSERT, UPDATE, DELETE
	 * ���һ�������� Object[] �� List ����: ��Ϊ�޸�һ����¼��Ҫһ�� Object ������, ��ô����������Ҫ��� Object ��������
	 */
	@Test
	public void testBatchUpdate(){
		String sql = "INSERT INTO employees(last_name, email, dept_id) VALUES(?,?,?)";
		
		List<Object[]> batchArgs = new ArrayList<>();
		
		batchArgs.add(new Object[]{"AA", "aa@atguigu.com", 1});
		batchArgs.add(new Object[]{"BB", "bb@atguigu.com", 2});
		batchArgs.add(new Object[]{"CC", "cc@atguigu.com", 3});
		batchArgs.add(new Object[]{"DD", "dd@atguigu.com", 3});
		batchArgs.add(new Object[]{"EE", "ee@atguigu.com", 2});
		
		jdbcTemplate.batchUpdate(sql, batchArgs);
	}
	
	/**
	 * ִ�� INSERT, UPDATE, DELETE
	 */
	@Test
	public void testUpdate(){
		String sql = "UPDATE employees SET last_name = ? WHERE id = ?";
		jdbcTemplate.update(sql, "Jack", 5);
	}
	
	@Test
	public void testDataSource() throws SQLException {
		DataSource dataSource = ctx.getBean(DataSource.class);
		System.out.println(dataSource.getConnection());
	}

}
