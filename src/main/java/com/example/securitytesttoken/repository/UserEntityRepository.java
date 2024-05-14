package com.example.securitytesttoken.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.securitytesttoken.entity.UserEntity;
import com.example.securitytesttoken.entity.UserEntityId;

public interface UserEntityRepository extends JpaRepository<UserEntity, UserEntityId> {

}
