package com.example.securitytesttoken.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDetailInfo {

	private String username;
	private String accessUrl;
	private String password;
	private String role;
	
	@Builder
	public UserDetailInfo(String username, String accessUrl, String password, String role) {
		this.username = username;
		this.accessUrl = accessUrl;
		this.password = password;
		this.role = role;
	}
	
}
