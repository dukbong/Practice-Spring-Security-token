package com.example.securitytesttoken.Listener;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.example.securitytesttoken.serviceImpl.EmailServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

	private final EmailServiceImpl emailServiceImpl;
	private final HttpServletRequest request;
	
	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		// login 실패
		log.info("로그인 실패!");
		
		String ip = request.getRemoteAddr();
		String uri = request.getRequestURI();
		String subject = "로그인 실패 알림";
		String message = "사용자 '" + ip + "'이(가) " + uri + "에 로그인에 실패[시도] 했습니다.";
        try {
			emailServiceImpl.sendMail(subject, message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
