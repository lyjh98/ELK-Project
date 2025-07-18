# 🦾 ELK-Project

## 📝 소개

<br>

✅ 본 프로젝트는 `Spring Boot`에서 로그를 발생시키고, `JMeter`로 부하 트래픽을 생성한 후, `Filebeat → Logstash → Elasticsearch → Kibana`로 이어지는 
**엔드-투-엔드 로그 파이프라인**을 구현한 실습형 프로젝트입니다. 
<br> <br>
✅ JSON 기반 로그를 수집하여 **국가별, 시간대별 공격 추이 분석**까지 직접 시각화하며, 운영환경 트러블슈팅 경험도 함께 설계했습니다.
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


## 📌 Pipe Line

<img width="1311" height="170" alt="image" src="https://github.com/user-attachments/assets/36cfdb1a-b179-44ed-b4e1-429038885c58" />


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



<!--logback-spring.xml에서 JSON로그를 사용 가능하게 하는 라이브러리 -->
<!-- 라이브러리 이름이 logstash인 이유 : logstash에 잘 들어가도록 로그를 JSON형태로 만들어주는 라이브러리 -->
<!-- Logback에 JSON포맷 출력 기능을 추가해주는 라이브러리-->
<dependency>
	<groupId>net.logstash.logback</groupId>
	<artifactId>logstash-logback-encoder</artifactId>
	<version>7.4</version>
</dependency>

```

</details>

## 📌 JMeter
  
- CSV 기반 200,000건의 로그인 시도

<img width="1462" height="721" alt="image" src="https://github.com/user-attachments/assets/548f27b6-4308-44cc-8bdf-f87e093d73d0" />

✅ 스레드 수(사용자):10000
가상의 사용자 수 (= 동시에 요청 보낼 사용자 수)
여기선 10,000명의 사용자가 시뮬레이션됨 <br>

✅ 램프업 기간(초):15
10,000명의 사용자를 15초 동안 나눠서 실행
→ 10000 ÷ 15 = 약 666명/초 속도로 투입됨
숫자가 작을수록 부하가 급격히 몰림 <br>

✅ 루프 카운트:20
각 사용자당 요청을 몇 번 반복할지 설정
→ 사용자 1명이 20번 요청을 보냄
총 요청 수 = 사용자 수 × 루프 수 = 10,000 × 20 = 20만
 <br> <br> <br>

 
<img width="1452" height="704" alt="image" src="https://github.com/user-attachments/assets/3f2266e9-ee18-42de-b1b9-c25bb0022309" />
✅ View Result Tree
요청별 성공/실패 여부, 응답 코드, 요청/응답 내용을 상세히 확인할 수 있는 디버깅 도구 <br>
로그인 성공/실패 메시지, 오류 응답, 파라미터 확인 등에 활용
<br>

</da>

## 📌 Filebeat → Logstash

- Filebeat로 로그 수집   <br>

- Logstash에서 grok, mutate, date 필터 사용해 JSON 로그 파싱  <br>


<details>

<summary><strong> ⌨️코드 </strong></summary>


```
input {
#   file {
#     path => "C:/fisa_project/dummy_data/logs/ianlogin.log"   
#     start_position => "beginning"
#     sincedb_path => "NUL"      
#     codec => "json"
#   }
  beats{
    port => 5044
  }
}
filter {
  grok {
    match => {
      "message" => "password=%{DATA:password}, country=%{DATA:country}, timestamp=%{TIMESTAMP_ISO8601:timestamp}, success=%{WORD:success}(?:, failureReason=%{DATA:failureReason})?"
    }
  }

  date {
    match => ["timestamp", "yyyy-MM-dd'T'HH:mm:ss"]
    target => "@timestamp"
    timezone => "Asia/Seoul"
  }

  mutate {
    remove_field => ["message", "logger_name", "thread_name", "level", "@version", "path", "host", "timestamp"]
  }
}


output {
  stdout { codec => rubydebug }  # 콘솔 확인용
  elasticsearch {
    hosts => ["http://localhost:9200"]
    index => "lastianlog"
  }
}


