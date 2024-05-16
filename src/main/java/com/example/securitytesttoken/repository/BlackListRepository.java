package com.example.securitytesttoken.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securitytesttoken.entity.BlackListToken;

public interface BlackListRepository extends JpaRepository<BlackListToken, Long> {
	
	boolean existsByBlackListTokenName(String blackListTokenName);

}
