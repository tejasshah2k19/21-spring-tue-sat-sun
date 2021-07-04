package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bean.EmployeeBean;
import com.bean.ResponseBean;
import com.dao.EmployeeDao;
import com.service.AuthTokenService;

@RestController
@RequestMapping("/api/")
public class EmployeeController {

	@Autowired
	AuthTokenService authTokenService;

	@Autowired
	EmployeeDao employeeDao;

	@PostMapping("/employee")
	public ResponseBean<EmployeeBean> saveEmployee(EmployeeBean employee) {

		employeeDao.insertEmployee(employee);
		return ResponseBean.data(employee, "Employee Save", 200);
	}

	@GetMapping("/employees")
	public ResponseBean<List<EmployeeBean>> getAllEmps(
			@RequestParam(defaultValue = "NA", name = "authToken") String authToken) {

		System.out.println("token => " + authToken);

		if (authToken == null || authToken.equals("NA")) {
			return ResponseBean.data(null, "Invalid user", -1);
		} else if (employeeDao.validateToken(authToken)) {
			return ResponseBean.data(employeeDao.getAllEmployees(), "Employees Retrieved", 200);

		} else {
			return ResponseBean.data(null, "Invalid user", -1);
		}
		// db

		//
	}

//	@GetMapping("/employee/{empId}")
//	public ResponseBean<EmployeeBean> getEmployeeById(@PathVariable("empId") int empId) {
//		EmployeeBean employee = employeeDao.getEmployeeById(empId);
//
//		ResponseBean<EmployeeBean> rb = new ResponseBean<>();
//
//		if (employee == null) {
//			rb.setMsg("Invalid employee Id");
//			rb.setStatus(-1);
//			
//		
//		} else {
//			rb.setData(employee);
//			rb.setStatus(200);
//			rb.setMsg("Employee retrieved");
//
//		}
//		return rb;
//	}
	

	//method  

	@GetMapping("/employee/{empId}")
	public ResponseBean<EmployeeBean> getEmployeeById(@PathVariable("empId") int empId,
			@RequestParam(defaultValue = "NA", name = "authToken") String authToken) {

		if (authToken == null || authToken.equals("NA") || employeeDao.validateToken(authToken) == false) {
			return ResponseBean.data(null, "Invalid user", -1);
		}

		EmployeeBean employee = employeeDao.getEmployeeById(empId);

		if (employee == null) {

			return ResponseBean.data(employee, "Invalid EmployeeId", -1);

		} else {
			return ResponseBean.data(employee, "Employee Successfully retrieved", 200);

		}
	}

	@DeleteMapping("/employee/{empId}")
	public ResponseBean deleteEmployeeById(@PathVariable("empId") int empId) {

		if (employeeDao.deleteEmployeeById(empId) == true) {
			return ResponseBean.data(null, "Employee Deleted", 200);
		} else {
			return ResponseBean.data(null, "Invalid Employee Id", -1);
		}
	}

	@PutMapping("/employee")
	public ResponseBean<EmployeeBean> updateEmployee(EmployeeBean employee) {
		System.out.println(employee.getEmpId());
		if (employeeDao.updateEmployee(employee) != 0) {
			return ResponseBean.data(employee, "Employee updated", 200);
		} else {
			return ResponseBean.data(employee, "Invalid Data", -1);
		}
	}

	@PostMapping("/login")
	public ResponseBean<EmployeeBean> login(EmployeeBean employee) {

		employee = employeeDao.authenticate(employee);
		if (employee == null) {
			return ResponseBean.data(null, "Invalid Credentials", -1);
		} else {
			// success
			//jwt 
			String authToken = authTokenService.generateToken();
			employeeDao.updateToken(authToken, employee.getEmpId());
			employee.setAuthToken(authToken);

			return ResponseBean.data(employee, "Login done", 200);
		}

	}
}
