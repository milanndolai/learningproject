package com.example.securityproject_withoutroles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {
    private String userName;
    private String email;
    private String password;
    private String role;
	}

