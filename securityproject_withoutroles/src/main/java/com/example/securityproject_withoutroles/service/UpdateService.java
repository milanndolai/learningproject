package com.example.securityproject_withoutroles.service;

import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.securityproject_withoutroles.dto.RegistrationResponse;
import com.example.securityproject_withoutroles.dto.UpdateRequest;
import com.example.securityproject_withoutroles.dto.UpdateResponse;
import com.example.securityproject_withoutroles.entity.User;
import com.example.securityproject_withoutroles.repository.UserRepository;

@Service
public class UpdateService {
    private final PasswordEncoder password;
	private final  UserRepository userRepo;
	
	public UpdateService(PasswordEncoder password, UserRepository userRepo) {
		this.password = password;
		this.userRepo = userRepo;
	}
	
	public RegistrationResponse changePassword(String userName,String oldPassword,String Newpassword) {
		 com.example.securityproject_withoutroles.entity.User user = userRepo.findByEmail(userName)
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));	
	
        if(!password.matches(oldPassword,user.getPassword())) {
        	throw new RuntimeException("INVALID PASSWORD");
        	}
        
        user.setPassword(password.encode(Newpassword));
        userRepo.save(user);
        return new RegistrationResponse("PASSWORD CHANGED", user.getEmail());
}
	
	public UpdateResponse profileUpdate(UpdateRequest req,Authentication authentication) {
		String email=authentication.getName();
		com.example.securityproject_withoutroles.entity.User user = userRepo.findByEmail(email)
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

	public String deleteProfile(Authentication authentication,String lastpassword) {
		String email=authentication.getName();
		User user=userRepo.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("USER NOT FOUND"));
		
		
		if(!password.matches(lastpassword, user.getPassword())) {
			throw new BadCredentialsException("WRONG PASSWORD");
		}
		
		userRepo.delete(user);
		return "PROFILE DELETED";
		
	}
}
