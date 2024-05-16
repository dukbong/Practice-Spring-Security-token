package com.example.securitytesttoken.serviceImpl;

import java.time.ZonedDateTime;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.securitytesttoken.entity.BlackListToken;
import com.example.securitytesttoken.repository.BlackListRepository;
import com.example.securitytesttoken.service.LogoutService;
import com.example.securitytesttoken.util.JwtUtil;

import io.fusionauth.jwt.domain.JWT;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutServiceImpl implements LogoutService {

	private final JwtUtil jwtUtil;
	private final BlackListRepository blackListRepository;
	
	@Override
	public void logoutProcess(String token) {
		JWT jwt = jwtUtil.validateToken(token);
		Assert.notNull(jwt, "토큰의 유효성이 확인 되지 않았습니다.");
		containBlackList(token);
		addBlackList(token, jwt.expiration);
	}
	
	private void addBlackList(String token, ZonedDateTime expiration) {
		BlackListToken blackListToken = BlackListToken.builder().blackListTokenName(token).expiredAt(expiration).build();
		blackListRepository.save(blackListToken);
	}
	
	private void containBlackList(String token) {
		boolean check = blackListRepository.existsByBlackListTokenName(token);
		Assert.isTrue(!check, "블랙 리스트에 이미 포함되어 있는 토큰 입니다.");
	}

}
