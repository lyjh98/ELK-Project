# ELK-Project

## 📝 소개
✅ 이 프로젝트는 Spring Boot → JMeter—Filebeat → Logstash—Elasticsearch → Kibana로 연결되는 통합 데이터 엔지니어링 파이프라인을 구축하여,
✅ 로그 기반 이상 행위 탐지를 위한 데이터 생성, 트래픽 시뮬레이션, 수집 파이프라인 구축 및 시각화 분석까지의 전 과정을 실습한 프로젝트입니다. <br>
✅ 공격 국가, 시간대, 실패 원인별로 대량 데이터를 분류·추적 가능하며, 운영환경 문제해결 경험까지 전 과정을 직접 설계·구현한 프로젝트입니다.

---

|강한솔|이제현|전수민|황병길|
|:---:|:---:|:---:|:---:|
|[kkangsol](https://github.com/kkangsol)|[lyjh98](https://github.com/lyjh98)|[Jsumin07](https://github.com/Jsumin07)|[Gill010147](https://github.com/Gill010147)|
|<img src="https://avatars.githubusercontent.com/kkangsol" width="150px;" alt=""/>>|<img src="https://avatars.githubusercontent.com/lyjh98" width="150px;" alt=""/>>|<img src="https://avatars.githubusercontent.com/Jsumin07" width="150px;" alt=""/>>|<img src="https://avatars.githubusercontent.com/Gill010147" width="150px;" alt=""/>|

---

## ✍️ 메인 시나리오
<img width="860" height="456" alt="image" src="https://github.com/user-attachments/assets/66ca00aa-fba1-4be5-960f-1c636d99d228" />

## 📈 공격 패턴 분석

🌐 다양한 국가에서 수천 건의 로그인 시도 <br>
❌ 접근 실패 (비밀번호 오류) <br>
🔁 특정 국가, 특정 시간에 반복적이고 조율된 시도 발생 <br>
  
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

---

## 🎯 학습목표
✅ 실무형로그 분석 시스템 구축 전 주기를 검증하기 위해, 트래픽 시뮬레이션 → 파이프라인 구축 → Kibana 시각화까지 통합 구현했습니다. <br>
✅ 기술데모형ELK 스택 기반 로그 분석 환경 구축을 위한 기술 검증 프로젝트로, JMeter 부하 테스트부터 이상 탐지 시각화까지 포함됩니다. <br>
✅ 포트폴리오형로그 수집 및 분석 파이프라인 전반을 직접 구현, 트래픽 생성부터 시각화 기반 인사이트 도출까지 실무형 데이터 흐름을 경험했습니다. 

---

## 📓 회고
- 강한솔
  - 
- 이제현
  - 
- 전수민
  - 
- 황병길
  - 

