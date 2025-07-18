# ELK-Project

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

