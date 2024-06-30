- 대용량 분산 캐시 환경 구축하기 위해 사용 : 분산 인 메모리 캐시를 손쉽게 생성하고 확장하는 서비스
- 읽기 중심의 서비스를 제공해야 하는 환경에 적합하다
- 두 가지 캐시 엔진 지원
  - Memcached
  - Redis 
### In Memory Cache 란?
  - 모든 데이터를 메모리(Ram)에만 올려 놓고 사용하는 데이터베이스의 일종
  - 디스크에 접근하지 않고 메모리로만 모든 처리를 하기 때문에 빠르다
  - 그러나 서버의 전원 공급이 중단되면 캐시 데이터는 사라진다. 
### 참고 
- [AWS로 ElastiCache Redis 사용 하기 ](https://minholee93.tistory.com/entry/AWS-ElastiCache-Redis-사용하기)
- [로컬에서 Redis로 캐시하기](https://velog.io/@daoh98/AWS-ElastiCache-와-Redis-적용) 