package helloJava.genericTest.basic;

public class Main {
    public static void main(String[] args) {
        Box<String, String> strBox = new Box<>("basic", "Hello, generic");
        strBox.print();
    }
}
