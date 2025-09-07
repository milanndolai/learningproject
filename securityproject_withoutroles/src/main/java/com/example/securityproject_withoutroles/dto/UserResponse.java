package com.example.securityproject_withoutroles.dto;

import com.example.securityproject_withoutroles.entity.ROLE;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
      private Long id;
      private String userName;
      private String email;
      private ROLE role;
}
