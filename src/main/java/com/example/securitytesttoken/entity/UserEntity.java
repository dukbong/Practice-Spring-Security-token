package com.example.securitytesttoken.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserEntity {
	
	@EmbeddedId
	private UserEntityId id;
	private String password;
	private String role;
	private Boolean apprv;
	
	@Builder
	public UserEntity(UserEntityId id, String password, String role, Boolean apprv) {
		this.id = id;
		this.password = password;
		this.role = role;
		this.apprv = apprv;
	}

	public void joinApprv() {
		this.apprv = true;
	}
	
}
