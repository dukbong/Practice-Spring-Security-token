package com.example.securitytesttoken.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.securitytesttoken.filter.JwtRequestFilter;
import com.example.securitytesttoken.repository.BlackListRepository;
import com.example.securitytesttoken.serviceImpl.CustomUserDetailsServiceImpl;
import com.example.securitytesttoken.util.JwtUtil;

@Configuration
public class FilterConfig {

	@Bean
	public JwtRequestFilter jwtRequestFilter(JwtUtil jwtUtil, CustomUserDetailsServiceImpl customUserDetailsServiceImpl, BlackListRepository blackListRepository) {
		return new JwtRequestFilter(jwtUtil, customUserDetailsServiceImpl, blackListRepository);
	}
	
}
