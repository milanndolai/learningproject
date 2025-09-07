package com.example.securityproject_withoutroles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateResponse {
     private String message;
     private String username;
     private String email;
}
