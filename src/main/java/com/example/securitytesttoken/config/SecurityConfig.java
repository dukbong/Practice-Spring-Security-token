package com.example.securitytesttoken.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		roleHierarchyImpl.setHierarchy("ROLE_USER > ROLE_MANAGER > ROLE_ADMIN");
		return roleHierarchyImpl;
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.securityMatcher("A")
			.authorizeHttpRequests(auth -> {auth.requestMatchers("/", "/login", "/join").permitAll();
											auth.requestMatchers("/manager/**").hasRole("MANAGER");
											auth.requestMatchers("/admin/**").hasRole("ADMIN");
											auth.anyRequest().authenticated();});
		
		// STATELESS 정책은 CSRF를 비활성하기 때문에 별도로 CSRF 설정을 해주지 않아도 된다.
		// 또한 세션 고정 공격등을 방지 할 필요가 없다.
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.formLogin(login -> login.disable());
		
		http.addFilterBefore(null, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
}
