package com.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bean.EmployeeBean;

@Repository
public class EmployeeDao {
	@Autowired
	JdbcTemplate stmt;

	public void insertEmployee(EmployeeBean empBean) {
		stmt.update("insert into employee (ename,salary) values (?,?)", empBean.getEname(), empBean.getSalary());
	}
	
	
	
	public List<EmployeeBean> getAllEmployees(){
		return stmt.query("select * from employee", new BeanPropertyRowMapper<EmployeeBean>(EmployeeBean.class));
	}
	
	
	
}
