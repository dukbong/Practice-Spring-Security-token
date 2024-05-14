package com.example.securitytesttoken.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginDTO {
	private String username;
	private String password;
	@Builder
	public LoginDTO(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
