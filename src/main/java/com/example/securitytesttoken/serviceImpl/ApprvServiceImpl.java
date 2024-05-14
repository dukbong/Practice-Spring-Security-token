package com.example.securitytesttoken.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.securitytesttoken.entity.UserEntity;
import com.example.securitytesttoken.repository.UserEntityRepository;
import com.example.securitytesttoken.service.ApprvService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApprvServiceImpl implements ApprvService {

	private final UserEntityRepository userEntityRepository;

	@Override
	@Transactional
	public void apprv(String accessUrl, String username) {
		UserEntity userEntity = userEntityRepository.findById_UsernameAndId_accessUrl(username, accessUrl)
				.orElseThrow(() -> new IllegalArgumentException("회원가입 승인 처리할 회원이 존재하지 않습니다."));
		userEntity.joinApprv();
		userEntityRepository.save(userEntity);
	}

}
