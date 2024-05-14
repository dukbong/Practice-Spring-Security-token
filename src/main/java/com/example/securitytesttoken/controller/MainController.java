package com.example.securitytesttoken.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.securitytesttoken.dto.LoginDTO;
import com.example.securitytesttoken.service.LoginService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/api/a")
public class MainController {

	private final LoginService LoginServiceImpl;
	
	@GetMapping("/main")
	public ResponseEntity<String> mainP() {
		return ResponseEntity.ok().body("main page");
	}
	
	@PostMapping("/login/{accessUrl}")
	public ResponseEntity<String> loginP(@PathVariable("accessUrl") String accessUrl, @RequestBody LoginDTO loginDTO) {
		String jwtToken = LoginServiceImpl.loginProcess(accessUrl, loginDTO);
		Assert.hasText(jwtToken, "Jwt Token 값은 비어 있을 수 없습니다.");
		return ResponseEntity.ok().header("Authorization", "Bearer " + jwtToken).body("로그인 성공 하였습니다.");
	}
	
}
