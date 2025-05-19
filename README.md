# SHOP-MSA
MSA 아키텍처 학습을 위한 쇼핑몰 토이 프로젝트 입니다.
<br>
새로운 기술을 배우고 적용하는 데 중점을 두었습니다.

<br>

개발기간 : 2025.04 ~ ing

## 🖥 기술 스택
- Java 21
- Spring boot 3.4.4
- Spring cloud
- spring cloud gateway
- JPA
- MySQL
- Redis
- Kafka

</br>

## 📖 서비스 별 기능
- common-service
  - Snowflake : 전 서비스 공통으로 사용하는 유니크 정렬(오름차순) ID 생성기
  - data-serializer : Kafka 이벤트 직렬화/역직렬화 모듈
  - event : Kafka 메시지 발행/구독을 위한 공통 이벤트 인터페이스
  - outbox-message-relay : Transactional Outbox Pattern 을 위한 모듈 (DB Polling 기반, Kafka 전송)
- goods-service
  - 상품 CRUD API : 상품 등록,수정,삭제 시 Kafka 이벤트 발행 (Outbox 패턴 적용)
  - 상품 목록-페이지 번호
  - 상품 목록-무한 스크롤
- comment-service
  - 댓글 CUD API
  - 댓글 2depth 목록
  - 댓글 무한 depth (Path Enumeration, 경로 열거 방식)
- like-service
  - 좋아요 수 - 동시성 문제(Optimistic Lock, Pessimistic Lock) 
- view-service
  - 조회수 어뷰징 방지 (redis - Distributed Lock, TTL)
  - 각 유저별 10분 당 1회 카운팅
- hot-goods-service
  - 조회 수, 좋아요 수 기반의 인기상품 조회
  - Kafka + Outbox Pattern 기반 이벤트로 인기 상품 집계
- goods-read-service
  - 상품 조회 전용 API(CQRS Pattern)
  - Redis 캐시 + 캐시 최적화(Request Collapsing)
- coupon-service (05.19 ~ ing)
  - 선착순 쿠폰 발급 이벤트 시스템 (05.19 ~ ing)
  - 선착순 1000명, 1인 1발급 (05.19 ~ ing)
