package com.example.securitytesttoken.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.securitytesttoken.filter.JwtRequestFilter;
import com.example.securitytesttoken.provider.CustomAuthenticationProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtRequestFilter jwtRequestFilter;
	private final CustomAuthenticationProvider customAuthenticationProvider;
	
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                   .authenticationProvider(customAuthenticationProvider)
                   .build();
    }
	
	@Bean
	public RoleHierarchy roleHierarchyA() {
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		roleHierarchyImpl.setHierarchy("ROLE_AUSER > ROLE_AMANAGER > ROLE_AADMIN");
		return roleHierarchyImpl;
	}
	@Bean
	public RoleHierarchy roleHierarchyB() {
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		roleHierarchyImpl.setHierarchy("ROLE_BUSER > ROLE_BMANAGER > ROLE_BADMIN");
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
											auth.requestMatchers("/manager/**").hasRole("AMANAGER");
											auth.requestMatchers("/admin/**").hasRole("AADMIN");
											auth.anyRequest().authenticated();});
		
		// STATELESS 정책은 CSRF를 비활성하기 때문에 별도로 CSRF 설정을 해주지 않아도 되지만 명시적으로 설정해주는 것이 좋다.
		// 또한 세션 고정 공격등을 방지 할 필요가 없다.
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.csrf(csrf -> csrf.disable());
		
		http.formLogin(login -> login.disable());
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		http.setSharedObject(DefaultWebSecurityExpressionHandler.class, createExpressionHandler(roleHierarchyA()));
		
		return http.build();
		
	}
	
	@Bean
	public SecurityFilterChain filterChainB(HttpSecurity http) throws Exception {
		
		http.securityMatcher("/api/b/**")
			.authorizeHttpRequests(auth -> {auth.requestMatchers("/", "/login", "/join").permitAll();
											auth.requestMatchers("/manager/**").hasAnyRole("BMANAGER");
											auth.requestMatchers("/admin/**").hasRole("BADMIN");
											auth.anyRequest().authenticated();});
		
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.csrf(csrf -> csrf.disable());
		
		http.formLogin(login -> login.disable());
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		http.setSharedObject(DefaultWebSecurityExpressionHandler.class, createExpressionHandler(roleHierarchyB()));
		
		return http.build();
		
	}
	
}
