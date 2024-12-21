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

# 5장. 응집도

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

### 메서드 체인

```java
/**
* 갑옷 입기
* @param memberId 장비를 변경하고 싶은 멤버의 ID
* @param newArmor 입을 갑옷
*/
void equipAmor(int memberId, Armor newAmor) {
   if(party.members[memberId].equipments.canChage) {
   	  party.members[memberId].equipments.armor = newAmor;
   }
}

```

- 이런 로직은 응집도를 낮춘다.
-  amor를 할당하는 코드는 어디에서나 있을 수 있기에 저런 메서드 체인 코드가 여러 곳에 중복될 가능성이 있다.

```java
class Equipmemts {
	private boolean canChange;
	private Equipment head;
	private Equipment armor;
	private Equipment arm;
    
	public Equipments() {
        this.canChange = true; // Default state allows changes
    }
	void equipAmor(final Equipment newArmor) {
       if(canChange) {
          armor = newArmor;
        }
    }
}

```

- 인스턴스 변수를 pricate로 변경해서 접근 할 수 없게 하고
- 인스턴스 변수에 대한 제어는 외부에서 메서드로 명령하는 형태로 만든다.
- 상세한 판단과 제어는 명령을 받는 쪽에서 담당한다.

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

# 7장. 컬렉션 : 중첩을 제거하는 구조화 테크닉

### 응집도가 낮은 컬렉션 이란

```java
// 필드 맵과 관련된 제어를 담당하는 클래스
class FieldManager {
    // 멤버를 추가 한다.
    void addMember(List<Member> members, Member newMember) {
        if (members.stream().antMatch(member -> member.id == newMember.id)) {
            throw new RumtimeException("이미 존재하는 멤버입니다.");
        }
        if (members.size() == MAX_MEMBER_COUNT) {
            throw new RumtimeException("이 이상 멤버를 추가할 수 없습니다.");
        }
        members.add(newMember);
    }
    // 파티 멤버가 1명이라도 존재하면 true를 리턴
    boolean partyIsAlive(List<Member> members) {
        return members.stream().anyMatch(member -> member.isAlive());
    }
}
```

- 필드맵 맵 말고도 멤버를 추가하는 시점이 생길 수 있는데, 이때 중복된 코드들이 생기게 됨.

  ```java
  class SpecialEventManager {
       void addMember(List<Member> members, Member newMember) {
       	members.add(newMember);
       }
  }
  ```

- 이처럼 컬렉션과 관련된 작업을 처리하는 코드가 여기저기에 구현된 가능성이 높아지며 응집도가 낮아진다.

### 일급 컬렉션

- 컬렉션과 관련된 로직을 캡슐화하는 디자인 패턴

- 일급 클래스에 있어야 할 것

  - 컬렉션 자료형의 인스턴스 변수

  - 인스턴스 변수에 잘못된 값이 할당되지 않게 막고, 정상적으로 조작하는 메서드

- 위의 잘못된 예시를 `List<member> -> Party` 개념으로 일급컬렉션으로 바꿔 설계해보자.
  ```java
  // 리스트 자료형의 인스턴스 변수를 갖는 클래스 
  class Party{
  private final List<Member> members;
  Party() {
  members = new ArrayList<Member>();
  }
  Party add(final Member newMember) {
  List<Member> adding = new ArrayList<>(members);
  members.add(newMember);
  return new Party(adding);
  }
  
      // Party 관련 검증, 확인 등의 로직... : isAlive(), isFull(), exists()
      
      List<Member> members() {
          return members.unmodifiableList(); // 외부에서 리스트를 조작하지 못하게 막는다.
      }
  }
  ```

### 외부로 컬렉션을 전달할 때 변경 막기

`List.unmodifiableList()` 함수 이용해서 컬렉션을 return했을 때, 해당 리턴 자료를 수정하지 못 하게 한다.

# 8장. 강한 결합

## 단일 책임 원칙

- 결합에 있어 제일 주요하게 인식해야 할 개념
- 단일 책임 원칙 기반으로 분리할 때 주의 할 점은, 책무를 생각하지 않고 로직의 중복을 제거한다고 일반화 하는 것을 주의 해야 함.
  - 예를 들어, 두 가격 개념 (여름 할인 가격, 정가)를 두 클래스로 나눴을 때, 할인율 빼곤 price를 산정하는 로직이 같더라도 함부로 일반화 할 수 없다.
  - 왜냐하면 정률제 할인을 도입하게 되면 관계를 다시 고쳐야 한다.
  - 코드 중복을 기준으로 생각하는 것이 아니라 비즈니스 개념 관점으로 생각하여 일반화해야 한다.



