package com.example.loginlog.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.loginlog.dto.LoginRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController	// 이 클래스가 HTTP 요청을 처리하는 컨트롤러임을 명시
public class LoginController {
	
	// 로그 찍기 위한 Logger객체 생성
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	// HTTP POST 요청을 /login 경로에서 받을 때 해당 메서드 실행
	@PostMapping("/login")
	// 요청 본문에 JSON으로 데이터가 들어오는데, 이 데이터를 LoginReequest 객체로 바인딩
	public ResponseEntity<String> login(@RequestBody LoginRequest request){
		
		if("user123".equals(request.getUsername()) && "password123".equals(request.getPassword())) {
			// 성공 로그
			logger.info("LOGIN_SUCCESS - userId={}, ip={}, userAgent={}", request.getUsername(), request.getIp(), request.getUserAgent());
			return ResponseEntity.ok("로그인 성공");
			// ResponseEntity : 응답코드까지 보낼 수 있는 Spring-web 라이브러리
			
		}else {
			// 실패 로그
			logger.warn("LOGIN_FAILED - userId={}, ip={}, reason=INVALID_CREDENTIALS, userAgent={}",
					request.getUsername(), request.getIp(), request.getUserAgent());
			// HTTP 401(인증 실패/HttpStatus.UNAUTHORIZED) 응답 반환 (로그인실패)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
			
		}
	}
	
}
