package javaPractice.overrideTest;

public class Child extends Parent{
    void method(){
        System.out.println("자식 메서드 실행");
    }

    void childMethod() {
        System.out.println("이건 자식한테만 있는 메서드");
    }
}
