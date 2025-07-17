package com.example.loginlog.dto;

import lombok.Data;

@Data	//	Getter, Setter, toString 등 필요한 lombok생성
public class LoginRequest {
	private String username;	//사용자 아이디
	private String password;	//비밀번호
	private String ip;			//클라이언트 ip
	private String userAgent;	//브라우저나 기기정보(크롬이나 포스트맨 같은 요청정보)
}
