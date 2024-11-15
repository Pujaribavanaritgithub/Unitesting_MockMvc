package com.mokito.demo.leran.repositoryTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.mokito.demo.leran.model.Employee;
import com.mokito.demo.leran.repository.EmployeeRepo;

@DataJpaTest
public class EmployeeRepoTest {
	
	@Autowired
	private EmployeeRepo empRepo;
	
	private Employee emp;
	private Employee emp1;
	
	@BeforeEach
	void init() {
		// we are keeping repeated code inside this method to reduce number lines
		emp = new Employee();
		emp.setName("Bavana");
		emp.setDept("IT");
		
		emp1 = new Employee();
		emp1.setName("Radhakrishna");
		emp1.setDept("Myth");
		
	}
	
	@Test
	@DisplayName("Save the employee details in the database")
	void save() {
		//  Arrange
		/*
		 * Employee emp = new Employee(); emp.setName("Bavana"); emp.setDept("IT");
		 */
		// Act
		Employee e1 = empRepo.save(emp);
		int id = e1.getId();
		
		// Assert
		assertNotNull(e1);
		assertEquals(e1.getId(), id);			
	}
	
	@Test
	@DisplayName("Get the all employee details from the database")
	void findAll() {
		
		empRepo.save(emp);
		empRepo.save(emp1);
		
		List<Employee> l1 = empRepo.findAll();
		
		assertNotNull(l1);
		assertEquals(2, l1.size());  
	}
	
	@Test
	@DisplayName("Find the employee details by its Id")
	void findById() {
		
		Employee save = empRepo.save(emp);
		int id = save.getId();
		
		 Employee find = empRepo.findById(id).get();
		assertNotNull(find);
		assertEquals("Bavana", find.getName());
		
	}
	
	@Test
	@DisplayName("Update the data of selected employee")
	void update() {
		
		Employee emp1 = empRepo.save(emp);
		int id = emp1.getId();
		
		
		Employee u1 = empRepo.findById(id).get();
		// update the data
		u1.setName("Darling");
		u1.setDept("Development");
		
		Employee u2 = empRepo.save(u1);
		assertEquals("Darling", u2.getName());
		assertEquals("Development", u2.getDept());
		
	}
	
	@Test
	@DisplayName("It should delete the data of given id")
	void delete() {
		
		empRepo.save(emp);
		int i = emp.getId();
		
		empRepo.save(emp1);
		
		empRepo.deleteById(i);
		Optional<Employee> emp2 = empRepo.findById(i);
		
		List<Employee> list = empRepo.findAll();
		
		assertNotNull(emp2);
		assertEquals(1, list.size());
		
	}
	
	@Test
	@DisplayName("Get the data of myth dept")
	void getBydept() {
		
		empRepo.save(emp);
		empRepo.save(emp1);
		
		List<Employee> list = empRepo.findByDept("Myth");
		assertNotNull(list);
		assertEquals(1, list.size());
		
	}
	

}
