package com.example.securitytesttoken.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import com.example.securitytesttoken.entity.UserEntity;
import com.example.securitytesttoken.entity.UserEntityId;

import jakarta.persistence.LockModeType;

public interface UserEntityRepository extends JpaRepository<UserEntity, UserEntityId> {
	
	Optional<UserEntity> findById_UsernameAndId_accessUrl(String username, String accessUrl);
	
	boolean  existsById_UsernameAndId_AccessUrl(String username, String accessUrl);
}
