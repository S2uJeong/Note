📒 내 코드가 그렇게 이상한가요? - 센바 다이야

# 2장. 설계 첫걸음

### 목적별로 변수를 따로 만들어 사용

- 계산의 중간 결과를 동일한 변수에 계속해서 대입하는 것을 재할당은 변수의 용도가 바뀌는 문제를 일으킨다.

```java
int price = 10_000;
double rate = 0.5;
price = price * rate // (x)
int discountedPrice = price * rate // (o)
```

### 관련된 데이터와 로직을 클래스로 모으기

- before  : 클래스 안에 public으로 되어 있는 필드만 있는 클래스 (x)

    ```java
    public class ContractAmount {
    		public int amountIncludingTax; // 세금 포함 금액
    		public double salesTaxRate; // 소비세율 
    }
    
    // 금액 계산 로직은 온갖데 퍼짐
    ```

- 외부에서 해당 필드에 대한 데이터를 언제나 바꿀 수 있어 필드와 관련된 조건이 있을 시 혼돈을 야기함 (코드 중복, 수정 누락, 가독성 저하, 초기화 되지 않은 상태, 잘못된 값 할당)
- after

    ```java
    public class Contract {
    	 private final int price; // 원래 가격
    	 private final int discountedPrice; // 할인된 가격
    	 private final double rate; // 할인율
    	 private static final double salesTaxRateMax = 0.8; // 최대 할인율 
    	 
    	 // 생성자 
    	 public Contract (int price, double rate) {
    			 if (rate > salesTaxRateMax) throw new IllegalArgumentException;
    			 this.price = price;
    			 this.discountedPrice = price; // 초기값은 원래 가격 
    			 this.rate = rate; 
    	 }
    	 
    	 // 할인된 가격 계산
    	 public Contract calculatePrice() {
    			this.discountedPrice = (int) (price * (1-rate));
    			return this;
    	 }
    }
    ```


# 3장. 클래스 기반 객체 지향 설계

### 클래스 단위로 동작하도록 설계

- 클래스는 클래스 하나로도 잘 동작할 수 있어야 한다.
- 복잡한 초기 설정을 하지 않아도 곧바로 사용할 수 있어야 한다.
- 최소한의 조작 방법(메서드)만 외부에 제공해야 한다.
- 반드시 인스턴스 변수, 인스턴스 변수에 잘못된 값이 할당되지 않게 막고, 정상적으로 작동하는 메서드 둘이 붙어 다녀야 한다.

### 클래스 설계 기법

- 인스턴스 변수 : 불변 변수로 제작

    ```java
    // 불변을 유지하면서 값을 변경 
    Class Money {
    			private final int amount;
    			
    			public Money(int amount) {
    					this.amount = amount;
    			}
    			// 새로운 인스턴스 만드는 메서드 활용
    			Money add(int other) {
    					int added = amount + other;
    					return new Money(added); 
    			}
    }
    ```

- 클래스의 메서드 내용 : 생성자 (값 초기화, 유효성 검사), 데이터(필드) 계산 로직
- 메서드 매개변수도 불변으로 만들기

    ```java
    public class Main {
    
        static class Money {
            private final int amount;
    
            public Money(final int amount) {
                // amount = 10000; 컴파일 오류 : final을 붙이면 이런 식으로 중간에 값을 넣는 것을 방지 가능
                this.amount = amount;
            }
    
            Money add(int other) {
                int added = amount + other;
                return new Money(added);
            }
        }
        public static void main(String[] args) {
            Money money = new Money(200);
    
            System.out.println(money.amount); // final 없으면 10_000이 나올 수 있음 
        }
    }
    ```

- 엉뚱한 값을 전달하지 않도록 하기

    ```java
    final int amount = 10;  // 가격을 넣었어야 했는데 같은 숫자인 물건 개수를 오기입 
    money.add(amount);
    // 같은 자료형이라 코드는 문제 없이 실행됨.
    ```

  ⇒ 해당 자료형을 매개변수로 받는 메서드로 변경

    ```java
    Money add(final Money other) {
    		final int added = amount + other.amount;
    		return new Money(added);
    }
    ```

  이렇게 일반 자료형(int, String)이 아닌 특정 클래스 타입과 같은 독자적인 자료형을 사용하면, 의미가 다른 값을 전달할 경우 컴파일 오류가 발생 할 수 있다. (유효성 검사를 거칠 수 있다.)

- 의미 없는 메서드 추가 하지 않기

### 프로그램 구조의 문제 해결에 도움을 주는 디자인 패턴

| 디자인 패턴 | 효과 |
| --- | --- |
| 완전 생성자 | 잘못된 상태로부터 보호함 |
| 값 객체 | 특정한 값과 관련된 로직의 응집도를 높임 |
| 전략 | 조건 분기를 줄이고 로직을 단순화 |
| 정책 | 조건 분기를 단순화, 더 자유롭게 만듦 |
| 일급 컬렉션 | 값 객체의 일종으로 컬렉션과 관련된 로직의 응집도를 높임 |
| 스프라우트 클래스 | 기존 로직을 변경하지 않고, 안전하게 새로운 기능을 추가 |
- 완전 생성자
    - 인스턴스 변수를 모두 초기화해야만 객체를 생성할 수 있게 한다.
- 값 객체
    - 값을 클래스(자료형)으로 나타내는 패턴
    - ex) 태스크 관리 도구 : 태스크 이름, 태스크 설명, 코멘트, 진행 상태 등
    - 제약과 의도를 들어낼 수 있다.

# 4장. 불변

### 재할당

- 불변 변수로 재할당 막기 : 변수에 final, 매개변수도 불변으로 (이전에 예시 있음)

    ```java
    // 전 : 재할당 문제
    int plusVisitCount() {
    	int count = post.getVisitCount();
    	count += 1;
    	return count;
    }
    
    // final로 재할당 막기 
    int plusVisitCount() {
    	final int count = post.getVisitCount();
    	count += 1; // 컴파일 오류 
    	return count;
    }
    ```


### 불변과 가변을 다루는 법

- 기본적으로 불변으로 만든다.  불변의 장점
    - 변수의 의미가 변하지 않으므로, 혼란을 줄인다.
    - 동작이 안정적이게 되므로, 결과를 예측하기 쉽다.
    - 코드의 영향 범위가 한정적이므로, 유지 보수가 편리해짐
- 가변을 사용하는 경우
    - performance가 중요한 경우 : 대량의 데이터, 이미지 처리, 리소스 제약이 큰 임베디드
    - 불변이라면 값을 변경할 때 인스턴스를 새로 만들어야 하므로.
    - 스코프가 국소적인 경우 : 반복문 카운터 등