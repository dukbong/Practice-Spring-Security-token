package com.example.securitytesttoken.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public RoleHierarchy roleHierarchyA() {
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		roleHierarchyImpl.setHierarchy("ROLE_A_USER > ROLE_A_MANAGER > ROLE_A_ADMIN");
		return roleHierarchyImpl;
	}
	@Bean
	public RoleHierarchy roleHierarchyB() {
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		roleHierarchyImpl.setHierarchy("ROLE_B_USER > ROLE_B_MANAGER > ROLE_B_ADMIN");
		return roleHierarchyImpl;
	}
	
    private DefaultWebSecurityExpressionHandler createExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }
	
	@Bean
	public SecurityFilterChain filterChainA(HttpSecurity http) throws Exception {
		
		http.securityMatcher("/api/a/**")
			.authorizeHttpRequests(auth -> {auth.requestMatchers("/", "/login", "/join").permitAll();
											auth.requestMatchers("/manager/**").hasRole("A_MANAGER");
											auth.requestMatchers("/admin/**").hasRole("A_ADMIN");
											auth.anyRequest().authenticated();});
		
		// STATELESS 정책은 CSRF를 비활성하기 때문에 별도로 CSRF 설정을 해주지 않아도 되지만 명시적으로 설정해주는 것이 좋다.
		// 또한 세션 고정 공격등을 방지 할 필요가 없다.
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.csrf(csrf -> csrf.disable());
		
		http.formLogin(login -> login.disable());
		
		http.addFilterBefore(null, UsernamePasswordAuthenticationFilter.class);
		
		http.setSharedObject(DefaultWebSecurityExpressionHandler.class, createExpressionHandler(roleHierarchyA()));
		
		return http.build();
		
	}
	
	@Bean
	public SecurityFilterChain filterChainB(HttpSecurity http) throws Exception {
		
		http.securityMatcher("/api/b/**")
			.authorizeHttpRequests(auth -> {auth.requestMatchers("/", "/login", "/join").permitAll();
											auth.requestMatchers("/manager/**").hasAnyRole("SUB", "MAIN");
											auth.requestMatchers("/admin/**").hasRole("MAIN");
											auth.anyRequest().authenticated();});
		
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.csrf(csrf -> csrf.disable());
		
		http.formLogin(login -> login.disable());
		
		http.addFilterBefore(null, UsernamePasswordAuthenticationFilter.class);
		
		http.setSharedObject(DefaultWebSecurityExpressionHandler.class, createExpressionHandler(roleHierarchyB()));
		
		return http.build();
		
	}
	
}
