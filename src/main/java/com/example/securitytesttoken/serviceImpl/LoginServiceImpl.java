package com.example.securitytesttoken.serviceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.securitytesttoken.customDto.CustomUserInfoToken;
import com.example.securitytesttoken.dto.LoginDTO;
import com.example.securitytesttoken.service.LoginService;
import com.example.securitytesttoken.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService{
	
	private final AuthenticationManager authenticationManager;
	private final JwtUtil jwtUtil;

	@Override
	public String loginProcess(String accessUrl, LoginDTO loginDTO) {
		log.info("로그인 중..");
		CustomUserInfoToken authToken = new CustomUserInfoToken(loginDTO.getUsername(), loginDTO.getPassword());
		Assert.hasText(accessUrl, "URL이 비어 있을 수 없습니다.");
		Map<String, String> map = new HashMap<>();
		map.put("url", accessUrl);
		authToken.setDetails(map);
		Authentication auth = authenticationManager.authenticate(authToken);
		return jwtUtil.generateToken(loginDTO.getUsername(), auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()), accessUrl);
	}

}
