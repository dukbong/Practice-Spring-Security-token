package com.example.securitytesttoken.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserEntity {
	
	@EmbeddedId
	UserEntityId id;
	private String password;
	private String role;
	
	@Builder
	public UserEntity(UserEntityId id, String password, String role) {
		this.id = id;
		this.password = password;
		this.role = role;
	}
	
}
