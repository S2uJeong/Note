package helloJava.lambdaTest;

public class ReturnedLambdaTest {
    public static void main(String[] args) {
        PersonV1 person = new PersonV1();

        person.action((x,y) -> {
            double result = x - y;
            return result;
        });

        person.action((x,y) -> (x * y));

        person.action((x,y) -> sum(x,y));
    }
    public static double sum(double x, double y) {
        return (x+y);
    }
}
