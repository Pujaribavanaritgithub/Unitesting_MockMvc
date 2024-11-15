package com.mokito.demo.leran.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mokito.demo.leran.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer>{
	
	//Custom query methods
	
	List<Employee> findByDept(String dept);
	
	

}