## 다양한 결합과 대처 방법

### 01. 상속은 강한 결합을 만드므로 컴포지션을 활용하라.

- “`super.method()` + 상속 받는 클래스의 개별 로직”은 슈퍼클래스의 변화를 주목하게 한다.
- 컴포지션 : private 인스턴스 변수로 갖고 사용하는 것.

  ![상속과컴포지션.png](https://raw.githubusercontent.com/S2uJeong/blogImages/main/images/상속과컴포지션.png)


```java
Class BasicClass extends SuperClass {
   @Override
   method() {
       super.method();
       print("hello BasicClass");
   }
}

class BasicClass {
    private final SuperClass superClass;
    
    method() {
	    superClass.method();
	    print("hello BasicClass");
    }
}
```

### 02. 인스턴스 변수별로 클래스 분별이 가능하게 하라

![클래스분리.png](https://raw.githubusercontent.com/S2uJeong/blogImages/main/images/클래스분리.png)

- before) 책무가 다른 여러 메서드가 포함되어 있는 클래스

    ```java
    class Util {
       private int reservationId;  // 상품예약 id
       private ViewSettings viewSettings;  // 화면 표시 설정
       
       void cancelReservation() {
    	   // reservationId를 사용한 예약 취소 처리
       }
       
       void darkMode() {
    		 // viewSettings를 사용한 다크 모드 표시 전환 처리 
       }
    }
    ```

- after) 책무 별로 클래스를 분리

    ```java
    class Reservation {
    	private final int reservationId;
    	void cancle() {
    	}
    }
    
    class ViewCustomizing {
    	private final ViewSettings viewSettings; 
    	void darkMode() {
    	}
    }
    ```


### 03. 클래스 public 사용 지양

- ‘패키지’ 개념을 잘 활용하자. 본디 패키지는 밀접한 클래스끼리 응집하게 설계하도록 유도 + default private
- public을 남발하면 영향 범위가 확대됨

### 04. private 메서드가 너무 많다는 것은 책임이 너무 많다는 것

- 책임이 다른 메서드는 다른 클래스로 분리

### 05. 높은 응집도를 오해하면 강한 결합이 생긴다.

- 높은 응집도란 관련이 깊은 데이터와 논리를 한 곳에 모은 구조이다.
- 정말 관련있는 ‘개념’끼리 모인건지 재점검해야 한다.
- before ) 관련이 깊지 않은 개념들이 한 클래스에 모여 있음.

    ```java
    
    class SellingPrice {
      ...
      // 판매 수수료 계산
      int calcSellingCommision() {
    	  return (int)(amount * SELLING_COMMISION_RATE);
      }
      // 배송비 계산
      int calcDeliveryCharge() {
    	  return DELIVERY_FREE_MIN <= amount ? 0 : 5000;
      }
      // 추가할 쇼핑 포인트 계산
      int calcShoppingPoint() {
        return (int)(amount * SHOPPING_POINT_RATE);
      }
    } 
    ```

- after) 관련 있는 개념끼리 분리한 클래스

    ```java
    class SellingCommision {
      private static final float SELLING_COMMISION_RATE = 0.05f;
      final int amount;
      
      SellingCommision(final SellingPrice price) {
    		  amount = (int)(price.amount * SELLING_COMMISION_RATE);
      }
    }
    
    class DeliveryCharge {
      private static final int DELIVERY_FREE_MIN = 20000;
      final int amount;
      
      DeliveryCharge(final SellingPrice price) {
    		  amount =  DELIVERY_FREE_MIN <= price.amount ? 0 : 5000;
      }
    }
    
    class ShoppingPoint {
      private static final floatSHOPPING_POINT_RATE= 0.01f;
      final int value;
      
      ShoppingPoint (final SellingPrice price) {
    		  value = (int)(price.amount * SHOPPING_POINT_RATE);
      }
    }
    ```


### 06. 거대 데이터 클래스

