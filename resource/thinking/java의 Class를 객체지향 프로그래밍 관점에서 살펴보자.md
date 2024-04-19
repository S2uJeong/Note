# 인사
- 안녕하세요. 이번 포스트는 Java의 상속관계와 오버라이드 기능을 확실히 이해하기 위해 메모리 구조와 함께 살펴보겠습니다.
- 해당 내용은 인프런에서 [JAVA기본 - 김영한] 강의를 보고 정리된 내용이 포함되어 있습니다.
# 기본 내용
1. class의 기능
   1-1. 데이터 타입 정의 기능
- 우선 Java언어는 Class라는 설계도 같은 개념을 통해 데이터를 하나의 객체로 개념화할 수 있습니다.
- 원시 타입인 int, boolean 형식이 존재 하듯 개발자가 직접 이런 데이터 타입을 정의할 수 있는것입니다.
```java
class Member {
    String name;
    int age;

    Member(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

public class Test {
    public static void main(String[] args) {
        int value = 10; // 원시타입
        Member member = new Member("수정", 26);  // 원시타입처럼 Member라는 타입을 만들어 사용
    }
}
```
1-2.
- 해당 데이터를 이용하는 기능(메서드)을 데이터와 함께 이 Class라는 개념으로 묶어 코드를 이해하기 쉽고 유지보수 범위를 줄이는데 성공했습니다.
-

# 내가 헷갈렸던 내용
## Static이 아닌 class안에 static 변수를 선언할 수 있는 이유
- static은 JVM이 구동될 때 메모리에 올라 가 있으며
- 클래스가 로드될 때, 메모리에는 클래스 정보가 올라간다.
- 따라서, Class.변수명 식으로 다른 클래스에서 인스턴스 생성 없이 호출 하여도 Class를 적는 순간 class 정보는 올라가고 static은 이미 메모리에 올라가 있었으므로 문제가 없다.
```java
   class Static {
    static int b = 10;
    public int a = 20;
   }
   
   public class Test {
       public static void main(String[] args) {
           int a = 10;
           Static.b = a;
       }
   }
```
### 이를 확실히 기억하기 위해 개념적으로 정리한 것 + code

# 결론적으로, 이것을 기억해야 한다!

