## 인사
안녕하세요. 
오늘은 Java의 기초중에 기초!! Class에 대해 객체지향 프로그래밍 관점에서 살펴보도록 하겠습니다. 

### 1. 데이터 타입 정의 기능
우선 Java언어는 Class라는 설계도 같은 개념을 통해 데이터를 하나의 객체로 개념화할 수 있습니다.
원시 타입인 int, boolean 형식이 존재 하듯 개발자가 직접 이런 데이터 타입을 정의할 수 있는것입니다.

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
### 데이터와 관련 메서드의 응집화 
class를 통해 데이터 타입을 직접 정의를 하면 **필요한 데이터와 해당 데이터를 이용하는 기능(메서드)을 함께 묶을 수** 있게 됩니다.
이를 통해 코드를 더 이해하기 쉽게하고 유지보수 범위를 줄일 수 있습니다.

응집화 사용 전 : 매번 데이터를 직접 초기화 하고 (생성자 안씀), 해당 데이터를 사용해야 하는 기능을 매번 구현
```java
public class Student {
    // 오직 데이터만 사용한 사용자 정의 타입(클래스)
    String name;
    int age;
    int grade;
}

public class ClassMethodNoUseExample {
    public static void main(String[] args) {
        Student student1;
        student1 = new Student();
        student1.name = "최수정";
        student1.age = 26;
        student1.grade =100;

        Student student2 = new Student();
        student2.name = "최모씨";
        student2.age = 29;
        student2.grade = 90;

        Student[] students = {student1, student2};

        for (int i =0; i<students.length; i++) {
            Student s = students[i];
            System.out.println(s.name + ' ' + s.age + ' ' + s.grade);
        }
        // 향상된 for 문 , 단축키 : iter
        for (Student s : students) {
            System.out.println(s.name + ' ' + s.age + ' ' + s.grade);
        }

        }

    }
```
응집화 사용 후 
```java
public class Student {

    String name;
    int age;
    int grade;

    public Student(String name, int age, int grade) {
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    void print() {
        System.out.println("이름: " + this.name + " 나이: " + this.age + " 점수: " + this.grade);
    }
}

public class ClassMethodUseExample {
    public static void main(String[] args) {
        Student student1 = new Student("user1", 19, 80);
        Student student2 = new Student("user2", 33, 100);

        student1.print();
        student2.print();
    }
}

```

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



## 결론적으로, 이것을 기억해야 한다! (두줄 정리)

