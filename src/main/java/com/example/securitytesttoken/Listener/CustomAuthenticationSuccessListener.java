package com.example.securitytesttoken.Listener;

import org.springframework.context.ApplicationListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.securitytesttoken.serviceImpl.EmailServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

	private final EmailServiceImpl emailServiceImpl;
	private final HttpServletRequest request;
	private final KafkaTemplate<String, Authentication> kafkaTemplate;
	private final RetryTemplate retryTemplate;
	
	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		// TODO Auto-generated method stub
		log.info("로그인 성공");
		// login 성공
//        String username = event.getAuthentication().getName();
//        CustomUserDetails detail = (CustomUserDetails)event.getAuthentication().getPrincipal();
//        String accessUrl = detail.getAccessUrl();
//        String role = detail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", "));
//        String ip = request.getRemoteAddr();
//        String subject = "로그인 성공 알림";
//        String message = "사용자 '" + username + "[ROLE: " + role + " / IP : " + ip + "]" +  "'이(가) " + accessUrl + "에 로그인에 성공했습니다.";
//		try {
//			emailServiceImpl.sendMail(subject, message);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		
		retryTemplate.execute(context -> {
			kafkaTemplate.send("login-topic", "key1", event.getAuthentication());
			return null;
		}, context -> {
			log.error("재시도 횟수 끝");
			return null;
		});
	}

}
