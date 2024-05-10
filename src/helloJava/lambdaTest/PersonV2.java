package helloJava.lambdaTest;

public class PersonV2 {
    public void ordering(Comparable comparable) {
        String a = "홍길동";
        String b = "김길동";

        int result = comparable.compare(a,b);

        System.out.println(result);
    }
}
