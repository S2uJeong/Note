https://github.com/S2uJeong/java-basic

## 클래스 
- 클래스를 만든다는 것은 데이터 타입을 정의할 수 있는 것이다.

### 클래스가 필요한 이유
- 첫번째 버전
```java
String student1Name = "학생1"; 
int student1Age = 15;
int student1Grade = 90;

String student2Name = "학생2";
int student2Age = 16;
int student2Grade = 80;
```
- 두번째 버전 
```java
String[] studentNames = {"학생1", "학생2"}; 
int[] studentAges = {15, 16};
int[] studentGrades = {90, 80};
```
- 클래스 적용
```java
 public class Student {
     String name;
    int age;
    int grade; 
}

void oppTest {
    public static void main(String[] args) {
        Student student1 = new Student(); 
        student1.name = "학생1"; 
        student1.age = 15; 
        student1.grade = 90;
        
        Student student2 = new Student(); 
        student2.name = "학생2";
        student2.age = 16;
        student2.grade = 80;
        }
```
- 
### 변수 
- 멤버 변수 
  - 클래스의 필드
  - 생성 시 자동으로 초기화 
- 지역 변수 
  - 함수 안이나 특정 범위 내에서 사용되는 변수
  - 직접 초기화 해주어야 한다. 

## 캡슐화
- 객체의 데이터와 기능을 사용할 때, 해당 객체를 이용하는 모듈 입장에서는 해당 객체 내에 어떤 속성이 있는지 몰라도 사용할 수 있어야 한다.
- 속성과 기능이 마치 하나의 캡슐에 쌓여있는 것 같다 하여 캡슐화이다.
- 캡슐화의 장점은 유지보수 범위 또한 좁힌다는 것이다.

## 생성자
- 반환값이 없고 클래스명과 똑같은 메서드로 생성
- 객체 생성 시, 초기화 하는 코드의 중복을 줄일 수 있고 호출이 강제되어 있는 제약을 통해 버그를 줄일 수 있다. 
- 개발자가 생성자를 만들지 않으면 기본 생성자가 제공된다. 
### this
- 멤버변수와 매개변수의 이름이 같으면 매개변수가 우선 순위를 가진다.
- 따라서, 기본 우선순위를 무시하고 멤버변수에 바로 접근하고 싶다면 이 this 지시어를 쓴다.
```java
public class Member {
  String name;
  int age;

  void initMember(String name, int age) {
    name = name;
    age = age;
  }  // member.name, age => null, 0 

  void initThisMemner(String name, int age) {
    this.name = name;
    this.age = age;
  } 
}
```

## 자바 메모리 구조
- 메서드 영역 : class, static, 상수 
- 스택 영역 : method, 지역 변수 
- 힙 영역 : instance

- 따라서, 인스턴스를 생성하면 힙 메모리를 사용하지만, 각 인스턴스가 해당하는 클래스의 메서드를 실행하게 되면,
- 메서드영역의 코드를 불러서 실행한다.

### 변수의 생명주기
- 지역 변수, 매개 변수 : 스택 영영의 스택 프레임 안에 보관. 메서드가 종료되면 스택 프레임은 제거
- 멤버 변수 
  - 인스턴스 변수 : 힙 영역, GC발생 전까지 생존
  - 클래스 변수 : 메서드 영역, JVM이 로딩 되는 순간 생성되고 JVM이 종료될 때 까지 생명주기가 이어진다.

### static 메서드
- static 메서드는 내부 기능을 작성할 때, static 변수/메서드 만 사용할 수 있다.
- 매개변수를 통해 객체의 참조값을 전달하면 정적 메서드 안에서 객체의 변수나 메서드를 사용할 수 있다. 
- src/helloJava/tmp/StaicTest.java

## 상속

### 상속과 메모리 구조
- 자식 클래스 생성 시, 메모리에는 부모 클래스와 자식 클래스가 모두 생성 되며 공간은 구분되어 있다 .
  - 따라서, 상속 관계를 사용하면 자식 클래스의 생성자에서 부모 클래스의 생성자를 반드시 호출해야 한다. 
- 인스턴스 생성 후, 호출하는 변수의 타입을 기준으로 부모/자식 메서드 중 선택하여 실행한다. 
  1. 호출하는 변수 타입에 탐색
  2. 부모 타입 탐색

### 오버라이딩
- 부모에게 상속받는 메서드를 자식 고유의 기능으로 업그레이드 하고 싶을 때 사용.
- 호출하는 변수의 타입에 따라 부모/자식 메서드 중 어떤걸 실행할지 정해지므로, 호출 변수 타입 상관없이
- 자식에서 실행되는 오버라이딩 메서드를 무조건 실행하고 싶다면 자바에서 지정한 오버라이딩 방법을 지켜서 메서드를 작성해야 한다. 
  - @Override
  - 메서드 이름이 같고
  - 메서드 매개변수가 같고 
  - 반환 타입이 같거나 하위클래스 타입이여야 한다. 
- 오버라이딩이 되어 있는 상황에서, 같은 필드명과 같은 메서드명인 부모 클래스의 기능을 사용하고 싶을 떈 super 명령어를 사용한다. 

## 다형성
- 다형적 참조 : 부모는 자식을 담을 수 있다. 자식은 생성 시 부모까지 생성한다. 
- 메서드 오버라이딩