package com.example.securitytesttoken.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.securitytesttoken.filter.JwtRequestFilter;

@Configuration
public class FilterConfig {

	@Bean
	public JwtRequestFilter jwtRequestFilter() {
		return new JwtRequestFilter();
	}
	
}
