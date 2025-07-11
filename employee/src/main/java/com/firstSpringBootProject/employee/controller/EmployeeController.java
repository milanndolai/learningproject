package com.firstSpringBootProject.employee.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firstSpringBootProject.employee.entity.Employee;
import com.firstSpringBootProject.employee.service.EmployeeService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class EmployeeController {
	private final EmployeeService employeeservice ;
	
	public EmployeeController(EmployeeService employeeservice) {
		super();
		this.employeeservice = employeeservice;
	}

	@PostMapping("/employee")
	public Employee postEmployee(@Valid @RequestBody Employee employee) {
		return employeeservice.postEmployee(employee);
	}
	
	@GetMapping("/employees")
	public List<Employee> getAllEmployee(){
	 return employeeservice.getAllEmployee();	
	 }
	
	@DeleteMapping("/employee/{id}")
	public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
		try {
			employeeservice.deleteEmployee(id);
			return new ResponseEntity<>("EMPLOYEE WITH ID "+id+" DELETED",HttpStatus.OK);
		}catch(EntityNotFoundException e){
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/employee/{id}")
	public ResponseEntity<?> getEmployeeById(@PathVariable Long id){
		Employee employee=employeeservice.getEmployeeById(id);
		if(employee==null) {return ResponseEntity.notFound().build();}
		return ResponseEntity.ok(employee);
	}
	
	@PatchMapping("/employee/{id}")
	public ResponseEntity<?> updateEmployee(@PathVariable Long id,@RequestBody Employee employee){
		Employee updatedEmployee=employeeservice.updateEmployee(id, employee);
		
		if(updatedEmployee==null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		
		return ResponseEntity.ok(updatedEmployee);
	}
		
	}
	
	