- `start_position`, `sincedb_path`로 수집 범위 제어 <br>
```


</details>


## 📌 Elasticsearch

- 파싱된 로그를 Elasticsearch에 색인  <br>

<img width="895" height="542" alt="image" src="https://github.com/user-attachments/assets/19b0432c-629f-428d-ba45-26633e4c3e0f" />



## 📌 Kibana

- 실시간 대시보드 구축
<img width="1122" height="344" alt="image" src="https://github.com/user-attachments/assets/33b3380d-d941-4b7c-b106-85601fc54885" />


</details>

<br>
<br>

---

## 📊 Kibana 시각화 내용

| 시각화 항목                                         | 이미지                           | 설명                             | 
| ---------------------------------------------- | ----------------------------- | ------------------------------ | 
| **1. 로그인 성공/실패 비율**       | <img width="300" height="300" alt="화면 캡처 2025-07-18 085422" src="https://github.com/user-attachments/assets/e675fb97-f3b7-440d-bf6d-574a02ca6924" />| 전체 로그인 시도 중 성공 72.8%, 실패 27.2% | 
| **2. 국가별 로그인 성공 횟수**         |<img width="300" height="300" alt="화면 캡처 2025-07-18 084330" src="https://github.com/user-attachments/assets/c6769a6a-0b9e-4643-accf-7f585c14babf" />| 국가별 로그인 성공 건수 비교               | 
| **3. 월별 로그인 시도량 분포**        | <img width="300" height="300" alt="화면 캡처 2025-07-18 092305" src="https://github.com/user-attachments/assets/efe7a908-fcb8-44f0-bdc8-aaa4c08ac0c1" />| 국가별 로그인 시도를 월 단위로 시각화          | 
| **4. 비밀번호 일치 여부 + 연도** | <img width="300" height="300" alt="화면 캡처 2025-07-18 093807" src="https://github.com/user-attachments/assets/65167038-15f9-41ae-a59e-73f877f72978" />| 비밀번호 일치/불일치 여부와 연도별 시도 분석      |
| **5. 국가별 로그인 시도 비율**    | <img width="300" height="300" alt="화면 캡처 2025-07-18 100221" src="https://github.com/user-attachments/assets/31f5ae9d-7b17-49d4-b862-97cc7b488887" />| 로그인 시도를 국가별 비율로 면적화            |

---

## 🛠 트러블슈팅 모음
<details> <summary>✅ JMeter CSV 첫 열 읽음 여부</summary>
- CSV 첫 열이 컬럼값으로, JMeter가 읽는 데이터에서는 빠졌어야 했는데 읽음 <br>

- ignore first line 옵션을 True로 설정하여 csv파일 컬럼명을 무시하고 읽음
<img width="1084" height="318" alt="image" src="https://github.com/user-attachments/assets/689ebe7a-4d4e-4c2d-91cc-0e5c87bf9ef3" /> </details>

<details> <summary>✅ CSV boolean 파싱 오류</summary>
- "True" / "False" 대문자 값이 DB에서 boolean 매핑 실패
 → 텍스트 파일에서 True 와 False의 대문자열을 일괄적으로 소문자열로 변경 <br>
	
</details>

<details> <summary>✅ JMeter 실행 시간 부족</summary>
Duration 설정이 짧아 일부 트래픽만 전송됨 <br>

- 특히 Thread Group > Duration, Scheduler, Ramp-Up 설정이 짧으면 전체 부하가 걸리기 전에 테스트 종료됨 <br>

 → Thread Group 설정에서 Duration 10초 → 20초 이상으로 설정

<img width="1091" height="551" alt="image" src="https://github.com/user-attachments/assets/51d802fc-536d-4d10-8ebf-b8c082c3ef1f" />
생성된 로그 라인수 확인 명령어

```
wc -l login.log
```

</details>


<details> <summary>✅ Logstash 경로 설정 이슈</summary>
- 팀원 간 로그 파일 경로가 달라 실행 오류 발생

- Logstash는 지정된 로컬 절대 경로에서 로그를 찾기 때문에 경로가 다르면 수집 자체가 되지 않음

- No such file or directory 오류 발생

→ 공통 경로 통일 및 .conf 파일 내 주석으로 주의사항 명시

</details>

<details> <summary>✅ Elasticsearch 인덱스 중복 문제</summary>
- 로그 파일 수정 또는 재테스트 후 동일한 로그 파일을 다시 Logstash로 넘기려 했으나 Elasticsearch에 기존 인덱스가 존재해 새 데이터가 적재되지 않음

- 로그 파일 경로와 내용은 동일하지만 이전에 처리된 상태로 인식됨

- Filebeat나 Logstash는 한 번 처리한 파일을 내부 상태(sincedb 등)에 기록
  → 동일 경로/내용의 파일은 변경되지 않은 것으로 판단하고 무시함

- Elasticsearch 인덱스가 이미 존재하면 문서 ID 중복 또는 파싱 실패로 skip

</details>

<details> <summary>✅ Filebeat 로그 파싱 메타 필드만 출력되는 문제</summary>
- 로그 수집 및 분석 과정에서 다음과 같은 메타데이터 필드만 나타나고, 정작 로그 메시지(message)가 보이지 않음. <br>
<br>
	
```
log.file.path: C:\fisa_project\dummy_data\logs\ianlogin.log  
agent.name: 1-01  
agent.version: 7.11.2  
tags: [ "_grokparsefailure" ]  
```

> 이 현상은 로그 파싱 실패 시 나타나는 정상적인 현상으로,
Filebeat는 로그 메시지를 파싱하지 못하더라도 아래와 같은 메타 필드를 자동으로 생성하여 Elasticsearch에 전송.
> 
<br>

### 📑 Elasticsearch 자동 생성 필드 요약
| **필드명**              | **설명**                                  |
| -------------------- | --------------------------------------- |
| `@timestamp`         | 로그가 수집된 시간 (Filebeat가 수집한 시점 기준)        |
| `_index`             | 로그가 저장된 인덱스 이름 (예: `ianlogend`)         |
| `_id`                | Elasticsearch가 부여한 문서 고유 ID             |
| `_type`              | 문서 타입 (보통 `_doc`)                       |
| `_score`             | 검색 relevance 점수 (기본 검색 시 `-`)           |
| `log.file.path`      | 실제 로그 파일의 전체 경로                         |
| `log.offset`         | 로그 파일 내에서 이 로그가 시작된 바이트 위치              |
| `agent.name`         | Filebeat가 실행된 호스트 이름                    |
| `agent.hostname`     | 호스트 이름 (보통 `agent.name`과 동일)            |
| `agent.type`         | 수집 에이전트 유형 (여기선 `filebeat`)             |
| `agent.version`      | Filebeat 버전 (예: `7.11.2`)               |
| `agent.id`           | Filebeat 인스턴스 고유 식별자                    |
| `agent.ephemeral_id` | 실행 중인 Filebeat 프로세스의 고유 ID (매 실행 시 변경됨) |
| `input.type`         | 입력 타입 (`log`, `stdin` 등)                |
| `tags`               | 파싱 상태를 나타내는 태그 (예: `_grokparsefailure`) |
| `ecs.version`        | Elastic Common Schema 버전 (`1.6.0` 등)    |


</details>
	
---


## 🚀 향후 발전 방향 (Develop Direction)

- ✅ 더미데이터 대신 **실제 로그 데이터를 기반으로 시뮬레이션**하여 분석의 현실성 향상 <br><br>
- ✅ 최신 Kibana 기능(Lens, Canvas 등)을 활용한 **고급 대시보드 커스터마이징 및 알림 기능 구현** <br><br>
- ✅ 머신러닝 기반 탐지 모델과 연동하여 **이상 행위 탐지 자동화 및 실시간 대응 체계 구축** <br><br>
- ✅ **Slack / Email 등 알림 시스템과의 연동**을 통해 실패율 급증 등 이상 상황에 즉시 대응 <br><br>
- ✅ **클라우드 기반 고가용성 아키텍처 설계**를 통해 확장성과 안정성을 갖춘 ELK 환경 구성 <br><br>


---

## 📓 회고

### 👤 강한솔
- 이번 프로젝트는 단순한 로그 수집을 넘어, 데이터의 설계부터 생성, 정제, 수집 파이프라인 구현, 그리고 시각화 분석에 이르기까지 전 과정을 경험할 수 있는 값진 기회였습니다. <br>
진행 과정에서 예상치 못한 트러블슈팅도 많았고, 기술적 난관에 부딪히는 순간도 적지 않았지만, 그만큼 직접 부딪히며 해결해나가는 과정을 통해 얻은 배움은 매우 컸습니다. <br>
특히 로그가 어떻게 구조화되고, 어떤 방식으로 실시간 분석이 가능해지는지를 체감하며, 단순한 구현을 넘어 시스템 전반을 바라보는 관점을 가질 수 있었고, 실제 운영 환경에서 요구되는 데이터 흐름의 안정성, 유연성, 가시성의 중요성도 깨달을 수 있었습니다. <br>
돌이켜보면 험난했지만, 그만큼 성장의 밀도가 높았던 프로젝트였다고 자신 있게 말할 수 있습니다. <br>


### 👤 이제현
- 데이터 시각화 프로젝트에 처음 도전했지만, 시나리오 기반으로 단계별 구현하며 실전 감각을 익히는 경험이었습니다. <br>
  JMeter를 활용해 JSON 형식으로 데이터를 변환하여 로그를 기록하며, Spring 기반 시스템의 흐름을 이해하는 데 집중했습니다. <br>
  기술 스택을 중심으로 데이터 처리 및 로깅 흐름을 체험하며, 실무에서 활용 가능한 개발 역량을 쌓을 수 있었습니다. <br>


### 👤 전수민
- 처음 접하는 내용이라 다소 어려움이 있었지만, 팀원들과 함께 협력하며 프로젝트를 차근차근 완성해 나갈 수 있었습니다. <br>
파이프라인을 처음 구축할 때는 다소 복잡하게 느껴졌지만, 실습을 통해 전체적인 흐름과 구조를 이해할 수 있었고, 매우 뜻깊은 시간이었습니다. <br>
특히 ELK를 직접 다뤄보면서 데이터가 어떻게 수집되고 시각화되는지를 체계적으로 배울 수 있어 유익한 경험이었습니다. <br>


### 👤 황병길
- JMeter로 20만 건 대규모 부하 테스트와 시나리오를 설계하고, 직접 생성한 로그인 로그 데이터를 기반으로 Filebeat-Logstash-Kibana를 연계한 파이프라인을 구축했습니다. <br>
  데이터 파이프라인 구축의 복잡성 이해와 기본 개념을 학습할 수 있었고, JMeter를 활용해서 직접 로그 데이터를 만들어 프로젝트를 진행했다는 점이 다른 데이터셋을 가져와서 진행하는 것보다 더 의미 있는 프로젝트였다고 생각합니다. <br>
  파이프라인이 구축된 후에도 지속적인 모니터링과 튜닝이 필요하다는 점을 깨달았습니다. 향후 실시간 알림 시스템 구축, 머신러닝 기반 이상 탐지 기능도 추가해보고 싶습니다. <br>
