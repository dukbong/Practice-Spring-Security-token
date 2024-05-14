package com.example.securitytesttoken.provider;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.securitytesttoken.customDto.CustomUserInfoToken;
import com.example.securitytesttoken.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private final CustomUserDetailsService CustomUserDetailsServiceImpl;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = (String) authentication.getCredentials();
		@SuppressWarnings("unchecked")
		Map<String, String> info = (Map<String, String>)authentication.getDetails();
		
		UserDetails detail = CustomUserDetailsServiceImpl.loadUserByUsernameAndAccessUrl(username, info.get("url"));
		
		if(!bCryptPasswordEncoder.matches(password, detail.getPassword())) {
			throw new BadCredentialsException("비밀번호가 틀렸습니다."); 
		}
		
		return new CustomUserInfoToken(detail, password, detail.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return CustomUserInfoToken.class.isAssignableFrom(authentication);
	}

}
