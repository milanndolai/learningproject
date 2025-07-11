package com.firstSpringBootProject.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.firstSpringBootProject.employee.entity.Employee;
import com.firstSpringBootProject.employee.repository.EmployeeRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
    	this.employeeRepository=employeeRepository;
    }
    public Employee postEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    
    public List<Employee> getAllEmployee(){
    	return employeeRepository.findAll();
    }
	
    public void deleteEmployee(Long id) {
    	if(!employeeRepository.existsById(id)) {
    		throw new EntityNotFoundException("Employee with id "+id+" NOT FOUND");
    	}
    	employeeRepository.deleteById(id);
    }
    
    public Employee getEmployeeById(Long id) {
    	return employeeRepository.findById(id).orElse(null);
    }
    
    public Employee updateEmployee(Long id,Employee employee) {
    	Optional<Employee>optionalEmployee=employeeRepository.findById(id);
    	
    	if(optionalEmployee.isPresent()) {
    		Employee existingEmployee=optionalEmployee.get();
    		
    		existingEmployee.setName(employee.getName());
    		existingEmployee.setEmail(employee.getEmail());
    		existingEmployee.setPhone(employee.getPhone());
    		existingEmployee.setDepartment(employee.getDepartment());
    		
    		return employeeRepository.save(existingEmployee);
    	}
    	return null;
    }
}

