package com.example.loginlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoginlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginlogApplication.class, args);
		// 포스트맨으로 확인 결과 로그 정상적으로 찍혔고 Logstash 통해서 logger_name에 LoginController인것만 필터링해서 분석하면 됨!
	}

}
