package com.example.securityproject_withoutroles.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.securityproject_withoutroles.dto.AdminDeleteUser;
import com.example.securityproject_withoutroles.dto.AdminEmailreq;
import com.example.securityproject_withoutroles.dto.AdminIdreq;
import com.example.securityproject_withoutroles.dto.AdminUpdateRequest;
import com.example.securityproject_withoutroles.dto.UpdateRequest;
import com.example.securityproject_withoutroles.dto.UpdateResponse;
import com.example.securityproject_withoutroles.dto.UserResponse;
import com.example.securityproject_withoutroles.service.AdminService;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController {
	private final AdminService adminService;
	
     @GetMapping("/allUsers")
     public ResponseEntity<List<UserResponse>> getAllUser(){
    	 return ResponseEntity.ok(adminService.allUser());
     }
     
     @GetMapping("/UserById")
     public ResponseEntity<UserResponse> getUserbyId(@RequestBody AdminIdreq req){
    	 return ResponseEntity.ok(adminService.getUserById(req.getId()));
     }
     
     @GetMapping("/UserByEmail")
     public ResponseEntity<UserResponse> getUserbyId(@RequestBody AdminEmailreq req){
    	 return ResponseEntity.ok(adminService.getUserByEmail(req.getEmail()));
     }
     
     @PostMapping("/updateprofile")
     public ResponseEntity<UpdateResponse> adminUpdates(@RequestBody AdminUpdateRequest req){
    	 return ResponseEntity.ok(adminService.profileUpdate(new UpdateRequest(req.getUserName(), req.getEmail()), req.getInputEmail()));
     }

     @DeleteMapping("/delete")
     public ResponseEntity<String> deletes(@RequestBody AdminDeleteUser req){
    	 adminService.deleteUser(req);
    	 return ResponseEntity.ok("DELETED SUCCESFULLY");
     }
}
