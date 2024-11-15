package com.mokito.demo.leran.controllerTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ObjectInputFilter.Status;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.MediaType;
import static org.hamcrest.CoreMatchers.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mokito.demo.leran.model.Employee;
import com.mokito.demo.leran.service.EmployeeService;

@WebMvcTest
public class EmployeeControllerTest {
	
	@MockBean
	private EmployeeService employeeService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Employee emp;
	private Employee emp1;
	private Employee emp2;
	
	@BeforeEach
	void init() {
		emp = new Employee();
		emp.setId(1);
		emp.setName("Bavana");
		emp.setDept("IT");
		
		emp1 = new Employee();
		emp1.setId(2);
		emp1.setName("Radha");
		emp1.setDept("Testing");
		
		emp2 = new Employee();
		emp2.setId(3);
		emp2.setName("Krishna");
		emp2.setDept("Testing");
	}
	@Test
	@DisplayName("Create the User")
	void createEmployee() throws Exception {
		
		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(emp);
		
		this.mockMvc.perform(post("/emps")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(emp)))
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.id", is(emp.getId())))
		.andExpect(jsonPath("$.name", is(emp.getName())))
		.andExpect(jsonPath("$.dept", is(emp.getDept())));
	}
	
	@Test
	@DisplayName("Get the Employees")
	void getAllEmployee() throws Exception {
		
		List<Employee> list = new ArrayList<Employee>();
		list.add(emp);
		list.add(emp1);
	
		when(employeeService.getAllEmployees()).thenReturn(list);
		
		this.mockMvc.perform(get("/emps"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()", is(list.size())));
	}
	
	@Test
	@DisplayName("Get the Employee by id")
	void fetchById() throws Exception {
		when(employeeService.getEmployeeById(anyInt())).thenReturn(emp);
		
		this.mockMvc.perform(get("/emps/{id}",1))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(emp.getId())))
		.andExpect(jsonPath("$.name", is(emp.getName())))
		.andExpect(jsonPath("$.dept", is(emp.getDept())));
		
	}
	
	@Test
	@DisplayName("update the Employee")
	void updateTest() throws Exception {
		when(employeeService.updateEmployee(any(Employee.class), anyInt())).thenReturn(emp);
		
		this.mockMvc.perform(put("/emps/{id}",1)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(emp)))
		    .andExpect(status().isOk())
		    .andExpect(jsonPath("$.id", is(emp.getId())))
		    .andExpect(jsonPath("$.name", is(emp.getName())))
		    .andExpect(jsonPath("$.dept", is(emp.getDept())));
	}
	
	@Test
	@DisplayName("Delete the Employee By its Id")
	void deleteEmployeeTest() throws Exception {
		doNothing().when(employeeService).deleteEmployee(anyInt());
		this.mockMvc.perform(delete("/emps/{id}",1))
		    .andExpect(status().isNoContent());
		    
	}
	
	@Test
	@DisplayName("Get the details by Employee Dept")
	void fetchEmployeeByDept() throws Exception {
		List<Employee> emps = Arrays.asList(new Employee(4, "Bavana", "Development"),
				new Employee(5, "Radha", "Development"));
		
		when(employeeService.getEmployeesbyDept(anyString())).thenReturn(emps);
		
		this.mockMvc.perform(get("/emps/department/{dept}","Development"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.size()", is(emps.size())))
		.andExpect(jsonPath("$[0].name", is("Bavana")));
		verify(employeeService, times(1)).getEmployeesbyDept("Development");
		
		
		
		
	}
}