- 클래스를 “데이터를 편리하게 운반하는 역할”로 인식하면 안된다.
- 이러면 수많은 유스케이스에서 사용하게 된다.
- 각각의 유스케이스에서 필요한 데이터만 접근하고 사용하게 구성해라.


# 9장. 설계의 건전성을 해치는 여러 악마

### 전역 변수 개념을 띄는 것을 주의한다.

- 데이터 클래스 or `public static`
- 어떤 시점에 어디에서 값을 변경했는지 알기 어려워 진다. 동기화 문제도 있다.

### null이 아니게 null을 표현하자.

- null을 리턴하거나 전달하지 말자.
- 예시) ‘장비하지 않음’을 null이 아니게 표현

    ```java
    class Equipment {
        static final Equipment EMPTY = new Equipment("장비 없음", 0,0,0);
        
        final String name;
        final int Price;
        final int defence;
        final int magicDefence;
    
        Equipment(String name, int price, int defence, int magicDefence) {
            if (name.isEmpty()) {
                throw new IllegalStateException("잘못된 이름입니다.");
            }
            this.name = name;
            Price = price;
            this.defence = defence;
            this.magicDefence = magicDefence;
        }
    }
    
     // 활용 방법 : 모든 장비 해제
    void takeOffAllEquipments() {
    		head = Equipment.EMPTY;
    		body = Equipment.EMPTY;
    		arm = Equipment.EMPTY; 
    }
    ```


### 설계 질서를 파괴하는 메타 프로그래밍

- 메타 프로그래밍 : 프로그램 실행 중에 해당 프로그램 구조 자체를 제어하는 프로그래밍
- 자바에서 메타프로그래밍을 활용해 클래스 구조를 읽고 쓸 때는 리플렉션 API를 사용한다.
- 리플랙션을 남용하면 ‘잘못된 상태로부터 클래스를 보호하는 설계’와 ‘영향 범위를 최대한 좁게 만드는 설계’가 아무런 의미를 갖지 못하게 된다.

    ```java
    public class Level {
        private static final int MIN = 1;
        private static final int MAX = 99;
        final int value;
    
        public Level(int value) {
            if (value < MIN || MAX < value) {
                throw new IllegalArgumentException();
            }
            this.value = value;
        }
    
        // 초기 레벨 리턴
        static Level initialize() {
            return new Level(MIN);
          }
    }
    
    /**
     * Level 클래스는 설계 규칙을 잘 만들어 작성했지만,
     * 실행할 때 리플렉션을 사용해서 규칙이 다 깨져버렸다.
     *
     * 객체 필드 검증이 적용 안되고 있음
     */
    public class Main {
        public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
            Level level = Level.initialize();
            System.out.println(level.value);
    
            Field field = Level.class.getDeclaredField("value");
            field.setAccessible(true);
            field.setInt(level, 999); // MAX가 99인데,,
            System.out.println(level.value);
        }
    }
    ```


- 자료형의 장점을 살리지 못하는 하드코딩로 오류를 야기한다.
  - 필드명을 변경하면 `class.getDeclaredField("value")` 을 사용한 것이 문제가 된다.
  - 보통 필드명 변경 시, IDE 도구가 사용된 모든 곳에 자료형을 기준으로 자동으로 같이 바꿔준다.
  - 하지만 위의 코드는 단순 `string` 으로 value를 찾았기 때문에 함께 바뀌지 않는다.

### 기술 중심 패키징 지양

- MVC를 예시로 하자면, `models, views, controllers` 로 패키징하는 것이 기술 중심 패키징
- 비즈니스 개념을 기준으로 폴더화 하자

    ```java
    - 재고
    		- 재고_유스케이스.java
    		- 발주_엔티티.java
    		- 입고_엔티티.java
    - 주문 
    		- 주문_유스케이스
    		- 장바구니_엔티티.java
    ```


# 10장. 이름 설계 : 구조를 파악할 수 있는

- 비즈니스 목적 중심 이름 설계를 해야 한다.
- 관심사의 분리를 생각하고, 비즈니스 목적에 맞게 이름을 붙이는 것은
- 결합이 느슨하고 응집도가 높은 구조를 만드는데 중요한 역할을 한다.
- 상품 →  예약/주문/재고/발송 + “상품”

### 목적 중심 이름 설계하기

