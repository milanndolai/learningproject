package com.firstSpringBootProject.employee.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)	
	private long id;
    
    @NotBlank(message = "Name cannot be blank")
    private String name;
    
    @Email(message = "Invalid email format")
    private String email;
    
    @NotBlank(message = "Phone cannot be blank")
    @Pattern(regexp = "\\d{10}", message = "Phone must be a 10-digit number")
    private String phone;
    
    @NotBlank(message = "Department cannot be blank")
    private String department;

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}


}
