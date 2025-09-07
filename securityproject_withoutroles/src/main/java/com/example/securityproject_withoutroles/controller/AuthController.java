package com.example.securityproject_withoutroles.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import com.example.securityproject_withoutroles.dto.*;
import com.example.securityproject_withoutroles.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authService;

    @PostMapping("/all/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest req) {
        return ResponseEntity.ok(authService.register(req));
    }

    @PostMapping("/all/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("Hello — authenticated!");
    }
}
