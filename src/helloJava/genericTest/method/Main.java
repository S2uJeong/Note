package helloJava.genericTest.method;

public class Main {
    public static <T> Box<T> boxing(T t) {
        Box<T> box = new Box<>();
        box.setT(t);
        return box;
    }

    public static void main(String[] args) {
        Box<Integer> intBox = boxing(100);  // 타입 파라미터 T는 매개값이 어떤 타입이냐에 따라 컴파일 과정에서 구체적인 타입으로 대체
        System.out.println(intBox.getT());

        Box<String> strBox = boxing("100");
        System.out.println(strBox.getT());
    }
}
