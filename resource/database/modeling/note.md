관계형 데이터 모델링 프리미엄 가이드
- 유사한 집합을 일반화
- SQL Plan을 분석해 효율적이니 구조의 모델 선택 
- 정규화, RDBMS, 무결성, 속도(성능)
- 모델링 목표
  - 데이터 무결성 향상
  - 데이터 정합성 향상 (중복 제거)
    - 중복 속성, 중복 엔티티, 추출 속성
  - 유연한 모델
    - 정규화, 일반화 : 유사한 데이터를 통합 
  - 애플리케이션에 종속되지 않은 모델 
    - 화면별로 엔티티가 도출되는 것 지양 
    - 화면에서 보려고 하는 뷰는 DB 뷰로 해결한다. 
## 서브타입
- 도메인적으로 의미를 가지는데 어느 엔티티에 속한 개념을 엔티티로 정의할 때 사용 
- 구성 요소를 서브타입으로 표현하면 서브타입 간에는 공통된 속성이 존재하지 않고 고유한 속성만이 존재한다. 
  - 이럴 땐 서브타입을 일반적으로 엔티티로 생성하지 않는다. 그냥 그 상위 (해당 서브타입을 포함한 개념) 엔티티에 서브타입의 속성이 합쳐져 하나의 엔티티로 표현된다. 

### 서브타입 설계 종류
- 올바른 서브타입 설계를 하지 않으면 중복 값이나 의미 없는 많은 null값, 조회 시 특정 데이터를 찾기 위한 조건문이 길어지는 문제점들이 생길 수 있다.
- 분할 : 서브타입마다 하나의 엔티티로
  - subType1
    ``` 
    #sueprtype-key
    supertype-attribute
    subtype1-attribute
    ```
  - subType2
    ``` 
    #sueprtype-key
    supertype-attribute
    subtype2-attribute
    ```
- 통합 : 하나의 엔티티로
  - superType
    ```
    #superType-key 
    supertype-attribute
    subtype-discriminator
    subtype1-attribute
    subtype2-attribute
    ```
- 혼합 : 통합 성격의 엔티티와 서브타입 별 엔티티로 분할
  - superType
    ```
    #superType-key
    supertype-attribute
    subtype-discriminator
    ```
  - subType1
    ```
    #superType-key
    subtype1-attribute
    ```
  - subType2
    ```
    #superType-key
    subtype2-attribute
    ```
- 세 종류별 기준 사례
  - 분할
    - 서브타입 별 업무가 서로 독립적
    - 서브타입 별 속성이 많이 다름
    - 서브타입 별 관계가 많이 다름
    - **모든 서브타입을 동시에 조회하는 경우가 드물 때**
    - 서브타입 별 식별자가 상호 배타적이 아닐 때
    - 서브타입이 업무적으로 서로 약 결합 관계일ㄷ 때 
  - 통합
    - 서브타입 별 고유 속성이 적을 때
    - **속성이 지속적으로 늘어날 가능성이 적을 때**
    - 하나의 서브타입은 속성도 많고 업무도 중요하며 나머지 서브타입을 덜 중요할 때
    - **서브타입 전체를 대상으로 하는 업무가 빈번할 때** 
    - 데이터 건수가 많지 않을 때 
    - 업무가 중요하지 않을 때
    - 서브타입이 서로 포함 관계일 때 
    - 서브타입이 업무적으로 서로 강 결합
  - 혼합 
    - 서브타입 별 공통 속성을 대상으로 하는 업무가 빈번할 때
    - 통합 하면 속성 개수가 너무 많아질 때
    - 업무의 변화가 빈번해 속성이 자주 추가될 때
    - 서브타입별 고유 속성이 많을 때
    - 트랜잭션의 락을 방지하기 위해 엔티티를 분리해야 할 때 
    - 공통 업무와 고유 업무가 다양하게 존재할 때 
    - 서브타입이 업무적으로 서로 강결합 
    - 슈퍼타입의 조회가 빈번하고 조회 범위가 넓을 때 
