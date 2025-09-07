package com.example.securityproject_withoutroles.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.example.securityproject_withoutroles.cofig.JwtService;
import com.example.securityproject_withoutroles.dto.*;
import com.example.securityproject_withoutroles.entity.ROLE;
import com.example.securityproject_withoutroles.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    public RegistrationResponse register(RegistrationRequest req) {
        if (userRepository.existsByEmail(req.getEmail())) {
            throw new RuntimeException("User exists");
        }
        var user = new com.example.securityproject_withoutroles.entity.User();
        user.setEmail(req.getEmail());
        user.setUserName(req.getUserName());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(changeEnum(req.getRole()));
        userRepository.save(user);
        return new RegistrationResponse("Registered", req.getEmail());
    }

    private ROLE changeEnum(String role) {
		try {
			return ROLE.valueOf(role.trim().toUpperCase());
		} catch (IllegalArgumentException | NullPointerException e) {
			throw new RuntimeException("Enter your Role: USER OR ADMIN");
		}
	}

	public LoginResponse login(LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        var userDetails = userDetailsService.loadUserByUsername(req.getEmail());
        String token = jwtService.generateToken(userDetails);
        return new LoginResponse(token);
    }
}
