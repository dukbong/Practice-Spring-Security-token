package com.example.securitytesttoken.service;

import com.example.securitytesttoken.dto.LoginDTO;

public interface LoginService {

	String loginProcess(String accessUrl, LoginDTO loginDTO);

}
