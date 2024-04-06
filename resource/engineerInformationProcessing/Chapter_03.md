# 📁 데이터 베이스 구축
- 세부과목 : 논리 DB 설계, 물리 DB 설계, SQL응용, SQL활용

### 01. 논리 데이터 베이스 설계 
### 📒 키워드 : 개체, 속성, 관계, 튜플, 도메인, 키, 무결성, 관계대수, 정규화, 시스템 카탈로그

- 용어정리
  - 속성 Attribute : 개체의 특성을 나타낸다.

  - 개체 Entity : 속성들의 집합

  - 관계 Relation : 개체 사이에 존재하는 관계

  - 스키마 : 데이터베이스 구조와 제약조건에 관해 전반적인 명세를 기술한 것 (dbms에선 계정명을 뜻하기도 한다.)

  - 개체 : 테이블 , 속성 : 컬럼 , 도메인 : 속성의값,타입,제약사항등에 대한 값의 범위

  - 릴레이션 : 관계형 데이터베이스에서 테이블을 가르킴.

  - 시스템 카탈로그 : 시스템 그 자체에 관련이 있는 다양한 객체에 관한 정보를 포함하는 시스템 데이터베이스 , 메타 데이터라고도 한다. DBMS가 스스로 생성하고 유지한다 , 데이터 사전 : 메타데이터, 이용자가 조회 가능, 갱신 불가

- 데이터 베이스 설계
  1. 요구 조건 분석 : 요구 조건 명세서 작성
  2. 개념적 설계 : 개념 스키마, 트랜잭션 모델링, E-R모델 (DBMS에 독립적으로 설계), 현실세계를 표현하는 과정
  3. 논리적 설계 : 목표 DBMS에 맞는 논리 스키마 설계, 트랜잭션 인터페이스 설계, 데이터 모델링, DBMS가 지원하는 논리적 자료 구조로 변환 
  4. 물리적 설계 : 저장 레코드의 양식, 순서, 접근 경로 등을 결정, 레코드 집중의 분석 및 설계 
     5. 물리적 설계 시 고려할 사항 : 트랜잭션 처리량, 응답 시간, 디스크 용량, 저장공간의 효율화 등
  5. 구현 : DDL로 데이터베이스 생성, 트랜백션 작성

- 데이터 모델
  - DB설계 과정에서 데이터의 구조(Schema)를 논리적으로 표현하기 위해 사용되는 도구
  - 구성 요소 : 개체, 속성, 관계
  - 종류 : 개념적/논리적/물리적 데이터 모델
  - 표시할 요소 : 구조 Structure, 연산 Operation, 제약조건 Constraint (오답 : Entity)

- E-R (개체-관계) 모델
  - 개체 (사각형), 속성 (타원), 관계(마름모)
  - 개념적 데이터베이스 단계에서 제작된다.

- 관계형데이터베이스
  - 용어
    - 릴레이션 : 데이터를 표의 형태로 표현한 것

    - 릴레이션 스키마 : 릴레이션의 논리적인 구조를 정의한 것  (표에서 속성 제목)

    - 릴레이션 인스턴스 : 데이터 개체를 구성하고 있는 속성들에 데이터 타입이 정의되어 구체적인 데이터 값을 갖고 있는 것 (데이터들, 튜플의 집합)

    - 도메인 : 한 속성에서 가질 수 있는 값의 범위. (한 colunm의 값)

    - 카디널리티 : 튜플의 수

    - 디그리, 차수 : 속성의 수

  - 제약조건
    - 무결성 제약, 참조 무결성 제약
  - KEY
    - 유일성 unique : 하나의 키 값으로 하나의 튜플만을 유일하게 식별 할 수 있어야 한다.
    - 최소성 Minimality: 모든 레코드들을 유일하게 식별하는 데 꼭 필요한 속성으로만 구성되어야 한다.
    - 키의 종류
      - 후보키 Candidate Key : 기본키로 사용할 수 있는 속성들 (기본키 <= 후보키)
      - 대체키 Alternate Key : 후보키가 둘 이상일 때 기본키를 제외한 나머지 후보키들
      - 슈퍼키 : 슈퍼키는 유일성은 만족시키지만, 최소성은 만족시키지 못한다 .모든 튜플 중 슈퍼키로 구성된 속성의 집합과 동일한 값은 나타나지 않는다.
      - 외래키 : 다른 릴레이션의 기본키를 참조하는 속성 , 외래키를 포함하는 릴레이션이 참조하는 릴레이션

  - 무결성 (=데이터 정확성) Integrity
    - 종류 : 개체 / 도메인 / 참조 / 사용자 정의 무결성
      - 개체 무결성 Entity : 기본키를 구성하는 어떤 속성도 null이나 중복 값을 가질 수 없다.
      - 도메인 무결성 : 주어진 속성 값이 정의된 도메인에 속해야 한다.
      - 참조 무결성 Referential : 외래키 값은 null이거나 참조 릴레이션의 기본키 값과 동일해야 한다.
      - 사용자 정의 무결성 : 속성 값들이 사용자가 정의한 제약조건에 만족해야 한다.
    - 데이터 무결성 강화 방법
      - 애플리케이션, 데이터베이스 트리거, 제약조건을 이용하여 무결성 강화 가능
        - 애플리케이션 : 복잡한 무결성 조건 구현 가능 / 소스 코드에 분산되어 있어 관리가 힘들고, 개별 시행으로 인한 적정성 검토 어려움
        - 데이터베이스 트리거 : 통합관리 가능, 복잡한 조건 구현 가능 / 운영 중 변경이 어렵고 사용상 주의 필요
        - 제약조건 : 통합관리 가능, 간단한 선언, 변경용이, 오류 데이터 발생 방지 / 복잡한 조건과 예외 처리 불가

