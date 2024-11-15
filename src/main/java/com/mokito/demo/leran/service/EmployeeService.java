package com.mokito.demo.leran.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mokito.demo.leran.model.Employee;
import com.mokito.demo.leran.repository.EmployeeRepo;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepo employeeRepo;
	
	public Employee saveEmployee(Employee employee) {
		
		return employeeRepo.save(employee);
	}
	
	public List<Employee> getAllEmployees() {
		
		return employeeRepo.findAll();
	}
	
	public Employee getEmployeeById(Integer id) {
		
		return employeeRepo.findById(id).orElseThrow(()-> new RuntimeException("Data not Found by id "+id));
	}
	
	public Employee updateEmployee(Employee employee, Integer id) {
		 
		Employee emp = employeeRepo.findById(id).get() ;
		emp.setDept(employee.getDept());
		emp.setName(emp.getName());
		
		return employeeRepo.save(emp);
	}
	
	public void deleteEmployee(Integer id) {
		Employee emp = employeeRepo.findById(id).get();
		employeeRepo.delete(emp);
		
	}
	
	public List<Employee> getEmployeesbyDept(String dept) {
		return employeeRepo.findByDept(dept);
		
	}
	

}
