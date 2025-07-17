package com.example.loginlog.dto;

import lombok.Data;

@Data	//	Getter, Setter, toString 등 필요한 lombok생성
public class LoginRequest {
	
    private String email = "ian.lee@celebritymail.com";	// 이메일 고정
    private String password;	//패스워드	
    private String country;		//접속 시도 나라
    private long timestamp;		//접속 시도 시간
    private boolean success;	//성공 실패
    private String failureReason;	//실패 이유

}
