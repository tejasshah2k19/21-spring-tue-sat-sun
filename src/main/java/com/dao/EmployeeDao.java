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
		stmt.update("insert into employee (ename,salary,email,password) values (?,?,?,?)", empBean.getEname(),
				empBean.getSalary(), empBean.getEmail(), empBean.getPassword());
	}

	public List<EmployeeBean> getAllEmployees() {
		return stmt.query("select * from employee", new BeanPropertyRowMapper<EmployeeBean>(EmployeeBean.class));
	}

	public EmployeeBean getEmployeeById(int empId) {

		EmployeeBean employee = null;

		try {
			employee = stmt.queryForObject("select * from employee where empid  = ?", new Object[] { empId },
					new BeanPropertyRowMapper<EmployeeBean>(EmployeeBean.class));
		} catch (Exception e) {

		}
		return employee;
	}

	public boolean deleteEmployeeById(int empId) {

		stmt.update("delete from empskill where empid = ?", empId);

		int i = stmt.update("delete from employee where empid = ?", empId);
		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}

	public int updateEmployee(EmployeeBean employee) {
		return stmt.update("update employee set ename = ? , salary = ? where  empid = ?", employee.getEname(),
				employee.getSalary(), employee.getEmpId());
	}

	public EmployeeBean authenticate(EmployeeBean employee) {

		try {
			employee = stmt.queryForObject("select * from employee where email  = ? and password = ?",
					new Object[] { employee.getEmail(), employee.getPassword() },
					new BeanPropertyRowMapper<EmployeeBean>(EmployeeBean.class));
		} catch (Exception e) {
			employee = null;
		}
		return employee;
	}

	public void updateToken(String authToken, int empId) {

		stmt.update("update employee set authtoken = ? where empid = ?", authToken, empId);
	}

	public boolean validateToken(String authToken) {
		List<EmployeeBean> e = stmt.query("select * from employee where authtoken = ?",
				new Object[] { authToken }, new BeanPropertyRowMapper<EmployeeBean>(EmployeeBean.class));
		if (e.size() == 0)
			return false;
		else
			return true;
	}

}
