package com.example.securitytesttoken.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntityId {
	
	private String username;
	private String accessUrl;
	
	@Builder
	public UserEntityId(String username, String accessUrl) {
		this.username = username;
		this.accessUrl = accessUrl;
	}
	
}
