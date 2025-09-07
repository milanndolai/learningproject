package com.example.securityproject_withoutroles.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password; 
    
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private ROLE role;
	   
    
    @Override public String getPassword() { return password; }

    // UserDetails methods:
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+role)); };
    @Override public String getUsername() { return email; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

	public String getUserName() {
		// TODO Auto-generated method stub
		return userName;
	}
}
