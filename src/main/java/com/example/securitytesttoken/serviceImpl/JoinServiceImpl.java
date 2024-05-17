package com.example.securitytesttoken.serviceImpl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.securitytesttoken.customDto.JoinDTO;
import com.example.securitytesttoken.entity.UserEntity;
import com.example.securitytesttoken.entity.UserEntityId;
import com.example.securitytesttoken.repository.UserEntityRepository;
import com.example.securitytesttoken.service.JoinService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JoinServiceImpl implements JoinService {

	private final UserEntityRepository userEntityRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

//	@Transactional(isolation = Isolation.REPEATABLE_READ)
	@Override
	public void joinProcess(JoinDTO joinDTO) {
		Assert.hasText(joinDTO.getUsername(), "아이디는 필수 입력 사항입니다.");
		Assert.hasText(joinDTO.getPassword(), "비밀번호는 필수 입력 사항입니다.");
		Assert.hasText(joinDTO.getAccessUrl(), "접근 URL은 필수 선택 사항입니다.");
		boolean check = userEntityRepository.existsById_UsernameAndId_AccessUrl(joinDTO.getUsername(),
				joinDTO.getAccessUrl());
		Assert.isTrue(!check, "중복된 아이디가 존재합니다.");
		UserEntity userEntity = UserEntity.builder()
				.id(UserEntityId.builder().username(joinDTO.getUsername()).accessUrl(joinDTO.getAccessUrl()).build())
				.password(bCryptPasswordEncoder.encode(joinDTO.getPassword())).role("ROLE_AUSER").apprv(false).build();

		userEntityRepository.save(userEntity);
	}

}
