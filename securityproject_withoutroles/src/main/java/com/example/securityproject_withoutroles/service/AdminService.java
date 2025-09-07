package com.example.securityproject_withoutroles.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.securityproject_withoutroles.dto.AdminDeleteUser;
import com.example.securityproject_withoutroles.dto.UpdateRequest;
import com.example.securityproject_withoutroles.dto.UpdateResponse;
import com.example.securityproject_withoutroles.dto.UserResponse;
import com.example.securityproject_withoutroles.entity.User;
import com.example.securityproject_withoutroles.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminService {
     private final UserRepository userRepo;
     
     public List<UserResponse> allUser(){
    	 List<UserResponse> list=userRepo.findAll().stream().map(
    			user-> new UserResponse(
    					user.getId(),
    					user.getUserName(),
    					user.getEmail(),
    					user.getRole())
    			).toList();
    			 
    	 return list;
     }
     
     public UserResponse getUserByEmail(String email) {
    	    return userRepo.findByEmail(email)
    	            .map(user -> new UserResponse(
    	                    user.getId(),
    	                    user.getUserName(),
    	                    user.getEmail(),
    	                    user.getRole()))
    	            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    	}
     
     public UserResponse getUserById(Long id) {
 	    return userRepo.findById(id)
 	            .map(user -> new UserResponse(
 	                    user.getId(),
 	                    user.getUserName(),
 	                    user.getEmail(),
 	                    user.getRole()))
 	            .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
 	}
     
     public UpdateResponse profileUpdate(UpdateRequest req,String inputEmail) {
 		com.example.securityproject_withoutroles.entity.User user = userRepo.findByEmail(inputEmail)
                 .orElseThrow(() -> new UsernameNotFoundException("User not found"));		
 		
 		
 		if(  req.getEmail()!=null && !req.getEmail().isEmpty() && !req.getEmail().equals(user.getEmail())) {
 			boolean newEmail=userRepo.existsByEmail(req.getEmail());
 			if(newEmail) {
 				throw new RuntimeException("THIS EMAIL ALREADY EXISTS");
 			}
 			user.setEmail(req.getEmail());
 		}
 		
 		if( req.getUserName()!=null && !req.getUserName().isEmpty()) {
 			user.setUserName(req.getUserName());
 		}
 		userRepo.save(user);
 		return new UpdateResponse("PROFILE UPDATED",user.getUserName(),user.getEmail());
 	}
     
     public void deleteUser(AdminDeleteUser req) {
    	 User user=userRepo.findByEmail(req.getEmail()).orElseThrow(()->new UsernameNotFoundException("USER NOT FOUND"));
    	 
    	 userRepo.delete(user);
     }
     

}
