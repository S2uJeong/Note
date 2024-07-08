package helloJava.tmp;
public class StaicTest {

    private static int staticVar = 3;

    public static void staticMethod() {
        System.out.println("static_method() 실행, Static Var : " + staticVar);
        anotherStaticMethod();
    }

    private static void anotherStaticMethod() {
        System.out.println("This is another static method.");
    }

    // Static 메서드 내에서 객체의 변수 및 메서드를 사용하는 예제 - 객체의 참조값을 매개변수로 받아서 해당 객체의 변수와 메서드를 사용
    public static void useInstanceMethod(StaicTest obj) {
        System.out.println("Instance Var" + obj.instanceVar);
        obj.instanceMethod();
    }
    private int instanceVar;

    public StaicTest(int instanceVar) {
        this.instanceVar = instanceVar;
    }
    private void instanceMethod() {
        System.out.println("This is an instance method");
    }

    public static void main(String[] args) {
        staticMethod();
        // instanceMethod(); -- 불가
        StaicTest st = new StaicTest(20);
        System.out.println(st.instanceVar);
        useInstanceMethod(st);

    }
}
