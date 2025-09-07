package com.example.securityproject_withoutroles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDeleteUser {
   private String email;

public String getEmail() {
	// TODO Auto-generated method stub
	return email;
}
}
