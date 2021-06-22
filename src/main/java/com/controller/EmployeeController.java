package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.EmployeeBean;
import com.dao.EmployeeDao;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeDao employeeDao;
	
	@PostMapping("/employee")
	public EmployeeBean saveEmployee(EmployeeBean employee) {
		
		employeeDao.insertEmployee(employee);
		return employee;
	}
	
	@GetMapping("/employees")
	public List<EmployeeBean> getAllEmps(){
		return  employeeDao.getAllEmployees();
	}
	
}
