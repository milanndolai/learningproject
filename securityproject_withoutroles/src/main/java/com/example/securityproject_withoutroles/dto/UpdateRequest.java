package com.example.securityproject_withoutroles.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class UpdateRequest {
   private String userName;
   private String email;
}
