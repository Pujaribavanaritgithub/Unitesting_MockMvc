package com.mokito.demo.leran.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mokito.demo.leran.model.Employee;
import com.mokito.demo.leran.repository.EmployeeRepo;
import com.mokito.demo.leran.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {
	
	@InjectMocks
	private EmployeeService employeeService;
	
	@Mock
	private EmployeeRepo employeeRepo;
	
	private Employee emp;
	private Employee emp1;
	
	@BeforeEach
	void init() {
		emp = new Employee();
		emp.setId(1);
		emp.setName("Bavana");
		emp.setDept("IT");
		
		emp1 = new Employee();
		emp1.setId(2);
		emp1.setName("Darling");
		emp1.setDept("Development");	
	}
	
	@Test
	@DisplayName("It will save the employee details in database")
	void save() {
		int id = emp.getId();
		when(employeeRepo.save(any(Employee.class))).thenReturn(emp);
		Employee saveEmployee = employeeService.saveEmployee(emp);
		
		assertNotNull(saveEmployee);
		assertEquals(id, saveEmployee.getId());
	}
	
	@Test
	@DisplayName("Get the all employess")
	void getAllEmployees() {
		
		List<Employee> list = new ArrayList<Employee>();
		list.add(emp);
		list.add(emp1);
		
		when(employeeRepo.findAll()).thenReturn(list);
		
		List<Employee> list2 = employeeService.getAllEmployees();
		
		assertNotNull(list2);
		assertEquals(2, list2.size());	
	}
	
	@Test
	@DisplayName("To get Employee deatails by its Id")
	void getEmployeeById() {
		when(employeeRepo.findById(anyInt())).thenReturn(Optional.of(emp));
		Employee newEmp = employeeService.getEmployeeById(emp.getId());
		
		assertNotNull(newEmp);
		assertEquals("Bavana", newEmp.getName());
	}
	
	@Test
	@DisplayName("Not Found Data of Id throws Exception")
	void getByIdException() {
		when(employeeRepo.findById(1)).thenReturn(Optional.of(emp));
		assertThrows(RuntimeException.class, ()->
		employeeService.getEmployeeById(2)
		);	
	}
	
	@Test
	@DisplayName("Update the employee data of given Id")
	void update() {
		when(employeeRepo.findById(anyInt())).thenReturn(Optional.of(emp));
		when(employeeRepo.save(any(Employee.class))).thenReturn(emp);
		emp.setDept("Testing");
		
		Employee updateEmployee = employeeService.updateEmployee(emp, 1);
		
		assertEquals("Testing" ,updateEmployee.getDept());
		
	}
	
	@Test
	@DisplayName("Delete the data from database of given Id")
	void delete() {
		when(employeeRepo.findById(anyInt())).thenReturn(Optional.of(emp));
		doNothing().when(employeeRepo).delete(any(Employee.class));
		
		employeeService.deleteEmployee(1);
		
		verify(employeeRepo, times(1)).delete(emp);
		
	}

}
