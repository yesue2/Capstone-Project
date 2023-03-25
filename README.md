# 2022-1-OSSP-Banana-4

## About Team


|이름|학과|역할|
|------|---|---|
|[김효정](https://github.com/hyojKim2) [![](https://img.shields.io/badge/Github-dgkhj-blue?style=flat-square&logo=Github)](https://github.com/hyojKim2)|컴퓨터공학전공|팀장 |
|[강현우](https://github.com/rkdgusdn) [![](https://img.shields.io/badge/Github-rkdgusdn-blue?style=flat-square&logo=Github)](https://github.com/rkdgusdn)|컴퓨터공학전공|Chatting|
|[이지호](https://github.com/jiholee0) [![](https://img.shields.io/badge/Github-jiholee0-blue?style=flat-square&logo=Github)](https://github.com/jiholee0)|컴퓨터공학전공|Matching Algorithm|
|[김재철](https://github.com/kjc4410) [![](https://img.shields.io/badge/Github-kjc4410-blue?style=flat-square&logo=Github)](https://github.com/kjc4410)|의생명공학전공|User|


## 1. 프로젝트 주제
<div>
<h4> 동국대 학생들끼리 먹고 싶은 음식을 같이 주문하여, 배달비 부담을 줄여주는 배달 음식 공동 구매 앱
</div>   
    
    
## 2. 기술스택 & Workflow
Kotlin & Firebase & Android Studio<br>
<img width="20%" src="https://user-images.githubusercontent.com/87844641/173768618-f9e968a8-1be8-486e-9cf9-0482e664ef9a.png"/>
<img width="20%" src="https://user-images.githubusercontent.com/87844641/173768636-6dc23d7a-2d7c-437a-8daa-5998a004d68c.png"/>
<img width="20%" src="https://user-images.githubusercontent.com/87844641/173817432-ddb8a924-3e7e-4370-8ed7-79e019a4bf67.png"/>    

    
## 3. 프로그램 구조도
<img width="70%" src="https://user-images.githubusercontent.com/87844641/173832072-fbde856d-a3ff-4614-bdd5-08857ae64a4d.png"/>
   
    
## 4. 어플 소개
    

<img width="20%" src="https://user-images.githubusercontent.com/87844641/173873678-c766150c-19ab-4516-ba16-04470bea5ddd.png"/>
<h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;DeliShare</h3><br>
   
    
    
<h3>(1) 로그인 및 회원가입</h3>

- 동국대학교 웹 메일(@dgu.ac.kr 및 @dongguk.edu)과 비밀번호를 사용하여 로그인하고, 한번 로그인하면 자동로그인 가능<br>
- 비밀번호를 잊은 경우 이메일을 입력하면 비밀번호 재설정 링크 전송(단, @dgu.ac.kr 메일을 사용한다면 비밀번호를 변경하는 메일이 스팸함에 있음)    
<img width="20%" src="https://user-images.githubusercontent.com/87844641/173760147-b5856c81-0647-45a6-9b13-dab5c4597f6d.png"/> <img width="20%" src="https://user-images.githubusercontent.com/87844641/173834290-7eb25c8a-6661-4605-b4ed-cc2d3fd51a26.png"/><br>

- **동국대 웹메일(@dgu.ac.kr 및 @dongguk.edu)** 만이 회원가입에 이용되고, 등록한 웹메일로 인증번호 발송<br>
<img width="20%" src="https://user-images.githubusercontent.com/87844641/173760479-a0da532b-d12f-4424-b720-34a21cf63740.png"/> <img width="20%" src="https://user-images.githubusercontent.com/87844641/173780458-e78af697-8741-429a-82f3-2f803e89718b.png"/><br>

<h3>(2) 카테고리 페이지</h3>
    
- 카테고리 종류 : 돈까스/회/일식, 중식, 치킨, 백반/죽/국수, 카페/디저트, 분식, 찜/탕/찌게, 피자, 양식,<br> 
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;고기/구이, 족발/보쌈, 아시안, 패스트푸드, 도시락
- 우측 상단의 프로필 이미지를 통해 마이페이지로 이동 가능
<br><img width="20%" src="https://user-images.githubusercontent.com/87844641/173780852-0018d0cc-8e83-4278-a073-6a3b1569cd77.png"/>


<h3>(3) 매칭 및 채팅</h3>
 
- 브랜드 선택<br>
    - 브랜드는 동국대학교 주변의 음식점들을 보여주며 3종류의 가게까지 선택가능<br>
    - 또한 상관없음 선택란이 있어, 결정이 어렵거나 음식점을 크게 고려하지 않는 사람에게 유용함  
<img width="20%" src="https://user-images.githubusercontent.com/87844641/173843675-00376ab3-ad9e-4673-96ac-a3f6fe558f46.png"/><br>
    
- **매칭중, 매칭 실패, 매칭 성공**<br>
    - 매칭에 성공하면 성공페이지와 함께 채팅 시작이 가능
    - 매칭에 실패하면 실패페이지와 함께 다시 찾기 가능<br>
<img width="20%" src="https://user-images.githubusercontent.com/87844641/173782938-4cf1e44b-c071-44cb-89cc-001a969c6910.png"/> <img width="20%" src="https://user-images.githubusercontent.com/87844641/173847413-92ab8880-dd3b-4745-9c60-783c9ec309b8.png"/> <img width="20%" src="https://user-images.githubusercontent.com/87844641/173844161-054ae823-51d6-41a9-8e9b-ef4464832a09.png"/><br>
- **채팅 및 평가 페이지**<br>
    - 메이트와 함께 음식을 받으면 거래종료를 통해 메이트에게 평가 가능
    - 별점을 통해 등급을 매기고, 리뷰를 남길 수 있음<br>
<img width="20%" src="https://user-images.githubusercontent.com/87844641/173843876-b4c4deac-635f-4883-b885-678d2217511b.png"/> <img width="20%" src="https://user-images.githubusercontent.com/87844641/173844424-d470d862-7c7e-427d-96ac-851e0e093d85.png"/> <img width="20%" src="https://user-images.githubusercontent.com/87844641/173879681-b535d4db-8784-4af4-bec3-8ffdc2d83dfe.png"/><br><br>
    
<h3>(4) 마이페이지</h3>
    
- 메이트들에게 받은 별점과 리뷰를 볼수 있음<br>
<img width="20%" src="https://user-images.githubusercontent.com/87844641/173847266-08adc6c6-de5e-43f1-bd4a-982012c0ef42.png"/><br>
    
## 5. 시연영상
    
  
https://user-images.githubusercontent.com/87844641/173886505-0e7d574a-37ff-4041-a2b4-8168e83a9a99.mp4    

    
## 6. 노션 링크
[노션 페이지로](https://www.notion.so/54a698022ddb46f8be22390bc8c99fe0)