## 엔티티
- 생성시 주의점
  - 엔티티는 명확한 성격이 보여야 한다.
    - 필요한 혼합 성격은 view로 표현한다. 
  - 무결성
    - 주 식별자 도출
  - 유일성
    - 동일한 성격의 데이터는 전사적으로 유일해야 한다. 
    - 유사항 성격을 관리하는 엔티티가 다수 존재하면 안된다.
  - 프로세스 도출 지양
    - 데이터 모델에는 순서가 없다.
    - 프로세스가 달라도 같은 성격을 가진 경우가 대부분이기 때문에 지양한다.
    - 프로세스 도출 예시 : 계약요쳥, 계약승인, 계약으로 3개 엔티티 도출 
  - 화면 도출 지양 
### 자립/종속 엔티티
- 자립 엔티티
- 종속 엔티티
  - 상위 (부모) 데이터가 없으면 존재할 수 없는 엔티티 
  - 예시 : 고객이란 엔티티에 속성이 많아서 자주 안 쓰이는 속성을 담은 고객상세 엔티티를 만들어 고객의 종속 엔티티로 생성한다. (1:1 관계 )
- 업무에 따라 구별해서 생성한다.
### 식별자
- 식별
  - 상위 엔티티의 주 식별자를 하위 엔티티의 주 식별자로 상속 받았을 떄
- 비식별
  - 주 식별자로 상속받지 않고 일반 속성으로 상속받음 
- 업무 식별자와 인조 식별자가 혼합되지 않도록 설정
  - 예시 : 고객의 주문을 관리하는 엔티티에서 한 고객이 하루에 같은 상품을 두 번 이상 주문할 수 없다면 주문 엔티티의 업무 식별자는 고객번호/상품번호/주문일자가 된다.
  - 이때에는 업무 식별자가 복잡하므로 주문번호와 같은 단순한 인조 식별자를 채택하는 것이 좋음
  - 하지만 주문에 주문순번(인조 식별자) 속성이 들어가면 업무 식별자랑 혼합되어 한 고객이 같은 상품을 하루에 두 번 이상 주문할 수 없다는 요건을 알기 어려워진다. 
  - 이 때에는 고객번호, 상품번로, 주문일자 속성으로 유니크 인덱스를 생성하지 않으면 한 고객이 같은 상품을 하루에도 여러 번 주문할 수 있다. 
  - 따라서 주 식별자는 데이터의 성격을 파악할 수 있게 업무 식별자를 주 식별자로 사용하는 것이 기본이다.
  - 업무 식별자가 비효율일때 인조 식별자를 사용한다. 
  - 하지만 업무 요건이 다양하게 바뀔 수 있다면 인조식별자를 통해 모델의 유연성을 확보하는 것이 바람직하다. 
- 식별자 검증 
  - 주 식별자가 인스턴스를 유일하게 보장하는가?
  - 주 식별자의 값이 변경되지 않는가
  - 복합 주 식별자의 순서가 데이터를 추출하는 데 있어 효율적으로 구성 됐는가? 
    - 선택도가 높은 속성이 주 식별자의 처음에 올 수 있도록 순서를 정해야 한다.
  - 생략해도 되는 속성이 주 식별자에 포함돼 있지 않는가? 
  - 인조 식별자가 적절하게 사용되었는가
    - 업무 식별자를 표시하거나 설명에 기술했는가 
    - 다른 엔티티와 관계가 빈번하게 발생하지 않는 한 인조 식별자를 채택하는 것은 실익이 없다.
  - 복합 주 식별자 대신 인조 식별자를 채택할 수 없는가?
  - 업무 식별자나 인조 식별자가 아닌 주 식별자가 사용되지 않았는가 
  - 
### 엔티티 성격에 따른 종류
```
- 실체 엔티티 : 실제 물체에 대한 본질적인 데이터 관리하는 
- 행위 엔티티 : 행위나 활동에 의해서 발생된 원천 데이터 관리하는
- 가공 엔티티 : 원천 데이터를 추출, 집계한 데이터를 관리하는 데이터
- 기준 엔티티 : 실체나 행위 데이터의 기준이 되는 데이터를 관리하는 데이터
```
- 집계 엔티티 활용 방법
  - 업무적으로 자주 조회되는 대량 집계 요건을 만족시키기 위해 사용
  - 집계를 하려는 기준이 주 식별자가 된다. (일자별, 부서별..)
  - 집계 엔티티에 데이터 쌓는 방법
    - 트랜잭션 데이터가 발생할 때마다 집계 엔티티에 반영하는 방법
    - 특정 시점에 해당하는 기간의 데이터를 집계해 일괄 반영
  - 사용을 자제해야 함. 중복이 생겨 정합성에 문제가 생길 확률이 높음 

