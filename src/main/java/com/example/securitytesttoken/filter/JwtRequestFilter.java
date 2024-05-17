package com.example.securitytesttoken.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.securitytesttoken.customDto.CustomUserInfoToken;
import com.example.securitytesttoken.repository.BlackListRepository;
import com.example.securitytesttoken.serviceImpl.CustomUserDetailsServiceImpl;
import com.example.securitytesttoken.util.JwtUtil;

import io.fusionauth.jwt.domain.JWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// 요청시 어떻게 인증 인가를 확인할 것인지 
public class JwtRequestFilter extends OncePerRequestFilter {
	
	private final JwtUtil jwtUtil;
	private final CustomUserDetailsServiceImpl customUserDetailsServiceImpl;
	private final BlackListRepository blackListRepository;
	
	public JwtRequestFilter(JwtUtil jwtUtil, CustomUserDetailsServiceImpl customUserDetailsServiceImpl, BlackListRepository blackListRepository) {
		this.jwtUtil = jwtUtil;
		this.customUserDetailsServiceImpl = customUserDetailsServiceImpl;
		this.blackListRepository = blackListRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		log.info("JwtRequestFilter 실행");
		
		final String authorizationHeader = request.getHeader("Authorization");
		
		String jwtToken = null;
		Map<String, Object> claims = new HashMap<>();
		
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwtToken = authorizationHeader.substring(7);
			log.info("jwt token = {}", jwtToken);
			// 1. 블랙 리스트에 존재하는지 확인
			Assert.isTrue(!blackListRepository.existsByBlackListTokenName(jwtToken), "해당 토큰은 블랙 리스트에 추가되어 있습니다.");
			// 2. 토큰이 유효한지 확인
			JWT jwt = jwtUtil.validateToken(jwtToken);
			Assert.notNull(jwt, "디코딩 된 Jwt Token은 null 일 수 없습니다.");
			claims = jwt.getAllClaims();
			if(SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails userDetails = customUserDetailsServiceImpl.loadUserByUsernameAndAccessUrl((String)claims.get("username"), (String)claims.get("accessUrl"));
				SecurityContextHolder.getContext().setAuthentication(new CustomUserInfoToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities()));
			}
		}
		filterChain.doFilter(request, response);
	}

}
 