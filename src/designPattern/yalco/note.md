# 디자인패턴
## Facade
- 여러 요소들로 복잡하게 구성된 시스템을 하나의 창구를 통해 간단하게 사용할 수 있게 함
- 서브시스템들과 그것들을 묶는 Facade 클래스로 이루어지며 client는 Facade를 호출한다.
## Strategy Pattern
- ![img.png](images/strategy.png)
- 특정 작업을 하는 방식들 '전략'을 여럿 만들어 두고 필요에 따라 갈아끼우는 패턴
- {특정 작업 : 계산}, {방식 : 현금, 카드, 카카오페이} 
- client는 작업을 생성 -> 전략을 선택(set) -> 작업 실행  
- 코드 실행 중 바꿀 수 있다 (set)
## Template Method
- 정해진 어떤 단계들을 거쳐서 실행되어야 하는 일들을 구현할 때 사용 
- 각 단계를 어떻게 수행할 것인지는 다양하게 구현가능, 반드시 이 순서에 따라 전체 과정이 실행되도록 만들어야 할 때 유용 

## Singleton
- 프로그램에서 특정 클래스의 객체가 단 하나만 존재해야 할 때 

### Spring에서 싱글톤을 사용하는 이유
- Spring은 기본적으로 모든 빈을 싱글톤 범위로 관리
- 자원 효율성 향상
  - 매번 요청이 있을 때마다 새로운 객체를 생성하지 않고, 이미 만들어진 하나의 인스턴스를 재사용 -> 메모리와 CPU 자원 절약
- 애플리케이션의 일관성 유지 
  - 같은 빈이 여러곳에서 사용되더라도 동일한 상태와 동작을 유지할 수 있음
  - 이를 통해 데이터나 설정값을 공유해야 하는 서비스의 일관성을 보장 가능
- 초기화 비용 절감 

## State Pattern
- 무언가의 상태가 각각 그에 해당하는 클래스의 객체로 존재
- 필요시 스스로를 다른 상태로 전환하도록 하여 코드의 복잡성을 줄이고 유연성을 높임
- 객체의 상태에 따라 if문으로 분기를 줘야하는 로직을 간단하게 표현 가능하다. 