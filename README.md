# ELK-Project

## 📝 소개
- 이 프로젝트는 Spring Boot—JMeter—Filebeat—Logstash—Elasticsearch—Kibana로 연결되는 통합 데이터 엔지니어링 파이프라인을 구축하여,
- 로그인 시도(Big Data) 로그를 실시간 수집, 정제, 시각화하는 실전 보안 관제/분석용 시스템입니다.
- 공격 국가, 시간대, 실패 원인별로 대량 데이터를 분류·추적 가능하며, 운영환경 문제해결 경험까지 담았습니다.

|강한솔|이제현|전수민|황병길|
|:---:|:---:|:---:|:---:|
|[kkangsol](https://github.com/kkangsol)|[lyjh98](https://github.com/lyjh98)|[Jsumin07](https://github.com/Jsumin07)|[Gill010147](https://github.com/Gill010147)|
|<img src="https://avatars.githubusercontent.com/kkangsol" width="150px;" alt=""/>>|<img src="https://avatars.githubusercontent.com/lyjh98" width="150px;" alt=""/>>|<img src="https://avatars.githubusercontent.com/Jsumin07" width="150px;" alt=""/>>|<img src="https://avatars.githubusercontent.com/Gill010147" width="150px;" alt=""/>|

## ✍️ 메인 시나리오
<img width="860" height="456" alt="image" src="https://github.com/user-attachments/assets/66ca00aa-fba1-4be5-960f-1c636d99d228" />

## 📈 공격 패턴 분석

- 🌐 다양한 국가에서 수천 건의 로그인 시도
- ❌ 접근 실패 (비밀번호 오류)
- 🔁 특정 국가, 특정 시간에 반복적이고 조율된 시도 발생
-  
  → **봇넷 혹은 조직적 공격**으로 의심

---

## ⚠️ 위험 감지

### 🔧 JMeter
- 다양한 국가의 IP를 시뮬레이션한 로그인 시도 생성

### 🧾 DTO 기반 JSON 로그 생성
- 로그인 요청을 구조화된 JSON 형태로 기록

### 📦 Logstash → Elasticsearch → Kibana
- 실시간 로그 수집 및 색인화
- **Kibana 대시보드**에서 국가별·시간별 공격 시각화

