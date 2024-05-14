package com.example.securitytesttoken.entity;

import com.example.securitytesttoken.enums.Role;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
	@Enumerated(EnumType.STRING)
	private Role role;
	
	@Builder
	public UserEntity(UserEntityId id, String password, Role role) {
		this.id = id;
		this.password = password;
		this.role = role;
	}
	
}
