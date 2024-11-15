package com.mokito.demo.leran.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mokito.demo.leran.model.Employee;
import com.mokito.demo.leran.service.EmployeeService;

@RestController
@RequestMapping("/emps")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	
	@PostMapping
	 @ResponseStatus(HttpStatus.CREATED)
	 public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.saveEmployee(employee);
    }
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Employee> getAllEmployees() {
		return employeeService.getAllEmployees();	
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Employee getById(@PathVariable("id") int id) {
		
		return employeeService.getEmployeeById(id);	
    }
	
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Employee updateEmployee(@PathVariable("id") int id, @RequestBody Employee employee){
		return employeeService.updateEmployee(employee, id);

	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteEmployee(@PathVariable int id) {
			employeeService.deleteEmployee(id);
	}
		
	@GetMapping("/department/{dept}")
	@ResponseStatus(HttpStatus.OK)
	public List<Employee> getByDept(@PathVariable("dept") String dept) {
		
		return employeeService.getEmployeesbyDept(dept);
		
	}
	
	
            
}