- 최대한 구체적이고, 의미 범위가 좁고, 특화된 이름 선택
- 존재가 아니라 목적을 기반으로 하는 이름 생각

    | 존재 기반 | 목적 기반 |
    | --- | --- |
    | 주소  | 발송지, 배송지, 업무지 |
    | 금액 | 청구 금액, 소비세액, 연체 보증료, 캠페인 할인 금액 |
- 어떤 관심사가 있는지 분석
- 소리 내어 이야기해 보기
- 이용 약관 읽어 보기
- 다른 이름으로 대체할 수 없는지 검토
- 결합이 느슨하고 응집도가 높은 구조인지 검토

### 이름 설계 시 주의 사항

- 이름의 차이를 설명 할 때 수식어를 붙이게 되면,
- 이름을 다르게 지어 구분하기 보단 클래스로 만들어서 설계해보자.

# 11장. 주석

- 코드를 설명하는 주석을 쓰지 말자 → 주석을 유지 보수하기 어려워진다.
- 주석을 달게 되면 이름을 대충 짓게 되어도 설명이 가능하게 된다고 생각하여 이름 설계를 소홀히 한다.

### 주석을 작성해야 할 때

- [code](https://github.com/S2uJeong/Note/blob/8196cfea2484b1da6ac400ef3a86f7d812dd001c/src/bookStrangeCode/goodComment)
- 의도와 사양 변경 시 주의 사항 작성

    ```java
    class Member {
        private final States states;
    
        Member(States states) {
            this.states = states;
        }
    
        // 고통받는 상태일 때 true를 리턴
        boolean isPainful() {
            // 이후 사양 변경으로 표정 변화를
            // 일으키는 상태를 추가할 경우
            // 이 메서드에 로직을 추가한다.
            if (states.contains(StateType.POISON) ||
                    states.contains(StateType.PARALYZED) ||
                    states.contains(StateType.FEAR)) {
                return true;
            }
            return false;
        }
    }
    ```

- 문서화를 대비한 메서드 단위 주석

    ```java
    /**
    * 상태 리스트에 상태를 추가한다.
    * @param state 캐릭터 상태
    * @throws IllegalArgumentException 상태에 null이 들어오면 발생
    */
    public void addState(State state) {
      if (state == null) throw new IllegalArgumentException("상태에 올바르지 않은 값이 들어왔습니다.");
      stateSet.add(state);
    }
    ```
    ![주석활용.png](https://raw.githubusercontent.com/S2uJeong/blogImages/main/images/image-20241221174012829.png)


# 12장. 메서드 : 좋은 클래스에는 좋은 메서드가 있다.

### 반드시 현재 클래스의 인스턴스 변수 사용하기

- 인스턴스 변수를 안전하게 조작하도록 메서드를 설계하면, 클래스 내부가 정상적인 상태인지 보장할 수 있다.

- 다른 클래스의 인스턴스 변수 사용하지 않기 -> 응집도 낮아짐

- 다른 클래스의 인스턴스 변수를 변경하는 메서드를 작성하고 싶다면, 변경된 내용을 다루는 새로운 인스턴스를 생성하고 이를 리턴하는 형태로 구현한다.

- 예시

  - `Person` 클래스의 `withUpdatedAge` 메서드는 기존 객체를 수정하지 않고, 변경된 나이를 반영한 새로운 객체를 반환

    `Team` 클래스의 `withNewLeader` 메서드는 기존 객체를 수정하지 않고, 새로운 리더를 반영한 새로운 팀 객체를 반환

  - `Team` 클래스는 `Person` 클래스의 필드에 직접 접근하지 않고, `Person` 객체 전체를 관리.
  - 변경된 상태를 다루는 새로운 인스턴스를 생성하고 반환하여 기존 객체를 안전하게 유지

  ```java
  class Person {
      private final String name;  // 불변 인스턴스 변수
      private final int age;      // 불변 인스턴스 변수
      // 생성자
      public Person(String name, int age) {
          if (name == null || name.isBlank() || age < 0) {
              throw new IllegalArgumentException("Invalid input values");
          }
          this.name = name;
          this.age = age;
      }
  
      // 인스턴스 변수를 안전하게 조작하는 메서드
      public Person withUpdatedAge(int newAge) {
          if (newAge < 0) {
              throw new IllegalArgumentException("Age must be non-negative");
          }
          // 새로운 인스턴스를 생성하여 변경된 내용을 반영
          return new Person(this.name, newAge);
      }
  }
  
  class Team {
      private final String teamName;
      private final Person leader;
      // 생성자 생략..
      // 인스턴스 변수 변경 -> 새로운 인스턴스를 생성하고 반환
      // `Person` 클래스의 필드에 직접 접근하지 않고, `Person` 객체 전체를 관리.
      public Team withNewLeader(Person newLeader) {
          if (newLeader == null) {
              throw new IllegalArgumentException("Leader cannot be null");
          }
          return new Team(this.teamName, newLeader);
      }
  }
  ```



### 불변을 활용해서 예상할 수 있는 메서드 만들기

### 묻지 말고 명령하라

- 메서드 체인같이 줄줄히 호출하는 것을 지양하라는 것
- 상세한 로직은 호출하는 쪽이 아니라, 호출되는 쪽에 구현

### 커맨드/쿼리 분리

### 매개변수 관련 규칙

- 불변 매개변수로 만들기
- 플래그 매개변수 사용하지 않기
- null로 전달하지 않기 : null에 의미 부여 하지 않기
- 출력 매개변수로 사용하지 않기
- 매개변수는 최대한 적게, 많아진다면 하나의 클래스로 만드는 방법 검토

### 리턴 값

- 자료형을 통해 리턴 값의 의도 나타내기
- null 리턴하지 않기



# 13장. 모델링 : 클래스 설계의 토대

- 모델 : 동작 원리와 구조를 간단하게 설명하기 위해, 사물의 특징과 관계를 그림으로 나타낸 것
- 모델링 : 모델을 만드는 활동

### 목적별로 모델링하기

- User가 아닌 사용자, 재고 담당자 등의 세분화
- User, Product와 같은 이름은 물리적인 세부 사항은 무시하고 개념적인 측면만 투영한 모델
- 이 처럼 정보시스템에서는 `현실 세계에 있는 물리적인 존재`와 `정보 시스템에 있는 모델`이 1:N로 대응되는 경우가 많다.
- 모델은 대상이 아니라 목적 달성의 수단

### 모델 설계 과정

- 해당 모델이 달성하려는 목적 모두 찾기
- 목적별로 모델링을 다시 수정
- 목적 중심 이름 설계를 기반으로 모델에 이름을 붙인다
- 모델에 목적 이외의 요소가 들어가 있다면 다시 수정

### 모델과 구현

- 모델은 구조를 단순화 한 것이므로, 모델을 기반으로 클래스를 설계하며 수정해 나가야 한다.
- 모델 != 클래스 , 모델 하나는 여러 개의 클래스로 구현된다.



# 14장. 리팩터링

### 과정을 순서대로 보며 감 익혀보자

1. if 중첩을 제거 : 조기 retutn 사용

   ```java
   PurchasePointPayment(final Customer customer, final Comic comic) {
       if (!customer.isEnabled()) {
           throw new IllgalArgumentException("유효하지 않은 계정입니다.")
       }
       customerId = customer.id;
       if (!comic.isEnabled()) {
           throw new IllgalArgumentException("유효하지 않은 계정입니다.")
       }
       comicId = comic.id;
       
       consumptionPoint = comic.currentPurchasePoint;
       paymentDateTime = LocalDateTime.now();
   }
   ```



2. 의미 단위로 로직 정리

  - 조건으로 검증하는 부분과 대입하는 부분으로 나눠서 정리

   ```java
   PurchasePointPayment(final Customer customer, final Comic comic) {
       if (!customer.isEnabled()) {
           throw new IllgalArgumentException("유효하지 않은 계정입니다.")
       } 
       if (!comic.isEnabled()) {
           throw new IllgalArgumentException("유효하지 않은 계정입니다.")
       }
       
       customerId = customer.id;
       comicId = comic.id;
       consumptionPoint = comic.currentPurchasePoint;
       paymentDateTime = LocalDateTime.now();
   }
   ```

3. 조건을 읽기 쉽게 하기

  - `!customer.isEnabled()` 와 같은 논리 부정 연산자를 사용하면 가독성이 떨어진다.
  - 대신 `isDisabled()` 를 사용한다.

4. 의미 없이 쓴 로직을 메서드로 선언하여 사용

### 테스트 코드를  사용한  리팩터링 흐름

1. 이상적인 구조의 클래스 기본 형태를 어느 정도 잡는다
2. 이 기본 형태를 기반으로 테스트 코드를 작성한다.
3. 테스트를 실패시킨다.
4. 테스트를 성공시키기 위해 최소한의 코드를 작성한다
5. 기본 형태의 클래스 내부에서 리팩터링 대상 코드를 호출한다.
6. 테스트가 성공할 수 있도록, 조금씩 로직을 이상적인 구조로 리팩터링

### 불확실한 사양을 이해하기 위한 분석 방법

- 문서화 테스트

  - 메서드에 Test를 통해 입력 값의 변화에 따라 어떤 리턴이 나오는지 일일이 확인.

- 스크래칭 리팩터링

  - 로직의 의미와 구조를 분석하기 위해 시험 삼아 리팩터링 하는 것
  - 과정
    1. 대상 코드를 리포지터리에서 체크아웃 한다.
    2. 코드를 리팩터링 한다.
    3. 분석한다.

  - 코드를 리팩터링 하는 이유는
    - 코드의 가독성이 좋아져 로직의 사양을 이해할 수 있게 된다.
    - 이상적인 구조가 보인다. 어느 범위를 메서드 또는 클래스로 끊어야 좋을지 보인다.
    - 즉, 리팩터링의 목표가 조금씩 보인다
    - 쓸데없는 코드(데드 코드)가 보인다.
    - 테스트 코드를 어떻게 작성해야 할 지 보인다.



### IDE의 리팩터링 기능

- 이름 변경
- 메서드 추출

### 리팩터링 시 주의 사항

- 기능 추가와 리팩터링 동시에 하지 않기
  - 이후에 버그가 발생했을 때,
  - 기능 추가로 버그가 발생한 것인지, 리팩토링으로 버그가 발생한 것인지 분석하기 힘들어진다.
- 작은 단계로 실시하기
  - 커밋은 어떻게 리팩터링했는지 차이를 알 수 있는 단위로 한다.
  - 예를 들어 메서드 이름 변경과 로직 이동을 했다면, 커밋을 따로따로 구분한다.
- 불필요한 사양은 제거 고려하기
  - 코드에 버그가 있거나 다른 사양과 모순되는 점이 있다면 리팩터링 해도 바로잡기 힘들다
  - 따라서 리팩터링 전에 불필요한 사양이 있는지, 사양을 다시 확인하는 것도 좋다.
  - 불필요한 사양과 코드를 미리 제거할 수 있다면, 더 쾌적하게 리팩터링 할 수 있다.

# 15장. 설계의 의의와 설계를 대하는 방법

- 소프트웨어 제품 품질 특성

  - 기능 적합성 : 기능이 니즈를 만족하는 정도
  - 성능 효율성 : 리소스 효율과 성능 정도
  - 호환성 : 다른 시스템과 정보 공유, 교환할 수 있는 정도
  - 사용성 : 사용자가 시스템을 만족하며 사용하는지 나타내는 정도
  - 신뢰성 : 필요할 때 기능을 실행

  | 품질 큭성   | 설명                                                         | 품질 관련 부가적인 특성                                |
    | ----------- | ------------------------------------------------------------ | ------------------------------------------------------ |
  | 기능 적합성 | 기능이 니즈를 만족하는 정도                                  | 기능 무결성, 기능 정확성, 기능 적절성                  |
  | 성능 효율성 | 리소스 효율과 성능 정도                                      | 시간 효율성, 자원 효율성, 용량 만족성                  |
  | 호환성      | 다른 시스템과 정보 공유, 교환할 수 있는 정도                 | 공존성, 상호 운용성                                    |
  | 사용성      | 사용자가 시스템을 만족하며 사용하는지 나타내는 정도          | 적절도 인식성, 습득성, 운용 조작성, 사용자 오류 방지성 |
  | 신뢰성      | 필요할 때 기능을 실행할 수 있는 정도                         | 성숙성, 가용성, 장애 허용성, 회복성                    |
  | 보안        | 허용되지 않은 사용으로부터 보호할 수 있는 정도               | 기밀성, 무결성, 부인 방지성, 책임 추적성, 인증성       |
  | 유지 보수성 | 시스템이 정상 운용되도록 유지 보수하기가 얼마나 쉬운가를 나타내는 정도 | 모듈성, 재사용성, 분석성, 수정성, 시험성               |
  | 이식성      | 다른 실행 환경에 이식할 수 있는 정도                         | 적응성, 설치성, 치환성                                 |

## 코드의 좋고 나쁨을 판단하는 지표

### 코드 메트릭

- 실행되는 코드의 줄 수

  - 주석을 제외하고, 실행되는 로직을 포함하는 코드의 줄 수
  - 수가 많을 수록, 너무 많은 일을 하고 있는 가능성이 높다.

  | 스코프 | 줄 수 상한 |
    | ------ | ---------- |
  | 메서드 | 10줄 이내  |
  | 클래스 | 100줄 이내 |

  - 메서드와 클래스 분할을 통해 해결

- 순환 복잡도

  - 코드의 구조적인 복잡함을 나타내는 지표
  - 조건 분기, 반복 처리, 중첩이 많아지면 커진다.
  - 조기 리턴, 전략 패턴, 일급 컬렉션 등으로 줄이지
  - 이상적인 복잡도는 10 ~ 15

- 응집도

  - LCOM 도구가 있음

- 결합도

  - 분석 도구나
  - 호출하는 클래스 수를 세거나, 클래스 다이어그램을 그려보며 파악
  - 단일 책임 원칙, 클래스 분할 검토

- 청크

  - 클래스에서 다루는 개념이 4+-1 정도가 되도록 설계
  - 커지면 분할

### 코드 분석을 지원하는 다양한 도구

- Code Climate Quality
  - 깃허브와 연동해서 레포에 저장된 코드의 품질 점수를 자동으로 계산
  - 자체 계산식을 사용해 기술 부채 산출
  - 부채의 증감을 시각화해줌
  - 변경 빈도가 높은 곳을 찾을 수 있어 중요도가 높은 서비스를 찾기 쉬움
- Understand
- Visual Studio
- 구문 강조를 통해 품질 시각화에 활용하기

### 설계 대상과 비용 대비 효과

- 비용 대비 효과가 높은 부분을 노려 리팩토링 하자!
- 파레토의 법칙 (80:20 법칙)
  - 매출의 80%는 전체 상품 중 20%가 차지한다.
  - 기능의 중요성, 사양 변경 빈도에 주목해 그 부분을 중점으로 리팩초링
- 코어 도메인 : 서비스의 중심 영역
  - 시스템에서 가장 큰 가치를 창출하는 곳
  - 경쟁 우위에 있고, 차별점을 만들며, 비즈니스 우위를 만들 수 있는 곳



# 16장. 설계 공부 계속하기

### 코딩 규칙 사용

- 언어별 코딩 규칙
  - [java](https://google.github.io/styleguide/javaguide.html)

### 추천 도서

- 현장에서 유용한 시스템 설계 원칙
  - 변경이 쉬운 설계에 대해 비즈니스 개념을 중심으로 설명
- 읽기 좋은 코드가 좋은 코드다
- 리팩터링 2판 + 클린코드
- 레거시 코드 활용 전략
  - 사양을 알 수 없고, 테스트도 없는 코드를 분석해서 리팩토링 하는 전략
- 레거시 소프트웨어 리엔지니어링
  - 계획과 조직에 초점을 맞춘 책
- 레거시 코드를 넘어서
  - 애자일 개발 방법을 중심으로 팀 운영 방법 설명
- 프로그래밍의 원칙
  - SOLID 원칙을 기반으로 설계 개선
- 클린 아키텍처
- 도메인 주도 설계 철저 입문 -> 도메인 주도 설계 + 설계를 통한 보안
- 도메인 주도 설계 모델링/구현 가이드
  - Q&A가 좋아서, 설계에 어려운을 겪을 때 읽어보면 좋음

### 설계 스킬을 높이는 학습 방법

- 인풋은 2 , 아웃풋은 8

  - 코드에 바로 적용해보며 시행 착오를 경험해 봐야 한다

- 설계 효과 반드시 검증

  - 설계 전후에 설계 효과를 확인해야 한다.
  - 설계 적용 후에 해당 효과가 제대로 발생헀는지 확인

  - 어설프게 배운 디자인 패턴을 적용해 코드가 복잡해 질 수 있다.

- 리팩토링 대상 쉬운것 부터 찾기

  - public, 줄 수 많은 것은 난이도 높음
  - private, 줄 수 적은것 위주로 먼저 시작 