package com.example.securitytesttoken.serviceImpl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.securitytesttoken.customDto.CustomUserDetails;
import com.example.securitytesttoken.dto.UserDetailInfo;
import com.example.securitytesttoken.entity.UserEntity;
import com.example.securitytesttoken.repository.UserEntityRepository;
import com.example.securitytesttoken.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {

	private final UserEntityRepository userEntityRepository;
	
	@Override
	// 사용하지 않고 CustomUserDetailsService에서 새로운 인증 방식을 만들어서 사용
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return null;
	}

	@Override
	public UserDetails loadUserByUsernameAndAccessUrl(String username, String accessUrl) {
		UserEntity userEntity = userEntityRepository.findById_UsernameAndId_accessUrl(username, accessUrl)
													.orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));
		
		CustomUserDetails customUserDetails = new CustomUserDetails(UserDetailInfo.builder().accessUrl(userEntity.getId().getAccessUrl())
																							.username(userEntity.getId().getUsername())
																							.password(userEntity.getPassword())
																							.role(userEntity.getRole())
																							.build());
		return customUserDetails;
	}


}