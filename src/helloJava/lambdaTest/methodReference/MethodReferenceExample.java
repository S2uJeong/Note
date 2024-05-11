package helloJava.lambdaTest.methodReference;

/**
 * String의 인스턴스 메소드인 comparToIgnoreCase를 메서드 참조로 사용해서
 * a.comparToIgnoreCase(b)로 호출했을 때 사전순으로 a가 b보다 먼저 오면 음수를, 동일하면 0을 나중에 오면 양수를 리턴한다.
 */
public class MethodReferenceExample {
    public static void main(String[] args) {
        PersonV2 person = new PersonV2();

        person.ordering(String::compareToIgnoreCase);  // (a,b) -> a.compareToIgnoreCase(b);
    }
}