## 코드 엔티티
- 속성에 사용되는 값을 간단하고 알기 쉽게 나타낼 수 있도록 약속한 또 다른 값 
- 코드 사용하기 좋은 유형
  - 구분코드 : 성질이나 특징이 다른 코드명이 고정적일 때 사용 예) 남녀구분코드, 매수매도구분코드
  - 유형코드 : 코드명을 성질이나 특징이 공통적인 것끼리 묶을 때 사용 예) 거래유형코드
  - 종류코드 : 고정적이지 않고 지속적으로 늘어날 수 있는 코드명을 나열할때 사용 예) 서비스종류코드 

### 코드 모델
- 코드 유형 + 코드 조합
  ```
  코드유형
  # 코드유형번호
  * 코드유형명
  
  코드
  # 코드유형번호 (FK)
  # 코드값
  * 코드명
  ```
- | #코드유형번호 | 코드유형명       |
  | ------------- | ---------------- |
  | 001           | 매수매도구분코드 |
  | 002           | 직급코드         |

- | #코드유형번호 | #코드값 | 코드명 |
  | ------------- | ------- | ------ |
  | 001           | 01      | 매수   |
  | 001           | 02      | 매도   |
  | 002           | 01      | 사원   |
  | 002           | 02      | 대리   |

- 코드 이력 엔티티를 추가하여 관리해도 좋다
  ```
  #코드유형번호
  #유효시작일자
  #코드값
  코드명
  유효종료일자
  ```
- | #코드유형번호 | #유효시작일자 | #코드값 | 코드명 | 유효종료일자 |
  | ------------- | ------------- | ------- | ------ | ------------ |
  | 100           | 2024-03-21    | 01      | 개인   | 2025-06-09   |
  |               |               |         |        |              |

## 널
- 널 값은 쿼리 그룹 연산에 포함되지 않고, 인덱스에 포함되지 않아 인덱스 스캔이 발생하지 않는 예외상황이 발생한다.
- 따라서 지양하는 것이 좋다
- 널 값을 허용하지 않고 모르는 값에 대해선 기본값을 지정해 주는 것이 올바르다. ("", ~)
- 모든 속성에 널을 허용하거나 널을 금지하는 것보다 도메인이나 개별 속성에 대한 판단으로 정하자.
### 외래 식별자 속성 Null 설정
- 업무적으로 Null이 가느ㅇ하면 허용헤야 한다. (무결성 제약 조건이 있으므로)
- 가끔은 상위 엔터티에 기본 값을 주 식별자로 하는 가상의 인스턴를 생성하기도 한다. 

## 관계
- 종속 관계에선 부모, 자식 엔터티
- 비종속 관계에선 상위(참조 되는), 하위 엔터티(참조 하는)로 지칭
- 관리하고자 하는 관계만을 관계로 표현해야하고 남발하며 안된다.
  - 연관이 있는 것과 참조 무결성 제약에 따라 관계를 맺어야 하는 것은 다르다.
  - 집계 테이블은 보통 원천 테이블과 연관은 있지만 참조 무결성 제약에 의해 관계를 맺어야 하는 경우는 거의 없다 
- 또한 1촌까지만 관계를 맺어야 한다. 이를 파악하는 것이 중요하다.
- 상위 엔터티와 연관있는 속성의 개수만큼 관계선/명이 표현되어야 한다. 
  - 이런 속성이 많고 많아질 예정이라면 엔터티로 관리한다. 
# 궁금한 점
1. JPA를 사용해서 DB를 관리할 때 코드 엔티티를 어떻게 관리하는 것이 좋을까. or 어떻게 사용할 수 있을까 
