package helloJava.variable;

public class AutoChangeVariable {
    public static void main(String[] args) {
        // 변수를 사용하지 않고 정수 연산을 하면 byte로 저장 가능하다.
        byte byteResult = 10 + 20 ;
        System.out.println("10 + 20 결과 타입 : " +((Object)byteResult).getClass());

        // 변수를 사용한 byte 끼리의 연산은 byte로 담을 수 없고 int로 자동 변환 된다.
        byte a = 10;
        byte b = 20;
        System.out.println("byte + byte 결과 타입 : " + ((Object)(a+b)).getClass());
        // byteResult = a+b; 불가

    }
}
