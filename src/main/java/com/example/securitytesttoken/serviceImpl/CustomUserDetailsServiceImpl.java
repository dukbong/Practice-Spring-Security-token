package com.example.securitytesttoken.serviceImpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.securitytesttoken.service.CustomUserDetailsService;

public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

	@Override
	// 사용하지 않고 CustomUserDetailsService에서 새로운 인증 방식을 만들어서 사용
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}

	@Override
	public UserDetails loadUserByUsernameAndAccessUrl() {
		return null;
	}

}
