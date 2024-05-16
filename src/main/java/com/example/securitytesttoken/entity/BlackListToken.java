package com.example.securitytesttoken.entity;

import java.time.ZonedDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SequenceGenerator(name = "black_list_token_gen", sequenceName = "black_list_seq", initialValue = 1, allocationSize = 50)
public class BlackListToken {

	@Id
	@GeneratedValue(generator = "black_list_token_gen")
	private Long id;
	
	private String blackListTokenName;
	
	private ZonedDateTime expiredAt;
	
	@Builder
	public BlackListToken(Long id, String blackListTokenName, ZonedDateTime expiredAt) {
		this.id = id;
		this.blackListTokenName = blackListTokenName;
		this.expiredAt = expiredAt;
	}
	
}
