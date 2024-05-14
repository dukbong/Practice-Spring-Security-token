package com.example.securitytesttoken.entity;

import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "refresh_token_gen", sequenceName = "refresh_token_seq", initialValue = 1, allocationSize = 50)
public class RefreshToken {

	@Id
	@GeneratedValue(generator = "refresh_token_gen")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private UserEntity userEntity;
	
	private String refreshToken;
	
	private ZonedDateTime expiredAt;
	
}
