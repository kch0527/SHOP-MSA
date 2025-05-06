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
  - Snowflake : PK 유니크 정렬 숫자
  - data-serializer : 직렬화/역직렬화
  - event : 이벤트 통신을 위한 모듈
  - outbox-message-relay : Transactional Outbox Pattern 을 위한 모듈
- goods-service
  - 상품 CRUD API
  - 상품 목록-페이지 번호
  - 상품 목록-무한 스크롤
- comment-service
  - 댓글 CUD API
  - 댓글 2depth 목록
  - 댓글 무한 depth
- like-service
  - 좋아요 수 - 동시성 문제(Optimistic Lock, Pessimistic Lock) 
- view-service
  - 조회수 어뷰징 방지 (redis - Distributed Lock, TTL)
- hot-goods-service
  - 인기 상품 업데이트 기능 (kafka - Transactional Outbox Pattern)
- goods-read-service
  - 상품 조회 최적화 (CQRS Pattern) (~ing)
