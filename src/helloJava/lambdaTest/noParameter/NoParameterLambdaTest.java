package helloJava.lambdaTest.noParameter;

public class NoParameterLambdaTest {
    public static void main(String[] args) {
        Person person = new Person();

        // 실행문이 두개 이상인 경우 중괄호 필수
        person.action( () -> {
            System.out.println("나는");
            System.out.println("출근을 한다!");
        });

        // 실행문이 하나면 중괄호 생략 가능
        person.action( () -> System.out.println("ㅋㅋ퇴근 뿡삥룽"));
    }
}
