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

# 5. 응집도

모듈 내부에 있는 데이터와 로직 사이의 관계가 얼마나 강한지를 나타내는 지표

응집도를 낮추는 요인들을 아래에서 살펴보자.

### static 메서드 오용

- 데이터와 관련 로직이 다른 클래스에 작성되게 함

    ```java
    // 주문을 관리하는 클래스
    public OrderManager {
    		static int add(int moneyAmount1, int moneyAmount2) {
    				return moneyAmount1 + moneyAmount2;
    		} 
    ```

- static 메서드는 인스턴스 변수를 사용할 수 없다. 이는 애초에 데이터와 데이터를 로직 사이에 괴리가 생긴 것.
- 인스턴스 메서드인 척하는 static 메서드 주의 : 매개변수만을 이용하고, 필드를 사용하지 않는 메서드

    ```java
    public OrderManager {
    	 private int discountRate; // 할인율 
    		static int add(int moneyAmount1, int moneyAmount2) {
    				return moneyAmount1 + moneyAmount2;
    		} 
    ```

- 언제 static 메서드를 사용해야 하나
  - 응집도의 영향을 받지 않는 경우 : 로그 출력 전용, 포맷 전환 전용, 팩토리 메서드

### 초기화 로직 분산

- public 생성자를 남발하면 로직이 분산 됨. private 생성자 + 팩토리 메서드 사용
- 팩토리 클래스
  - 객체 생성 메서드에 이름을 부여할 수 있어 가독성이 높아집니다.
  - 객체 생성 과정이 캡슐화됩니다.
  - 생성자 오버로딩 대신 메서드 이름으로 목적을 분명히 알 수 있습니다.

    ```java
    public class UserFactory {
        // 정적 팩토리 메서드
        public static User createAdmin(String name) {
            return new User(name, "ADMIN");
        }
    
        public static User createGuest(String name) {
            return new User(name, "GUEST");
        }
    }
    // main
    User admin = UserFactory.createAdmin("Alice");
    User guest = UserFactory.createGuest("Bob");
    ```


### 범용 처리 클래스 (Common/Util)

꼭 필요한 경우가 아니면, 범용 처리 클래스는 만들지 말고, 객체 지향 설계의 기본으로 처리한다.

횡단 관심사 (로그 출력, 오류 확인, 예외 처리, 디버깅, 동기화, 캐시, 분산 처리) 등은 범용 코드 ok

### 결과를 리턴하는 데 매개변수 사용하지 않기

- 데이터와 로직이 각자 다른 클래스에 있게 된다

    ```java
    class ActorManager {
    		// 캐릭터 위치를 이동
    		void shift(Location location, int shiftX, int shiftY) {
    		      // 인스턴스를 매개변수로 전달받고, 이를 변경하고 있음 
    		      // 그런데 데이터 조작 로직은 ActorManager에 있음 
    		      location.x += shiftX;
    		      location.y += shiftY;
    		}
    }
    ```

    ```java
    class Location {  // 이와 같이 객체지향을 따라서 만들어주댜!
    		final int x; 
    		final int y;
    		
    		Location(final int x, final int y) {
    				this.x = x; this.y = y;
    		}
    		
    		Location(final int shiftX, final int shiftY) {
    				final int nextX = x + shiftX;
    				final int nextY = y + shiftY;
    				return new Location(nextX, nextY);
    		}
    }
    ```


### 매개변수가 너무 많은 경우

- 단일 책임이 아니어질 확률이 높고, 잘못된 값을 대입할 가능성이 높다
- 의미 있는 단위는 모두 클래스로 만든다. : 매개변수에 있는 많은 기본 타입의 변수들을 모아 필드로 갖는 클래스를 만들어 활용


# 6장. 조건분기

### 중첩된 조건 분기

- 조기리턴 : 조건을 만족하지 않는 경우 곧바로 리턴
- else 구문 특히 조기 리턴으로 처리

### swicth 조건문 중복

- switch 조건문 한 번에 작성 → 클래스가 거대해지면 인터페이스 사용

### interface를 이용한 조건 제거

|                 | Do not            | Do                   |
| --------------- | ----------------- | -------------------- |
| 분기            | if, switch 조건문 | 인터페이스 설계 사용 |
| 분기마다의 처리 | 로직을 그냥 작성  | 클래스 사용          |

### Map을 사용해 고도화

### 예시 : 회원 등급 산정 로직

```java
/**
* @return 골드 회원이라면 ture
* @param history 구매이력
*/
boolean isGoldCustomer(PurchaseHistory history) {
		if (1_000_000 <= history.totalAmount) {
			if (10 <= history.purchaseFrequencyPerMonth) {
					if (history.returnRate <= 0.001) {
							return true;
					}
			}
		}
	return false;
}

/**
* @return 실버회원이라면 ture
* @param history 구매이력
*/
boolean isSilverCustomer(PurchaseHistory history) {
		if (10 <= history.purchaseFrequencyPerMonth) {
				if (history.returnRate <= 0.001) {
						return true;
				}
		}
	return false;
}
```

