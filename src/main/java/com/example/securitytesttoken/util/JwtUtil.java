package com.example.securitytesttoken.util;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import io.fusionauth.jwt.InvalidJWTException;
import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j 
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private long expiration;
	
	public String generateToken(String username, List<String> role, String accessUrl) {
		
		Assert.isTrue(role.size() == 1, "권한은 하나만 부여 받을 수 있습니다.");
		
		Signer signer = HMACSigner.newSHA256Signer(secret);
		
		ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
		
		JWT jwt = new JWT().setIssuer("발행자 표기")
						   .setIssuedAt(now)
						   .setSubject(username)
						   .setExpiration(now.plusSeconds(expiration))
						   .addClaim("username", username)
						   .addClaim("role", role.get(0))
						   .addClaim("accessUrl", accessUrl);
		
		String encodeJwt = jwt.getEncoder().encode(jwt, signer);
		
		return encodeJwt;
	}
	
	public JWT validateToken(String jwtToken) {
		Verifier verifier = HMACVerifier.newVerifier(secret);
		try {
			JWT jwt = JWT.getDecoder().decode(jwtToken, verifier);
			ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
			if(jwt.expiration.isBefore(now)) {
				throw new InvalidJWTException("토큰이 만료되었습니다.");
			}
			
			// 추가적인 유효성 검사를 진행할 수 있다.
			
			return jwt;
		} catch (InvalidJWTException e) {
			log.error("유효성 검사 중 오류가 발생 : {}", e.getMessage());
			return null;
		} catch (Exception e) {
			log.error("유효성 검사 중 오류가 발생했습니다.");
			return null;
		}
	}
}
