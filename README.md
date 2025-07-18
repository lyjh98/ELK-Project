# 🦾 ELK-Project

## 📝 소개

<br>

✅ 본 프로젝트는 `Spring Boot`에서 로그를 발생시키고, `JMeter`로 부하 트래픽을 생성한 후, <br>
`Filebeat → Logstash → Elasticsearch → Kibana`로 이어지는<br>
**엔드-투-엔드 로그 파이프라인**을 구현한 실습형 프로젝트입니다. 
<br> <br>
✅ JSON 기반 로그를 수집하여 **국가별, 시간대별, 실패 사유별 공격 추이 분석**까지 직접 시각화하며, 운영환경 트러블슈팅 경험도 함께 설계했습니다.
<br>
<br>

---

| 강한솔 | 이제현 | 전수민 | 황병길 |
|:---:|:---:|:---:|:---:|
| [kkangsol](https://github.com/kkangsol) | [lyjh98](https://github.com/lyjh98) | [Jsumin07](https://github.com/Jsumin07) | [Gill010147](https://github.com/Gill010147) |
| <img src="https://avatars.githubusercontent.com/kkangsol" width="150px;"> | <img src="https://avatars.githubusercontent.com/lyjh98" width="150px;"> | <img src="https://avatars.githubusercontent.com/Jsumin07" width="150px;"> | <img src="https://avatars.githubusercontent.com/Gill010147" width="150px;"> |






---

## 🎬 메인 시나리오

<img src="https://github.com/user-attachments/assets/66ca00aa-fba1-4be5-960f-1c636d99d228" width="860" height="456" />

---

## 🎯 프로젝트 목표
<br>
- ✅ 로그 분석 시스템 구축의 전 과정을 실무 흐름대로 구현 <br><br>
- ✅ JMeter 기반의 공격 시나리오 생성 → 구조화된 JSON 로그 수집   <br><br>
- ✅ Filebeat → Logstash → Elasticsearch → Kibana로 이어지는 수집/색인/시각화 파이프라인 검증   <br><br>
- ✅ 공격 패턴, 시간대, 국가별 이상 로그 분포 시각화로 인사이트 도출 <br><br>

---
## 🛠️ 기술 스택
![Filebeat](https://img.shields.io/badge/Filebeat-005571?style=flat&logo=elastic&logoColor=white)
![Logstash](https://img.shields.io/badge/Logstash-005571?style=flat&logo=elastic&logoColor=white)
![Elasticsearch](https://img.shields.io/badge/Elasticsearch-005571?style=flat&logo=elasticsearch&logoColor=white)
![Kibana](https://img.shields.io/badge/Kibana-005571?style=flat&logo=kibana&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat&logo=springboot&logoColor=white)
![JMeter](https://img.shields.io/badge/JMeter-D22128?style=flat&logo=apachejmeter&logoColor=white)
![Eclipse](https://img.shields.io/badge/Eclipse-2C2255?style=flat&logo=eclipseide&logoColor=white)
![ChatGPT](https://img.shields.io/badge/ChatGPT-41B06E?style=flat&logo=openai&logoColor=white)
![Lombok](https://img.shields.io/badge/Lombok-ED6C30?style=flat&logo=lombok&logoColor=white)


## 📋 주요 기술 및 버전
| 목적 | 기능 | 버전 |
|---|---|---|
| 🔍 로그 수집 | Filebeat | 7.11.2 |
| ⚡ 데이터 가공 | Logstash | 7.11.2 |
| 💾 저장/검색 | Elasticsearch | 7.11.2 |
| 📊 시각화 | Kibana | 7.11.2 |
| 🌐 API/데이터 | Spring Boot | 3.5.3 |
| 🚀 부하생성 | JMeter | 5.6.3 |

---
## ⚙️ 프로젝트 내용

### 📌 Spring Boot

<details>
<summary><strong> 사용자 로그인 요청 수신 후 DTO 기반 로그 생성</strong></summary>


```
package com.example.loginlog.dto;

import lombok.Data;

@Data	//	Getter, Setter, toString 등 필요한 lombok생성
public class LoginRequest {
	
    private String email = "ian.lee@celebritymail.com";	// 이메일 고정
    private String password;	//패스워드	
    private String country;		//접속 시도 나라
    private String timestamp;		//접속 시도 시간
    private boolean success;	//성공 실패
    private String failureReason;	//실패 이유

}
```

</details>


<details>

<summary><strong> 성공/실패 시 로그 형태 구분하여 JSON 로그 기록</strong></summary>


```
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
		
		if(request.isSuccess()) {
			// 성공 로그
			logger.info("LOGIN_SUCCESS - password={}, country={}, timestamp={}, success={}",
					request.getPassword(),
					request.getCountry(),
					request.getTimestamp(),
					request.isSuccess());
			// ResponseEntity : 응답코드까지 보낼 수 있는 Spring-web 라이브러리
			return ResponseEntity.ok("로그인 성공");
		}else {
			// 실패 로그
			logger.warn("LOGIN_FAILED - password={}, country={}, timestamp={}, success={}, failureReason={}",
			        request.getPassword(),
			        request.getCountry(),
			        request.getTimestamp(),
			        request.isSuccess(),
			        request.getFailureReason());
			// HTTP 401(인증 실패/HttpStatus.UNAUTHORIZED) 응답 반환 (로그인실패)
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
			
		}
	}
	
}
```

</details>


<summary><strong>📌 JMeter</strong></summary>

- 다양한 국가, IP, 시간대에 대한 부하 시나리오 설계   <br>
- CSV 기반 10,000건 이상 로그인 시도 시뮬레이션 <br>



<details>
<summary><strong>📌 Filebeat → Logstash</strong></summary>

- Filebeat로 로그 수집   <br>
- Logstash에서 grok, mutate, date 필터 사용해 JSON 로그 파싱  <br>
- `start_position`, `sincedb_path`로 수집 범위 제어 <br>

</details>

<details>
<summary><strong>📌 Elasticsearch</strong></summary>

- 파싱된 로그를 Elasticsearch에 색인  <br>
- 검색, 필터링, 쿼리를 통해 국가별·시간대별 분석 수행 <br>

</details>

<details>
<summary><strong>📌 Kibana</strong></summary>

- 실시간 대시보드 구축  <br>
- 국가별 실패 비율, 시간대별 공격 집중도, 실패 사유 분석 등 시각화 <br>

</details>

---

## 🛠 트러블슈팅 모음

### ✅ CSV boolean 파싱 오류
- `"True"` / `"False"` 대문자 값이 DB에서 boolean 인식 실패  
- → `"1"` / `"0"`으로 변환하여 해결 (사전 전처리 or SQL SET 구문 사용)

### ✅ JMeter 실행 시간 부족
- Duration 설정이 짧아 일부 트래픽만 전송됨  
- → Duration/Loop Count 충분히 증가시켜 전체 데이터 반영되도록 수정

### ✅ Logstash 경로 설정 이슈
- 팀원 간 로그 파일 경로가 달라 실행 오류 발생  
- → 공통 경로 통일 및 `.conf` 파일 내 주석으로 주의사항 명시

### ✅ Elasticsearch 인덱스 중복 문제
- 기존 인덱스가 남아있어 로그 재수집 안 됨  
- → `sincedb_path => "NUL"` 설정 or 로그 파일명 변경 or 인덱스 삭제로 해결

---

## 📊 Kibana 시각화 내용

| 시각화 항목 | 설명 |
|------------|------|
| 📅 시간대별 실패 로그 | 새벽 시간대 집중 공격 여부 분석 |
| 🌍 국가별 실패 분포 | 특정 국가(중국, 러시아 등) 중심 공격 추정 |
| ❗ 실패 사유 분석 | 잘못된 비밀번호, 계정 없음, 잠금 계정 등 |
| 📈 전체 트렌드 | 시간 흐름에 따른 공격 시도 패턴 추이 |

---

## 🚀 향후 발전 방향 (Develop Direction)

- ✅ 더미데이터 대신 **실제 로그 데이터를 기반으로 시뮬레이션**하여 분석의 현실성 향상 <br>
- ✅ 최신 Kibana 기능(Lens, Canvas 등)을 활용한 **고급 대시보드 커스터마이징 및 알림 기능 구현** <br>
- ✅ 머신러닝 기반 탐지 모델과 연동하여 **이상 행위 탐지 자동화 및 실시간 대응 체계 구축** <br>
- ✅ **Slack / Email 등 알림 시스템과의 연동**을 통해 실패율 급증 등 이상 상황에 즉시 대응 <br>
- ✅ **클라우드 기반 고가용성 아키텍처 설계**를 통해 확장성과 안정성을 갖춘 ELK 환경 구성 <br>


---

## 📓 회고

### 👤 강한솔
- (작성 예정)

### 👤 이제현
- (작성 예정)

### 👤 전수민
- (작성 예정)

### 👤 황병길
- (작성 예정)