1. 정책 패턴으로 조건 집약
  - 정책 패턴 : 조건을 부품처럼 만들고, 부품으로 만든 조건을 조합해서 사용하는 패턴
  - 하나하나의 규칙 (판정 조건)을 나타내는 인터페이스 제작
  - if문이 정책클래스에만 사용되어 로직이 단순해짐
  - [상세 코드](https://github.com/S2uJeong/Note/blob/d25addee49997b1f5f0859a66590c0fac4f02784/src/bookStrangeCode/notUseSwitch)

      ```java
      
      interface ExcellentCustomerRule {
        // @return 조건을 만족하는 경우 true
          boolean ok(final PurchaseHistory history);
      }
      
      // 구매 금액 규칙 
      class GoldCustomerPurchaseAmountRule implements ExcellentCustomerRule  {
              public boolean ok(final PurchaseHistory history) {
                      return 1_000_000 <= history.totalAmount;
              }
      }
      // 구매 빈도 규칙
      class PurchaseFrequencyRule implements ExcellentCustomerRule  {
              public boolean ok(final PurchaseHistory history) {
                      return 10<= history.purchaseFrequencyPerMonth;
              }
      }
      // 반품률 규칙
      class ReturnRateRule implements ExcellentCustomerRule  {
              public boolean ok(final PurchaseHistory history) {
                      return 0.001 >= history.returnRate;
              }
      }
      ```

  - 정책 클래스 : `add()` 로 규칙을 집약하고, `complyWithAll()` 내부에서 규칙을 모두 만족하는지 판정

      ```java
      public class ExcellentCustomerPolicy {
          private final Set<ExcellentCustomerRule> rules; // rule을 세분화 한뒤, 필드를 rule list 만들어 해당하는 것 다 지켜야 한다는 로직 만든게 인상적
      
          public ExcellentCustomerPolicy() {
              this.rules = new HashSet<>();
          }
      
          /**
           * 규칙 추가
           * @param rule 규칙
           */
          void add(final ExcellentCustomerRule rule) {
              rules.add(rule);
          }
      
          /**
           * @param history 구매이력
           * @return 규칙을 모두 만족하는 경우 true
           */
          boolean complyWithAll(final PurchaseHistory history) {
              for (ExcellentCustomerRule each : rules) {
                  if (!each.ok(history)) return false;
              }
              return true;
          }
      }
      ```
### 플래그 매개변수 사용하지 마!

```java
/**
* 플래그 변수 사용한 안 좋은 예시 : damage(true, damageAmount)
*/
void damage(boolean damageFlag, int damageAmount) {
	if (damageFlag == true) {
			// 물리 대미지 (히트포인트 기반 대미지)
			member.hitPoint -= damageAmount;
			if (0 < member.hitPoint) return;
			
			member.hitPoint = 0;
			member.addState(StateType.dead);
	}
	else {
			// 마법 대미지 (매직포인트 기반 대미지)
			member.magicPoint -= damageAmount;
			if (0 < member.magicPoint) return;
			member.magicPoint = 0;
	}
}
```

```java
// bool이 아닌 int형 플래그도 주의
void execute(int processNumber) {
	if (processNumber = 0) {
			// 계정 등록 처리
	} else if {
			// 주문 처리 
	} else ...
}
```

1. 메서드 분리

    ```java
    void hitPointDamage(final int damageAmount) }
          member.hitPoint -= damageAmount;
    			if (0 < member.hitPoint) return;
    			
    			member.hitPoint = 0;
    			member.addState(StateType.dead);
    }
    
    void magicPointDamage(final int damageAmount) {
         member.magicPoint -= damageAmount;
    			if (0 < member.magicPoint) return;
    			member.magicPoint = 0;
    }
    ```

2. 기능 전환은 전략패턴으로 구현

    ```java
    interface Damage {
    	  void execute(final int damageAmount);
    }
    
    class HitPointDamage implements Damage  {
    
    		void execute(final int damageAmount) {
    				member.hitPoint -= damageAmount;
    				if (0 < member.hitPoint) return;
    				
    				member.hitPoint = 0;
    				member.addState(StateType.dead);
    		}
    }
    
    class MagicPointDamage implements Damage  {
    
    		void execute(final int damageAmount) {
    				member.magicPoint -= damageAmount;
    				if (0 < member.magicPoint) return;
    				member.magicPoint = 0;
    		}
    }
    ```

3. enum과 Map 활용

    ```java
    enum DamageType {
    		hitPoint,
    		magicPoint
    }
    
    private final Map<DamageType, Damage> damages;
    
    void applyDamage(final DamageType type, final int damageAmoun) {
    		final Damage damage = damages.get(type);
    		damage.execute(damageAmount);
    }
    // 호출 하는 형태 : applyDamage(DamageType.magicPoint, damageAmount);
    ```
