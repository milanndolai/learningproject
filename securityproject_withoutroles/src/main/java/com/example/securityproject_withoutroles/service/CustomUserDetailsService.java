package com.example.securityproject_withoutroles.service;

import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.*;

import com.example.securityproject_withoutroles.entity.User;
import com.example.securityproject_withoutroles.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return (UserDetails) user; 
    }
}