- 관계대수 및 관계해석
  - `관계 대수` : 원라는 정보와 그 정보를 검색하기 위해서 어떻게 유도하는가를 기술하는 `절차적` 언어
  
- 순수 관계 연산자 : 관계 db에 적용하기 위해 특별히 개발한 연산자. Select, project, join, division

- 일반 집합 연산자 : Union(합집합), Intersection(교집합), Difference(차집합), Cartesian Pruduct(교차곱)

### select

- 릴레이션에 존재하는 튜플 중에서 선택 조건을 만족하는 튜플의 부분집합을 구하여 새로운 릴레이션을 만드는 연산
- 릴레이션의 튜플을 구하는 것이므로 수평 연산이라고도 한다.
- 기호는 시그마이다.

### project

- 주어진 릴레이션에서 속성 리스트에 제시된 속성 값만을 추출하여 새로운 릴레이션을 만드는 연산이다.

단, 연산 결과에 중복이 발생하면 중복이 제거된다.

- 수직 연산이라고도 한다.
- 기호는 파이이다.

### Join

- 공통 속성을 중심으로 두 개의 릴레이션을 하나로 합쳐서 새로운 릴레이션을 만드는 연산
- Join의 결과는 교차곱을 수행한 다음 select 를 수행한 것과 같다.
- 연산자의 기호는 (넥타이모양 ㅎ)

### Division

- 연산의 기호는 나누기
- 서로 속성 상 연관이 있는 데이터만 뽑되, 뒤에 나누는 값이 들어 있으면 안된다.

### 일반 집합 연산자

- 합병 조건 : 합병 조건을 만족하는 릴레이션이 해당 연산을 이용할 수 있다.

두 릴레이션 간에 속성의 수가 같고, 대응되는 속성별로 도메인이 같아야 한다.

- 교차곱 : 두 릴레이션에 있는 튜플들의 순서쌍 이다. 두 릴레이션의 차수는 더하고, 카디널리티는 곱한다.

### 관계해석

- 특징과 주요 논리기호에 대한 문제 , 관계 대수와 구분할 수 있을 정도로 알아 둘 것.
- 비절차적 특성, 튜플 / 도메인 관계해석
- 관계대수로 표현한 식은 관계해석으로 표현할 수 있다.

## 정규화

- 데이터의 중복성을 최소화하고 일관성 등을 보장
- 정규화의 개념이나 목적이 아닌 것을 구분해낼 수 있는 것이 포인트
- 하나의 종속성이 하나의 릴레이션에 표현될 수 있도록 분해해가는 과정
- 개요

- 정규화는 데이터베이스의 논리적 설계 단계에서 수행한다.

- 정규화된 데이터 모델은 일관성, 정확성, 단순성, 비중복성, 안정성 등을 보장

- 이상(Anomaly)의 개념 및 종류

- 삽입 이상 : 원하지 않은 값들도 함께 삽입

- 삭제 이상 : 연쇄 삭제

- 갱신 이상 : 속성 값 갱신 시, 일부 튜플의 정보만 갱신되어 정보에 모순이 생김

### 정규화 과정

1. 제 1정규형

- 릴레이션에 속한 모든 도메인이 원자값으로만 되어 있다.

2. 제 2정규형

- 기본키가 아닌 모든 속성이 기본키에 대하여 완전 함수적 종속을 만족한다.

3. 제 3정규형

- 기본키가 아닌 모든 속성이 기본키에 대해 이행적 종속을 만족하지 않는 정규형
- 이행적 종속 : A -> B 이고 B -> C 일 때 A -> C를 만족하는 관계

