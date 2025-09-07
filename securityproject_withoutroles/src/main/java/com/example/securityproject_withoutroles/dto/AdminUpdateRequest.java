package com.example.securityproject_withoutroles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor

public class AdminUpdateRequest {
	private String userName;
	private String email;
	private String inputEmail;
	}
