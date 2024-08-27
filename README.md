#pushtrip(최종 프로젝트)

## EMAIL
- 김태주 :<0690lsh@naver.com>
- 김민욱 :<stardust117@naver.com>
- 김승진 :<soojoo0114@gmail.com>
- 이호기 :<sackje4896@naver.com>

## 프로젝트 소개

- pushtrip는 여행을 좋아하는 사람들이 일정관리 및 여행관련 정보를 공유하고 소통하는 커뮤니티입니다.
- 마이 페이지에 일정을 작성하고 친구들에게 공유할 수 있습니다.
- 게시판을 통해 여행용품 중고거래 및 여행지 정보공유 등 다양한 활동을 할 수 있습니다.
- 실시간 채팅 및 쪽지를 통해 사용자들과 원활하게 대화할 수 있습니다.

<br>

## 팀원 구성

<div align="center">

| **김태주(PL)[백엔드]** | **김민욱(DBA)[백엔드]** | **김승진[백엔드]** | **이호기[프론트엔드]** |
| :------: |  :------: | :------: | :------: |
| [<img src="https://github.com/user-attachments/assets/0701c137-1eee-4acb-a5da-d05e4748f05b" height=150 width=150> <br/> @김태주](https://github.com/ktj4102) | [<img src="https://github.com/user-attachments/assets/6671d06b-4557-4ac8-b46d-76df69628ad5" height=150 width=150> <br/> @김민욱](https://github.com/Minwook2053) | [<img src="https://github.com/user-attachments/assets/7d29ac91-c1f3-4151-9340-51696839698c" height=150 width=150> <br/> @김승진](https://github.com/tmdwlsdl) | [<img src="https://github.com/user-attachments/assets/a2bc51a2-f8ba-419c-a961-c48a5992f662" height=150 width=150> <br/> @이호기](https://github.com/LEEHOKEE) |

</div>




### 프로젝트 개요
- [x] [💿서비스 시연 영상](#서비스-시연-영상)
- [x] [🎯서비스 핵심기능](#서비스-핵심기능)
- [x] [🛠기술 스택](#기술-스택)
- [x] [✨기술적 의사결정](#기술적-의사결정)
- [x] [🖥사이트 흐름도](#사이트-흐름도)
- [x] [🚧시스템 아키텍처](#시스템-아키텍처)
- [x] [📖ERD](#erd)

<hr/>

### 💿서비스 시연 영상

[![pushtripYouTube](https://github.com/user-attachments/assets/aa37c5fe-39a8-483e-82b2-df3ba0fba875)](https://youtu.be/rt0gXZzta-8)
- pushtripYouTube

### 🎯서비스 핵심기능
```
    회원 : 로그인 | 회원가입 | 이메일 인증 | 전화번호 인증 | 소셜 로그인(카카오/구글) | 아이디 찾기 | 비밀번호 찾기 | 임시 비밀번호 발송
    마이페이지 : 여행일정 등록,수정,삭제,조회| 카카오톡 일정 공유 | 내정보 조회, 수정, 삭제
    여행 : 여행게시판 top5 | 여행지 정보제공
    문의사항 : 1:1문의 등록,수정,삭제 | 게시판 검색 | 작성자/관리자 한정 댓글 작성 | 댓글 등록,수정,삭제 | 로그인 한정 접속
    공지사항 : 관리자 한정 게시물 작성 | 댓글 등록, 수정, 삭제, 조회 | 게시판 검색
    쪽지기능 : 1:1 쪽지 전송, 조회, 삭제
    채팅방 : 실시간 알림 | 실시간 채팅
    챗봇 : ai 상담
    자유게시판 : 게시물 등록, 수정, 삭제, 조회 | 댓글 등록, 수정, 삭제, 조회 
    여행게시판 : 게시물 추천| 게시물 등록, 수정, 삭제, 조회 | 댓글 등록, 수정, 삭제, 조회 
    중고거래 게시판 : 게시물 등록, 수정, 삭제, 조회 | 댓글 등록, 수정, 삭제, 조회  | 인기게시물 조회
```

<details>
<summary>핵심기능 #1. 실시간 알림</summary>

- [x] 채팅방을 닫고 있을 경우 새로운알림의 갯수를 카운트해서 뱃지로 표시합니다.
</details>


<details>
<summary>핵심기능 #2. 일정관리 및 공유</summary>

- [x] 맛집이나 놀거리를 검색하고 메모를 작성하여 일정에 추가하거나 수정할 수 있습니다.
- [x] 추가한 일정을 카카오톡으로 공유할 수 있다. 
</details>

<details>
<summary>핵심기능 #3. ai상담 </summary>

- [x] ap챗봇을 활용하여 24시간 상담이 가능합니다.
</details>

<details>
<summary>핵심기능 #4. 전화번호 전송</summary>

- [x] coolSMS api 이용해 전화번호 인증번호 발송 기능을 구현하였습니다.
</details>

<details>
<summary>핵심기능 #5. 이메일 발송</summary>

- [x] JavaMailSender를 이용해 이메일 인증 및 임시 비밀번호 발송 기능을 구현하였습니다.
</details>

<details>
<summary>핵심기능 #6. 소셜 로그인</summary>

- [x] 일반 로그인의 경우 회원가입 양식 작성 후 이메일 인증을 거쳐야 하는 반면, 소셜 로그인한 회원은 `해당 계정에서 불러온 이름 및 이메일 정보가 연동`돼 입력란을 채우며 나아가 별도의 이메일 인증 없이 곧바로 이용이 가능합니다.
</details>

<details>
<summary>핵심기능 #7. 여행게시판top5 </summary>

- [x] 여행게시판에서 추천수가 가장 높은 게시물을 가져와 이용자에게 제공합니다.

</details>
<details>
<summary>핵심기능 #8. 일정공유</summary>

- [x] 작성한 일정을 카카오톡 공유하기로 공유할 수 있습니다.
</details>

### 🛠기술 스택
OS | Windows 10
--- | --- |
Language | ![Java](https://img.shields.io/badge/JAVA-000?style=for-the-badge&logo=java&logoColor=white) ![Spring](https://img.shields.io/badge/Spring-000?style=for-the-badge&logo=spring&logoColor=white) ![HTML5](https://img.shields.io/badge/html5-000?style=for-the-badge&logo=html5&logoColor=white) ![CSS3](https://img.shields.io/badge/css3-000?style=for-the-badge&logo=css3&logoColor=white) ![JavaScript](https://img.shields.io/badge/javascript-000?style=for-the-badge&logo=javascript&logoColor=white)
IDE | ![SQL Developer](https://img.shields.io/badge/SQL%20Developer-000?style=for-the-badge&logo=oracle&logoColor=white) ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJ%20IDEA-000?style=for-the-badge&logo=intellijidea&logoColor=white)
Framework | ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white) 
Build Tool | ![apache groovy](https://img.shields.io/badge/Apache%20Groovy-4298B8?style=for-the-badge&logo=apachegroovy&logoColor=white)
Database | ![Oracle Database 11g](https://img.shields.io/badge/Oracle-F80000?style=for-the-badge&logo=oracle&logoColor=white) ![redis](https://img.shields.io/badge/redis-FF4438?style=for-the-badge&logo=redis&logoColor=white)
Frontend | ![HTML5](https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white) ![CSS3](https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=css3&logoColor=white) ![JavaScript](https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black) ![jQuery](https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white)
Library | ![Spring Security](https://img.shields.io/badge/spring%20security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white) ![Thymeleaf](https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white) ![OAuth 2.0 Client](https://img.shields.io/badge/OAuth%202.0%20Client-4b4b4b?style=for-the-badge) ![jsonwebtokens](https://img.shields.io/badge/json%20web%20tokens-000000?style=for-the-badge&logo=jsonwebtokens&logoColor=white)
API | ![Java Mail](https://img.shields.io/badge/Java%20Mail-3a75b0?style=for-the-badge) ![Daum Postcode](https://img.shields.io/badge/Daum%20Postcode-f94756?style=for-the-badge) ![coolSMS](https://img.shields.io/badge/cool%20SMS-f7943a?style=for-the-badge) ![kakaotalk](https://img.shields.io/badge/kakaotalk-FFCD00?style=for-the-badge) ![kakaomap](https://img.shields.io/badge/kakaomap-FFCD00?style=for-the-badge) ![naver](https://img.shields.io/badge/naver-03C75A?style=for-the-badge) ![tawk.to](https://img.shields.io/badge/tawk.to-000?style=for-the-badge&logo=tawk.to&logoColor=white) ![jpa](https://img.shields.io/badge/jpa-000?style=for-the-badge&logo=jpa&logoColor=white) 
Server |![Apache Tomcat 9.0](https://img.shields.io/badge/Apache%20Tomcat%20-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black)
Version Control | ![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white) ![Notion](https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white)

### 🖥사이트 흐름도
<img src="https://github.com/user-attachments/assets/014d3218-6435-42d2-9aa1-9879c2040cc4">


### ✨기술적 의사결정
선택기술 | 선택이유 및 근거
--- | --- |
`SSE` | 로그인 통해 Server와 Client간 연결이 이뤄지거든 Client에게 추가적인 요청을 요구하지 않으면서도 불시에 발생되는 이벤트들을 즉각 송신할 수 있어야 하기에 **Server to Client로의 단방향 통신**이 가능한 `Server-Sent Events(SSE)`가 실시간 알림을 구현하는 데 적합하다고 판단하였음.
`Redis` | 휘발성 데이터인 _이메일 인증 토큰_ 을 데이터베이스에 직접 저장 및 호출하는 것은 불필요한 부하를 초래할 수 있으므로 인메모리(In-Memory) 형태에 TTL(Time to Live) 특성을 지녀 유효기간이 설정된 `Redis` 기반의 **Refresh Token**으로서 관리.
`Jasypt` | 보안 측면을 고려하여 `Jasypt` 통해 _비밀번호_ 와 같은 **property의 암호화**를 진행하고, 어플리케이션 실행 시 값을 요청할 수 있도록 VM arguments로 등록함.
`JUnit5` | 주어진 환경에서(given) 특정 코드가 실행됐을 때(when) 어떤 결과로 이어지는지(then) 모듈 단위의 테스트케이스로 구분. **전제적 요구사항을 점검하고, 의도대로 작동하는지 검증**하면서 코드의 생산성 및 유지보수성을 높이고자 함.
`Enum` | 문구 쇼핑몰 특성에 맞춰 _잉크컬러, 바디컬러, 심두께, 태그, 할인율, 상품카테고리, 기본배송메시지, 카드사별 할부혜택내용, 문의게시판 말머리, 실시간 알림 종류_ 등을 **연관된 상수들의 집합인 열거형** `Enum`으로 정의. 각 필드마다 데이터를 배정하고 이를 접근자(getter method) 통해 호출하면서 리팩토링 시 변경 범위를 최소화하였음.
`Commons Lang3` | 상품 전체 검색 시 _StringUtils, NumberUtils_ 등 `Apache Commons Lang3` 클래스에 정의된 메소드 통해 **parameter type을 동적으로 구분 및 처리**하도록 조건별 쿼리문을 최적화하였음.
`Thymeleaf Layout` | _Alert, Modal, ***Pagination***, ***Comment***, Header/Footer,  Dashboard_ 등을 각각 `Fragment`로 구분하여 사이트 전역적으로 사용되는 데 있어 코드 중복을 피하고 추가/수정에 용이하도록 의도하였음.
`MessageSource` | 쇼핑몰 확장성 및 코드 유지보수성을 고려하여 **필요에 따라 다국어 지원**할 수 있도록 앞서 `Thymeleaf Layout Fragment`로 구분해둔 알러트(alert) 메시지들은 `MessageSource` 인터페이스의 `properties`로 관리되고 있음.
`@SessionAttributes` | 어노테이션 통해 **세션상에 정보를 저장하고, 여러 화면 또는 연계된 요청 중에 해당 객체를 공유**하도록 정의해 다음과 같이 활용하였음.<br>`로그인 확인용 정보(loginMember)`<br>:  현재 로그인한 회원의 아이디를 가리키며 나아가 관리자인지 혹은 작성자 본인인지를 구분.<br>`최근 본 상품(recentlyViewed)`<br>: 회원은 접속 이래 현재까지 조회한 상품 목록을 '마이페이지'에서 확인 가능.<br>`장바구니(geulBeotCart)`<br>: 비로그인 상태에서 담은 장바구니 상품이 회원의 장바구니 목록으로 저장.<br>`바로주문 요청 정보(orderItem)`<br>: 로그인 전 선택한 상품 및 세부 옵션 정보 그대로 '주문페이지'로 이어지며, 이는 일회성이기에 사용자의 기존 장바구니와는 무관.<br>`소셜 로그인 여부(signInWithSocialAccount)`<br>: 소셜 로그인(카카오/구글) 시 이동 경로 구분.

### 🚧시스템 아키텍처

<img src="https://github.com/user-attachments/assets/ae3f885a-8f50-4cb2-b607-79f064685f58">


### 📖ERD

<img src="https://github.com/user-attachments/assets/e253ff17-1f45-4f81-ad5b-9e9660ce4044">
