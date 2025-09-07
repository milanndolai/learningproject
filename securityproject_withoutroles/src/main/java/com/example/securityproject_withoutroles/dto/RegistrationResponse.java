package com.example.securityproject_withoutroles.dto;
public class RegistrationResponse {
    private String message;
    private String email;
    public RegistrationResponse(String message, String email) { this.message = message; this.email = email; }
    public String getMessage(){ return message; }
    public String getEmail(){ return email; }
}
