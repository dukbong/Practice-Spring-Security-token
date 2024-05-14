package com.example.securitytesttoken.dto;

import com.example.securitytesttoken.enums.Role;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDetailInfo {

	private String username;
	private String accessUrl;
	private String password;
	private Role role;
	
	@Builder
	public UserDetailInfo(String username, String accessUrl, String password, Role role) {
		this.username = username;
		this.accessUrl = accessUrl;
		this.password = password;
		this.role = role;
	}
	
}
