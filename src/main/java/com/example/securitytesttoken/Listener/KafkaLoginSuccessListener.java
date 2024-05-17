package com.example.securitytesttoken.Listener;

import java.util.stream.Collectors;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.securitytesttoken.customDto.CustomUserDetails;
import com.example.securitytesttoken.serviceImpl.EmailServiceImpl;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaLoginSuccessListener {
	
	private final EmailServiceImpl emailServiceImpl;

	@KafkaListener(topicPartitions = @TopicPartition(topic = "login-topic", partitions = {"0"}), groupId = "login-success")
	public void loginSuccessListener(Authentication authentication) {
		CustomUserDetails detail = (CustomUserDetails)authentication;
		String message = String.format("접속자 명 : %s, 권한 : %s, 이(가) + %s경로에 로그인 하였습니다.", detail.getUsername(),
				detail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", ")),
				detail.getAccessUrl());
		String subject = "[성공] 로그인 알림";
		
		try {
			emailServiceImpl.sendMail(subject, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
