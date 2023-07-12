# 배달비 나눔 애플리케이션



## 1. 프로젝트 주제
<div>
<h4> 같은 학교 학생들끼리 먹고 싶은 음식을 같이 주문하여, 배달비 부담을 줄여주는 배달 음식 공동 구매 앱
</div>   
    
    
## 2. 기술스택 & Workflow
<div>
    <h4>Kotlin & Firebase & Android Studio<br></h4>
</div>
<img width="20%" src="https://user-images.githubusercontent.com/87844641/173768618-f9e968a8-1be8-486e-9cf9-0482e664ef9a.png"/>
<img width="20%" src="https://user-images.githubusercontent.com/87844641/173768636-6dc23d7a-2d7c-437a-8daa-5998a004d68c.png"/>
<img width="20%" src="https://user-images.githubusercontent.com/87844641/173817432-ddb8a924-3e7e-4370-8ed7-79e019a4bf67.png"/>    

    
## 3. 프로그램 구조도
<div>
<h4>-시스템 구조도

![KakaoTalk_20230713_000541096](https://github.com/yesue2/deliveryshare/assets/108323785/9f9b2e59-7edc-446c-be4a-109ddac63be6)

-시스템 흐름도
![KakaoTalk_20230713_000626219](https://github.com/yesue2/deliveryshare/assets/108323785/6bc86488-4ea1-425c-afdd-10e63ec6caa6)
</div>
   
    
## 4. 어플 소개
같은 학교 학생들끼리 **먹고 싶은 음식을 같이 주문**하여, 배달비 부담을 줄여주는 배달 음식 공동 구매 앱 입니다.

현재 위 서비스에서 제공하는 핵심 기능 3가지입니다.

1. (호스트) 음식 카테고리 별 음식점 목록 중 하나를 선택해 공동 구매 글 올리기
(게스트) 내가 원하는 음식을 카테고리별로 나눠서 가게 선택하기
2. 호스트와 게스트 채팅 기능
3. 공동 구매 완료 후 상호 평가 기능

## 5. 개발 내용
### **공동 구매 글 올리기 및 가게 선택 기능**
![KakaoTalk_20230711_161814224](https://github.com/yesue2/deliveryshare/assets/108323785/304a9685-480d-442d-a7db-f34f65f7215f)
![KakaoTalk_20230711_161814224_01](https://github.com/yesue2/deliveryshare/assets/108323785/0d8e32ac-58d4-46ad-b23c-561c94b23cbe)

1. **동적인 데이터 로딩**: 음식 카테고리와 음식점 목록은 서버에서 동적으로 가져와야 합니다. 이를 위해 비동기적인 데이터 로딩 방식을 적용하여 데이터가 로드될 때까지 사용자에게 로딩 인디케이터를 제공하도록 코드를 구현하였습니다.
2. **사용자 경험 개선**: 사용자들이 원하는 음식을 쉽고 빠르게 선택할 수 있도록 사용자 경험을 개선하였습니다. 카테고리별로 음식점을 필터링하고 검색 기능을 추가하여 사용자들이 원하는 가게를 빠르게 찾을 수 있도록 했습니다. 또한, 사용자가 선택한 가게 정보를 시각적으로 강조하여 사용자 경험을 향상시켰습니다.
3. **데이터 모델링과 데이터베이스 구조**: Firebase의 NoSQL 데이터베이스 구조를 활용하여 음식 카테고리, 음식점 정보, 공동 구매 글 등을 적절하게 모델링하고 관리했습니다. Firebase의 JSON 기반 데이터베이스 구조를 이용하여 데이터를 저장하고 조회하는 코드를 구현했습니다.
4. **효율적인 알고리즘 및 데이터 처리**: 음식 카테고리와 음식점 목록을 효율적으로 처리하기 위해 알고리즘 및 데이터 처리 방법을 최적화하였습니다. 캐싱 메커니즘을 활용하여 반복적인 데이터 로딩을 최소화하고, 데이터베이스 쿼리의 효율성을 고려하여 필요한 데이터를 조회하고 필터링하는 코드를 구현하였습니다.

### **호스트와 게스트 채팅 기능**
![KakaoTalk_20230711_161814224_02](https://github.com/yesue2/deliveryshare/assets/108323785/eb482c89-663f-48de-b200-63b715bdddc5)


1. **실시간 채팅 기능**: 호스트와 게스트 간의 채팅은 실시간으로 이루어져야 합니다. 이를 위해 Firebase의 실시간 데이터베이스를 활용하여 채팅 메시지를 실시간으로 동기화하는 코드를 구현하였습니다. 사용자가 메시지를 보내면 상대방에게 실시간으로 전달되어 즉시 표시됩니다.
2. **채팅 인터페이스 디자인**: 채팅 인터페이스는 사용자들이 쉽게 메시지를 보낼 수 있도록 직관적이고 편리해야 합니다. 채팅창의 디자인과 사용자 인터페이스(UI)를 고려하여 코드를 구현하였습니다. 메시지 입력창, 전송 버튼, 메시지 목록 등의 요소를 구현하여 사용자들이 채팅을 원활하게 할 수 있도록 했습니다.

### **공동 구매 완료 후 상호 평가 기능**
![KakaoTalk_20230711_161814224_03](https://github.com/yesue2/deliveryshare/assets/108323785/3d6d8256-ec2c-40c4-90dc-880dd77463b8)
![KakaoTalk_20230711_161814224_04](https://github.com/yesue2/deliveryshare/assets/108323785/d11ff5ef-af24-4995-89c8-6063a7161f7e)

1. **동적인 별점 표시**: 상호 평가를 위한 별점은 사용자가 터치한 위치에 따라 동적으로 표시되어야 합니다. 이를 위해 터치 이벤트를 감지하고 별점을 실시간으로 업데이트하는 코드를 구현하였습니다.
2. **애니메이션 효과**: 별점을 주는 과정에서 애니메이션 효과를 추가하여 사용자들에게 시각적인 흥미를 제공했습니다. 별점을 클릭하거나 스와이프할 때 애니메이션 효과를 적용하여 부드럽고 즐거운 사용자 경험을 제공했습니다.
3. **상호 평가 데이터의 실시간 동기화**: 사용자들이 별점을 주고 평가를 완료하면 해당 데이터를 Realtime Database에서 실시간으로 동기화하여 서버에 저장하였습니다. 이를 통해 다른 사용자들이 평가 결과를 실시간으로 확인하고 상호 신뢰도를 파악할 수 있도록 구현하였습니다.
4. **평가 기록 및 통계**: 별점을 주는 기능을 통해 수집된 평가 데이터를 활용하여 개인의 평가 기록 및 통계를 제공하였습니다. 이를 통해 사용자들은 자신의 평가 히스토리를 확인할 수 있습니다.

## 6. 성장 경험
### 모바일 환경에 대한 이해

사용자 인터렉션에 대해 학습하면서 모바일 환경에 대한 이해도를 높였습니다. 

### 팀원들과의 협업

저희 팀은 기획1, 백엔드2, 프론트엔드1 로 총 4명으로 구성되어 있습니다. 이 4명 모두 기획 단계부터 함께 참여했으며, 개발 과정에서도 끊임없는 대화를 나누었습니다. 기술적으로 해결할 수 있는 문제와 그렇지 않은 문제에 대해 함께 논의하며 서비스를 발전시켰습니다.

단순히 기능만 구현하는 것이 아닌, 서비스의 비즈니스를 이해하고 코드에 의미를 담으려 노력하였습니다.
