package com.example.securitytesttoken.Listener;

import java.util.stream.Collectors;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.example.securitytesttoken.customDto.CustomUserDetails;
import com.example.securitytesttoken.serviceImpl.EmailServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KafkaLoginFailureListener {
	
	private final EmailServiceImpl emailServiceImpl;
	private final HttpServletRequest request;

	@KafkaListener(topicPartitions = @TopicPartition(topic = "login-topic", partitions = {"1"}), groupId = "login-failure")
	public void loginFailureListener(Authentication authentication) {
		String message = String.format("로그인 시도 %s에서 %s경로로 로그인을 시도하였습니다.", request.getRemoteAddr(), request.getRequestURI());
		String subject = "[실패] 로그인 알림";
		
		try {
			emailServiceImpl.sendMail(subject, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
