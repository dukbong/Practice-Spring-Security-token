package com.example.securitytesttoken.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.securitytesttoken.filter.JwtRequestFilter;
import com.example.securitytesttoken.handler.JwtAccessDenied;
import com.example.securitytesttoken.handler.JwtAuthenticationEntryPoint;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JwtRequestFilter jwtRequestFilter;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
    // 권한 핸들러
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new JwtAccessDenied();
    }
    
    // 인증 핸들러
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new JwtAuthenticationEntryPoint();
    }
	
//	@Primary
	@Bean
	public RoleHierarchy roleHierarchy() {
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		roleHierarchyImpl.setHierarchy("ROLE_AADMIN > ROLE_AMANAGER > ROLE_AUSER");
		return roleHierarchyImpl;
	}
//	@Bean
//	public RoleHierarchy roleHierarchyB() {
//		RoleHierarchyImpl roleHierarchyImplB = new RoleHierarchyImpl();
//		roleHierarchyImplB.setHierarchy("ROLE_BUSER > ROLE_BMANAGER > ROLE_BADMIN");
//		return roleHierarchyImplB;
//	}
	
//    private DefaultWebSecurityExpressionHandler createExpressionHandler(RoleHierarchy roleHierarchy) {
//        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
//        expressionHandler.setRoleHierarchy(roleHierarchy);
//        return expressionHandler;
//    }
	
	@Bean
	public SecurityFilterChain filterChainA(HttpSecurity http) throws Exception {
		
		http
			.authorizeHttpRequests(auth -> {auth.requestMatchers("/main", "/login/**", "/join").permitAll();
											auth.requestMatchers("/manager/**").hasRole("AMANAGER");
											auth.requestMatchers("/admin/**").hasRole("AADMIN");
											auth.anyRequest().authenticated();});
		
		// STATELESS 정책은 CSRF를 비활성하기 때문에 별도로 CSRF 설정을 해주지 않아도 되지만 명시적으로 설정해주는 것이 좋다.
		// 또한 세션 고정 공격등을 방지 할 필요가 없다.
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.csrf(csrf -> csrf.disable());
		
		http.formLogin(login -> login.disable());
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
//		http.setSharedObject(DefaultWebSecurityExpressionHandler.class, createExpressionHandler(roleHierarchyA()));
		
		http.exceptionHandling(ex -> ex.accessDeniedHandler(accessDeniedHandler())
									   .authenticationEntryPoint(authenticationEntryPoint()));
		
		return http.build();
		
	}
	
//	@Bean
//	public SecurityFilterChain filterChainB(HttpSecurity http) throws Exception {
//		http.securityMatcher("/api/b")
//			.authorizeHttpRequests(auth -> {
//											auth.requestMatchers("/manager/**").hasAnyRole("BMANAGER");
//											auth.requestMatchers("/admin/**").hasRole("BADMIN");
//											auth.anyRequest().authenticated();});
//		
//		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		http.csrf(csrf -> csrf.disable());
//		
//		http.formLogin(login -> login.disable());
//		
//		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//		
//		return http.build();
//		
//	}
//	
//	@Bean
//	public SecurityFilterChain filterChainC(HttpSecurity http) throws Exception {
//		http.securityMatcher("/api/c")
//			.authorizeHttpRequests(auth -> {
//											auth.requestMatchers("/manager/**").hasAnyRole("BMANAGER");
//											auth.requestMatchers("/admin/**").hasRole("BADMIN");
//											auth.anyRequest().authenticated();});
//		
//		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		http.csrf(csrf -> csrf.disable());
//		
//		http.formLogin(login -> login.disable());
//		
//		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//		
//		return http.build();
//		
//	}
//	
//	@Bean
//	public SecurityFilterChain filterChainGlobal(HttpSecurity http) throws Exception {
//		http
//			.authorizeHttpRequests(auth -> {auth.requestMatchers("/", "/login", "/join").permitAll();
//											auth.anyRequest().authenticated();});
//		
//		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
//		http.csrf(csrf -> csrf.disable());
//		
//		http.formLogin(login -> login.disable());
//		
//		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
//		
//		return http.build();
//	}
	
}
