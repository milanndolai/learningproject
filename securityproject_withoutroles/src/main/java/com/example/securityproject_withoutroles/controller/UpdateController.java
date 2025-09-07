package com.example.securityproject_withoutroles.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.securityproject_withoutroles.dto.DeleteRequest;
import com.example.securityproject_withoutroles.dto.PasswordChangeRequest;
import com.example.securityproject_withoutroles.dto.RegistrationResponse;
import com.example.securityproject_withoutroles.dto.UpdateRequest;
import com.example.securityproject_withoutroles.dto.UpdateResponse;
import com.example.securityproject_withoutroles.service.UpdateService;

@RequestMapping("/user/update")
@RestController
public class UpdateController {
	private UpdateService updateService;
	
	public UpdateController(UpdateService updateService) {
		this.updateService=updateService;
	}
	
	@PostMapping("/changepassword")
    public ResponseEntity<RegistrationResponse> changePassword(@RequestBody PasswordChangeRequest req,Authentication authentication){
    	String username=authentication.getName();
    	
		return ResponseEntity.ok(updateService.changePassword(username, req.getOldPassword(), req.getNewPassword()));
    	    }
	
	@PostMapping("/profilechange")
	public ResponseEntity<UpdateResponse> updateProfile(@RequestBody UpdateRequest req,Authentication authentication){
		
		return ResponseEntity.ok(updateService.profileUpdate(req, authentication));
		
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<String> updateProfile(@RequestBody DeleteRequest req,Authentication authentication){
		
		return ResponseEntity.ok(updateService.deleteProfile(authentication, req.getPassword()));
		
	}

	
	}
