package com.example.securitytesttoken.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDetailInfo {

	private String username;
	private String accessUrl;
	private String password;
	private String role;
	private Boolean apprv;
	
	@Builder
	public UserDetailInfo(String username, String accessUrl, String password, String role, Boolean apprv) {
		this.username = username;
		this.accessUrl = accessUrl;
		this.password = password;
		this.role = role;
		this.apprv = apprv;
	}
	
}
