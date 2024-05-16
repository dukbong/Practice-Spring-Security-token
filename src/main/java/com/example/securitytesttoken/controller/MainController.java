package com.example.securitytesttoken.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.securitytesttoken.customDto.JoinDTO;
import com.example.securitytesttoken.dto.LoginDTO;
import com.example.securitytesttoken.service.ApprvService;
import com.example.securitytesttoken.service.JoinService;
import com.example.securitytesttoken.service.LoginService;
import com.example.securitytesttoken.service.LogoutService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/api/a")
public class MainController {

	private final LoginService loginServiceImpl;
	private final LogoutService logoutServiceImpl;
	private final JoinService JoinServiceImpl;
	private final ApprvService apprvServiceImpl;
	
	@GetMapping("/main")
	public ResponseEntity<String> mainP() {
		return ResponseEntity.ok().body("main page");
	}
	
	@PostMapping("/login/{accessUrl}")
	public ResponseEntity<String> loginP(@PathVariable("accessUrl") String accessUrl, @RequestBody LoginDTO loginDTO) {
		String jwtToken = loginServiceImpl.loginProcess(accessUrl, loginDTO);
		Assert.hasText(jwtToken, "Jwt Token 값은 비어 있을 수 없습니다.");
		return ResponseEntity.ok().header("Authorization", "Bearer " + jwtToken).body("로그인 성공 하였습니다.");
	}
	
	@PostMapping("/join")
	public ResponseEntity<String> joinP(@RequestBody JoinDTO joinDTO) {
		JoinServiceImpl.joinProcess(joinDTO);
		return ResponseEntity.ok().body("회원가입에 성공했습니다.");
	}
	
	@PatchMapping("{accessUrl}/apprv/{username}")
	public ResponseEntity<String> apprvP(@PathVariable("accessUrl") String accessUrl, @PathVariable("username") String username) {
		apprvServiceImpl.apprv(accessUrl, username);
		return ResponseEntity.ok().body(username + "님의 회원가입을 승인하였습니다.");
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logoutP(@RequestHeader("Authorization") String token) {
		logoutServiceImpl.logoutProcess(token);
		return ResponseEntity.ok().body("logout 성공");
	}
	
}
