package com.example.securitytesttoken.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {

	// 새로운 인증 방식 사용
	public UserDetails loadUserByUsernameAndAccessUrl(String username, String accessUrl);
}
