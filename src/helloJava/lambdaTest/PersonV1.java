package helloJava.lambdaTest;

public class PersonV1 {
    public void action(CalculableV1 calculable) {
        double result = calculable.calc(10,4);
        System.out.println("결과 : " + result);
    }
}
