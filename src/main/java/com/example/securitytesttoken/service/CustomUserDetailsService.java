package com.example.securitytesttoken.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {

	public UserDetails loadUserByUsernameAndAccessUrl(String username, String accessUrl);
}