4. BCNF (Boyce-Codd 정규형)

- 릴레이션에서 결정자가 모두 후보키인 정규형
- 결정자 : 속성 간의 종속성을 규명할 때 기준이 되는 값. 종속자는 결정자의 값에 의해 정해지는 값
- 키가 아닌 모든 속성은 키에 대하여 완전 종속해야 한다.
- 키가 아닌 모든 속성은 그 자신이 부분적으로 들어가 있지 않은 모든 키에 대하여 완전 종속해야 한다.
- 어떤 속성도 키가 아닌 속성에 대해서는 완전 종속할 수 없다.

5. 제 4정규형

- 다치 종속 A —>> B 이 성립하는 경우, 릴레이션의 모든 속성이 A에 함수적 종속 관계를 만족하는 정규형

6. 제 5정규형

- 릴레이션의 모든 조인 종속이 후보키를 통해서만 성립되는 정규형

# 물리 데이터베이스 설계

- CRUD, 트랜잭션, 인덱스, 뷰, 파티션, 분산 데이터베이스, 암호화, 접근 통제, DAS, SAN

## 트랜잭션 / CURD 분석

- 트랜잭션의 상태

- 활동, 부분완료, 실패, 철회(Rollback), 완료(Commit)

- 트랜잭션의 특성 (ACID)

- 데이터 무결성을 보장하기 위하여 트랜잭션이 가져야 할 특성

- 원자성 Atomicity : 어느 하나라도 오류가 발생하면 전부 취소 되어야 한다.

- 일관성 Consistency: 시스템이 가지고 있는 고정요소는 트랜잭션 수행 전과 후의 상태가 같아야 한다.

- 독립/격리/순차성 Isolation : 수행중인 트랜잭션은 완전히 완료될 때까지 다른 트랜잭션에서 수행 결과를 참조할 수 없다.

- 영속/지속성 Durability : 성공적으로 완료된 트랜잭션의 결과는 시스템이 고장나더라도 영구적으로 반영되어야 한다.

## 데이터베이스 보안 - 접근통제

- 임의/강제/역할기반 접근통제

- 임의 : DAC, Discretionary Access Control

- 사용자의 신ㄴㄴ원에 따라 접근을 통제

- 데이터 소유자가 접근통제 권한을 지정하고 제어

- Grant, Revoke

- 강제 : MAC; Mandatory Access Control

- 주체과 객체의 등급을 비교하여 접근 권한을 부여

- 시스템이 접근통제 권한을 지정한다.

- 역할기반 : RBAC; Role Based Access Control

- 중앙관리자가 접근 통제 권한을 지정

- 임의와 강제의 단점을 보완하였으며, 다중 프로그래밍 환경에 최적화된 방식이다.

- 접근통제 정책

- 신분 기반 정책 : 주체나 그룹의 신분에 근거하여 객체의 접근을 제한

- 규칙 기반 정책 : 주체가 갖는 권한에 근거하여 제한

- 역할 기반 정책 : 주체가 맡은 역할에 근거하여 제한

## 스토리지

- 단일 디스크로 처리할 수 없는 대용량의 데이터를 저장하기 위해 서버와 저장장치를 연결하는 기술
- 종류로는 DAS, NAS, SAN이 있다.

- DAS; Direct Attached Storage : 서버와 저장장치를 전용 케이블로 직접 연결

- 속도가 빠르고 설치 및 운영이 쉽다.

- 저장 데이터가 적고 공유가 필요 없는 환경에 적합

- NAS; Network Attached Storage : 서버와 저장장치를 네트워크를 통해 연결

- 접속 증가 시 성능이 저하될 수 있다.

- SAN; Storage Area Network : DAS의 빠른 처리와 NAS의 파일 공유 장점을 혼합

# SQL

## SQL 개요

- DDL : 데이터 정의 / DML : 데이터 조작 / DCL : 제이터 제어

### DDL

- Create, Alter, Drop
- Constraint : 제약조건의 이름을 지정한다. 지정 필요 없으면 CHECK절만 사용하여 속성 값에 대한 제약조건을 명시
- On Delete 옵션 : 참조 테이블의 튜플이 삭제되었을 때 기본 테이블에 취해야 할 사항을 지정

- No action

- CASCADE : 기본 테이블의 관련 튜츨도 모두 삭제, 속성이 변경되면 관련 튜플의 속성 값도 모두 변경

- SET NULL : 기본 테이블의 관련 튜플의 속성 값을 NULL로 변경

- SET DEFAULT : 속성값의 기본값으로 변경

### DCL

- Grant-권한부여, Revoke-권한취소, Commit, Rollback, SavePoint

### DML