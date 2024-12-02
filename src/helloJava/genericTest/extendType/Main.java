package helloJava.genericTest.extendType;

public class Main {
    public static <T extends Number> boolean compare(T t1, T t2) {
        // 입력된 T의 구체적 타입 출력
        System.out.println(t1.getClass().getSimpleName() + " is compared to " + t2.getClass().getSimpleName());

        // Number 메서드 사용
        double v1 = t1.doubleValue();
        double v2 = t2.doubleValue();

        return (v1 == v2);
    }

    public static void main(String[] args) {
        boolean result1 = compare(10, 20);
        System.out.println(result1);

        boolean result2 = compare(4.5, 4.50f);
        System.out.println(result2);
    }
}
